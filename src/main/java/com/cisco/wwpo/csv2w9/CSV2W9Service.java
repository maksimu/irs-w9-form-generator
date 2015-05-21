package com.cisco.wwpo.csv2w9;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.RadioCheckField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by maustino on 5/20/2015.
 */
public class CSV2W9Service {
    String w9File = "C:\\Users\\maustino\\IdeaProjects\\WWPO-csv2w9\\src\\main\\resources\\fw9.pdf";

    public final static String FIELD_requestorsNameAndAddress_optional                          = "topmostSubform[0].Page1[0].f1_9[0]";  // Requester’s name and address (optional)
    public final static String FIELD_listAccountNumbersHere_optional                            = "topmostSubform[0].Page1[0].f1_10[0]"; // 7 List account number(s) here (optional)
    public final static String FIELD_federalClassification_c1_1_0_Individual                    = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[0]"; // 3: Individual/sole proprietor or single-member LLC
    public final static String FIELD_federalClassification_c1_1_1_C_Corporation                 = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[1]";
    public final static String FIELD_federalClassification_c1_1_2_S_Corporation                 = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[2]";
    public final static String FIELD_federalClassification_c1_1_3_Partnership                   = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[3]";
    public final static String FIELD_federalClassification_c1_1_4_Trust_estate                  = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[4]";
    public final static String FIELD_federalClassification_c1_1_5_Limited_liability_company     = "topmostSubform[0].Page1[0].FederalClassification[0].c1_1[5]";
    public final static String FIELD_federalClassification_f1_3___Limited_Liability_company_txt = "topmostSubform[0].Page1[0].FederalClassification[0].f1_3[0]";
    public final static String FIELD_federalClassification_c1_7_0_OTHER                         = "topmostSubform[0].Page1[0].FederalClassification[0].c1_7[0]";
    public final static String FIELD_federalClassification_OTHER_TEXT                           = "topmostSubform[0].Page1[0].FederalClassification[0].f1_4[0]"; // 3 Other (see instructions)

    public final static String FIELD_employerId_f1_14 = "topmostSubform[0].Page1[0].EmployerID[0].f1_14[0]";
    public final static String FIELD_employerId_f1_15 = "topmostSubform[0].Page1[0].EmployerID[0].f1_15[0]";

    public final static String FIELD_f1_2 = "topmostSubform[0].Page1[0].f1_2[0]"; // 2 Business name/disregarded entity name, if different from above
    public final static String FIELD_ssn_f1_11 = "topmostSubform[0].Page1[0].SSN[0].f1_11[0]"; // Social security number, FIRST 3 numbers. Ex. 123
    public final static String FIELD_ssn_f1_12 = "topmostSubform[0].Page1[0].SSN[0].f1_12[0]"; // Social security number, SECOND 2 numbers. Ex. 45
    public final static String FIELD_ssn_f1_13 = "topmostSubform[0].Page1[0].SSN[0].f1_13[0]"; // Social security number, THIRD 4 numbers. Ex. 6789
    public final static String FIELD_address_f1_7 = "topmostSubform[0].Page1[0].Address[0].f1_7[0]"; // 5 Address (number, street, and apt. or suite no.)
    public final static String FIELD_address_f1_8 = "topmostSubform[0].Page1[0].Address[0].f1_8[0]"; // 6 City, state, and ZIP code
    public final static String FIELD_f1_1 = "topmostSubform[0].Page1[0].f1_1[0]";
    public final static String FIELD_exemptions_f1_6 = "topmostSubform[0].Page1[0].Exemptions[0].f1_6[0]";
    public final static String FIELD_exemptions_f1_5 = "topmostSubform[0].Page1[0].Exemptions[0].f1_5[0]";





    public void itext(W9FormData w9FormData, String fileToSave) throws IOException, DocumentException {


        PdfReader reader = new PdfReader(w9File);
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(fileToSave));
        AcroFields form = stamper.getAcroFields();



