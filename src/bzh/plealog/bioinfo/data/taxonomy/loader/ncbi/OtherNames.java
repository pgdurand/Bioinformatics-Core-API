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
    "genbankCommonName",
    "genbankAcronym",
    "blastName",
    "equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph",
    "name"
})
@XmlRootElement(name = "OtherNames")
public class OtherNames {

    @XmlElement(name = "GenbankCommonName")
    protected String genbankCommonName;
    @XmlElement(name = "GenbankAcronym")
    protected String genbankAcronym;
    @XmlElement(name = "BlastName")
    protected String blastName;
    @XmlElements({
        @XmlElement(name = "EquivalentName", type = EquivalentName.class),
        @XmlElement(name = "Synonym", type = Synonym.class),
        @XmlElement(name = "Acronym", type = Acronym.class),
        @XmlElement(name = "Misspelling", type = Misspelling.class),
        @XmlElement(name = "Anamorph", type = Anamorph.class),
        @XmlElement(name = "Includes", type = Includes.class),
        @XmlElement(name = "CommonName", type = CommonName.class),
        @XmlElement(name = "Inpart", type = Inpart.class),
        @XmlElement(name = "Misnomer", type = Misnomer.class),
        @XmlElement(name = "Teleomorph", type = Teleomorph.class),
        @XmlElement(name = "GenbankSynonym", type = GenbankSynonym.class),
        @XmlElement(name = "GenbankAnamorph", type = GenbankAnamorph.class)
    })
    protected List<Object> equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph;
    @XmlElement(name = "Name")
    protected List<Name> name;

    /**
     * Obtient la valeur de la propriété genbankCommonName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenbankCommonName() {
        return genbankCommonName;
    }

    /**
     * Définit la valeur de la propriété genbankCommonName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenbankCommonName(String value) {
        this.genbankCommonName = value;
    }

    /**
     * Obtient la valeur de la propriété genbankAcronym.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenbankAcronym() {
        return genbankAcronym;
    }

    /**
     * Définit la valeur de la propriété genbankAcronym.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenbankAcronym(String value) {
        this.genbankAcronym = value;
    }

    /**
     * Obtient la valeur de la propriété blastName.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlastName() {
        return blastName;
    }

    /**
     * Définit la valeur de la propriété blastName.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlastName(String value) {
        this.blastName = value;
    }

    /**
     * Gets the value of the equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getEquivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link EquivalentName }
     * {@link Synonym }
     * {@link Acronym }
     * {@link Misspelling }
     * {@link Anamorph }
     * {@link Includes }
     * {@link CommonName }
     * {@link Inpart }
     * {@link Misnomer }
     * {@link Teleomorph }
     * {@link GenbankSynonym }
     * {@link GenbankAnamorph }
     * 
     * 
     */
    public List<Object> getEquivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph() {
        if (equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph == null) {
            equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph = new ArrayList<Object>();
        }
        return this.equivalentNameOrSynonymOrAcronymOrMisspellingOrAnamorphOrIncludesOrCommonNameOrInpartOrMisnomerOrTeleomorphOrGenbankSynonymOrGenbankAnamorph;
    }

    /**
     * Gets the value of the name property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Name }
     * 
     * 
     */
    public List<Name> getName() {
        if (name == null) {
            name = new ArrayList<Name>();
        }
        return this.name;
    }

}
