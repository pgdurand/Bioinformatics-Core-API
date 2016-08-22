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
 * Class Iteration.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Iteration implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = 5967830812918137324L;

    /**
     * Field _iteration_iterNum.
     */
    private long _iteration_iterNum;

    /**
     * keeps track of state for field: _iteration_iterNum
     */
    private boolean _has_iteration_iterNum;

    /**
     * Field _iteration_queryID.
     */
    private java.lang.String _iteration_queryID;

    /**
     * Field _iteration_queryDef.
     */
    private java.lang.String _iteration_queryDef;

    /**
     * Field _iteration_queryLen.
     */
    private long _iteration_queryLen;

    /**
     * keeps track of state for field: _iteration_queryLen
     */
    private boolean _has_iteration_queryLen;

    /**
     * Field _iteration_hits.
     */
    private bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits _iteration_hits;

    /**
     * Field _iteration_stat.
     */
    private bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_stat _iteration_stat;

    /**
     * Field _iteration_message.
     */
    private java.lang.String _iteration_message;


      //----------------/
     //- Constructors -/
    //----------------/

    public Iteration() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteIteration_iterNum(
    ) {
        this._has_iteration_iterNum= false;
    }

    /**
     */
    public void deleteIteration_queryLen(
    ) {
        this._has_iteration_queryLen= false;
    }

    /**
     * Returns the value of field 'iteration_hits'.
     * 
     * @return the value of field 'Iteration_hits'.
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits getIteration_hits(
    ) {
        return this._iteration_hits;
    }

    /**
     * Returns the value of field 'iteration_iterNum'.
     * 
     * @return the value of field 'Iteration_iterNum'.
     */
    public long getIteration_iterNum(
    ) {
        return this._iteration_iterNum;
    }

    /**
     * Returns the value of field 'iteration_message'.
     * 
     * @return the value of field 'Iteration_message'.
     */
    public java.lang.String getIteration_message(
    ) {
        return this._iteration_message;
    }

    /**
     * Returns the value of field 'iteration_queryDef'.
     * 
     * @return the value of field 'Iteration_queryDef'.
     */
    public java.lang.String getIteration_queryDef(
    ) {
        return this._iteration_queryDef;
    }

    /**
     * Returns the value of field 'iteration_queryID'.
     * 
     * @return the value of field 'Iteration_queryID'.
     */
    public java.lang.String getIteration_queryID(
    ) {
        return this._iteration_queryID;
    }

    /**
     * Returns the value of field 'iteration_queryLen'.
     * 
     * @return the value of field 'Iteration_queryLen'.
     */
    public long getIteration_queryLen(
    ) {
        return this._iteration_queryLen;
    }

    /**
     * Returns the value of field 'iteration_stat'.
     * 
     * @return the value of field 'Iteration_stat'.
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_stat getIteration_stat(
    ) {
        return this._iteration_stat;
    }

    /**
     * Method hasIteration_iterNum.
     * 
     * @return true if at least one Iteration_iterNum has been added
     */
    public boolean hasIteration_iterNum(
    ) {
        return this._has_iteration_iterNum;
    }

    /**
     * Method hasIteration_queryLen.
     * 
     * @return true if at least one Iteration_queryLen has been adde
     */
    public boolean hasIteration_queryLen(
    ) {
        return this._has_iteration_queryLen;
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
     * Sets the value of field 'iteration_hits'.
     * 
     * @param iteration_hits the value of field 'iteration_hits'.
     */
    public void setIteration_hits(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_hits iteration_hits) {
        this._iteration_hits = iteration_hits;
    }

    /**
     * Sets the value of field 'iteration_iterNum'.
     * 
     * @param iteration_iterNum the value of field
     * 'iteration_iterNum'.
     */
    public void setIteration_iterNum(
            final long iteration_iterNum) {
        this._iteration_iterNum = iteration_iterNum;
        this._has_iteration_iterNum = true;
    }

    /**
     * Sets the value of field 'iteration_message'.
     * 
     * @param iteration_message the value of field
     * 'iteration_message'.
     */
    public void setIteration_message(
            final java.lang.String iteration_message) {
        this._iteration_message = iteration_message;
    }

    /**
     * Sets the value of field 'iteration_queryDef'.
     * 
     * @param iteration_queryDef the value of field
     * 'iteration_queryDef'.
     */
    public void setIteration_queryDef(
            final java.lang.String iteration_queryDef) {
        this._iteration_queryDef = iteration_queryDef;
    }

    /**
     * Sets the value of field 'iteration_queryID'.
     * 
     * @param iteration_queryID the value of field
     * 'iteration_queryID'.
     */
    public void setIteration_queryID(
            final java.lang.String iteration_queryID) {
        this._iteration_queryID = iteration_queryID;
    }

    /**
     * Sets the value of field 'iteration_queryLen'.
     * 
     * @param iteration_queryLen the value of field
     * 'iteration_queryLen'.
     */
    public void setIteration_queryLen(
            final long iteration_queryLen) {
        this._iteration_queryLen = iteration_queryLen;
        this._has_iteration_queryLen = true;
    }

    /**
     * Sets the value of field 'iteration_stat'.
     * 
     * @param iteration_stat the value of field 'iteration_stat'.
     */
    public void setIteration_stat(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration_stat iteration_stat) {
        this._iteration_stat = iteration_stat;
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.Iteration
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Iteration.class, reader);
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
