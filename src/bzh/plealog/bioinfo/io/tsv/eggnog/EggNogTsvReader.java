/* Copyright (C) 2022 Patrick G. Durand
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  You may obtain a copy of the License at
 *
 *     https://www.gnu.org/licenses/agpl-3.0.txt
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 */
package bzh.plealog.bioinfo.io.tsv.eggnog;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import bzh.plealog.bioinfo.api.data.feature.FeatureTable;

/**
 * EggNog Mapper TSV file reader.
 * 
 * @see test.TestFeatureSystem for sample use case
 * 
 * @author Patrick G. Durand
 * */
public class EggNogTsvReader {

  private String commandLine=null;
  private String eggNogMapperVer=null;
  private String date=null;
  
  /**
   * Read an EggNog Mapper TSV data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return list of predictions.
   **/
  private List<EggNogObject> processFileToList(String inputFilePath) {
    List<EggNogObject> inputList = new ArrayList<EggNogObject>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));){
      inputList = br.lines().map(mapToItem).filter(o -> o!=null).collect(Collectors.toList());
    } catch (IOException e) {
      System.err.println(e);
    }
    return inputList ;

  }

  /**
   * Read an EggNog Mapper TSV data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return map of predictions. Keys are query_name, i.e. first column in the TSV data file.
   **/
  public Map<String, List<EggNogObject>> processFileToMap(String inputFilePath) {
    commandLine = eggNogMapperVer = date = null;

    // Process the eggnog data file (TSV format) 
    // First, we group all results by query_name
    Map<String, List<EggNogObject>> iprscanData = processFileToList(inputFilePath)
        .stream().
        collect(Collectors.groupingBy(EggNogObject::getQuery_name));
    // Then, we scan the results to get distinct predictions for each query_name
    // the following code "simply" filters out List<EggNogObject> to get distinct objects
    Map<String, List<EggNogObject>> predictions = 
        iprscanData.entrySet()
                        .stream()
                        .collect(
                            Collectors.toMap(
                                e->e.getKey(),
                                e->e.getValue()
                                .stream()
                                .distinct()
                                .collect(Collectors.toList())));
    return predictions;
  }

  /**
   * Read a TSV EggNog Maper data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return map of predictions. Keys are query_name and values are FeatureTables.
   **/
  public Map<String, FeatureTable> readFile(String inputFilePath) {
    Map<String, List<EggNogObject>> gffMap = processFileToMap(inputFilePath);
    HashMap<String, FeatureTable> domains = new HashMap<>();
    gffMap.entrySet().forEach(e -> domains.put(e.getKey(), 
        new EggNogPredictions(e.getValue(), eggNogMapperVer, date, commandLine).getFeatureTable(true)));
    return domains;
  }
  
  /**
   * Figures out whether or not file is a EggNog Mapper TSV format
   */
  public boolean canRead(String inputFilePath) {
    String line;
    boolean eggOk=false;
    int i = 0;

    try (BufferedReader bReader = new BufferedReader(new FileReader(inputFilePath))) {
      while ((line = bReader.readLine()) != null) {
        if (line.indexOf("emapper version:") >= 0) {
          eggOk = true;
        }
        i++;
        if (i > 5)
          break;
      }
    } 
    catch (Exception e) {
    }

    return eggOk;
  }

  private Function<String, EggNogObject> mapToItem = (line) -> {
    if (line.startsWith("#")) {
      //hope there is no exception in the eggnog mapper format
      //to provide these pieces of information!
      if(line.contains("emapper version:")) {
        eggNogMapperVer = line.substring(19);
      }
      else if(line.contains("command:")) {
        commandLine = line.substring(11);
      }
      else if(line.contains("time:")) {
        date = line.substring(8);
      }
      return null;
    }
    String[] p = line.split("\t");
    if (p.length<=1) {//skip not valid data line (no tab, or empty lines)
      return null;
    }
    else {
      return new EggNogObject(p);
    }
  };
}
