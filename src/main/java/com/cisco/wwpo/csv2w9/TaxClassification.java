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
}
