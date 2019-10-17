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
 *       &lt;choice>
 *         &lt;element name="Target_db" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Target_subjects">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *                   &lt;element name="Target_subjects_E" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "targetDb",
    "targetSubjects"
})
@XmlRootElement(name = "Target")
public class Target {

    @XmlElement(name = "db")
    protected String targetDb;
    @XmlElement(name = "subjects")
    protected Target.TargetSubjects targetSubjects;

    /**
     * Obtient la valeur de la propriété targetDb.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetDb() {
        return targetDb;
    }

    /**
     * Définit la valeur de la propriété targetDb.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetDb(String value) {
        this.targetDb = value;
    }

    /**
     * Obtient la valeur de la propriété targetSubjects.
     * 
     * @return
     *     possible object is
     *     {@link Target.TargetSubjects }
     *     
     */
    public Target.TargetSubjects getTargetSubjects() {
        return targetSubjects;
    }

    /**
     * Définit la valeur de la propriété targetSubjects.
     * 
     * @param value
     *     allowed object is
     *     {@link Target.TargetSubjects }
     *     
     */
    public void setTargetSubjects(Target.TargetSubjects value) {
        this.targetSubjects = value;
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
     *         &lt;element name="Target_subjects_E" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "targetSubjectsE"
    })
    public static class TargetSubjects {

        @XmlElement(name = "subjects_E")
        protected List<String> targetSubjectsE;

        /**
         * Gets the value of the targetSubjectsE property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the targetSubjectsE property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTargetSubjectsE().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getTargetSubjectsE() {
            if (targetSubjectsE == null) {
                targetSubjectsE = new ArrayList<String>();
            }
            return this.targetSubjectsE;
        }

    }

}
