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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;

/**
 * Utility class to model EggNog mapper domain predictions. It is used to convert list
 * of eggNogObjects to a FeatureTable instance suitable to be used by all other
 * Core APIs in this library.
 * 
 * @see test.TestFeatureSystem for sample use case

 * @author Patrick G. Durand
 */
public class EggNogPredictions {

  private List<EggNogPrediction> gffObjs;
  private String enmVersion;
  private String enmDate;
  private String commandLine;

  /**
   * Constructor.
   * 
   * @param objs list of EggNogMapper predictions as read from file
   */
  public EggNogPredictions(List<EggNogObject> objs, String version, String date, String cmdLine) {
    gffObjs = new ArrayList<EggNogPrediction>();
    for(EggNogObject gff : objs) {
      gffObjs.add(new EggNogPrediction(gff));
    }
    setIEggNogMapperVersion(version);
    setEggNogMapperDate(date);
    SetCommandLine(cmdLine);
  }

  public String getEggNogMapperVersion() {
    return enmVersion;
  }

  public void setIEggNogMapperVersion(String iprscanVersion) {
    this.enmVersion = iprscanVersion;
  }

  public void setEggNogMapperDate(String d) {
    this.enmDate = d;
  }

  public String getEggNogMapperDate() {
    return enmDate;
  }

  public void SetCommandLine(String cmdLine) {
    commandLine = cmdLine;
  }
  
  public String getCommandLine() {
    return commandLine;
  }
  
  /**
   * Return number of domain predictions.
   * 
   * @return number of domain predictions.
   */
  public int size() {
    return gffObjs.size();
  }

  /**
   * Returns all domains.
   * 
   * @return all domain predictions
   */
  public List<EggNogPrediction> getAllDomains(){
    return gffObjs;
  }
  
  /**
   * Returns unique domain predictions. Rely upon the use of equal method from
   * EggNogPrediction class.
   * 
   * @return list of unique domain predictions.
   */
  public List<EggNogPrediction> filterUniqueDomains(){
    return gffObjs.stream().distinct().collect(Collectors.toList());
  }


  public FeatureTable getFeatureTable(boolean uniqueDomainId) {
    List<EggNogPrediction> filtered_objs;
    FeatureTable ft;
    Feature feat;
    
    ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
    ft.setSource(enmVersion);
    ft.setDate(enmDate);
    
    if (uniqueDomainId) {
      filtered_objs = filterUniqueDomains();
    }
    else {
      filtered_objs = gffObjs;
    }
    
    for(EggNogPrediction pred : filtered_objs) {
      feat = pred.getFeature();
      ft.addFeature(feat);
    }
    
    return ft;
  }

}
