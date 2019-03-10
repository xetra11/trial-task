package com.xetra11.trial.documentmerger.processor;

import org.json.JSONObject;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
public interface ImageProcessor<T> {
  public void process(T jsonObject);
}
