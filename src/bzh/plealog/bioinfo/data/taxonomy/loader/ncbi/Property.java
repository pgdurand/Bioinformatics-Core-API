//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.11.01 à 06:32:58 PM CET 
//


package bzh.plealog.bioinfo.data.taxonomy.loader.ncbi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "propName",
    "propValueIntOrPropValueBoolOrPropValueString"
})
@XmlRootElement(name = "Property")
public class Property {

    @XmlElement(name = "PropName", required = true)
    protected String propName;
    @XmlElements({
        @XmlElement(name = "PropValueInt", required = true, type = PropValueInt.class),
        @XmlElement(name = "PropValueBool", required = true, type = PropValueBool.class),
        @XmlElement(name = "PropValueString", required = true, type = PropValueString.class)
    })
    protected List<Object> propValueIntOrPropValueBoolOrPropValueString;

    /**
     * Obtient la valeur de la propriété propName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropName() {
        return propName;
    }

    /**
     * Définit la valeur de la propriété propName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropName(String value) {
        this.propName = value;
    }

    /**
     * Gets the value of the propValueIntOrPropValueBoolOrPropValueString property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the propValueIntOrPropValueBoolOrPropValueString property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPropValueIntOrPropValueBoolOrPropValueString().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropValueInt }
     * {@link PropValueBool }
     * {@link PropValueString }
     * 
     * 
     */
    public List<Object> getPropValueIntOrPropValueBoolOrPropValueString() {
        if (propValueIntOrPropValueBoolOrPropValueString == null) {
            propValueIntOrPropValueBoolOrPropValueString = new ArrayList<Object>();
        }
        return this.propValueIntOrPropValueBoolOrPropValueString;
    }

}
