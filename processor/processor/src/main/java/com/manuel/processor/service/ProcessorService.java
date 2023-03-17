package com.manuel.processor.service;

import com.manuel.processor.file.ArchivoMetodos;
import com.manuel.processor.factory.FactoryLectura;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProcessorService {
    Map<String,ArchivoMetodos> map = new HashMap<>();

    public ProcessorService() {
    }
    public String leerArchivo(String path){
        //Se recibe la direccion del archivo y se retorna el resultado en un string
        FactoryLectura leerArchivo = new FactoryLectura();
        ArchivoMetodos file;
        file = leerArchivo.obtenerLectura(path);
        file.lectura(path);
        String resultado = file.toString();
        return resultado;
    }

}
