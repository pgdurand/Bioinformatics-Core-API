//
// Ce fichier a été généré par l'implémentation de référence JavaTM Architecture for XML Binding (JAXB), v2.2.8-b130911.1802 
// Voir <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Toute modification apportée à ce fichier sera perdue lors de la recompilation du schéma source. 
// Généré le : 2019.10.17 à 11:24:31 AM CEST 
//


package bzh.plealog.bioinfo.data.blast.loader.ncbi2;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="Hit_num" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hit_description">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}HitDescr"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Hit_len" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="Hit_hsps" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element ref="{http://www.ncbi.nlm.nih.gov}Hsp"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "hitNum",
    "hitDescription",
    "hitLen",
    "hitHsps"
})
@XmlRootElement(name = "Hit")
public class Hit {

    @XmlElement(name = "num", required = true)
    protected Integer hitNum;
    @XmlElement(name = "description", required = true)
    protected Hit.HitDescription hitDescription;
    @XmlElement(name = "len", required = true)
    protected Integer hitLen;
    @XmlElement(name = "hsps")
    protected Hit.HitHsps hitHsps;

    /**
     * Obtient la valeur de la propriété hitNum.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHitNum() {
        return hitNum;
    }

    /**
     * Définit la valeur de la propriété hitNum.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHitNum(Integer value) {
        this.hitNum = value;
    }

    /**
     * Obtient la valeur de la propriété hitDescription.
     * 
     * @return
     *     possible object is
     *     {@link Hit.HitDescription }
     *     
     */
    public Hit.HitDescription getHitDescription() {
        return hitDescription;
    }

    /**
     * Définit la valeur de la propriété hitDescription.
     * 
     * @param value
     *     allowed object is
     *     {@link Hit.HitDescription }
     *     
     */
    public void setHitDescription(Hit.HitDescription value) {
        this.hitDescription = value;
    }

    /**
     * Obtient la valeur de la propriété hitLen.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHitLen() {
        return hitLen;
    }

    /**
     * Définit la valeur de la propriété hitLen.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHitLen(Integer value) {
        this.hitLen = value;
    }

    /**
     * Obtient la valeur de la propriété hitHsps.
     * 
     * @return
     *     possible object is
     *     {@link Hit.HitHsps }
     *     
     */
    public Hit.HitHsps getHitHsps() {
        return hitHsps;
    }

    /**
     * Définit la valeur de la propriété hitHsps.
     * 
     * @param value
     *     allowed object is
     *     {@link Hit.HitHsps }
     *     
     */
    public void setHitHsps(Hit.HitHsps value) {
        this.hitHsps = value;
    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}HitDescr"/>
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
        "hitDescr"
    })
    public static class HitDescription {

        @XmlElement(name = "HitDescr")
        protected List<HitDescr> hitDescr;

        /**
         * Gets the value of the hitDescr property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hitDescr property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHitDescr().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HitDescr }
         * 
         * 
         */
        public List<HitDescr> getHitDescr() {
            if (hitDescr == null) {
                hitDescr = new ArrayList<HitDescr>();
            }
            return this.hitDescr;
        }

    }


    /**
     * <p>Classe Java pour anonymous complex type.
     * 
     * <p>Le fragment de schéma suivant indique le contenu attendu figurant dans cette classe.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
     *         &lt;element ref="{http://www.ncbi.nlm.nih.gov}Hsp"/>
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
        "hsp"
    })
    public static class HitHsps {

        @XmlElement(name = "Hsp")
        protected List<Hsp> hsp;

        /**
         * Gets the value of the hsp property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hsp property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHsp().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Hsp }
         * 
         * 
         */
        public List<Hsp> getHsp() {
            if (hsp == null) {
                hsp = new ArrayList<Hsp>();
            }
            return this.hsp;
        }

    }

}
