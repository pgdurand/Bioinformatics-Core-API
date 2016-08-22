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
package bzh.plealog.bioinfo.api.data.feature;

import java.io.Serializable;
import java.util.Enumeration;

/**
 * This class describes a single Feature that can be associated to a sequence. Usually,
 * a Feature is not directly set to a sequence: instead, Features have to be associated
 * to a FeatureTable.
 * 
 * @author Patrick G. Durand
 */
public interface Feature extends Cloneable, Serializable {
  public static final int PLUS_STRAND  = 1;
  public static final int MINUS_STRAND = 2;

  /**
   * Returns the key name of the feature.
   */
  public String getKey();
  /**
   * Returns the starting position of the Feature on the sequence. Value is one-based.
   */
  public int getFrom();
  /**
   * Returns the ending position of the Feature on the sequence. Value is one-based.
   */
  public int getTo();
  /**
   * Returns the strand location. See XXX_STRAND constants defined in
   * this class.
   */
  public int getStrand();

  /**
   * Sets the key name of this Feature.
   */
  public void setKey(String str);

  /**
   * Sets the strand location. Use one of the XXX_STRAND constants defined in
   * this class.
   */
  public void setStrand(int i);

  /**
   * Sets the starting position of the Feature on the sequence. Value is one-based.
   */
  public void setFrom(int i);
  /**
   * Sets the ending position of the Feature on the sequence. Value is one-based.
   */
  public void setTo(int i);
  /**
   * Returns the feature location description. Use only when a feature location cannot
   * be described with a single from/to pair of values. May return null.
   */
  public FeatureLocation getFeatureLocation();

  /**
   * Sets a FeatureLocation. Use this king of object when a Feature location is described
   * with more than a single from/to pair of values.
   */
  public void setFeatureLocation(FeatureLocation fl);

  /**
   * Adds a new Qualifier to this Feature.
   * 
   * @param name the name of the Qualifier
   * @param value the value of the Qualifier
   * @return the new Qualifier created with name and value
   */
  public Qualifier addQualifier(String name, String value);

  /**
   * Adds a new Qualifier to this Feature.
   * 
   */
  public void addQualifier(Qualifier q);

  /**
   * Returns the number of Qualifiers contained in this Feature.
   */
  public int qualifiers();
  /**
   * Returns a particular Qualifier.
   */
  public Qualifier getQualifier(int i);
  /**
   * Returns an enumeration over the Qualifiers contained in this Feature.
   */
  public Enumeration<Qualifier> enumQualifiers();

  /**
   * Forces the implementation of a clone method.
   */
  public Object clone();

  /**
   * For internal use only.
   * */
  public boolean isValid(int hitFrom, int hitTo);

  /**
   * Cut a feature given new from and to values. If the feature is out of range
   * given the new coordinates, then this method should return null.
   */
  public Feature cut(int from, int to);

  /**
   * Figures out if a feature spans beyond left limit.
   */
  public boolean spanLeft();

  /**
   * Figures out if a feature spans beyond right limit.
   */
  public boolean spanRight();
}
