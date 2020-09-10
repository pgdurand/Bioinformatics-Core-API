//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.11.01 à 06:32:58 PM CET 
//


package bzh.plealog.bioinfo.data.taxonomy.loader.ncbi;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "classCDE",
    "dispName",
    "uniqueName"
})
@XmlRootElement(name = "Name")
public class Name {

    @XmlElement(name = "ClassCDE", required = true)
    protected String classCDE;
    @XmlElement(name = "DispName", required = true)
    protected String dispName;
    @XmlElement(name = "UniqueName")
    protected String uniqueName;

    /**
     * Obtient la valeur de la propriété classCDE.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCDE() {
        return classCDE;
    }

    /**
     * Définit la valeur de la propriété classCDE.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCDE(String value) {
        this.classCDE = value;
    }

    /**
     * Obtient la valeur de la propriété dispName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDispName() {
        return dispName;
    }

    /**
     * Définit la valeur de la propriété dispName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDispName(String value) {
        this.dispName = value;
    }

    /**
     * Obtient la valeur de la propriété uniqueName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUniqueName() {
        return uniqueName;
    }

    /**
     * Définit la valeur de la propriété uniqueName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUniqueName(String value) {
        this.uniqueName = value;
    }

}
