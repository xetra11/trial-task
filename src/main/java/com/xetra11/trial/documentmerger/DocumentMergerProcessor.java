package com.xetra11.trial.documentmerger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@Service
public class DocumentMergerProcessor {

  private DocumentGenerator resultFileGenerator;

  @Autowired
  public DocumentMergerProcessor(@Qualifier("resultDocumentGenerator") DocumentGenerator resultFileGenerator) {
    this.resultFileGenerator = resultFileGenerator;
  }


  public void merge() {
    File resultFile = new File("dist/test/result.json");

    resultFileGenerator.generate(resultFile, null);


  }
}
