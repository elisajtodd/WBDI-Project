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
import org.apache.poi.sl.usermodel.PaintStyle.SolidPaint;
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
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectHasValue;
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
    //private static final String JOB_GPS = "C:/Users/elisa/Documents/GitHub/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/jobgps-from-api.owl";
    private static final String JOB_GPS = "/Users/erikmacik/Developer/git/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/jobgps-from-api.owl";

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

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();
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

        OWLDatatype str_datatype = this.dataFactory.getStringOWLDatatype();
        OWLLiteral val2 = this.dataFactory.getOWLLiteral(value2);
        OWLFacet facet = OWLFacet.PATTERN;
        OWLDataRange value_2 = this.dataFactory.getOWLDatatypeRestriction(str_datatype, facet, val2);

        // has property second class :: hasIncomeClass value "High Income"
        OWLDataSomeValuesFrom havePropertyClass2 = this.dataFactory.getOWLDataSomeValuesFrom(hasProperty2, value_2);

        // SWE AND hasIncomeClass value "High Income"
        OWLClassExpression intersection1 = this.dataFactory.getOWLObjectIntersectionOf(classToFind2,
                havePropertyClass2);

        // has property second class :: hasOccupation some (SWE AND hasIncomeClass value
        // "High Income")
        OWLObjectSomeValuesFrom havePropertyClass1 = this.dataFactory.getOWLObjectSomeValuesFrom(hasProperty,
                intersection1);

        // City AND hasOccupation (SWE AND hasIncomeClass value "High Income")
        OWLClassExpression intersection2 = this.dataFactory.getOWLObjectIntersectionOf(classToFind, havePropertyClass1);

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

    /**
     * Complex class 3
     * Input = subjectClass, property, objectClass, property , value
     * Output = Individuals
     * 
     * @param className1
     * @param className2
     * @param dataProperty
     * @param dataProperty2
     * @param value2
     */
    public List<String> getComplexQuery3(String className, String value1, String dataProperty,
            String dataProperty2, String value2) {
        // first class :: Occupation
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className));
        // property :: isLocatedIn
        OWLObjectProperty hasProperty = this.dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + dataProperty));
        // property2 :: hasIncomeClass
        OWLDataProperty hasProperty2 = this.dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + dataProperty2));
        // instance Boulder__CO
        OWLIndividual instance_input = this.dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + value1));

        // has property second class :: isLocatedIn value "Boulder__CO"
        OWLObjectHasValue havePropertyClass1 = this.dataFactory.getOWLObjectHasValue(hasProperty,
                instance_input);

        OWLDatatype str_datatype = this.dataFactory.getStringOWLDatatype();
        OWLFacet facet = OWLFacet.PATTERN;
        OWLLiteral val2 = this.dataFactory.getOWLLiteral(value2);
        OWLDataRange value_2 = this.dataFactory.getOWLDatatypeRestriction(str_datatype, facet, val2);

        // has property second class :: hasIncomeClass value "High Income"
        OWLDataSomeValuesFrom havePropertyClass2 = this.dataFactory.getOWLDataSomeValuesFrom(hasProperty2, value_2);

        // Occupation and ... and ...
        OWLClassExpression intersection1 = this.dataFactory.getOWLObjectIntersectionOf(classToFind, havePropertyClass1,
                havePropertyClass2);

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();

        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = this.reasoner.getInstances(intersection1, true);
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
     * Complex class 4
     * Input = subjectClass, property, objectClass, property , value
     * Output = Individuals
     * 
     * @param className1
     * @param className2
     * @param dataProperty
     * @param dataProperty2
     */
    public String getComplexQuery4(String className, String value1, String dataProperty,
            String dataProperty2) {
        // first class :: SWE
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className));
        // property :: isLocatedIn
        OWLObjectProperty hasProperty = this.dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + dataProperty));
        // property2 :: hasMedianAnnualWage
        OWLDataProperty hasProperty2 = this.dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + dataProperty2));
        // instance Boulder__CO
        OWLIndividual instance_input = this.dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + value1));

        // has property second class :: isLocatedIn value "Boulder__CO"
        OWLObjectHasValue havePropertyClass1 = this.dataFactory.getOWLObjectHasValue(hasProperty,
                instance_input);

        // SWE and isLocatedIn Boulder
        OWLClassExpression intersection1 = this.dataFactory.getOWLObjectIntersectionOf(classToFind, havePropertyClass1);

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();

        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = this.reasoner.getInstances(intersection1, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        String val = "";

        // display
        for (OWLNamedIndividual ind : individuals) {
            Set<OWLLiteral> prop = this.reasoner.getDataPropertyValues(ind, hasProperty2);
            for (OWLLiteral p : prop) {
                String s = p.toString();
                val = s.substring(s.indexOf("\"") + 1, s.indexOf("^") - 1);
            }
        }
        return val;
    }

    /**
     * Complex class
     * Input = subjectClass, property, objectClass
     * Output = Individuals
     * 
     * @param className1
     * @param className2
     * @param individual
     */
    public List<String> getComplexQuery5(String className1, String property, String individual) {
        // first class :: Occupation
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className1));
        // property :: isLocatedIn
        OWLObjectProperty hasProperty = this.dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));
        // instance Boulder__CO
        OWLIndividual instance_input = this.dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual));

        // has property second class :: isLocatedIn value "Boulder__CO"
        OWLObjectHasValue havePropertyClass1 = this.dataFactory.getOWLObjectHasValue(hasProperty,
                instance_input);

        // Occupation and isLocatedIn Boulder__CO
        OWLClassExpression intersection = this.dataFactory.getOWLObjectIntersectionOf(classToFind, havePropertyClass1);

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
     * Complex class 10
     * Input = subjectClass, property, objectClass, property , value
     * Output = Individuals
     * 
     * @param className
     * @param dataProperty
     */
    public String getComplexQuery10(String className, String dataProperty) {
        // property2 :: hasMedianAnnualWage
        OWLDataProperty hasProperty2 = this.dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + dataProperty));
        // instance SWE
        OWLNamedIndividual instance_input = this.dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + className));

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();

        String val = "";
        Set<OWLLiteral> prop = this.reasoner.getDataPropertyValues(instance_input, hasProperty2);
        for (OWLLiteral p : prop) {
            String s = p.toString();
            val = s.substring(s.indexOf("\"") + 1, s.indexOf("^") - 1);
        }
        return val;
    }

    /**
     * Complex class 2
     * Input = subjectClass, dataProperty, value
     * Output = Individuals
     * 
     * @param className1
     * @param className2
     * @param property
     * @param property2
     * @param value2
     */
    public List<String> getStringDataQuery(String className1, String dataProperty,
            String value) {
        // first class :: Occupation
        OWLClass classToFind = this.manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className1));
        // property :: hasIncomeClass
        OWLDataProperty hasProperty = this.dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + dataProperty));

        OWLDatatype str_datatype = this.dataFactory.getStringOWLDatatype();
        OWLLiteral val2 = this.dataFactory.getOWLLiteral(value);
        OWLFacet facet = OWLFacet.PATTERN;
        OWLDataRange value_2 = this.dataFactory.getOWLDatatypeRestriction(str_datatype, facet, val2);

        // has property second class :: hasIncomeClass value "High Income"
        OWLDataSomeValuesFrom havePropertyClass2 = this.dataFactory.getOWLDataSomeValuesFrom(hasProperty, value_2);

        // Occupation AND hasIncomeClass value "High Income"
        OWLClassExpression intersection1 = this.dataFactory.getOWLObjectIntersectionOf(classToFind,
                havePropertyClass2);

        InferredOntologyGenerator generator = new InferredOntologyGenerator(this.reasoner);
        generator.fillOntology(this.dataFactory, this.ontology);
        this.reasoner.isConsistent();

        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = this.reasoner.getInstances(intersection1, true);
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
            System.out.println("Occupations: ");
            List<String> occupations = myOntology.getSubclasses("Occupation");
            for (String occupation : occupations) {
                System.out.println(occupation);
            }
            System.out.println("Places: ");
            List<String> places = myOntology.getInstancesOfClass("City");
            for (String place : places) {
                System.out.println(place);
            }
            System.out.println("Q 1");
            // example question 1
            List<String> swd_cities = myOntology.getComplexQuery("City", "software_developers", "hasOccupation");
            for (String city : swd_cities) {
                System.out.println(city);
            }
            System.out.println("Q 2");
            // example question 2
            List<String> swd_cities_high = myOntology.getComplexQuery2("City", "software_developers", "hasOccupation",
                    "hasIncomeClass", "High Income");
            for (String city : swd_cities_high) {
                System.out.println(city);
            }
            // example question 3
            System.out.println("Q 3");
            List<String> high_occ = myOntology.getComplexQuery3("Occupation", "Boulder__CO", "isLocatedIn",
                    "hasIncomeClass", "High Income");
            for (String city : high_occ) {
                System.out.println(city);
            }
            // example question 4
            System.out.println("Q 4");
            String medianIncome = myOntology.getComplexQuery4("software_developers", "Boulder__CO", "isLocatedIn",
                    "hasMedianAnnualWage");
            System.out.println(medianIncome);
            // example question 5
            System.out.println("Q 5");
            List<String> occ = myOntology.getComplexQuery5("Occupation", "isLocatedIn", "Boulder__CO");
            for (String o : occ) {
                System.out.println(o);
            }
            // example question 6
            System.out.println("Q 6");
            String meanIncome = myOntology.getComplexQuery4("software_developers", "Boulder__CO", "isLocatedIn",
                    "hasMeanAnnualWage");
            System.out.println(meanIncome);
            // example question 7
            System.out.println("Q 7");
            String totalEmployees = myOntology.getComplexQuery4("software_developers", "Boulder__CO", "isLocatedIn",
                    "hasTotalEmployees");
            System.out.println(totalEmployees);
            // example question 8
            System.out.println("Q 8");
            List<String> low_occ = myOntology.getComplexQuery3("Occupation", "College_Station_Bryan__TX", "isLocatedIn",
                    "hasIncomeClass", "Low Income");
            for (String o : low_occ) {
                System.out.println(o);
            }
            // example question 9
            System.out.println("Q 9");
            List<String> class_occ = myOntology.getStringDataQuery("Occupation", "hasIncomeClass", "High Income");
            for (String o : class_occ) {
                System.out.println(o);
            }
            // example question 10
            System.out.println("Q 10");
            String womenPercentage = myOntology.getComplexQuery10("software_developers", "hasWomenEmployeesPercentage");
            System.out.println(womenPercentage);
        } catch (

        Exception e) {
            e.printStackTrace();
        }
    }
}