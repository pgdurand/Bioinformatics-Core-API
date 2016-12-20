/* Copyright (C) 2006-2016 Patrick G. Durand
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
package bzh.plealog.bioinfo.api.data.sequence;

import java.io.Serializable;

/**
 * This interface defines a Sequence Information Object.
 * 
 * @author Patrick G. Durand
 */
public interface BankSequenceInfo extends Cloneable, Serializable{

  /* !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   * Modifying this class requires to modify SequenceInfoViewer.
   * Optionally, you may also need to modify the Filtering System
   * to use new data from SequenceInfo within Filters; see
   * com.plealog.bioinfo.api.filter.BAccessors for more info.
   * !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
   */

  /**
   * Returns the Id of a sequence.
   */
  public String getId();

  /**
   * Returns the description of a sequence.
   */
  public String getDescription();

  /**
   * Returns the molecular type of a sequence.
   */
  public String getMoltype();
  /**
   * Returns the topology of a sequence.
   */
  public String getTopology();
  /**
   * Returns the division to which belongs a sequence.
   */
  public String getDivision();
  /**
   * Returns the organism to which belongs a sequence.
   */
  public String getOrganism();
  /**
   * Returns the taxonomy classification associated to the organism.
   */
  public String getTaxonomy();

  /**
   * Returns the creation date of this sequence in a database. Date format is indeed a number
   * formatted as follows: YYYYMMDD. Month (MM) is formatted using two digits ranging from 1 to 12.
   * Day (DD) is formatting using two digits ranging from 1 to 31.
   */
  public int getCreationDate();

  /**
   * Returns the last update date of this sequence in a database. Date format is indeed a number
   * formatted as follows: YYYYMMDD. Month (MM) is formatted using two digits ranging from 1 to 12.
   * Day (DD) is formatting using two digits ranging from 1 to 31.
   */
  public int getUpdateDate();

  public int getSequenceSize();

  /**
   * Sets the Id of a sequence.
   */
  public void setId(String id);

  /**
   * Sets the description of a sequence.
   */
  public void setDescription(String desc);
  
  /**
   * Sets the molecular type of a sequence.
   */
  public void setMoltype(String mtype);
  /**
   * Sets the topology of a sequence.
   */
  public void setTopology(String topo);
  /**
   * Sets the division to which a sequence belongs to.
   */
  public void setDivision(String div);
  /**
   * Sets the organism to which belongs a sequence.
   */
  public void setOrganism(String org);

  /**
   * Sets the taxonomy classification associated to the organism. Passed String should
   * be formatted using strings separated by a semicolon (e.g. Bacteria;Proteobacteria;Gammaproteobacteria;...)
   */
  public void setTaxonomy(String tax);

  /**
   * Sets the creation date of this sequence in a database. Date format is indeed a number
   * formatted as follows: YYYYMMDD. Month (MM) is formatted using two digits ranging from 1 to 12.
   * Day (DD) is formatting using two digits ranging from 1 to 31.
   */
  public void setCreationDate(int d);

  /**
   * Sets the last update date of this sequence in a database. Date format is indeed a number
   * formatted as follows: YYYYMMDD. Month (MM) is formatted using two digits ranging from 1 to 12.
   * Day (DD) is formatting using two digits ranging from 1 to 31.
   */
  public void setUpdateDate(int d);

  public void setSequenceSize(int size);

  /**
   * Forces the implementation of a clone method.
   */
  public Object clone();
}
