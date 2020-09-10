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
    "modId",
    "modType",
    "modName",
    "modGBhidden",
    "rModIdOrRTaxId"
})
@XmlRootElement(name = "Modifier")
public class Modifier {

    @XmlElement(name = "ModId", required = true)
    protected String modId;
    @XmlElement(name = "ModType", required = true)
    protected String modType;
    @XmlElement(name = "ModName", required = true)
    protected String modName;
    @XmlElement(name = "ModGBhidden", required = true)
    protected String modGBhidden;
    @XmlElements({
        @XmlElement(name = "RModId", type = RModId.class),
        @XmlElement(name = "RTaxId", type = RTaxId.class)
    })
    protected List<Object> rModIdOrRTaxId;

    /**
     * Obtient la valeur de la propriété modId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModId() {
        return modId;
    }

    /**
     * Définit la valeur de la propriété modId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModId(String value) {
        this.modId = value;
    }

    /**
     * Obtient la valeur de la propriété modType.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModType() {
        return modType;
    }

    /**
     * Définit la valeur de la propriété modType.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModType(String value) {
        this.modType = value;
    }

    /**
     * Obtient la valeur de la propriété modName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModName() {
        return modName;
    }

    /**
     * Définit la valeur de la propriété modName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModName(String value) {
        this.modName = value;
    }

    /**
     * Obtient la valeur de la propriété modGBhidden.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModGBhidden() {
        return modGBhidden;
    }

    /**
     * Définit la valeur de la propriété modGBhidden.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModGBhidden(String value) {
        this.modGBhidden = value;
    }

    /**
     * Gets the value of the rModIdOrRTaxId property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rModIdOrRTaxId property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRModIdOrRTaxId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RModId }
     * {@link RTaxId }
     * 
     * 
     */
    public List<Object> getRModIdOrRTaxId() {
        if (rModIdOrRTaxId == null) {
            rModIdOrRTaxId = new ArrayList<Object>();
        }
        return this.rModIdOrRTaxId;
    }

}
