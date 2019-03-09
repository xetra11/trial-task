package com.xetra11.trial.documentmerger;

import java.io.File;
import java.nio.file.Path;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung:
 * API: -                              
 **************************************/
public interface DocumentGenerator {
  public void generate(File filePath, Object document);
}
