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
 * Class Parameters.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Parameters implements java.io.Serializable {


      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
   * 
   */
  private static final long serialVersionUID = 2262958678652059978L;

    /**
     * Field _parameters_matrix.
     */
    private java.lang.String _parameters_matrix;

    /**
     * Field _parameters_expect.
     */
    private double _parameters_expect;

    /**
     * keeps track of state for field: _parameters_expect
     */
    private boolean _has_parameters_expect;

    /**
     * Field _parameters_include.
     */
    private double _parameters_include;

    /**
     * keeps track of state for field: _parameters_include
     */
    private boolean _has_parameters_include;

    /**
     * Field _parameters_scMatch.
     */
    private long _parameters_scMatch;

    /**
     * keeps track of state for field: _parameters_scMatch
     */
    private boolean _has_parameters_scMatch;

    /**
     * Field _parameters_scMismatch.
     */
    private long _parameters_scMismatch;

    /**
     * keeps track of state for field: _parameters_scMismatch
     */
    private boolean _has_parameters_scMismatch;

    /**
     * Field _parameters_gapOpen.
     */
    private long _parameters_gapOpen;

    /**
     * keeps track of state for field: _parameters_gapOpen
     */
    private boolean _has_parameters_gapOpen;

    /**
     * Field _parameters_gapExtend.
     */
    private long _parameters_gapExtend;

    /**
     * keeps track of state for field: _parameters_gapExtend
     */
    private boolean _has_parameters_gapExtend;

    /**
     * Field _parameters_filter.
     */
    private java.lang.String _parameters_filter;

    /**
     * Field _parameters_pattern.
     */
    private java.lang.String _parameters_pattern;

    /**
     * Field _parameters_entrezQuery.
     */
    private java.lang.String _parameters_entrezQuery;


      //----------------/
     //- Constructors -/
    //----------------/

    public Parameters() {
        super();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     */
    public void deleteParameters_expect(
    ) {
        this._has_parameters_expect= false;
    }

    /**
     */
    public void deleteParameters_gapExtend(
    ) {
        this._has_parameters_gapExtend= false;
    }

    /**
     */
    public void deleteParameters_gapOpen(
    ) {
        this._has_parameters_gapOpen= false;
    }

    /**
     */
    public void deleteParameters_include(
    ) {
        this._has_parameters_include= false;
    }

    /**
     */
    public void deleteParameters_scMatch(
    ) {
        this._has_parameters_scMatch= false;
    }

    /**
     */
    public void deleteParameters_scMismatch(
    ) {
        this._has_parameters_scMismatch= false;
    }

    /**
     * Returns the value of field 'parameters_entrezQuery'.
     * 
     * @return the value of field 'Parameters_entrezQuery'.
     */
    public java.lang.String getParameters_entrezQuery(
    ) {
        return this._parameters_entrezQuery;
    }

    /**
     * Returns the value of field 'parameters_expect'.
     * 
     * @return the value of field 'Parameters_expect'.
     */
    public double getParameters_expect(
    ) {
        return this._parameters_expect;
    }

    /**
     * Returns the value of field 'parameters_filter'.
     * 
     * @return the value of field 'Parameters_filter'.
     */
    public java.lang.String getParameters_filter(
    ) {
        return this._parameters_filter;
    }

    /**
     * Returns the value of field 'parameters_gapExtend'.
     * 
     * @return the value of field 'Parameters_gapExtend'.
     */
    public long getParameters_gapExtend(
    ) {
        return this._parameters_gapExtend;
    }

    /**
     * Returns the value of field 'parameters_gapOpen'.
     * 
     * @return the value of field 'Parameters_gapOpen'.
     */
    public long getParameters_gapOpen(
    ) {
        return this._parameters_gapOpen;
    }

    /**
     * Returns the value of field 'parameters_include'.
     * 
     * @return the value of field 'Parameters_include'.
     */
    public double getParameters_include(
    ) {
        return this._parameters_include;
    }

    /**
     * Returns the value of field 'parameters_matrix'.
     * 
     * @return the value of field 'Parameters_matrix'.
     */
    public java.lang.String getParameters_matrix(
    ) {
        return this._parameters_matrix;
    }

    /**
     * Returns the value of field 'parameters_pattern'.
     * 
     * @return the value of field 'Parameters_pattern'.
     */
    public java.lang.String getParameters_pattern(
    ) {
        return this._parameters_pattern;
    }

    /**
     * Returns the value of field 'parameters_scMatch'.
     * 
     * @return the value of field 'Parameters_scMatch'.
     */
    public long getParameters_scMatch(
    ) {
        return this._parameters_scMatch;
    }

    /**
     * Returns the value of field 'parameters_scMismatch'.
     * 
     * @return the value of field 'Parameters_scMismatch'.
     */
    public long getParameters_scMismatch(
    ) {
        return this._parameters_scMismatch;
    }

    /**
     * Method hasParameters_expect.
     * 
     * @return true if at least one Parameters_expect has been added
     */
    public boolean hasParameters_expect(
    ) {
        return this._has_parameters_expect;
    }

    /**
     * Method hasParameters_gapExtend.
     * 
     * @return true if at least one Parameters_gapExtend has been
     * added
     */
    public boolean hasParameters_gapExtend(
    ) {
        return this._has_parameters_gapExtend;
    }

    /**
     * Method hasParameters_gapOpen.
     * 
     * @return true if at least one Parameters_gapOpen has been adde
     */
    public boolean hasParameters_gapOpen(
    ) {
        return this._has_parameters_gapOpen;
    }

    /**
     * Method hasParameters_include.
     * 
     * @return true if at least one Parameters_include has been adde
     */
    public boolean hasParameters_include(
    ) {
        return this._has_parameters_include;
    }

    /**
     * Method hasParameters_scMatch.
     * 
     * @return true if at least one Parameters_scMatch has been adde
     */
    public boolean hasParameters_scMatch(
    ) {
        return this._has_parameters_scMatch;
    }

    /**
     * Method hasParameters_scMismatch.
     * 
     * @return true if at least one Parameters_scMismatch has been
     * added
     */
    public boolean hasParameters_scMismatch(
    ) {
        return this._has_parameters_scMismatch;
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
     * Sets the value of field 'parameters_entrezQuery'.
     * 
     * @param parameters_entrezQuery the value of field
     * 'parameters_entrezQuery'.
     */
    public void setParameters_entrezQuery(
            final java.lang.String parameters_entrezQuery) {
        this._parameters_entrezQuery = parameters_entrezQuery;
    }

    /**
     * Sets the value of field 'parameters_expect'.
     * 
     * @param parameters_expect the value of field
     * 'parameters_expect'.
     */
    public void setParameters_expect(
            final double parameters_expect) {
        this._parameters_expect = parameters_expect;
        this._has_parameters_expect = true;
    }

    /**
     * Sets the value of field 'parameters_filter'.
     * 
     * @param parameters_filter the value of field
     * 'parameters_filter'.
     */
    public void setParameters_filter(
            final java.lang.String parameters_filter) {
        this._parameters_filter = parameters_filter;
    }

    /**
     * Sets the value of field 'parameters_gapExtend'.
     * 
     * @param parameters_gapExtend the value of field
     * 'parameters_gapExtend'.
     */
    public void setParameters_gapExtend(
            final long parameters_gapExtend) {
        this._parameters_gapExtend = parameters_gapExtend;
        this._has_parameters_gapExtend = true;
    }

    /**
     * Sets the value of field 'parameters_gapOpen'.
     * 
     * @param parameters_gapOpen the value of field
     * 'parameters_gapOpen'.
     */
    public void setParameters_gapOpen(
            final long parameters_gapOpen) {
        this._parameters_gapOpen = parameters_gapOpen;
        this._has_parameters_gapOpen = true;
    }

    /**
     * Sets the value of field 'parameters_include'.
     * 
     * @param parameters_include the value of field
     * 'parameters_include'.
     */
    public void setParameters_include(
            final double parameters_include) {
        this._parameters_include = parameters_include;
        this._has_parameters_include = true;
    }

    /**
     * Sets the value of field 'parameters_matrix'.
     * 
     * @param parameters_matrix the value of field
     * 'parameters_matrix'.
     */
    public void setParameters_matrix(
            final java.lang.String parameters_matrix) {
        this._parameters_matrix = parameters_matrix;
    }

    /**
     * Sets the value of field 'parameters_pattern'.
     * 
     * @param parameters_pattern the value of field
     * 'parameters_pattern'.
     */
    public void setParameters_pattern(
            final java.lang.String parameters_pattern) {
        this._parameters_pattern = parameters_pattern;
    }

    /**
     * Sets the value of field 'parameters_scMatch'.
     * 
     * @param parameters_scMatch the value of field
     * 'parameters_scMatch'.
     */
    public void setParameters_scMatch(
            final long parameters_scMatch) {
        this._parameters_scMatch = parameters_scMatch;
        this._has_parameters_scMatch = true;
    }

    /**
     * Sets the value of field 'parameters_scMismatch'.
     * 
     * @param parameters_scMismatch the value of field
     * 'parameters_scMismatch'.
     */
    public void setParameters_scMismatch(
            final long parameters_scMismatch) {
        this._parameters_scMismatch = parameters_scMismatch;
        this._has_parameters_scMismatch = true;
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.Parameters
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Parameters unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Parameters) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Parameters.class, reader);
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
