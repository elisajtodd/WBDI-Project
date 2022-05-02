package cybershare.utep.edu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.HermiT.Configuration;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.InferenceType;
// import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
// import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;
import org.semanticweb.owlapi.vocab.OWLFacet;

public class JobGPSOWL {

    private static final String MLSchema_IRI = "http://www.w3.org/TR/turtle/";
    private static final String MLImplementation = "ontologies/TEST.owl";
    private static final String mlsBase = "http://www.w3.org/ns/mls";
    private static final String BASE = "http://www.semanticweb.org/dopeteam/diclass/jobgps";
    private static final String JOB_GPS = "C:/Users/elisa/Documents/GitHub/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/jobgps-from-api.owl";

    public OWLOntology ontology;
    public OWLDataFactory dataFactory;
    public OWLOntologyManager manager;
    public OWLReasoner reasoner;

    public JobGPSOWL() throws OWLOntologyStorageException {

        this.manager = createManager();
        try {
            this.ontology = loadOntologyFromFile(JOB_GPS, manager);
            this.dataFactory = manager.getOWLDataFactory();
        } catch (OWLOntologyCreationException e) {
            e.printStackTrace();
        }

        Configuration config = new Configuration();
        this.reasoner = new Reasoner(config, ontology);
        this.reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
    }

