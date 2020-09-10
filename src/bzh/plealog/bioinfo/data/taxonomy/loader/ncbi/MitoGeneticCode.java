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
    "mgcId",
    "mgcName"
})
@XmlRootElement(name = "MitoGeneticCode")
public class MitoGeneticCode {

    @XmlElement(name = "MGCId", required = true)
    protected String mgcId;
    @XmlElement(name = "MGCName", required = true)
    protected String mgcName;

    /**
     * Obtient la valeur de la propriété mgcId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMGCId() {
        return mgcId;
    }

    /**
     * Définit la valeur de la propriété mgcId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMGCId(String value) {
        this.mgcId = value;
    }

    /**
     * Obtient la valeur de la propriété mgcName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMGCName() {
        return mgcName;
    }

    /**
     * Définit la valeur de la propriété mgcName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMGCName(String value) {
        this.mgcName = value;
    }

}
