package com.cisco.wwpo.csv2w9;

import com.cisco.wwpo.csv2w9.helper.ExcelCell;
import com.cisco.wwpo.csv2w9.helper.ExcelColumn;
import com.google.common.io.Files;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by maustino on 5/21/2015.
 */
@Log4j
public class Excel2W9Form {

    CSV2W9Service csv2W9Service = new CSV2W9Service();

    public void generateW9sFromExcel(String w9XlsFile, String w9DestinationFolder) throws Exception {


        @Cleanup InputStream inputStr = new FileInputStream(w9XlsFile);

        Workbook workbook = null;

        if (w9XlsFile.toLowerCase().endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStr);
        } else if (w9XlsFile.toLowerCase().endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStr);
        } else {

            throw new Exception("Not supported file type for '" + w9XlsFile.toLowerCase() + "'");
        }


        //Get the nth sheet from the workbook
        Sheet sheet = workbook.getSheetAt(0);

        //every sheet has rows, iterate over them
        Iterator<Row> rowIterator = sheet.iterator();

        int count = -1;

        while (rowIterator.hasNext()) {

            count++;
            Row row = rowIterator.next();

            if (count == 0) {
                continue;
            }

            log.debug("Processing row [" + count + "]");


            String PIN = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("A")));                                                   // PIN
            String CCOID = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("B")));                                                 // CCOID
            String SSN = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("C")));                                                   // SSN (TAX ID)
            String phone =  ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("D")));                                                // Telephone Number

            String taxName_F =          ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("F")));                                    // taxName
            String taxBusinessName_G =  ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("I")));                                    // taxBusinessName
            String taxClassification =  ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("H")));                                    // taxClassification
            String taxClassificationLimitedLiabilityCompanyClassification_I = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("I"))); // taxClassificationLimitedLiabilityCompanyClassification
            String taxExemptPayeeCode_K = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("K")));                                  // taxExemptPayeeCode
            String taxFATCAReportingExemptionCode_L = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("L")));                        // taxFATCAReportingExemptionCode
            String taxRequestersNameAndAddress_M = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("M")));                           // taxRequestersNameAndAddress
            String taxAddress_N = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("N")));
            String taxCity_O = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("O")));
            String taxState_P = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("P")));
            String taxZipcode_Q = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("Q")));
            String taxAccountNumbers_R = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("R"))); // 7
            String taxSignature_S = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("S")));
            String taxDate_T = ExcelCell.getStringFromCell(row.getCell(ExcelColumn.toNumber("T")));


            String finalFileName = "w9_2015_" + taxName_F.replaceAll("\\W+", "") + "_" + PIN + ".pdf";

            W9FormData w9FormData = new W9FormData();

            w9FormData.setName(taxName_F);
            w9FormData.setBusinessName(taxBusinessName_G);

            TaxClassification tc = TaxClassification.fromString(taxClassification);
            if(tc == TaxClassification.LIMITED_LIABILITY_CO){
                w9FormData.setTaxClassification(tc);
                TaxClassification tcllcc = TaxClassification.llcMapping(taxClassificationLimitedLiabilityCompanyClassification_I);
                w9FormData.setLimitedLiabilityCompanyTaxClassification(tcllcc);
            } else if(tc == TaxClassification.OTHER){
                log.warn("Don't know how to properly handler [" + TaxClassification.OTHER + "] tax classification");
//                w9FormData.setOtherFederalTaxClassificationTxt();
                w9FormData.setTaxClassification(tc);
            } else {
                w9FormData.setTaxClassification(tc);
            }


            w9FormData.setExemptEmployeeCode(taxExemptPayeeCode_K);
            w9FormData.setExemptionFromFATCSReportingCode(taxFATCAReportingExemptionCode_L);
            w9FormData.setAddress1(taxAddress_N);
            w9FormData.setAddress2(taxCity_O + ", " + taxState_P + ", " + taxZipcode_Q);
            w9FormData.setRequestorName(taxRequestersNameAndAddress_M);
            w9FormData.setAccountNumbers(taxAccountNumbers_R);

            w9FormData.setSsn(SSN);
//            w9FormData.setEmployerIdNumber();


            w9FormData.setSignature(taxSignature_S);

            w9FormData.setSignatureDate(taxDate_T);


            Files.createParentDirs(new File(w9DestinationFolder));
            new File(w9DestinationFolder).mkdirs();
            String fileToSave = w9DestinationFolder + finalFileName;

            csv2W9Service.createPDF(w9FormData, fileToSave);
        }
    }
}