    /**
     * Get instances
     * input = class
     * Output = individuals
     * 
     * @param classToFind
     */
    public List<String> getInstancesOfClass(String className) {
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className));
        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = this.reasoner.getInstances(classToFind, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        List<String> individual_list = new ArrayList<String>();
        // display
        for (OWLNamedIndividual ind : individuals) {
            String s = ind.toString();
            individual_list.add(s.substring(s.indexOf("#") + 1, s.length() - 1));
        }
        return individual_list;
    }

    /**
     * Find object
     * Input = Subject, objectProperty
     * Output = Object instances
     * 
     * @param individual_1
     * @param property
     */
    public List<String> findObject(String individual_1, String property) {
        OWLNamedIndividual i1 = this.dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_1));
        OWLObjectProperty o1 = this.dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));

        NodeSet<OWLNamedIndividual> petValuesNodeSet = this.reasoner.getObjectPropertyValues(i1, o1);
        Set<OWLNamedIndividual> values = petValuesNodeSet.getFlattened();

        List<String> value_list = new ArrayList<String>();
        // display
        for (OWLNamedIndividual ind : values) {
            String s = ind.toString();
            value_list.add(s.substring(s.indexOf("#") + 1, s.length() - 1));
        }
        return value_list;
    }

    /**
     * Complex class
     * Input = subjectClass, property, objectClass
     * Output = Individuals
     * 
     * @param className1
     * @param className2
     * @param property
     */
    public List<String> getComplexQuery(String className1, String className2, String property) {
        // first class :: Person
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className1));
        // hasproperty second class :: Cat
        OWLClass classToFind2 = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className2));
        // property :: hasPet
        OWLObjectProperty hasProperty = this.dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));
        // has property second class :: hasPet some Cat
        OWLObjectSomeValuesFrom havePropertyClass2 = this.dataFactory.getOWLObjectSomeValuesFrom(hasProperty,
                classToFind2);
        // Person AND hasPet someCat
        OWLClassExpression intersection = this.dataFactory.getOWLObjectIntersectionOf(classToFind, havePropertyClass2);

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();

        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = this.reasoner.getInstances(intersection, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        List<String> individual_list = new ArrayList<String>();
        // display
        for (OWLNamedIndividual ind : individuals) {
            String s = ind.toString();
            individual_list.add(s.substring(s.indexOf("#") + 1, s.length() - 1));
        }
        return individual_list;
    }

    /**
     * Complex class 2
     * Input = subjectClass, property, objectClass, property , value
     * Output = Individuals
     * 
     * @param className1
     * @param className2
     * @param property
     * @param property2
     * @param value2
     */
    public List<String> getComplexQuery2(String className1, String className2, String property, String property2,
            String value2) {
        // first class :: City
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className1));
        // hasproperty second class :: Software Developer
        OWLClass classToFind2 = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className2));
        // property :: hasOccupation
        OWLObjectProperty hasProperty = this.dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));
        // property2 :: hasIncomeClass
        OWLDataProperty hasProperty2 = this.dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + property2));
        // has property second class :: hasOccupation some SWE
        OWLObjectSomeValuesFrom havePropertyClass2 = this.dataFactory.getOWLObjectSomeValuesFrom(hasProperty,
                classToFind2);

        OWLDatatype str_datatype = this.dataFactory.getStringOWLDatatype();
        OWLLiteral val2 = this.dataFactory.getOWLLiteral(value2);
        OWLFacet facet = OWLFacet.LANG_RANGE;
        OWLDataRange value_2 = this.dataFactory.getOWLDatatypeRestriction(str_datatype, facet, val2);

        // has property second class :: hasIncomeClass value "High Income"
        OWLDataAllValuesFrom havePropertyClass3 = this.dataFactory.getOWLDataAllValuesFrom(hasProperty2, value_2);

        // SWE AND hasIncomeClass value "High Income"
        OWLClassExpression intersection1 = this.dataFactory.getOWLObjectIntersectionOf(classToFind2,
                havePropertyClass3);

        // City AND hasOccupation SWE AND hasIncomeClass value "High Income"
        OWLClassExpression intersection2 = this.dataFactory.getOWLObjectIntersectionOf(classToFind, intersection1);

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();

        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = this.reasoner.getInstances(intersection2, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        List<String> individual_list = new ArrayList<String>();
        // display
        for (OWLNamedIndividual ind : individuals) {
            String s = ind.toString();
            individual_list.add(s.substring(s.indexOf("#") + 1, s.length() - 1));
        }
        return individual_list;
    }

    public List<String> getSubclasses(String superClass) {
        List<String> subClasses = new ArrayList<String>();
        for (final OWLSubClassOfAxiom subClasse : this.ontology.getAxioms(AxiomType.SUBCLASS_OF)) {
            String foundSuperClass = subClasse.getSuperClass().toString();
            foundSuperClass = foundSuperClass.substring(foundSuperClass.indexOf("#") + 1, foundSuperClass.length() - 1);
            if (foundSuperClass.equals(superClass)) {
                String foundSubClass = subClasse.getSubClass().toString();
                foundSubClass = foundSubClass.substring(foundSubClass.indexOf("#") + 1, foundSubClass.length() - 1);
                subClasses.add(foundSubClass);
            }
        }
        return subClasses;
    }

    /**
     * Creates a new ontology manager
     * 
     * @return
     */
    public OWLOntologyManager createManager() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager;
    }

    /**
     * Load an ontology to memory from URI source
     * 
     * @param source
     * @param manager
     */
    public OWLOntology loadOntologyFromURI(String source, OWLOntologyManager manager)
            throws OWLOntologyCreationException {
        IRI iri = IRI.create(source);
        OWLOntology ontology;
        ontology = manager.loadOntologyFromOntologyDocument(iri);
        System.out.println("Loaded ontology: " + ontology);
        return ontology;
    }

    /**
     * Load ontology from local file
     * 
     * @param path
     * @param manager
     * @return
     * @throws OWLOntologyCreationException
     */
    public OWLOntology loadOntologyFromFile(String path, OWLOntologyManager manager)
            throws OWLOntologyCreationException {
        File file = new File(path);
        // Load local ontology
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(file);
        System.out.println("Loaded ontology: " + ontology);
        // We can always obtain the location where an ontology was loaded from
        IRI documentIRI = manager.getOntologyDocumentIRI(ontology);
        System.out.println("    from: " + documentIRI);
        return ontology;
    }

    public static void main(String[] args) throws OWLOntologyStorageException {

        try {
            JobGPSOWL myOntology = new JobGPSOWL();
            myOntology.reasoner.isConsistent();
            myOntology.getSubclasses("Occupation");
            // example question 1
            List<String> swd_cities = myOntology.getComplexQuery("City", "software_developers", "hasOccupation");
            for (String city : swd_cities) {
                System.out.println(city);
            }
            // example question 2
            List<String> swd_cities_high = myOntology.getComplexQuery2("City", "software_developers", "hasOccupation",
                    "hasIncomeClass", "High Income");
            for (String city : swd_cities_high) {
                System.out.println(city);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}