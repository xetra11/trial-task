package com.xetra11.trial.documentmerger;

import java.io.File;
import java.util.List;
import java.util.Map;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
public interface DocumentAppender {
  public enum DocumentType { XML, JSON }

  public void apply(File fileToAppend, Map<String, List<Object>> appendTo, DocumentType type);
}
