package com.manuel.processor.factory;

import com.manuel.processor.file.ArchivoCsv;
import com.manuel.processor.file.ArchivoMetodos;
import com.manuel.processor.file.ArchivoXlsx;

public class FactoryLectura {

    public ArchivoMetodos obtenerLectura(String path){

        if(path.contains(".xlsx")){
            return new ArchivoXlsx();
        } else if(path.contains(".csv")){
            return new ArchivoCsv();
        } else {
            try {
                throw new NoSuchFieldException("Tipo de archivo no encontrado");
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
