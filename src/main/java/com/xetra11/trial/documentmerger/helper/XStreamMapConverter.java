package com.xetra11.trial.documentmerger.helper;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.*;

/***************************************
 * Author: xetra11                     
 * Datum: 3/9/2019                      
 * Funktion/Komponente: -              
 * Beschreibung: -                     
 * API: -
 * Quelle: https://stackoverflow.com/questions/22226116/convert-xml-to-map-of-nested-maps
 **************************************/
public class XStreamMapConverter implements Converter {
  @SuppressWarnings("rawtypes")
  public boolean canConvert(Class clazz) {
    return AbstractMap.class.isAssignableFrom(clazz);
  }

  @SuppressWarnings({"unchecked"})
  public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {
    AbstractMap<String, List<?>> map = (AbstractMap<String, List<?>>) value;
    List<Map<String, ?>> list = (List<Map<String, ?>>) map.get("xml");
    for (Map<String, ?> maps : list) {
      for (Map.Entry<String, ?> entry : maps.entrySet()) {
        mapToXML(writer, entry);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private void mapToXML(HierarchicalStreamWriter writer, Map.Entry<String, ?> entry) {
    writer.startNode(entry.getKey());
    if (entry.getValue() instanceof String) {
      writer.setValue(entry.getValue().toString());
    } else if (entry.getValue() instanceof ArrayList) {
      List<?> list = (List<?>) entry.getValue();
      for (Object object : list) {
        Map<String, ?> map = (Map<String, ?>) object;
        for (Map.Entry<String, ?> entryS : map.entrySet()) {
          mapToXML(writer, entryS);
        }
      }
    }
    writer.endNode();
  }

  public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
    Map<String, List<Object>> map = new HashMap<String, List<Object>>();
    map = xmlToMap(reader, new HashMap<String, List<Object>>());
    return map;
  }

  private Map<String, List<Object>> xmlToMap(HierarchicalStreamReader reader, Map<String, List<Object>> map) {
    List<Object> list = new ArrayList<Object>();
    while (reader.hasMoreChildren()) {
      reader.moveDown();
      if (reader.hasMoreChildren()) {
        list.add(xmlToMap(reader, new HashMap<String, List<Object>>()));
      } else {
        Map<String, Object> mapN = new HashMap<String, Object>();
        mapN.put(reader.getNodeName(), reader.getValue());
        list.add(mapN);
      }
      reader.moveUp();
    }
    map.put(reader.getNodeName(), list);
    return map;
  }

}
