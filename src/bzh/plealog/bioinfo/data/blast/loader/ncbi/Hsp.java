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
 * Class Hsp.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Hsp implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = -4209206884575477033L;

    /**
     * Field _hsp_num.
     */
    private long _hsp_num;

    /**
     * keeps track of state for field: _hsp_num
     */
    private boolean _has_hsp_num;

    /**
     * Field _hsp_bitScore.
     */
    private double _hsp_bitScore;

    /**
     * keeps track of state for field: _hsp_bitScore
     */
    private boolean _has_hsp_bitScore;

    /**
     * Field _hsp_score.
     */
    private double _hsp_score;

    /**
     * keeps track of state for field: _hsp_score
     */
    private boolean _has_hsp_score;

    /**
     * Field _hsp_evalue.
     */
    private double _hsp_evalue;

    /**
     * keeps track of state for field: _hsp_evalue
     */
    private boolean _has_hsp_evalue;

    /**
     * Field _hsp_queryFrom.
     */
    private long _hsp_queryFrom;

    /**
     * keeps track of state for field: _hsp_queryFrom
     */
    private boolean _has_hsp_queryFrom;

    /**
     * Field _hsp_queryTo.
     */
    private long _hsp_queryTo;

    /**
     * keeps track of state for field: _hsp_queryTo
     */
    private boolean _has_hsp_queryTo;

    /**
     * Field _hsp_hitFrom.
     */
    private long _hsp_hitFrom;

    /**
     * keeps track of state for field: _hsp_hitFrom
     */
    private boolean _has_hsp_hitFrom;

    /**
     * Field _hsp_hitTo.
     */
    private long _hsp_hitTo;

    /**
     * keeps track of state for field: _hsp_hitTo
     */
    private boolean _has_hsp_hitTo;

    /**
     * Field _hsp_patternFrom.
     */
    private long _hsp_patternFrom;

    /**
     * keeps track of state for field: _hsp_patternFrom
     */
    private boolean _has_hsp_patternFrom;

    /**
     * Field _hsp_patternTo.
     */
    private long _hsp_patternTo;

    /**
     * keeps track of state for field: _hsp_patternTo
     */
    private boolean _has_hsp_patternTo;

    /**
     * Field _hsp_queryFrame.
     */
    private long _hsp_queryFrame;

    /**
     * keeps track of state for field: _hsp_queryFrame
     */
    private boolean _has_hsp_queryFrame;

    /**
     * Field _hsp_hitFrame.
     */
    private long _hsp_hitFrame;

    /**
     * keeps track of state for field: _hsp_hitFrame
     */
    private boolean _has_hsp_hitFrame;

    /**
     * Field _hsp_identity.
     */
    private long _hsp_identity;

    /**
     * keeps track of state for field: _hsp_identity
     */
    private boolean _has_hsp_identity;

    /**
     * Field _hsp_positive.
     */
    private long _hsp_positive;

    /**
     * keeps track of state for field: _hsp_positive
     */
    private boolean _has_hsp_positive;

    /**
     * Field _hsp_gaps.
     */
    private long _hsp_gaps;

    /**
     * keeps track of state for field: _hsp_gaps
     */
    private boolean _has_hsp_gaps;

    /**
     * Field _hsp_alignLen.
     */
    private long _hsp_alignLen;

    /**
     * keeps track of state for field: _hsp_alignLen
     */
    private boolean _has_hsp_alignLen;

    /**
     * Field _hsp_density.
     */
    private long _hsp_density;

    /**
     * keeps track of state for field: _hsp_density
     */
    private boolean _has_hsp_density;

    /**
     * Field _hsp_qseq.
     */
    private java.lang.String _hsp_qseq;

    /**
     * Field _hsp_hseq.
     */
    private java.lang.String _hsp_hseq;

    /**
     * Field _hsp_midline.
     */
    private java.lang.String _hsp_midline;


      //----------------/
     //- Constructors -/
    //----------------/

    public Hsp() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteHsp_alignLen(
    ) {
        this._has_hsp_alignLen= false;
    }

    /**
     */
    public void deleteHsp_bitScore(
    ) {
        this._has_hsp_bitScore= false;
    }

    /**
     */
    public void deleteHsp_density(
    ) {
        this._has_hsp_density= false;
    }

    /**
     */
    public void deleteHsp_evalue(
    ) {
        this._has_hsp_evalue= false;
    }

    /**
     */
    public void deleteHsp_gaps(
    ) {
        this._has_hsp_gaps= false;
    }

    /**
     */
    public void deleteHsp_hitFrame(
    ) {
        this._has_hsp_hitFrame= false;
    }

    /**
     */
    public void deleteHsp_hitFrom(
    ) {
        this._has_hsp_hitFrom= false;
    }

    /**
     */
    public void deleteHsp_hitTo(
    ) {
        this._has_hsp_hitTo= false;
    }

    /**
     */
    public void deleteHsp_identity(
    ) {
        this._has_hsp_identity= false;
    }

    /**
     */
    public void deleteHsp_num(
    ) {
        this._has_hsp_num= false;
    }

    /**
     */
    public void deleteHsp_patternFrom(
    ) {
        this._has_hsp_patternFrom= false;
    }

    /**
     */
    public void deleteHsp_patternTo(
    ) {
        this._has_hsp_patternTo= false;
    }

    /**
     */
    public void deleteHsp_positive(
    ) {
        this._has_hsp_positive= false;
    }

    /**
     */
    public void deleteHsp_queryFrame(
    ) {
        this._has_hsp_queryFrame= false;
    }

    /**
     */
    public void deleteHsp_queryFrom(
    ) {
        this._has_hsp_queryFrom= false;
    }

    /**
     */
    public void deleteHsp_queryTo(
    ) {
        this._has_hsp_queryTo= false;
    }

    /**
     */
    public void deleteHsp_score(
    ) {
        this._has_hsp_score= false;
    }

    /**
     * Returns the value of field 'hsp_alignLen'.
     * 
     * @return the value of field 'Hsp_alignLen'.
     */
    public long getHsp_alignLen(
    ) {
        return this._hsp_alignLen;
    }

    /**
     * Returns the value of field 'hsp_bitScore'.
     * 
     * @return the value of field 'Hsp_bitScore'.
     */
    public double getHsp_bitScore(
    ) {
        return this._hsp_bitScore;
    }

    /**
     * Returns the value of field 'hsp_density'.
     * 
     * @return the value of field 'Hsp_density'.
     */
    public long getHsp_density(
    ) {
        return this._hsp_density;
    }

    /**
     * Returns the value of field 'hsp_evalue'.
     * 
     * @return the value of field 'Hsp_evalue'.
     */
    public double getHsp_evalue(
    ) {
        return this._hsp_evalue;
    }

    /**
     * Returns the value of field 'hsp_gaps'.
     * 
     * @return the value of field 'Hsp_gaps'.
     */
    public long getHsp_gaps(
    ) {
        return this._hsp_gaps;
    }

    /**
     * Returns the value of field 'hsp_hitFrame'.
     * 
     * @return the value of field 'Hsp_hitFrame'.
     */
    public long getHsp_hitFrame(
    ) {
        return this._hsp_hitFrame;
    }

    /**
     * Returns the value of field 'hsp_hitFrom'.
     * 
     * @return the value of field 'Hsp_hitFrom'.
     */
    public long getHsp_hitFrom(
    ) {
        return this._hsp_hitFrom;
    }

    /**
     * Returns the value of field 'hsp_hitTo'.
     * 
     * @return the value of field 'Hsp_hitTo'.
     */
    public long getHsp_hitTo(
    ) {
        return this._hsp_hitTo;
    }

    /**
     * Returns the value of field 'hsp_hseq'.
     * 
     * @return the value of field 'Hsp_hseq'.
     */
    public java.lang.String getHsp_hseq(
    ) {
        return this._hsp_hseq;
    }

    /**
     * Returns the value of field 'hsp_identity'.
     * 
     * @return the value of field 'Hsp_identity'.
     */
    public long getHsp_identity(
    ) {
        return this._hsp_identity;
    }

    /**
     * Returns the value of field 'hsp_midline'.
     * 
     * @return the value of field 'Hsp_midline'.
     */
    public java.lang.String getHsp_midline(
    ) {
        return this._hsp_midline;
    }

    /**
     * Returns the value of field 'hsp_num'.
     * 
     * @return the value of field 'Hsp_num'.
     */
    public long getHsp_num(
    ) {
        return this._hsp_num;
    }

    /**
     * Returns the value of field 'hsp_patternFrom'.
     * 
     * @return the value of field 'Hsp_patternFrom'.
     */
    public long getHsp_patternFrom(
    ) {
        return this._hsp_patternFrom;
    }

    /**
     * Returns the value of field 'hsp_patternTo'.
     * 
     * @return the value of field 'Hsp_patternTo'.
     */
    public long getHsp_patternTo(
    ) {
        return this._hsp_patternTo;
    }

    /**
     * Returns the value of field 'hsp_positive'.
     * 
     * @return the value of field 'Hsp_positive'.
     */
    public long getHsp_positive(
    ) {
        return this._hsp_positive;
    }

    /**
     * Returns the value of field 'hsp_qseq'.
     * 
     * @return the value of field 'Hsp_qseq'.
     */
    public java.lang.String getHsp_qseq(
    ) {
        return this._hsp_qseq;
    }

    /**
     * Returns the value of field 'hsp_queryFrame'.
     * 
     * @return the value of field 'Hsp_queryFrame'.
     */
    public long getHsp_queryFrame(
    ) {
        return this._hsp_queryFrame;
    }

    /**
     * Returns the value of field 'hsp_queryFrom'.
     * 
     * @return the value of field 'Hsp_queryFrom'.
     */
    public long getHsp_queryFrom(
    ) {
        return this._hsp_queryFrom;
    }

    /**
     * Returns the value of field 'hsp_queryTo'.
     * 
     * @return the value of field 'Hsp_queryTo'.
     */
    public long getHsp_queryTo(
    ) {
        return this._hsp_queryTo;
    }

    /**
     * Returns the value of field 'hsp_score'.
     * 
     * @return the value of field 'Hsp_score'.
     */
    public double getHsp_score(
    ) {
        return this._hsp_score;
    }

    /**
     * Method hasHsp_alignLen.
     * 
     * @return true if at least one Hsp_alignLen has been added
     */
    public boolean hasHsp_alignLen(
    ) {
        return this._has_hsp_alignLen;
    }

    /**
     * Method hasHsp_bitScore.
     * 
     * @return true if at least one Hsp_bitScore has been added
     */
    public boolean hasHsp_bitScore(
    ) {
        return this._has_hsp_bitScore;
    }

    /**
     * Method hasHsp_density.
     * 
     * @return true if at least one Hsp_density has been added
     */
    public boolean hasHsp_density(
    ) {
        return this._has_hsp_density;
    }

    /**
     * Method hasHsp_evalue.
     * 
     * @return true if at least one Hsp_evalue has been added
     */
    public boolean hasHsp_evalue(
    ) {
        return this._has_hsp_evalue;
    }

    /**
     * Method hasHsp_gaps.
     * 
     * @return true if at least one Hsp_gaps has been added
     */
    public boolean hasHsp_gaps(
    ) {
        return this._has_hsp_gaps;
    }

    /**
     * Method hasHsp_hitFrame.
     * 
     * @return true if at least one Hsp_hitFrame has been added
     */
    public boolean hasHsp_hitFrame(
    ) {
        return this._has_hsp_hitFrame;
    }

    /**
     * Method hasHsp_hitFrom.
     * 
     * @return true if at least one Hsp_hitFrom has been added
     */
    public boolean hasHsp_hitFrom(
    ) {
        return this._has_hsp_hitFrom;
    }

    /**
     * Method hasHsp_hitTo.
     * 
     * @return true if at least one Hsp_hitTo has been added
     */
    public boolean hasHsp_hitTo(
    ) {
        return this._has_hsp_hitTo;
    }

    /**
     * Method hasHsp_identity.
     * 
     * @return true if at least one Hsp_identity has been added
     */
    public boolean hasHsp_identity(
    ) {
        return this._has_hsp_identity;
    }

    /**
     * Method hasHsp_num.
     * 
     * @return true if at least one Hsp_num has been added
     */
    public boolean hasHsp_num(
    ) {
        return this._has_hsp_num;
    }

    /**
     * Method hasHsp_patternFrom.
     * 
     * @return true if at least one Hsp_patternFrom has been added
     */
    public boolean hasHsp_patternFrom(
    ) {
        return this._has_hsp_patternFrom;
    }

    /**
     * Method hasHsp_patternTo.
     * 
     * @return true if at least one Hsp_patternTo has been added
     */
    public boolean hasHsp_patternTo(
    ) {
        return this._has_hsp_patternTo;
    }

    /**
     * Method hasHsp_positive.
     * 
     * @return true if at least one Hsp_positive has been added
     */
    public boolean hasHsp_positive(
    ) {
        return this._has_hsp_positive;
    }

    /**
     * Method hasHsp_queryFrame.
     * 
     * @return true if at least one Hsp_queryFrame has been added
     */
    public boolean hasHsp_queryFrame(
    ) {
        return this._has_hsp_queryFrame;
    }

    /**
     * Method hasHsp_queryFrom.
     * 
     * @return true if at least one Hsp_queryFrom has been added
     */
    public boolean hasHsp_queryFrom(
    ) {
        return this._has_hsp_queryFrom;
    }

    /**
     * Method hasHsp_queryTo.
     * 
     * @return true if at least one Hsp_queryTo has been added
     */
    public boolean hasHsp_queryTo(
    ) {
        return this._has_hsp_queryTo;
    }

    /**
     * Method hasHsp_score.
     * 
     * @return true if at least one Hsp_score has been added
     */
    public boolean hasHsp_score(
    ) {
        return this._has_hsp_score;
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
     * Sets the value of field 'hsp_alignLen'.
     * 
     * @param hsp_alignLen the value of field 'hsp_alignLen'.
     */
    public void setHsp_alignLen(
            final long hsp_alignLen) {
        this._hsp_alignLen = hsp_alignLen;
        this._has_hsp_alignLen = true;
    }

    /**
     * Sets the value of field 'hsp_bitScore'.
     * 
     * @param hsp_bitScore the value of field 'hsp_bitScore'.
     */
    public void setHsp_bitScore(
            final double hsp_bitScore) {
        this._hsp_bitScore = hsp_bitScore;
        this._has_hsp_bitScore = true;
    }

    /**
     * Sets the value of field 'hsp_density'.
     * 
     * @param hsp_density the value of field 'hsp_density'.
     */
    public void setHsp_density(
            final long hsp_density) {
        this._hsp_density = hsp_density;
        this._has_hsp_density = true;
    }

    /**
     * Sets the value of field 'hsp_evalue'.
     * 
     * @param hsp_evalue the value of field 'hsp_evalue'.
     */
    public void setHsp_evalue(
            final double hsp_evalue) {
        this._hsp_evalue = hsp_evalue;
        this._has_hsp_evalue = true;
    }

    /**
     * Sets the value of field 'hsp_gaps'.
     * 
     * @param hsp_gaps the value of field 'hsp_gaps'.
     */
    public void setHsp_gaps(
            final long hsp_gaps) {
        this._hsp_gaps = hsp_gaps;
        this._has_hsp_gaps = true;
    }

    /**
     * Sets the value of field 'hsp_hitFrame'.
     * 
     * @param hsp_hitFrame the value of field 'hsp_hitFrame'.
     */
    public void setHsp_hitFrame(
            final long hsp_hitFrame) {
        this._hsp_hitFrame = hsp_hitFrame;
        this._has_hsp_hitFrame = true;
    }

    /**
     * Sets the value of field 'hsp_hitFrom'.
     * 
     * @param hsp_hitFrom the value of field 'hsp_hitFrom'.
     */
    public void setHsp_hitFrom(
            final long hsp_hitFrom) {
        this._hsp_hitFrom = hsp_hitFrom;
        this._has_hsp_hitFrom = true;
    }

    /**
     * Sets the value of field 'hsp_hitTo'.
     * 
     * @param hsp_hitTo the value of field 'hsp_hitTo'.
     */
    public void setHsp_hitTo(
            final long hsp_hitTo) {
        this._hsp_hitTo = hsp_hitTo;
        this._has_hsp_hitTo = true;
    }

    /**
     * Sets the value of field 'hsp_hseq'.
     * 
     * @param hsp_hseq the value of field 'hsp_hseq'.
     */
    public void setHsp_hseq(
            final java.lang.String hsp_hseq) {
        this._hsp_hseq = hsp_hseq;
    }

    /**
     * Sets the value of field 'hsp_identity'.
     * 
     * @param hsp_identity the value of field 'hsp_identity'.
     */
    public void setHsp_identity(
            final long hsp_identity) {
        this._hsp_identity = hsp_identity;
        this._has_hsp_identity = true;
    }

    /**
     * Sets the value of field 'hsp_midline'.
     * 
     * @param hsp_midline the value of field 'hsp_midline'.
     */
    public void setHsp_midline(
            final java.lang.String hsp_midline) {
        this._hsp_midline = hsp_midline;
    }

    /**
     * Sets the value of field 'hsp_num'.
     * 
     * @param hsp_num the value of field 'hsp_num'.
     */
    public void setHsp_num(
            final long hsp_num) {
        this._hsp_num = hsp_num;
        this._has_hsp_num = true;
    }

    /**
     * Sets the value of field 'hsp_patternFrom'.
     * 
     * @param hsp_patternFrom the value of field 'hsp_patternFrom'.
     */
    public void setHsp_patternFrom(
            final long hsp_patternFrom) {
        this._hsp_patternFrom = hsp_patternFrom;
        this._has_hsp_patternFrom = true;
    }

    /**
     * Sets the value of field 'hsp_patternTo'.
     * 
     * @param hsp_patternTo the value of field 'hsp_patternTo'.
     */
    public void setHsp_patternTo(
            final long hsp_patternTo) {
        this._hsp_patternTo = hsp_patternTo;
        this._has_hsp_patternTo = true;
    }

    /**
     * Sets the value of field 'hsp_positive'.
     * 
     * @param hsp_positive the value of field 'hsp_positive'.
     */
    public void setHsp_positive(
            final long hsp_positive) {
        this._hsp_positive = hsp_positive;
        this._has_hsp_positive = true;
    }

    /**
     * Sets the value of field 'hsp_qseq'.
     * 
     * @param hsp_qseq the value of field 'hsp_qseq'.
     */
    public void setHsp_qseq(
            final java.lang.String hsp_qseq) {
        this._hsp_qseq = hsp_qseq;
    }

    /**
     * Sets the value of field 'hsp_queryFrame'.
     * 
     * @param hsp_queryFrame the value of field 'hsp_queryFrame'.
     */
    public void setHsp_queryFrame(
            final long hsp_queryFrame) {
        this._hsp_queryFrame = hsp_queryFrame;
        this._has_hsp_queryFrame = true;
    }

    /**
     * Sets the value of field 'hsp_queryFrom'.
     * 
     * @param hsp_queryFrom the value of field 'hsp_queryFrom'.
     */
    public void setHsp_queryFrom(
            final long hsp_queryFrom) {
        this._hsp_queryFrom = hsp_queryFrom;
        this._has_hsp_queryFrom = true;
    }

    /**
     * Sets the value of field 'hsp_queryTo'.
     * 
     * @param hsp_queryTo the value of field 'hsp_queryTo'.
     */
    public void setHsp_queryTo(
            final long hsp_queryTo) {
        this._hsp_queryTo = hsp_queryTo;
        this._has_hsp_queryTo = true;
    }

    /**
     * Sets the value of field 'hsp_score'.
     * 
     * @param hsp_score the value of field 'hsp_score'.
     */
    public void setHsp_score(
            final double hsp_score) {
        this._hsp_score = hsp_score;
        this._has_hsp_score = true;
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.Hsp
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp.class, reader);
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
