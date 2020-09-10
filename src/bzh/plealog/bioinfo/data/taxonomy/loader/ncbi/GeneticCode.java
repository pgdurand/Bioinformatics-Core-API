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
    "gcId",
    "gcName"
})
@XmlRootElement(name = "GeneticCode")
public class GeneticCode {

    @XmlElement(name = "GCId", required = true)
    protected String gcId;
    @XmlElement(name = "GCName", required = true)
    protected String gcName;

    /**
     * Obtient la valeur de la propriété gcId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGCId() {
        return gcId;
    }

    /**
     * Définit la valeur de la propriété gcId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGCId(String value) {
        this.gcId = value;
    }

    /**
     * Obtient la valeur de la propriété gcName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGCName() {
        return gcName;
    }

    /**
     * Définit la valeur de la propriété gcName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGCName(String value) {
        this.gcName = value;
    }

}
