package com.xetra11.trial.documentmerger;

import org.w3c.dom.Document;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Optional;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
public interface DocumentCombiner {
  public enum DocumentType { XML, JSON }
  public Optional<Document> combine(File fileA, File fileB);
  public Optional<Document> combine(DocumentPair documentPair);
}
