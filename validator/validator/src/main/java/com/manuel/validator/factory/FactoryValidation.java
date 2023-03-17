package com.manuel.validator.factory;

import com.manuel.validator.validation.Validation;
import com.manuel.validator.validation.ValidationCsv;
import com.manuel.validator.validation.ValidationXlsx;

import java.util.List;

public class FactoryValidation {
    public Validation obtenerMetodos(List<String> linea){
        String tipoArchivo = linea.get(0);
        if(tipoArchivo.equals("csv")){
            return new ValidationCsv();
        } else if(tipoArchivo.equals("xlsx")){
            return new ValidationXlsx();
        } else {
            try {
                throw new NoSuchFieldException("Tipo de archivo no encontrado");
            } catch (NoSuchFieldException e){
                throw  new RuntimeException(e);
            }
        }
    }
}
