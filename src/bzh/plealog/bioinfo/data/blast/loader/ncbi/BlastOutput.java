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
 * Class BlastOutput.
 * 
 * @version $Revision: 1.4 $ $Date: 2010/07/13 16:00:05 $
 */
public class BlastOutput implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = -5940336319918147352L;

    /**
     * Field _blastOutput_program.
     */
    private java.lang.String _blastOutput_program;

    /**
     * Field _blastOutput_version.
     */
    private java.lang.String _blastOutput_version;

    /**
     * Field _blastOutput_reference.
     */
    private java.lang.String _blastOutput_reference;

    /**
     * Field _blastOutput_db.
     */
    private java.lang.String _blastOutput_db;

    /**
     * Field _blastOutput_queryID.
     */
    private java.lang.String _blastOutput_queryID;

    /**
     * Field _blastOutput_queryDef.
     */
    private java.lang.String _blastOutput_queryDef;

    /**
     * Field _blastOutput_queryLen.
     */
    private long _blastOutput_queryLen;

    /**
     * keeps track of state for field: _blastOutput_queryLen
     */
    private boolean _has_blastOutput_queryLen;

    /**
     * Field _blastOutput_querySeq.
     */
    private java.lang.String _blastOutput_querySeq;

    /**
     * Field _blastOutput_param.
     */
    private bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_param _blastOutput_param;

    /**
     * Field _blastOutput_iterations.
     */
    private bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations _blastOutput_iterations;

    /**
     * Field _blastOutput_mbstat.
     */
    private bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_mbstat _blastOutput_mbstat;


      //----------------/
     //- Constructors -/
    //----------------/

    public BlastOutput() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteBlastOutput_queryLen(
    ) {
        this._has_blastOutput_queryLen= false;
    }

    /**
     * Returns the value of field 'blastOutput_db'.
     * 
     * @return the value of field 'BlastOutput_db'.
     */
    public java.lang.String getBlastOutput_db(
    ) {
        return this._blastOutput_db;
    }

    /**
     * Returns the value of field 'blastOutput_iterations'.
     * 
     * @return the value of field 'BlastOutput_iterations'.
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations getBlastOutput_iterations(
    ) {
        return this._blastOutput_iterations;
    }

    /**
     * Returns the value of field 'blastOutput_mbstat'.
     * 
     * @return the value of field 'BlastOutput_mbstat'.
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_mbstat getBlastOutput_mbstat(
    ) {
        return this._blastOutput_mbstat;
    }

    /**
     * Returns the value of field 'blastOutput_param'.
     * 
     * @return the value of field 'BlastOutput_param'.
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_param getBlastOutput_param(
    ) {
        return this._blastOutput_param;
    }

    /**
     * Returns the value of field 'blastOutput_program'.
     * 
     * @return the value of field 'BlastOutput_program'.
     */
    public java.lang.String getBlastOutput_program(
    ) {
        return this._blastOutput_program;
    }

    /**
     * Returns the value of field 'blastOutput_queryDef'.
     * 
     * @return the value of field 'BlastOutput_queryDef'.
     */
    public java.lang.String getBlastOutput_queryDef(
    ) {
        return this._blastOutput_queryDef;
    }

    /**
     * Returns the value of field 'blastOutput_queryID'.
     * 
     * @return the value of field 'BlastOutput_queryID'.
     */
    public java.lang.String getBlastOutput_queryID(
    ) {
        return this._blastOutput_queryID;
    }

    /**
     * Returns the value of field 'blastOutput_queryLen'.
     * 
     * @return the value of field 'BlastOutput_queryLen'.
     */
    public long getBlastOutput_queryLen(
    ) {
        return this._blastOutput_queryLen;
    }

    /**
     * Returns the value of field 'blastOutput_querySeq'.
     * 
     * @return the value of field 'BlastOutput_querySeq'.
     */
    public java.lang.String getBlastOutput_querySeq(
    ) {
        return this._blastOutput_querySeq;
    }

    /**
     * Returns the value of field 'blastOutput_reference'.
     * 
     * @return the value of field 'BlastOutput_reference'.
     */
    public java.lang.String getBlastOutput_reference(
    ) {
        return this._blastOutput_reference;
    }

    /**
     * Returns the value of field 'blastOutput_version'.
     * 
     * @return the value of field 'BlastOutput_version'.
     */
    public java.lang.String getBlastOutput_version(
    ) {
        return this._blastOutput_version;
    }

    /**
     * Method hasBlastOutput_queryLen.
     * 
     * @return true if at least one BlastOutput_queryLen has been
     * added
     */
    public boolean hasBlastOutput_queryLen(
    ) {
        return this._has_blastOutput_queryLen;
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
     * Sets the value of field 'blastOutput_db'.
     * 
     * @param blastOutput_db the value of field 'blastOutput_db'.
     */
    public void setBlastOutput_db(
            final java.lang.String blastOutput_db) {
        this._blastOutput_db = blastOutput_db;
    }

    /**
     * Sets the value of field 'blastOutput_iterations'.
     * 
     * @param blastOutput_iterations the value of field
     * 'blastOutput_iterations'.
     */
    public void setBlastOutput_iterations(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_iterations blastOutput_iterations) {
        this._blastOutput_iterations = blastOutput_iterations;
    }

    /**
     * Sets the value of field 'blastOutput_mbstat'.
     * 
     * @param blastOutput_mbstat the value of field
     * 'blastOutput_mbstat'.
     */
    public void setBlastOutput_mbstat(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_mbstat blastOutput_mbstat) {
        this._blastOutput_mbstat = blastOutput_mbstat;
    }

    /**
     * Sets the value of field 'blastOutput_param'.
     * 
     * @param blastOutput_param the value of field
     * 'blastOutput_param'.
     */
    public void setBlastOutput_param(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput_param blastOutput_param) {
        this._blastOutput_param = blastOutput_param;
    }

    /**
     * Sets the value of field 'blastOutput_program'.
     * 
     * @param blastOutput_program the value of field
     * 'blastOutput_program'.
     */
    public void setBlastOutput_program(
            final java.lang.String blastOutput_program) {
        this._blastOutput_program = blastOutput_program;
    }

    /**
     * Sets the value of field 'blastOutput_queryDef'.
     * 
     * @param blastOutput_queryDef the value of field
     * 'blastOutput_queryDef'.
     */
    public void setBlastOutput_queryDef(
            final java.lang.String blastOutput_queryDef) {
        this._blastOutput_queryDef = blastOutput_queryDef;
    }

    /**
     * Sets the value of field 'blastOutput_queryID'.
     * 
     * @param blastOutput_queryID the value of field
     * 'blastOutput_queryID'.
     */
    public void setBlastOutput_queryID(
            final java.lang.String blastOutput_queryID) {
        this._blastOutput_queryID = blastOutput_queryID;
    }

    /**
     * Sets the value of field 'blastOutput_queryLen'.
     * 
     * @param blastOutput_queryLen the value of field
     * 'blastOutput_queryLen'.
     */
    public void setBlastOutput_queryLen(
            final long blastOutput_queryLen) {
        this._blastOutput_queryLen = blastOutput_queryLen;
        this._has_blastOutput_queryLen = true;
    }

    /**
     * Sets the value of field 'blastOutput_querySeq'.
     * 
     * @param blastOutput_querySeq the value of field
     * 'blastOutput_querySeq'.
     */
    public void setBlastOutput_querySeq(
            final java.lang.String blastOutput_querySeq) {
        this._blastOutput_querySeq = blastOutput_querySeq;
    }

    /**
     * Sets the value of field 'blastOutput_reference'.
     * 
     * @param blastOutput_reference the value of field
     * 'blastOutput_reference'.
     */
    public void setBlastOutput_reference(
            final java.lang.String blastOutput_reference) {
        this._blastOutput_reference = blastOutput_reference;
    }

    /**
     * Sets the value of field 'blastOutput_version'.
     * 
     * @param blastOutput_version the value of field
     * 'blastOutput_version'.
     */
    public void setBlastOutput_version(
            final java.lang.String blastOutput_version) {
        this._blastOutput_version = blastOutput_version;
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {

        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.BlastOutput.class, reader);
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
