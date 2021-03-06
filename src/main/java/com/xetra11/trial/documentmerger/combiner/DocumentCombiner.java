package com.xetra11.trial.documentmerger.combiner;

import com.xetra11.trial.documentmerger.model.DocumentPair;
import org.w3c.dom.Document;

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
