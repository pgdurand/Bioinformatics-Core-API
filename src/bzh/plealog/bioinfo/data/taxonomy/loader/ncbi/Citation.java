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
    "citId",
    "citKey",
    "citUrl",
    "citText",
    "citPubmedId",
    "citMedlineId"
})
@XmlRootElement(name = "Citation")
public class Citation {

    @XmlElement(name = "CitId", required = true)
    protected String citId;
    @XmlElement(name = "CitKey", required = true)
    protected String citKey;
    @XmlElement(name = "CitUrl")
    protected String citUrl;
    @XmlElement(name = "CitText")
    protected String citText;
    @XmlElement(name = "CitPubmedId")
    protected String citPubmedId;
    @XmlElement(name = "CitMedlineId")
    protected String citMedlineId;

    /**
     * Obtient la valeur de la propriété citId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitId() {
        return citId;
    }

    /**
     * Définit la valeur de la propriété citId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitId(String value) {
        this.citId = value;
    }

    /**
     * Obtient la valeur de la propriété citKey.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitKey() {
        return citKey;
    }

    /**
     * Définit la valeur de la propriété citKey.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitKey(String value) {
        this.citKey = value;
    }

    /**
     * Obtient la valeur de la propriété citUrl.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitUrl() {
        return citUrl;
    }

    /**
     * Définit la valeur de la propriété citUrl.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitUrl(String value) {
        this.citUrl = value;
    }

    /**
     * Obtient la valeur de la propriété citText.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitText() {
        return citText;
    }

    /**
     * Définit la valeur de la propriété citText.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitText(String value) {
        this.citText = value;
    }

    /**
     * Obtient la valeur de la propriété citPubmedId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitPubmedId() {
        return citPubmedId;
    }

    /**
     * Définit la valeur de la propriété citPubmedId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitPubmedId(String value) {
        this.citPubmedId = value;
    }

    /**
     * Obtient la valeur de la propriété citMedlineId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCitMedlineId() {
        return citMedlineId;
    }

    /**
     * Définit la valeur de la propriété citMedlineId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCitMedlineId(String value) {
        this.citMedlineId = value;
    }

}
