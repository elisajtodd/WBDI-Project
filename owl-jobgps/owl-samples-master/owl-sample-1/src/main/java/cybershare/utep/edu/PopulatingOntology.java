package cybershare.utep.edu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
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
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.reasoner.structural.StructuralReasonerFactory;
import org.semanticweb.owlapi.util.InferredOntologyGenerator;

/**
 * First OWL API exercise
 * Web-based data ingetration
 * 
 * @author Instruction team Spring and Fall 2020 with contributions from L.
 *         Garnica
 * @version 2.0
 *          Description: The purpose of these file is to provide basic elements
 *          for using the OWLAPI
 *          Resources:https://owlcs.github.io/owlapi/ -- OWL-API
 *          Include your name here - ex. Modified by Erik Macik for Assignment 3
 *
 */
public class PopulatingOntology {

    private static final String BASE = "http://www.semanticweb.org/dopeteam/diclass/jobgps";
    private static final String JOB_GPS = "C:/Users/elisa/Documents/GitHub/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/jobgps-from-api.owl";

    private static final String ANNUAL_MEAN = "A_MEAN";
    private static final String HOURLY_MEAN = "H_MEAN";
    private static final String ANNUAL_MEDIAN = "A_MEDIAN";
    private static final String HOURLY_MEDIAN = "H_MEDIAN";
    private static final String TOTAL_EMPLOYEES = "TOT_EMP";
    private static final String AREA = "AREA_TITLE";
    private static final String OCCUPATION_NAME = "OCC_TITLE";
    private static final String LOC_QUO = "LOC_QUOTIENT";

    private static final String HAS_ANNUAL_MEAN = "hasMeanAnnualWage";
    private static final String HAS_HOURLY_MEAN = "hasMeanHourlyWage";
    private static final String HAS_ANNUAL_MEDIAN = "hasMedianAnnualWage";
    private static final String HAS_HOURLY_MEDIAN = "hasMedianHourlyWage";
    private static final String HAS_TOTAL_EMPLOYEES = "hasTotalEmployees";
    private static final String IS_LOCATED_IN = "isLocatedIn";
    private static final String HAS_OCCUPATION = "hasOccupation";

    private static final String TOTAL_ESTIMATE = "Total Estimate";
    private static final String MEN_ESTIMATE = "Men Estimate";
    private static final String WOMEN_ESTIMATE = "Women Estimate";
    private static final String WOMEN_PERCENTAGE = "Women Percentage Estimate";
    private static final String OCC_CATEGORY = "Occupational Category";

    private static final String HAS_MEN_TOTAL = "hasTotalMenEmployees";
    private static final String HAS_WOMEN_TOTAL = "hasTotalWomenEmployees";
    private static final String HAS_WOMEN_PERCENTAGE = "hasWomenEmployeesPercentage";

    private static final String HAS_INCOME_CLASS = "hasIncomeClass";
    private static final String LOW_INCOME = "Low Income";
    private static final String MEDIUM_INCOME = "Medium Income";
    private static final String HIGH_INCOME = "High Income";

