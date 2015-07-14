package com.cisco.wwpo.csv2w9;

import org.junit.Test;

/**
 * Created by maustino on 5/20/2015.
 */
public class CSV2W9ServiceTest {

    @Test
    public void testItext() throws Exception {

        CSV2W9Service csv2W9Service = new CSV2W9Service();

        W9FormData formData = new W9FormData();
        formData.setName("Maksim Ustinov");

        formData.setTaxClassification(TaxClassification.OTHER);
        formData.setOtherFederalTaxClassificationTxt("Some OTHER Classification");

        formData.setExemptEmployeeCode("EX-2345");
        formData.setExemptionFromFATCSReportingCode("FA-EX-345");

        formData.setAddress1("ADDRESS 1");
        formData.setAddress2("ADDRESS 2");
        formData.setAccountNumbers("SOME-ACCT-NO 234, 234567");
        formData.setRequestorName("Joseph Stalin");

        formData.setSsn("012345678");               // format: nnn-nn-nnnn
        formData.setEmployerIdNumber("876543210");  // format: nn-nnnnnnn


        formData.setSignature("Lenin");
        formData.setSignatureDate("12/23/1921");
        csv2W9Service.createPDF(formData, "C:\\Users\\maustino\\IdeaProjects\\WWPO-csv2w9\\src\\main\\resources\\fw9-copy-" + formData.getTaxClassification() + ".pdf");
    }
}