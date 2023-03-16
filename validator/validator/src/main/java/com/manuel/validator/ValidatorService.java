package com.manuel.validator;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class ValidatorService {
    private String linea;

    public  ValidatorService() {
    }

    public void validar(String line){
        System.out.println(line);
        //crear un elemento tipo Validation. hacer un if para saber q tipo es y llamar la funcion.

    }


}
