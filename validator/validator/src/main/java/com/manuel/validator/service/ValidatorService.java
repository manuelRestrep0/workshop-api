package com.manuel.validator.service;

import com.manuel.validator.factory.FactoryValidation;
import com.manuel.validator.validation.Validation;
import com.manuel.validator.validation.ValidationCsv;
import com.manuel.validator.validation.ValidationXlsx;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidatorService {

    public  ValidatorService() {
    }

    public boolean validar(String line){
        Validation validador;
        List<String> lineSplitted = Arrays.asList(line.split(","));
        FactoryValidation validarArchivo = new FactoryValidation();
        validador = validarArchivo.obtenerMetodos(lineSplitted);
        return validador.validar(lineSplitted);
    }


}
