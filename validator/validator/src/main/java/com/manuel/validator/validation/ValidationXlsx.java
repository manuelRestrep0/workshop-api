package com.manuel.validator.validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ValidationXlsx implements Validation{
    public ValidationXlsx() {
    }

    @Override
    public boolean validar(List<String> linea) {
        //Se recibe una lista de string y se retorna un boolean
        if(validarReport(linea.get(2)) && validarLocation(linea.get(1))){
            return true;
        }
        return false;
    }

    public static boolean validarLocation(String location){
        //retorna true cuando el string location es diferente a N/A
        if(location.equals("N/A")){
            return false;
        }
        return true;
    }
    public static boolean validarReport(String report){
        //Retorna true si el string report esta dentro de la lista de types.
        List<String> types = new ArrayList<>(Arrays.asList("Near Miss","Lost Time","First Aid"));
        if(types.contains(report)){
            return true;
        }
        return false;
    }
}
