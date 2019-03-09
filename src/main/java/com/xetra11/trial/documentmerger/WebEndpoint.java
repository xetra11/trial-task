package com.xetra11.trial.documentmerger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@RestController
public class WebEndpoint {
  private DocumentProcessor documentProcessor;

  @Autowired
  public WebEndpoint(DocumentProcessor documentProcessor) {
    this.documentProcessor = documentProcessor;
  }

  @GetMapping("/start")
  public void startProcessor() {
    documentProcessor.merge();
  }
}