        form.removeXfa();
        form.setField(FIELD_f1_1, w9FormData.getName());
        form.setField(FIELD_f1_2, w9FormData.getBusinessName());

        switch (w9FormData.getTaxClassification()){
            case INDIVIDUAL:
                printAvailableStates(form, FIELD_federalClassification_c1_1_0_Individual);
                form.setField(FIELD_federalClassification_c1_1_0_Individual, "1");
                break;
            case C_CORP:
                printAvailableStates(form, FIELD_federalClassification_c1_1_1_C_Corporation);
                form.setField(FIELD_federalClassification_c1_1_1_C_Corporation, "2");
                break;
            case S_CORP:
                printAvailableStates(form, FIELD_federalClassification_c1_1_2_S_Corporation);
                form.setField(FIELD_federalClassification_c1_1_2_S_Corporation, "3");
                break;
            case PARTNERSHIP:
                printAvailableStates(form, FIELD_federalClassification_c1_1_3_Partnership);
                form.setField(FIELD_federalClassification_c1_1_3_Partnership, "4");
                break;
            case TRUSTESTATE:
                printAvailableStates(form, FIELD_federalClassification_c1_1_4_Trust_estate);
                form.setField(FIELD_federalClassification_c1_1_4_Trust_estate, "5");
                break;
            case LIMITED_LIABILITY_CO:
                printAvailableStates(form, FIELD_federalClassification_c1_1_5_Limited_liability_company);
                form.setField(FIELD_federalClassification_c1_1_5_Limited_liability_company, "6");
                form.setField(FIELD_federalClassification_f1_3___Limited_Liability_company_txt, w9FormData.getLimitedLiabilityCompanyTaxClassification().getCharacter().toString());
                break;
            case OTHER:
                printAvailableStates(form, FIELD_federalClassification_c1_7_0_OTHER);
                form.setField(FIELD_federalClassification_c1_7_0_OTHER, "7");
                form.setField(FIELD_federalClassification_OTHER_TEXT, w9FormData.getOtherFederalTaxClassificationTxt());
                break;
        }



        form.setField(FIELD_exemptions_f1_5, w9FormData.getExemptEmployeeCode());
        form.setField(FIELD_exemptions_f1_6, w9FormData.getExemptionFromFATCSReportingCode());



        form.setField(FIELD_address_f1_7, w9FormData.getAddress1());
        form.setField(FIELD_address_f1_8, w9FormData.getAddress2());
        form.setField(FIELD_listAccountNumbersHere_optional, w9FormData.getAccountNumbers());
        form.setField(FIELD_requestorsNameAndAddress_optional, w9FormData.getRequestorName());


        String ssn = w9FormData.getSsn(); // format: 012-34-56789
        String employerIdNumber = w9FormData.getEmployerIdNumber();

        System.out.println(ssn.substring(0, 3));
        System.out.println(ssn.substring(3, 5));
        System.out.println(ssn.substring(5, 9));

        form.setField(FIELD_ssn_f1_11, ssn.substring(0, 3));
        form.setField(FIELD_ssn_f1_12, ssn.substring(3, 5));
        form.setField(FIELD_ssn_f1_13, ssn.substring(5, 9));

        form.setField(FIELD_employerId_f1_14, employerIdNumber.substring(0, 2));
        form.setField(FIELD_employerId_f1_15, employerIdNumber.substring(2, 9));

        stamper.close();
        reader.close();

    }

    public void printAvailableStates(AcroFields form, String fieldName){

        System.out.println("Available fields for :" + fieldName);
        int count = 1;
        for (String s : form.getAppearanceStates(fieldName)) {
            System.out.println("---> > " + count++ + " [" + s + "]");
        }

        System.out.println("\n");
    }

    public void printAllFields(AcroFields form){
        Map<String, AcroFields.Item> fields = form.getFields();
        for (Map.Entry<String, AcroFields.Item> entry : fields.entrySet()) {
            System.out.println(entry.getKey());
        }

    }

    public String getCheckboxValue(AcroFields fields) throws IOException {
        // CP_1 is the name of a check box field
        String[] values = fields.getAppearanceStates("CP_1");
        StringBuffer sb = new StringBuffer();
        for (String value : values) {
            sb.append(value);
            sb.append('\n');
        }
        return sb.toString();
    }














