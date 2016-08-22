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

package bzh.plealog.bioinfo.data.blast.loader.ncbi;

  //---------------------------------/
 //- Imported classes and packages -/
//---------------------------------/

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;

/**
 * Class Statistics.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Statistics implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = -5959406158157327624L;

    /**
     * Field _statistics_dbNum.
     */
    private long _statistics_dbNum;

    /**
     * keeps track of state for field: _statistics_dbNum
     */
    private boolean _has_statistics_dbNum;

    /**
     * Field _statistics_dbLen.
     */
    private long _statistics_dbLen;

    /**
     * keeps track of state for field: _statistics_dbLen
     */
    private boolean _has_statistics_dbLen;

    /**
     * Field _statistics_hspLen.
     */
    private long _statistics_hspLen;

    /**
     * keeps track of state for field: _statistics_hspLen
     */
    private boolean _has_statistics_hspLen;

    /**
     * Field _statistics_effSpace.
     */
    private double _statistics_effSpace;

    /**
     * keeps track of state for field: _statistics_effSpace
     */
    private boolean _has_statistics_effSpace;

    /**
     * Field _statistics_kappa.
     */
    private double _statistics_kappa;

    /**
     * keeps track of state for field: _statistics_kappa
     */
    private boolean _has_statistics_kappa;

    /**
     * Field _statistics_lambda.
     */
    private double _statistics_lambda;

    /**
     * keeps track of state for field: _statistics_lambda
     */
    private boolean _has_statistics_lambda;

    /**
     * Field _statistics_entropy.
     */
    private double _statistics_entropy;

    /**
     * keeps track of state for field: _statistics_entropy
     */
    private boolean _has_statistics_entropy;


      //----------------/
     //- Constructors -/
    //----------------/

    public Statistics() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteStatistics_dbLen(
    ) {
        this._has_statistics_dbLen= false;
    }

    /**
     */
    public void deleteStatistics_dbNum(
    ) {
        this._has_statistics_dbNum= false;
    }

    /**
     */
    public void deleteStatistics_effSpace(
    ) {
        this._has_statistics_effSpace= false;
    }

    /**
     */
    public void deleteStatistics_entropy(
    ) {
        this._has_statistics_entropy= false;
    }

    /**
     */
    public void deleteStatistics_hspLen(
    ) {
        this._has_statistics_hspLen= false;
    }

    /**
     */
    public void deleteStatistics_kappa(
    ) {
        this._has_statistics_kappa= false;
    }

    /**
     */
    public void deleteStatistics_lambda(
    ) {
        this._has_statistics_lambda= false;
    }

    /**
     * Returns the value of field 'statistics_dbLen'.
     * 
     * @return the value of field 'Statistics_dbLen'.
     */
    public long getStatistics_dbLen(
    ) {
        return this._statistics_dbLen;
    }

    /**
     * Returns the value of field 'statistics_dbNum'.
     * 
     * @return the value of field 'Statistics_dbNum'.
     */
    public long getStatistics_dbNum(
    ) {
        return this._statistics_dbNum;
    }

    /**
     * Returns the value of field 'statistics_effSpace'.
     * 
     * @return the value of field 'Statistics_effSpace'.
     */
    public double getStatistics_effSpace(
    ) {
        return this._statistics_effSpace;
    }

    /**
     * Returns the value of field 'statistics_entropy'.
     * 
     * @return the value of field 'Statistics_entropy'.
     */
    public double getStatistics_entropy(
    ) {
        return this._statistics_entropy;
    }

    /**
     * Returns the value of field 'statistics_hspLen'.
     * 
     * @return the value of field 'Statistics_hspLen'.
     */
    public long getStatistics_hspLen(
    ) {
        return this._statistics_hspLen;
    }

    /**
     * Returns the value of field 'statistics_kappa'.
     * 
     * @return the value of field 'Statistics_kappa'.
     */
    public double getStatistics_kappa(
    ) {
        return this._statistics_kappa;
    }

    /**
     * Returns the value of field 'statistics_lambda'.
     * 
     * @return the value of field 'Statistics_lambda'.
     */
    public double getStatistics_lambda(
    ) {
        return this._statistics_lambda;
    }

    /**
     * Method hasStatistics_dbLen.
     * 
     * @return true if at least one Statistics_dbLen has been added
     */
    public boolean hasStatistics_dbLen(
    ) {
        return this._has_statistics_dbLen;
    }

    /**
     * Method hasStatistics_dbNum.
     * 
     * @return true if at least one Statistics_dbNum has been added
     */
    public boolean hasStatistics_dbNum(
    ) {
        return this._has_statistics_dbNum;
    }

    /**
     * Method hasStatistics_effSpace.
     * 
     * @return true if at least one Statistics_effSpace has been
     * added
     */
    public boolean hasStatistics_effSpace(
    ) {
        return this._has_statistics_effSpace;
    }

    /**
     * Method hasStatistics_entropy.
     * 
     * @return true if at least one Statistics_entropy has been adde
     */
    public boolean hasStatistics_entropy(
    ) {
        return this._has_statistics_entropy;
    }

    /**
     * Method hasStatistics_hspLen.
     * 
     * @return true if at least one Statistics_hspLen has been added
     */
    public boolean hasStatistics_hspLen(
    ) {
        return this._has_statistics_hspLen;
    }

    /**
     * Method hasStatistics_kappa.
     * 
     * @return true if at least one Statistics_kappa has been added
     */
    public boolean hasStatistics_kappa(
    ) {
        return this._has_statistics_kappa;
    }

    /**
     * Method hasStatistics_lambda.
     * 
     * @return true if at least one Statistics_lambda has been added
     */
    public boolean hasStatistics_lambda(
    ) {
        return this._has_statistics_lambda;
    }

    /**
     * Method isValid.
     * 
     * @return true if this object is valid according to the schema
     */
    public boolean isValid(
    ) {
        try {
            validate();
        } catch (org.exolab.castor.xml.ValidationException vex) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 
     * @param out
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void marshal(
            final java.io.Writer out)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, out);
    }

    /**
     * 
     * 
     * @param handler
     * @throws java.io.IOException if an IOException occurs during
     * marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     */
    public void marshal(
            final org.xml.sax.ContentHandler handler)
    throws java.io.IOException, org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        Marshaller.marshal(this, handler);
    }

    /**
     * Sets the value of field 'statistics_dbLen'.
     * 
     * @param statistics_dbLen the value of field 'statistics_dbLen'
     */
    public void setStatistics_dbLen(
            final long statistics_dbLen) {
        this._statistics_dbLen = statistics_dbLen;
        this._has_statistics_dbLen = true;
    }

    /**
     * Sets the value of field 'statistics_dbNum'.
     * 
     * @param statistics_dbNum the value of field 'statistics_dbNum'
     */
    public void setStatistics_dbNum(
            final long statistics_dbNum) {
        this._statistics_dbNum = statistics_dbNum;
        this._has_statistics_dbNum = true;
    }

    /**
     * Sets the value of field 'statistics_effSpace'.
     * 
     * @param statistics_effSpace the value of field
     * 'statistics_effSpace'.
     */
    public void setStatistics_effSpace(
            final double statistics_effSpace) {
        this._statistics_effSpace = statistics_effSpace;
        this._has_statistics_effSpace = true;
    }

    /**
     * Sets the value of field 'statistics_entropy'.
     * 
     * @param statistics_entropy the value of field
     * 'statistics_entropy'.
     */
    public void setStatistics_entropy(
            final double statistics_entropy) {
        this._statistics_entropy = statistics_entropy;
        this._has_statistics_entropy = true;
    }

    /**
     * Sets the value of field 'statistics_hspLen'.
     * 
     * @param statistics_hspLen the value of field
     * 'statistics_hspLen'.
     */
    public void setStatistics_hspLen(
            final long statistics_hspLen) {
        this._statistics_hspLen = statistics_hspLen;
        this._has_statistics_hspLen = true;
    }

    /**
     * Sets the value of field 'statistics_kappa'.
     * 
     * @param statistics_kappa the value of field 'statistics_kappa'
     */
    public void setStatistics_kappa(
            final double statistics_kappa) {
        this._statistics_kappa = statistics_kappa;
        this._has_statistics_kappa = true;
    }

    /**
     * Sets the value of field 'statistics_lambda'.
     * 
     * @param statistics_lambda the value of field
     * 'statistics_lambda'.
     */
    public void setStatistics_lambda(
            final double statistics_lambda) {
        this._statistics_lambda = statistics_lambda;
        this._has_statistics_lambda = true;
    }

    /**
     * Method unmarshal.
     * 
     * @param reader
     * @throws org.exolab.castor.xml.MarshalException if object is
     * null or if any SAXException is thrown during marshaling
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     * @return the unmarshaled
     * com.plealog.bioinfo.data.blast.loader.ncbi.Statistics
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Statistics unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Statistics) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Statistics.class, reader);
    }

    /**
     * 
     * 
     * @throws org.exolab.castor.xml.ValidationException if this
     * object is an invalid instance according to the schema
     */
    public void validate(
    )
    throws org.exolab.castor.xml.ValidationException {
        org.exolab.castor.xml.Validator validator = new org.exolab.castor.xml.Validator();
        validator.validate(this);
    }

}
