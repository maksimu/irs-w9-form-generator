package com.cisco.wwpo.csv2w9;

/**
 * Created by maustino on 5/20/2015.
 */
public enum TaxClassification {


    /**
     * Individual/sole proprietor or single-member LLC
     */
    INDIVIDUAL('I'),

    /**
     * C Corporation
     */
    C_CORP('C'),

    /**
     * S Corporation
     */
    S_CORP('S'),

    /**
     * Partnership
     */
    PARTNERSHIP('P'),

    /**
     * Trust/estate
     */
    TRUSTESTATE('T'),

    /**
     * Limited liability company. Enter the tax classification (C=C corporation, S=S corporation, P=partnership)
     */
    LIMITED_LIABILITY_CO('L'),

    /**
     * Other (see instructions)
     */
    OTHER('O');


    Character character;
    TaxClassification(Character a){
        this.character = a;
    };

    public Character getCharacter(){
        return character;
    }

    /**
     * - C Corporation
     * - Individual/sole proprietor
     * - S Corporation
     * @param str
     * @return
     */
    public static TaxClassification fromString(String str){

        if("C Corporation".equalsIgnoreCase(str)){
            return C_CORP;
        } else if ("S Corporation".equalsIgnoreCase(str)){
            return S_CORP;
        } else if (("Individual/sole proprietor".equalsIgnoreCase(str)) || ("Individual/sole proprietor or single-member LLC".equalsIgnoreCase(str)) ){
            return INDIVIDUAL;
        } else if ("Limited liability company".equalsIgnoreCase(str)){
            return LIMITED_LIABILITY_CO;
        } else if ("Partnership".equalsIgnoreCase(str)){
            return PARTNERSHIP;
        } else if ("S Corporation".equalsIgnoreCase(str)){
            return S_CORP;
        } else if ("Other".equalsIgnoreCase(str)){
            return OTHER;
        } else {
            throw new EnumConstantNotPresentException(null, str);
        }
    }

    public static TaxClassification llcMapping(String str){

        if("C".equalsIgnoreCase(str)){
            return C_CORP;
        } else if ("S".equalsIgnoreCase(str)){
            return S_CORP;
        } else if ("P".equalsIgnoreCase(str)){
            return PARTNERSHIP;
        } else {
            throw new EnumConstantNotPresentException(null, str);
        }
    }
}
