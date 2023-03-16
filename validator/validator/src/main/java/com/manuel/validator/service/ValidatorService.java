package com.manuel.validator.service;

import com.manuel.validator.validation.Validation;
import com.manuel.validator.validation.ValidationCsv;
import com.manuel.validator.validation.ValidationXlsx;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ValidatorService {
    private String linea;

    public  ValidatorService() {
    }

    public boolean validar(String line){
        //Se recibe un string y se convierte a una lista separandolo por ,
        //el primer elemento de la lista corresponde a la extension del archivo,
        //dependiendo de la extension se inicializa la variable validador.
        //se llama al metodo validar que recibe una lista de tipo string y
        //se retorna un boolean.
        //System.out.println(line);
        Validation validador;
        List<String> lineSplitted = Arrays.asList(line.split(","));
        if(lineSplitted.get(0).equals("csv")){
            validador = new ValidationCsv();
            return validador.validar(lineSplitted);
        } else if(lineSplitted.get(0).equals("xlsx")){
            validador = new ValidationXlsx();
            return validador.validar(lineSplitted);
        }
        return false;

    }


}