    public static void main(String[] args) {
        System.out.println("Running OWL demo");
        System.out.println("Running file from" + System.getProperty("user.dir"));

        OWLOntologyManager manager = createManager();
        try {
            long startTime = System.nanoTime();

            OWLOntology ontology = loadOntologyFromFile(JOB_GPS, manager);
            // OWLOntology ontology = manager.createOntology();
            OWLDataFactory dataFactory = manager.getOWLDataFactory();

            // System.out.println("Importing workbook...");
            // XSSFWorkbook workbook = new XSSFWorkbook(
            // new File(
            // "C:/Users/elisa/Documents/GitHub/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/oesm21ma/MSA_M2021_dl.xlsx"));
            // XSSFSheet sheet = workbook.getSheetAt(0);
            // System.out.println("Finished importing workbook.");

            // System.out.println("Reading workbook...");
            // Map<String, Integer> columnLocations = getColumnLocations(sheet);
            // getData(ontology, manager, dataFactory, sheet, columnLocations, false);
            // System.out.println("Finished reading workbook.");

            // OWLReasonerFactory reasonerFactory = new StructuralReasonerFactory(); // look
            // for reasoner to load
            // OWLReasoner reasoner = reasonerFactory.createReasoner(ontology);

            // System.gc();

            XSSFWorkbook workbook_2 = new XSSFWorkbook(
                    new File(
                            "C:/Users/elisa/Documents/GitHub/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/STEMOccupations.xlsx"));
            XSSFSheet sheet_2 = workbook_2.getSheetAt(0);
            System.out.println("Finished importing workbook_2.");

            System.out.println("Reading workbook_2...");
            Map<String, Integer> columnLocations_2 = getColumnLocations(sheet_2);
            getData(ontology, manager, dataFactory, sheet_2, columnLocations_2);
            System.out.println("Finished reading workbook_2.");
            System.out.println("Finished populating ontology.");

            OWLObjectProperty loc = dataFactory.getOWLObjectProperty(IRI.create(BASE +
                    "#" + "hasCity"));
            OWLObjectProperty oc = dataFactory.getOWLObjectProperty(IRI.create(BASE + "#"
                    + "isLocatedIn"));
            OWLInverseObjectPropertiesAxiom hasOcc = dataFactory.getOWLInverseObjectPropertiesAxiom(loc, oc);
            manager.addAxiom(ontology, hasOcc);

            // addEquivalentClass(ontology, manager, dataFactory, reasoner,
            // "SoftwareDeveloperCities", "City",
            // "software_developers", "hasOccupation");

            System.out.println("Saving Ontology...");
            saveOntology(
                    "C:/Users/elisa/Documents/GitHub/WBDI-Project/owl-jobgps/owl-samples-master/ontologies/jobgps-from-api.owl",
                    manager, ontology);

            long endTime = System.nanoTime();

            long elapsedTime = endTime - startTime;
            long seconds = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);

            System.out.println("\nTotal time: " + seconds);
        } catch (OWLOntologyCreationException | OWLOntologyStorageException | IOException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    public static void populateOntology(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory,
            Map<String, Integer> columnLocations, List<String> row) {
        try {
            if (row.get(columnLocations.get(OCCUPATION_NAME)).isEmpty()
                    || Double.parseDouble(row.get(columnLocations.get(LOC_QUO))) < 2.25) {
                return;
            }
            String locationName = row.get(columnLocations.get(AREA)).trim().replace(" ", "_").replace(",", "");
            addSubClass(ontology, manager, factory, "dbo:Place", "State");
            addSubClass(ontology, manager, factory, "dbo:Place", "City");

            String state = locationName.substring(Math.max(locationName.length() - 2, 0));
            addClassIndividual(ontology, manager, factory, state, "State");
            String city = locationName;
            addClassIndividual(ontology, manager, factory, city, "City");

            addObjectPropertyAssertion(ontology, manager, factory, state, city, "hasCity");
            addObjectPropertyAssertion(ontology, manager, factory, city, state, "isPartOf");

            String occupationName = row.get(columnLocations.get(OCCUPATION_NAME)).toLowerCase().trim().replace(" ",
                    "_");
            addSubClass(ontology, manager, factory, "Occupation", occupationName);

            String individualName = occupationName + "_" + locationName;
            addClassIndividual(ontology, manager, factory, individualName, occupationName);

            double annualMean = Double.parseDouble(row.get(columnLocations.get(ANNUAL_MEAN)));
            addDataPropertyAssertionWithDouble(ontology, manager, factory, individualName, annualMean, HAS_ANNUAL_MEAN);

            double hourlyMean = Double.parseDouble(row.get(columnLocations.get(HOURLY_MEDIAN)));
            addDataPropertyAssertionWithDouble(ontology, manager, factory, individualName, hourlyMean, HAS_HOURLY_MEAN);

            double annualMedian = Double.parseDouble(row.get(columnLocations.get(ANNUAL_MEDIAN)));
            addDataPropertyAssertionWithDouble(ontology, manager, factory, individualName, annualMedian,
                    HAS_ANNUAL_MEDIAN);
            addSalaryRange(ontology, manager, factory, annualMedian, individualName);

            double hourlyMedian = Double.parseDouble(row.get(columnLocations.get(HOURLY_MEAN)));
            addDataPropertyAssertionWithDouble(ontology, manager, factory, individualName, hourlyMedian,
                    HAS_HOURLY_MEDIAN);

            int totalEmployees = (int) Double.parseDouble(row.get(columnLocations.get(TOTAL_EMPLOYEES)));
            addDataPropertyAssertionWithInt(ontology, manager, factory, individualName, totalEmployees,
                    HAS_TOTAL_EMPLOYEES);

            addObjectPropertyAssertion(ontology, manager, factory, individualName, locationName, IS_LOCATED_IN);

        } catch (Exception e) {
            // skip row
        }
    }

    public static void populateOntology2(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory,
            Map<String, Integer> columnLocations, List<String> row) {
        try {
            if (row.get(columnLocations.get(OCC_CATEGORY)).isEmpty()) {
                return;
            }
            String occupationName = row.get(columnLocations.get(OCC_CATEGORY)).toLowerCase().trim().replace(" ", "_")
                    .replace(",",
                            "");
            addSubClass(ontology, manager, factory, "Occupation", occupationName);

            addClassIndividual(ontology, manager, factory, occupationName, occupationName);

            int totalEmployees = (int) Double.parseDouble(row.get(columnLocations.get(TOTAL_ESTIMATE)));
            addDataPropertyAssertionWithInt(ontology, manager, factory, occupationName, totalEmployees,
                    HAS_TOTAL_EMPLOYEES);

            int menEstimate = (int) Double.parseDouble(row.get(columnLocations.get(MEN_ESTIMATE)));
            addDataPropertyAssertionWithInt(ontology, manager, factory, occupationName, menEstimate, HAS_MEN_TOTAL);

            int womenEstimate = (int) Double.parseDouble(row.get(columnLocations.get(WOMEN_ESTIMATE)));
            addDataPropertyAssertionWithInt(ontology, manager, factory, occupationName, womenEstimate,
                    HAS_WOMEN_TOTAL);

            double womenPercent = Double.parseDouble(row.get(columnLocations.get(WOMEN_PERCENTAGE)));
            addDataPropertyAssertionWithDouble(ontology, manager, factory, occupationName, womenPercent,
                    HAS_WOMEN_PERCENTAGE);
        } catch (Exception e) {
            // skip row
        }
    }

    public static Map<String, Integer> getColumnLocations(XSSFSheet sheet) {
        Map<String, Integer> locations = new HashMap<>();
        for (Cell cell : sheet.getRow(0)) {
            locations.put(cell.getRichStringCellValue().toString(), cell.getColumnIndex());
        }
        return locations;
    }

    public static void getData(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory,
            XSSFSheet sheet, Map<String, Integer> columnLocations) {
        // List<List<String>> data = new ArrayList<>();

        Iterator<Row> iterator = sheet.iterator();
        iterator.next();
        int i = 0;
        while (iterator.hasNext() && i != 100000) {
            i++;
            Row row = iterator.next();
            List<String> rowData = new ArrayList<>();
            // data.add(rowData);
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case BLANK:
                        rowData.add("");
                    case BOOLEAN:
                        break;
                    case ERROR:
                        break;
                    case FORMULA:
                        break;
                    case NUMERIC:
                        rowData.add(cell.getNumericCellValue() + "");
                        break;
                    case STRING:
                        rowData.add(cell.getRichStringCellValue().getString().replaceAll("[^a-zA-Z0-9]", " "));
                        break;
                    case _NONE:
                        break;
                    default:
                        break;
                }
            }
            populateOntology2(ontology, manager, factory, columnLocations, rowData);
        }

