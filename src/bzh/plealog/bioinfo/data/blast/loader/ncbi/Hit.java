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
 * Class Hit.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Hit implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = -7089665719253506386L;

    /**
     * Field _hit_num.
     */
    private long _hit_num;

    /**
     * keeps track of state for field: _hit_num
     */
    private boolean _has_hit_num;

    /**
     * Field _hit_id.
     */
    private java.lang.String _hit_id;

    /**
     * Field _hit_def.
     */
    private java.lang.String _hit_def;

    /**
     * Field _hit_accession.
     */
    private java.lang.String _hit_accession;

    /**
     * Field _hit_len.
     */
    private long _hit_len;

    /**
     * keeps track of state for field: _hit_len
     */
    private boolean _has_hit_len;

    /**
     * Field _hit_hsps.
     */
    private bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps _hit_hsps;


      //----------------/
     //- Constructors -/
    //----------------/

    public Hit() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteHit_len(
    ) {
        this._has_hit_len= false;
    }

    /**
     */
    public void deleteHit_num(
    ) {
        this._has_hit_num= false;
    }

    /**
     * Returns the value of field 'hit_accession'.
     * 
     * @return the value of field 'Hit_accession'.
     */
    public java.lang.String getHit_accession(
    ) {
        return this._hit_accession;
    }

    /**
     * Returns the value of field 'hit_def'.
     * 
     * @return the value of field 'Hit_def'.
     */
    public java.lang.String getHit_def(
    ) {
        return this._hit_def;
    }

    /**
     * Returns the value of field 'hit_hsps'.
     * 
     * @return the value of field 'Hit_hsps'.
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps getHit_hsps(
    ) {
        return this._hit_hsps;
    }

    /**
     * Returns the value of field 'hit_id'.
     * 
     * @return the value of field 'Hit_id'.
     */
    public java.lang.String getHit_id(
    ) {
        return this._hit_id;
    }

    /**
     * Returns the value of field 'hit_len'.
     * 
     * @return the value of field 'Hit_len'.
     */
    public long getHit_len(
    ) {
        return this._hit_len;
    }

    /**
     * Returns the value of field 'hit_num'.
     * 
     * @return the value of field 'Hit_num'.
     */
    public long getHit_num(
    ) {
        return this._hit_num;
    }

    /**
     * Method hasHit_len.
     * 
     * @return true if at least one Hit_len has been added
     */
    public boolean hasHit_len(
    ) {
        return this._has_hit_len;
    }

    /**
     * Method hasHit_num.
     * 
     * @return true if at least one Hit_num has been added
     */
    public boolean hasHit_num(
    ) {
        return this._has_hit_num;
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
     * Sets the value of field 'hit_accession'.
     * 
     * @param hit_accession the value of field 'hit_accession'.
     */
    public void setHit_accession(
            final java.lang.String hit_accession) {
        this._hit_accession = hit_accession;
    }

    /**
     * Sets the value of field 'hit_def'.
     * 
     * @param hit_def the value of field 'hit_def'.
     */
    public void setHit_def(
            final java.lang.String hit_def) {
        this._hit_def = hit_def;
    }

    /**
     * Sets the value of field 'hit_hsps'.
     * 
     * @param hit_hsps the value of field 'hit_hsps'.
     */
    public void setHit_hsps(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps hit_hsps) {
        this._hit_hsps = hit_hsps;
    }

    /**
     * Sets the value of field 'hit_id'.
     * 
     * @param hit_id the value of field 'hit_id'.
     */
    public void setHit_id(
            final java.lang.String hit_id) {
        this._hit_id = hit_id;
    }

    /**
     * Sets the value of field 'hit_len'.
     * 
     * @param hit_len the value of field 'hit_len'.
     */
    public void setHit_len(
            final long hit_len) {
        this._hit_len = hit_len;
        this._has_hit_len = true;
    }

    /**
     * Sets the value of field 'hit_num'.
     * 
     * @param hit_num the value of field 'hit_num'.
     */
    public void setHit_num(
            final long hit_num) {
        this._hit_num = hit_num;
        this._has_hit_num = true;
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.Hit
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit.class, reader);
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
