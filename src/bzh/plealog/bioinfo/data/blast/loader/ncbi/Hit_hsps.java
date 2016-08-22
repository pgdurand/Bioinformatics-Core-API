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
 * Class Hit_hsps.
 * 
 * @version $Revision: 1.2 $ $Date: 2007/07/26 15:23:45 $
 */
public class Hit_hsps implements java.io.Serializable {


      /**
   * 
   */
  private static final long serialVersionUID = -7997545103310132022L;
      //--------------------------/
     //- Class/Member Variables -/
    //--------------------------/

    /**
     * Field _hspList.
     */
    private java.util.List<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp> _hspList;


      //----------------/
     //- Constructors -/
    //----------------/

    public Hit_hsps() {
        super();
        this._hspList = new java.util.ArrayList<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp>();
    }


      //-----------/
     //- Methods -/
    //-----------/

    /**
     * 
     * 
     * @param vHsp
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHsp(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp vHsp)
    throws java.lang.IndexOutOfBoundsException {
        this._hspList.add(vHsp);
    }

    /**
     * 
     * 
     * @param index
     * @param vHsp
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void addHsp(
            final int index,
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp vHsp)
    throws java.lang.IndexOutOfBoundsException {
        this._hspList.add(index, vHsp);
    }

    /**
     * Method enumerateHsp.
     * 
     * @return an Enumeration over all possible elements of this
     * collection
     */
    public java.util.Enumeration<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp> enumerateHsp(
    ) {
        return java.util.Collections.enumeration(this._hspList);
    }

    /**
     * Method getHsp.
     * 
     * @param index
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     * @return the value of the
     * com.plealog.bioinfo.data.blast.loader.ncbi.Hsp at the
     * given index
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp getHsp(
            final int index)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._hspList.size()) {
            throw new IndexOutOfBoundsException("getHsp: Index value '" + index + "' not in range [0.." + (this._hspList.size() - 1) + "]");
        }
        
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp) _hspList.get(index);
    }

    /**
     * Method getHsp.Returns the contents of the collection in an
     * Array.  <p>Note:  Just in case the collection contents are
     * changing in another thread, we pass a 0-length Array of the
     * correct type into the API call.  This way we <i>know</i>
     * that the Array returned is of exactly the correct length.
     * 
     * @return this collection as an Array
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp[] getHsp(
    ) {
        bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp[] array = new bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp[0];
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp[]) this._hspList.toArray(array);
    }

    /**
     * Method getHspCount.
     * 
     * @return the size of this collection
     */
    public int getHspCount(
    ) {
        return this._hspList.size();
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
     * Method iterateHsp.
     * 
     * @return an Iterator over all possible elements in this
     * collection
     */
    public java.util.Iterator<bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp> iterateHsp(
    ) {
        return this._hspList.iterator();
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
     */
    public void removeAllHsp(
    ) {
        this._hspList.clear();
    }

    /**
     * Method removeHsp.
     * 
     * @param vHsp
     * @return true if the object was removed from the collection.
     */
    public boolean removeHsp(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp vHsp) {
        boolean removed = _hspList.remove(vHsp);
        return removed;
    }

    /**
     * Method removeHspAt.
     * 
     * @param index
     * @return the element removed from the collection
     */
    public bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp removeHspAt(
            final int index) {
        java.lang.Object obj = this._hspList.remove(index);
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp) obj;
    }

    /**
     * 
     * 
     * @param index
     * @param vHsp
     * @throws java.lang.IndexOutOfBoundsException if the index
     * given is outside the bounds of the collection
     */
    public void setHsp(
            final int index,
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp vHsp)
    throws java.lang.IndexOutOfBoundsException {
        // check bounds for index
        if (index < 0 || index >= this._hspList.size()) {
            throw new IndexOutOfBoundsException("setHsp: Index value '" + index + "' not in range [0.." + (this._hspList.size() - 1) + "]");
        }
        
        this._hspList.set(index, vHsp);
    }

    /**
     * 
     * 
     * @param vHspArray
     */
    public void setHsp(
            final bzh.plealog.bioinfo.data.blast.loader.ncbi.Hsp[] vHspArray) {
        //-- copy array
        _hspList.clear();
        
        for (int i = 0; i < vHspArray.length; i++) {
                this._hspList.add(vHspArray[i]);
        }
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
     * com.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps
     */
    public static bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps unmarshal(
            final java.io.Reader reader)
    throws org.exolab.castor.xml.MarshalException, org.exolab.castor.xml.ValidationException {
        return (bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps) Unmarshaller.unmarshal(bzh.plealog.bioinfo.data.blast.loader.ncbi.Hit_hsps.class, reader);
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