        // data.removeIf(row ->
        // row.get(columnLocations.get(OCCUPATION_NAME)).equals("All Occupations"));
        // data.removeIf(row -> row.toString().contains("#") ||
        // row.toString().contains("*"));

        // return data;
    }

    public static void getData(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory factory,
            XSSFSheet sheet, Map<String, Integer> columnLocations, boolean getSubset) {
        // List<List<String>> data = new ArrayList<>();

        Iterator<Row> iterator = sheet.iterator();
        iterator.next();
        int i = 0;
        while (iterator.hasNext() && i != 150000) {
            Row row = iterator.next();

            if (getSubset && Math.random() < 0.25) {
                continue;
            }
            if (i % 100 == 0) {
                System.out.println(i);
            }

            i++;
            if (i < 130000) {
                continue;
            }

            List<String> rowData = new ArrayList<>();
            // data.add(rowData);
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case BLANK:
                        rowData.add("");
                    case BOOLEAN:
                        break;
                    case ERROR:
                        break;
                    case FORMULA:
                        break;
                    case NUMERIC:
                        rowData.add(cell.getNumericCellValue() + "");
                        break;
                    case STRING:
                        rowData.add(cell.getRichStringCellValue().getString().replaceAll("[^a-zA-Z0-9]", " "));
                        break;
                    case _NONE:
                        break;
                    default:
                        break;
                }
            }
            populateOntology(ontology, manager, factory, columnLocations, rowData);
        }

        // data.removeIf(row ->
        // row.get(columnLocations.get(OCCUPATION_NAME)).equals("All Occupations"));
        // data.removeIf(row -> row.toString().contains("#") ||
        // row.toString().contains("*"));

        // return data;
    }

    /**
     * Creates a new ontology manager
     * 
     * @return
     */
    public static OWLOntologyManager createManager() {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        return manager;
    }

    /**
     * Load an ontology to memory from URI source
     * 
     * @param source
     * @param manager
     */
    public static OWLOntology loadOntologyFromURI(String source, OWLOntologyManager manager)
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
    public static OWLOntology loadOntologyFromFile(String path, OWLOntologyManager manager)
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

    /**
     * Insert individual to a class
     * 
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param parentClassName
     * @param childClassName
     * @return
     */
    public static void addSubClass(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory dataFactory,
            String parentClassName, String childClassName) {
        OWLClass parentC = dataFactory.getOWLClass(IRI.create(BASE + "#" + parentClassName));
        OWLClass childC = dataFactory.getOWLClass(IRI.create(BASE + "#" + childClassName));
        OWLSubClassOfAxiom ax = dataFactory.getOWLSubClassOfAxiom(childC, parentC);

        manager.addAxiom(ontology, ax);
    }

    /**
     * Insert individual to a class
     * 
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param subjectName
     * @param className
     * @return
     */
    public static void addClassIndividual(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory dataFactory,
            String subjectName, String className) {
        OWLIndividual subject = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + subjectName));
        OWLClass someClass = dataFactory.getOWLClass(IRI.create(BASE + "#" + className));
        OWLClassAssertionAxiom ax = dataFactory.getOWLClassAssertionAxiom(someClass, subject);
        manager.addAxiom(ontology, ax);
    }

    /**
     * Add object property assertion (between individuals)
     * 
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param individual_1
     * @param individual_2
     * @param property
     */
    public static void addObjectPropertyAssertion(OWLOntology ontology, OWLOntologyManager manager,
            OWLDataFactory dataFactory, String individual_1, String individual_2, String property) {
        OWLIndividual i1 = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_1));
        OWLIndividual i2 = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_2));
        OWLObjectProperty o = dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));

        OWLAxiom assertion = dataFactory.getOWLObjectPropertyAssertionAxiom(o, i1, i2);
        manager.addAxiom(ontology, assertion);
    }

    /**
     * Add data property assertion
     * 
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param individual_1
     * @param literal_1
     * @param property
     */
    public static void addDataPropertyAssertionWithString(OWLOntology ontology, OWLOntologyManager manager,
            OWLDataFactory dataFactory, String individual_1, String literal_1, String property) {
        OWLIndividual i1 = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_1));
        OWLLiteral l1 = dataFactory.getOWLLiteral(literal_1);
        OWLDataProperty o = dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + property));

        OWLAxiom assertion = dataFactory.getOWLDataPropertyAssertionAxiom(o, i1, l1);
        manager.addAxiom(ontology, assertion);
    }

    /**
     * Add data property assertion
     * 
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param individual_1
     * @param double_1
     * @param property
     */
    public static void addDataPropertyAssertionWithDouble(OWLOntology ontology, OWLOntologyManager manager,
            OWLDataFactory dataFactory, String individual_1, double double_1, String property) {
        OWLIndividual i1 = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individual_1));
        OWLLiteral l1 = dataFactory.getOWLLiteral(double_1);
        OWLDataProperty o = dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + property));

        OWLAxiom assertion = dataFactory.getOWLDataPropertyAssertionAxiom(o, i1, l1);
        manager.addAxiom(ontology, assertion);
    }

    public static void addDataPropertyAssertionWithInt(OWLOntology ontology, OWLOntologyManager manager,
            OWLDataFactory dataFactory, String individualName, int value, String property) {
        OWLIndividual individual = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individualName));
        OWLLiteral literal = dataFactory.getOWLLiteral(value);
        OWLDataProperty dataProperty = dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + property));

        OWLAxiom assertion = dataFactory.getOWLDataPropertyAssertionAxiom(dataProperty, individual, literal);
        manager.addAxiom(ontology, assertion);
    }

    public static void addSalaryRange(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory dataFactory,
            double salary, String individualName) {
        OWLIndividual individual = dataFactory.getOWLNamedIndividual(IRI.create(BASE + "#" + individualName));
        OWLDataProperty dataProperty = dataFactory.getOWLDataProperty(IRI.create(BASE + "#" + HAS_INCOME_CLASS));
        if (salary < 45000) {
            OWLAxiom assertion = dataFactory.getOWLDataPropertyAssertionAxiom(dataProperty, individual, LOW_INCOME);
            manager.addAxiom(ontology, assertion);

        } else if (salary < 90000) {
            OWLAxiom assertion = dataFactory.getOWLDataPropertyAssertionAxiom(dataProperty, individual, MEDIUM_INCOME);
            manager.addAxiom(ontology, assertion);

        } else {
            OWLAxiom assertion = dataFactory.getOWLDataPropertyAssertionAxiom(dataProperty, individual, HIGH_INCOME);
            manager.addAxiom(ontology, assertion);
        }
    }

    /**
     * save ontology locally to specific path
     * 
     * @param ontology
     * @param manager
     * @param reasoner
     * @param className
     *                  https://stackoverflow.com/questions/47628797/how-to-get-the-class-of-individual-in-owl-api
     */
    public static void getInstancesOfClass(OWLOntology ontology, OWLOntologyManager manager, OWLReasoner reasoner,
            String className) {
        OWLClass classToFind = manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className));
        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(classToFind, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        System.out.println("Instances of " + className + ":");
        // display
        for (OWLNamedIndividual ind : individuals) {
            String s = ind.toString();
            System.out.println(s.substring(s.indexOf("#") + 1, s.length() - 1));
        }
    }

    public static void getInstancesOfClass(OWLOntology ontology, OWLOntologyManager manager, OWLReasoner reasoner,
            OWLClass classToFind) {
        // get instances
        NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(classToFind, true);
        Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();

        System.out.println("Instances of given class:");
        // display
        for (OWLNamedIndividual ind : individuals) {
            String s = ind.toString();
            System.out.println(s.substring(s.indexOf("#") + 1, s.length() - 1));
        }
    }

    /**
     * Add equivalent class
     * 
     * 
     * @param ontology
     * @param manager
     * @param dataFactory
     * @param reasoner
     * @param newClassName
     * @param className1
     * @param className2
     * @param property
     */
    public static void addEquivalentClass(OWLOntology ontology, OWLOntologyManager manager, OWLDataFactory dataFactory,
            OWLReasoner reasoner,
            String newClassName, String className1, String className2, String property) {

        // new class to store query
        OWLClass newClass = dataFactory.getOWLClass(IRI.create(BASE + "#" + newClassName));

        // first class :: Person
        OWLClass classToFind = manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className1));
        // hasproperty second class :: Cat
        OWLClass classToFind2 = manager.getOWLDataFactory().getOWLClass(IRI.create(BASE + "#" + className2));
        // property :: hasPet
        OWLObjectProperty hasProperty = dataFactory.getOWLObjectProperty(IRI.create(BASE + "#" + property));

        // make newClass subclass of firstClass :: CatOwner subClass of Person
        OWLSubClassOfAxiom subClass = dataFactory.getOWLSubClassOfAxiom(newClass, classToFind);
        manager.addAxiom(ontology, subClass);

        // has property second class :: hasPet some Cat
        OWLObjectSomeValuesFrom havePropertyClass2 = dataFactory.getOWLObjectSomeValuesFrom(hasProperty,
                classToFind2);

        // Person AND hasPet someCat
        OWLClassExpression intersection = dataFactory.getOWLObjectIntersectionOf(classToFind, havePropertyClass2);

        // CatOwner == Person AND hasPet someCat
        OWLEquivalentClassesAxiom newDefinition = dataFactory.getOWLEquivalentClassesAxiom(newClass, intersection);
        manager.addAxiom(ontology, newDefinition);

        Configuration config = new Configuration();
        OWLReasoner owlReasoner = new Reasoner(config, ontology);
        owlReasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);

        InferredOntologyGenerator generator = new InferredOntologyGenerator(owlReasoner);
        generator.fillOntology(dataFactory, ontology);
        reasoner.isConsistent();
        // get instances
        getInstancesOfClass(ontology, manager, owlReasoner, newClass);
    }
    // -------------------------------

    /**
     * save ontology locally to specific path
     * 
     * @param path
     * @param manager
     * @param ontology
     * @throws IOException
     * @throws OWLOntologyStorageException
     */
    public static void saveOntology(String path, OWLOntologyManager manager, OWLOntology ontology)
            throws IOException, OWLOntologyStorageException {
        File file = new File(path);

        // default format as they are loaded e.g. xml, turtle
        manager.saveOntology(ontology, IRI.create(file.toURI()));
        System.out.println("Ontology was saved");
    }

}
