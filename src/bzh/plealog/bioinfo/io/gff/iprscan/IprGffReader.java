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
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * IprScan ggf3 file reader.
 * 
 * @see test.TestSerialSystem for sample use case
 * 
 * @author Patrick G. Durand
 * */
public class IprGffReader {

  private String currentSeqRegion;
  
  /**
   * Read a ggf3 IPRscan data file.
   * 
   * @param inputFilePath file to read
   * 
   * @return list of predictions.
   **/
  public List<IprGffObject> processFileToList(String inputFilePath) {
    List<IprGffObject> inputList = new ArrayList<IprGffObject>();
    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFilePath)));){
      inputList = br.lines().skip(3).map(mapToItem).filter(o -> o!=null).collect(Collectors.toList());
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

  
  private Function<String, IprGffObject> mapToItem = (line) -> {
    if (line.startsWith("#")) {
      if(line.contains("sequence-region")) {
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
    return item;
  };
}
