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
 *         &lt;element name="HitDescr_id" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HitDescr_accession" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HitDescr_title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="HitDescr_taxid" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="HitDescr_sciname" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "hitDescrId",
    "hitDescrAccession",
    "hitDescrTitle",
    "hitDescrTaxid",
    "hitDescrSciname"
})
@XmlRootElement(name = "HitDescr")
public class HitDescr {

    @XmlElement(name = "id", required = true)
    protected String hitDescrId;
    @XmlElement(name = "accession")
    protected String hitDescrAccession;
    @XmlElement(name = "title")
    protected String hitDescrTitle;
    @XmlElement(name = "taxid")
    protected Integer hitDescrTaxid;
    @XmlElement(name = "sciname")
    protected String hitDescrSciname;

    /**
     * Obtient la valeur de la propriété hitDescrId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHitDescrId() {
        return hitDescrId;
    }

    /**
     * Définit la valeur de la propriété hitDescrId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHitDescrId(String value) {
        this.hitDescrId = value;
    }

    /**
     * Obtient la valeur de la propriété hitDescrAccession.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHitDescrAccession() {
        return hitDescrAccession;
    }

    /**
     * Définit la valeur de la propriété hitDescrAccession.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHitDescrAccession(String value) {
        this.hitDescrAccession = value;
    }

    /**
     * Obtient la valeur de la propriété hitDescrTitle.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHitDescrTitle() {
        return hitDescrTitle;
    }

    /**
     * Définit la valeur de la propriété hitDescrTitle.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHitDescrTitle(String value) {
        this.hitDescrTitle = value;
    }

    /**
     * Obtient la valeur de la propriété hitDescrTaxid.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getHitDescrTaxid() {
        return hitDescrTaxid;
    }

    /**
     * Définit la valeur de la propriété hitDescrTaxid.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setHitDescrTaxid(Integer value) {
        this.hitDescrTaxid = value;
    }

    /**
     * Obtient la valeur de la propriété hitDescrSciname.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHitDescrSciname() {
        return hitDescrSciname;
    }

    /**
     * Définit la valeur de la propriété hitDescrSciname.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHitDescrSciname(String value) {
        this.hitDescrSciname = value;
    }

}
