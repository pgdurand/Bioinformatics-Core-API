/* Copyright (C) 2020 Patrick G. Durand
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
package bzh.plealog.bioinfo.io.gff.iprscan;


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
 * IprScan ggf3 file reader.
 * 
 * @see test.TestFeatureSystem for sample use case
 * 
 * @author Patrick G. Durand
 * */
public class IprGffReader {

  private String currentSeqRegion=null;
  private String iprscanVersion=null;
  private String date=null;
  
  public String getIprScanVersion() {
    return iprscanVersion;
  }
  
  public String getIprScanDate() {
    return date;
  }

  /**
   * Read a ggf3 IPRscan data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return list of predictions.
   **/
  private List<IprGffObject> processFileToList(String inputFilePath) {
    List<IprGffObject> inputList = new ArrayList<IprGffObject>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));){
      inputList = br.lines().map(mapToItem).filter(o -> o!=null).collect(Collectors.toList());
    } catch (IOException e) {
      System.err.println(e);
    }
    return inputList ;

  }

  /**
   * Read a ggf3 IPRscan data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return map of predictions. Keys are regionID, i.e. first column in the gff3 data file.
   **/
  public Map<String, List<IprGffObject>> processFileToMap(String inputFilePath) {
    currentSeqRegion = iprscanVersion = date = null;
    // A T T E N T I O N :  there are duplicated predictions in IprScan GGF3 output!

    // Process the iprscan data file (gff3 format) 
    // First, we group all results by sequence ID, i.e. regionID as reported in the gff3 file
    Map<String, List<IprGffObject>> iprscanData = processFileToList(inputFilePath)
        .stream().
        collect(Collectors.groupingBy(IprGffObject::getRegionId));
    // Then, we scan the results to get distinct predictions for each regionID
    // the following code "simply" filters out List<IprGffObject> to get distinct objects
    Map<String, List<IprGffObject>> predictions = 
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
   * Read a ggf3 IPRscan data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return map of predictions. Keys are regionID and values are FeatureTables.
   **/
  public Map<String, FeatureTable> readFile(String inputFilePath) {
    Map<String, List<IprGffObject>> gffMap = processFileToMap(inputFilePath);
    HashMap<String, FeatureTable> domains = new HashMap<>();
    gffMap.entrySet().forEach(e -> domains.put(e.getKey(), new IprPredictions(e.getValue()).getFeatureTable(true)));
    return domains;
  }
  
  /**
   * Figures out whether or not file is a GFF 3 format
   */
  public boolean canRead(String inputFilePath) {
    String line;
    boolean gffOk = false, iprOk=false;
    int i = 0;

    try (BufferedReader bReader = new BufferedReader(new FileReader(inputFilePath))) {
      while ((line = bReader.readLine()) != null) {
        if (line.indexOf("##gff-version") >= 0) {
          gffOk = true;
        }
        else if (line.indexOf("##interproscan-version") >= 0) {
          iprOk = true;
        }
        i++;
        if (i > 10)
          break;
      }
    } 
    catch (Exception e) {
    }

    return gffOk && iprOk;
  }

  private Function<String, IprGffObject> mapToItem = (line) -> {
    if (line.startsWith("#")) {
      if(line.contains("interproscan-version")) {
        //hope there is no exception in the IPRscan format...
        //  we always expect ##interproscan-version number
        //  so we get version as second string in the result of split!
        iprscanVersion = line.split(" ")[1];
      }
      else if(line.contains("sequence-region")) {
        //hope there is no exception in the IPRscan format...
        //  we always expect ##sequence-region ID from to
        //  so we get ID as second string in the result of split!
        currentSeqRegion = line.split(" ")[1];
      }
      return null;
    }
    String[] p = line.split("\t");
    //do we have canonical result?
    if (p.length!=9){
      return null;
    }
    IprGffObject item = new IprGffObject(currentSeqRegion,p[0],p[1],p[2],p[3],p[4],p[5],p[6],p[7],p[8]);
    if (date==null) {
      date = item.getAttributeValue(IprGffObject.DATE_ATTR);
    }
    return item;
  };
}
