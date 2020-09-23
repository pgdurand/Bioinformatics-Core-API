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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import bzh.plealog.bioinfo.api.core.config.CoreSystemConfigurator;
import bzh.plealog.bioinfo.api.data.feature.Feature;
import bzh.plealog.bioinfo.api.data.feature.FeatureTable;
import test.TestSerialSystem;

/**
 * Utility class to model IPRscan domain predictions. It is used to convert list
 * of IprGffObjects to a FeatureTable instance suitable to be used by all other
 * Core APIs in this library.
 * 
 * @see TestSerialSystem for sample use case

 * @author Patrick G. Durand
 */
public class IprPredictions {

  private List<IprPrediction> gffObjs;
  
  /**
   * Constructor.
   * 
   * @param objs list of IPRscan predictions as read from file
   */
  public IprPredictions(List<IprGffObject> objs) {
    gffObjs = new ArrayList<IprPrediction>();
    for(IprGffObject gff : objs) {
      gffObjs.add(new IprPrediction(gff));
    }
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
  public List<IprPrediction> getAllDomains(){
    return gffObjs;
  }
  
  /**
   * Returns unique domain predictions. Rely upon the use of equal method from
   * IprPrediciton class.
   * 
   * @return list of unique domain predictions.
   */
  public List<IprPrediction> filterUniqueDomains(){
    return gffObjs.stream().distinct().collect(Collectors.toList());
  }

  /**
   * Return a FeatureTable representation of list of domain predictions.
   * 
   * @param uniqueDomainId figure out whether or not return unique domains. 
   * 
   * @see IprPredictions#filterUniqueDomains()
   * 
   * @return a FeatureTable representation of list of (unique) domain predictions
   */
  public FeatureTable getFeatureTable(boolean uniqueDomainId) {
    List<IprPrediction> filtered_objs;
    FeatureTable ft;
    Feature feat;
    
    ft = CoreSystemConfigurator.getFeatureTableFactory().getFTInstance();
    
    if (uniqueDomainId) {
      filtered_objs = filterUniqueDomains();
    }
    else {
      filtered_objs = gffObjs;
    }
    
    for(IprPrediction pred : filtered_objs) {
      feat = pred.getFeature();
      if (feat.getKey().equals(IprPrediction.UNK)==false) {
        ft.addFeature(feat);
      }
    }
    
    return ft;
  }
}
