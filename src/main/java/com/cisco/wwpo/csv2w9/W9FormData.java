package com.cisco.wwpo.csv2w9;

import lombok.Data;

import java.util.IllegalFormatException;
import java.util.IllegalFormatWidthException;

/**
 * Created by maustino on 5/20/2015.
 */
@Data
public class W9FormData{

    /**
     * Name (as shown on your income tax return). Name is required on this line; do not leave this line blank.
     */
    String name;

    /**
     * Business name/disregarded entity name, if different from above
     */
    String businessName;

    TaxClassification taxClassification;

    TaxClassification limitedLiabilityCompanyTaxClassification;

    String otherFederalTaxClassificationTxt;


    /**
     * Exemptions (codes apply only to certain entities, not individuals; see instructions on page 3): Exempt payee code (if any)
     */
    String exemptEmployeeCode;


    /**
     * Exemption from FATCA reporting code (if any)
     */
    String exemptionFromFATCSReportingCode;


    /**
     * 5 Address (number, street, and apt. or suite no.)
     */
    String address1;

    /**
     * 6 City, state, and ZIP code
     */
    String address2;

    /**
     * 7 List account number(s) here (optional)
     */
    String accountNumbers;

    /**
     * Requester’s name and address (optional)
     */
    String requestorName;

    /**
     * Social security number
     */
    String ssn;

    /**
     * Employer identification number
     */
    String employerIdNumber;

    public void setSsn(String ssn) throws IllegalFormatWidthException {
        if(ssn.length() != 9){
            throw new IllegalFormatWidthException(9);
        }

        this.ssn = ssn;
    }

    public void setEmployerIdNumber(String employerIdNumber){
        if(employerIdNumber.length() != 9){
            throw new IllegalFormatWidthException(9);
        }

        this.employerIdNumber = employerIdNumber;
    }
}