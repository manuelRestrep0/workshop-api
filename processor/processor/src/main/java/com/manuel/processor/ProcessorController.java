package com.manuel.processor;

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
    public String recibirArchivo(@RequestBody FileReader file){
        return this.processorService.leerArchivo(file.getPath());
    }
}