//    public void asdf() throws IOException, COSVisitorException {
//
//        File w9FileTemplate = new File("C:\\Users\\maustino\\IdeaProjects\\WWPO-csv2w9\\src\\main\\resources\\fw9.pdf");
//        File w9File_copy = new File("C:\\Users\\maustino\\IdeaProjects\\WWPO-csv2w9\\src\\main\\resources\\fw9-copy.pdf");
//        Files.copy(w9FileTemplate, w9File_copy);
//
//        // Load the pdfTemplate
//        PDDocument pdfTemplate = PDDocument.load(w9FileTemplate);
//
//        PDDocumentCatalog docCatalog = pdfTemplate.getDocumentCatalog();
//        System.out.println("..." + docCatalog.getAllPages());
//
//        List<PDPage> allPages = docCatalog.getAllPages();
//        for (PDPage p : allPages) {
//            System.out.println(p.getActions());
//        }
//
//
//        List fields = pdfTemplate.getDocumentCatalog().getAcroForm().getFields();
//        PDField firstLevel = (PDField) fields.get(0);
//        List kids = firstLevel.getKids();
//        PDField firstKid = (PDField) kids.get(0);
//        PDField secondKid = (PDField) firstKid.getKids().get(0);
//        System.out.println(firstKid.getFullyQualifiedName() + ", " + secondKid.getFullyQualifiedName());
//
//
//
//
//
//
//
//
//
//
//
//
//        PDAcroForm acroForm = docCatalog.getAcroForm();
//
//        // Get field names
//        List<PDField> fieldList1 = acroForm.getFields();
//        PDField pdfiled0 = fieldList1.get(0);
//        List<COSObjectable> pdfiled0Kids = pdfiled0.getKids();
//
//
//        System.out.println("Type: " + pdfiled0.getAcroForm().getDocument().getPageMap());
//
//
//        List<PDField> fieldList2 = pdfiled0.getAcroForm().getFields();
//
//        // String the object array
//        String[] fieldArray = new String[fieldList2.size()];
//        int i = 0;
//        for (PDField sField : fieldList2) {
//            sField.getAcroForm().getFields();
//            fieldArray[i] = sField.getFullyQualifiedName();
//            System.out.println(sField.getFullyQualifiedName());
//            i++;
//        }
//
//        // Loop through each field in the array and do something
//
//        PDField field = acroForm.getField("topmostSubform[0].Page1[0].f1_01_0_[0]");
//        field.setValue("Maksim Ustinov");
//
//        // Set the List account number(s) here field to "N/A"
//        PDField fieldListAccountNumber = acroForm.getField("topmostSubform[0].Page1[0].f1_07_0_[0]");
//        field.setValue("N/A");
//        // Save edited file
//        pdfTemplate.save(w9File_copy);
//        pdfTemplate.close();
//
//    }

//    public void printFileds(PDAcroForm acroForm) throws IOException {
//
//        List<PDField> fieldList = acroForm.getFields();
//
//        for (PDField pdField : fieldList) {
//            if(pdField.getAcroForm() != null){
//                printFileds(pdField.getAcroForm());
//            } else {
//                String[] fieldArray = new String[fieldList.size()];
//                int i = 0;
//                for (PDField sField : fieldList) {
//
//                    sField.getAcroForm().getFields();
//
//                    fieldArray[i] = sField.getFullyQualifiedName();
//                    System.out.println("---> " + sField.getFullyQualifiedName());
//                    i++;
//                }
//            }
//        }
//    }
}
