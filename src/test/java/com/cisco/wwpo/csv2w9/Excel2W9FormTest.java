package com.cisco.wwpo.csv2w9;

import com.itextpdf.text.DocumentException;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by maustino on 5/21/2015.
 */
public class Excel2W9FormTest {

    @Test
    public void testAdsf() throws Exception {

        Excel2W9Form excel2W9Form = new Excel2W9Form();
        excel2W9Form.generateW9sFromExcel(
                "C:/Users/maustino/Documents/Cisco/WWPO/W9 Forms Data/CiscoRewards_W9_2014_Report_20150622-removed-notvalid-and-unknown.xlsx",
                "C:/Users/maustino/Documents/Cisco/WWPO/W9 Forms Data/pdfs20150625/");
    }

    @Test
    public void generateSampleW9() throws IOException, DocumentException {
        CSV2W9Service csv2W9Service = new CSV2W9Service();

        W9FormData formData = new W9FormData();
        formData.setName("George Washington");
        formData.setBusinessName("United States of America");

        formData.setTaxClassification(TaxClassification.INDIVIDUAL);
//        formData.setOtherFederalTaxClassificationTxt("Some OTHER Classification");

        formData.setExemptEmployeeCode("US-001");
        formData.setExemptionFromFATCSReportingCode("US-VA");

        formData.setAddress1("1600 Pennsylvania Avenue ");
        formData.setAddress2("Northwest, Washington, DC 20500");
        formData.setAccountNumbers("US-PRES-0001");
        formData.setRequestorName("John Adams");

        formData.setSsn("000000001");               // format: nnn-nn-nnnn
//        formData.setEmployerIdNumber("876543210");  // format: nn-nnnnnnn


        formData.setSignature("George Washington");
        formData.setSignatureDate("March 4, 1797");
        csv2W9Service.createPDF(formData, "C:\\Users\\maustino\\IdeaProjects\\WWPO-csv2w9\\src\\main\\resources\\w9-" + formData.getName() + "-" + formData.getSignatureDate() + ".pdf");


    }
}