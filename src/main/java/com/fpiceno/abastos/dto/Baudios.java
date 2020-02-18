/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fpiceno.abastos.dto;

/**
 *
 * @author fpiceno
 */
public enum Baudios {
    

    BAUDIOS_2400 ("FML",2400), BAUDIOS_4800 ("FMLSE",4800), BAUDIOS_9600("MS Consultores",9600), BAUDIOS_14400("MDP Abogados",14400),BAUDIOS_19200("FML Martinez",19200)
,BAUDIOS_28800("FML Martinez",28800),BAUDIOS_38400("FML Martinez",38400),BAUDIOS_57600("FML Martinez",57600),BAUDIOS_115200("FML Martinez",115200);
    
    private final String key;
    private final Integer value;

    Baudios(String key,Integer value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }
    public Integer getValue() {
        return value;
    }
    
    @Override
    public String toString(){
        return this.key;
    }
  
}
