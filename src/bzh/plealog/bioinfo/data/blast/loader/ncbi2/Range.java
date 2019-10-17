//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.10.17 à 11:24:31 AM CEST 
//


package bzh.plealog.bioinfo.data.blast.loader.ncbi2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour anonymous complex type.
 * 
 * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Range_from" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Range_to" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "rangeFrom",
    "rangeTo"
})
@XmlRootElement(name = "Range")
public class Range {

    @XmlElement(name = "from", required = true)
    protected Integer rangeFrom;
    @XmlElement(name = "to", required = true)
    protected Integer rangeTo;

    /**
     * Obtient la valeur de la propriété rangeFrom.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRangeFrom() {
        return rangeFrom;
    }

    /**
     * Définit la valeur de la propriété rangeFrom.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRangeFrom(Integer value) {
        this.rangeFrom = value;
    }

    /**
     * Obtient la valeur de la propriété rangeTo.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRangeTo() {
        return rangeTo;
    }

    /**
     * Définit la valeur de la propriété rangeTo.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRangeTo(Integer value) {
        this.rangeTo = value;
    }

}
