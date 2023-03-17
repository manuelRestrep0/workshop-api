package com.manuel.processor.controller;

import com.manuel.processor.service.ProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1")
public class ProcessorController {

    private ProcessorService processorService;

    @Autowired
    public void ProcessorController(ProcessorService processorService){
        this.processorService = processorService;
    }

    @PostMapping("/archivo")
    public String recibirArchivo(@RequestBody String path){
        //Recibe un objecto tipo FileReader que contiene una variable llamada
        //Path de tipo String y ahi debe estar la ruta del archivo a trabajar.
        return this.processorService.leerArchivo(path);
    }
}
