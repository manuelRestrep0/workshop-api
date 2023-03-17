package com.manuel.validator.validation;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationCsv implements Validation {

    public ValidationCsv() {
    }

    @Override
    public boolean validar(List<String> linea) {
        //Se realiza un condicional donde se llaman 3 metodos que reciben cada uno un string
        //que equivale al valor de la columna de interes de cada metodo,
        //y todos retornan un boolean.
        if(
                validarEmail(linea.get(1)) &&
                        validarFecha(linea.get(2))
                && validarTrabajo(linea.get(3))
        ){
            return true;
        }

        return false;
    }
    public boolean validarEmail(String email){
        //Se recibe el email en tipo String y, por medio de una expresion regular se verifica que sea un correo valido.
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }
    public boolean validarFecha(String date){
        //Se recibe la fecha en un String, se convierte a una variable de tipo
        //LocalDate y se compara con la fecha del String fechaAComparar.
        //Se retorna un boolean
        String fechaAComparar = "1980-01-01";
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fecha1 = LocalDate.parse(fechaAComparar,formato);
        LocalDate fecha2 = LocalDate.parse(date, formato);
        if(fecha2.isBefore(fecha1)){
            return true;
        }
        return false;
    }
    public boolean validarTrabajo(String job){
        //Se recibe un String con el trabajo, si este trabajo esta en la lista
        //de jobs entonces se retorna true, de lo contrario se retorna false.
        List<String> jobs = new ArrayList<>(Arrays.asList("Haematologist","Phytotherapist","Building surveyor","Insurance account manager","Educational psychologist"));
        if(jobs.contains(job)){
            return true;
        }
        return false;
    }
}
