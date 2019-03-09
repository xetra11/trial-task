package com.xetra11.trial.documentmerger

import com.xetra11.trial.documentmerger.combiner.GIATAToCOAHCombiner
import org.w3c.dom.Document
import spock.lang.Specification

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -                              
 **************************************/
class GIATAToCOAHCombinerSpec extends Specification {
    def "should combine giata files and coah files by a reference id"() {
        given:
        File coahFile = new File(getClass().getClassLoader().getResource("3956-coah.xml").toURI())
        File giataFile = new File(getClass().getClassLoader().getResource("3956-giata.xml").toURI())
        GIATAToCOAHCombiner uut = new GIATAToCOAHCombiner();

        when:
        Optional<Document> actual = uut.combine(coahFile, giataFile)

        then:
        actual.isPresent()
        Document actualResult = actual.get();
        actualResult.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(1).getNodeName() == "result"
        actualResult.getChildNodes().item(0).getChildNodes().item(1).getChildNodes().item(2).getNodeName() == "giata_id"
    }
}
