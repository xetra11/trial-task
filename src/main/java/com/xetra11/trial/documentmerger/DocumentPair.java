package com.xetra11.trial.documentmerger;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.File;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
@AllArgsConstructor
@Data
public class DocumentPair {
  private File fileA;
  private File fileB;
}
