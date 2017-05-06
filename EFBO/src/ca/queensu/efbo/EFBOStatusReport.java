package ca.queensu.efbo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
//import org.semanticweb.owlapi.sparql.*;


import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.util.FileManager;

import ca.queensu.efbo.EFBOOntologyManager.OWLExpressionAxiom;

public class EFBOStatusReport 
{
	Set <OWLNamedIndividual> firstSystemEntity;
	Set <OWLNamedIndividual> secondSystemEntity;
	private OWLOntology efboMergedInferredOntology = null;
	EFBOOntologyManager efboStatusReportManager = null;
//	OWLOntologyDataSet dataset;
		
	
	private static final String EFBO_FRC_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-frc.owl";
	private static final String EFBO_V_URI = "http://www.cs.queensu.ca/~imam/ontologies/efbo-v.owl";
	
	
	public static void main(String[] args) throws Exception
	{
		String location =  System.getProperty("user.dir") + "/Resources/Ontologies/Test-007/untitled-ontology-209.owl";
		File f = new File(location);
		EFBOStatusReport esr = new EFBOStatusReport(f);
		esr.printEntityBySystem();
	}
	

	EFBOStatusReport(File ontologyFile) throws Exception
	{
		this.efboStatusReportManager = new EFBOOntologyManager();
		this.efboStatusReportManager.loadOntology("EFBO-V Inferred", ontologyFile);
		this.efboMergedInferredOntology = efboStatusReportManager.getLoadedOntology();
		
		//dataset = new OWLOntologyDataSet(localOntology, new HashMap());
		this.setEntityBySystem();
	}
	
		
	private void setEntityBySystem()
	{
		firstSystemEntity = new HashSet<OWLNamedIndividual>();
		secondSystemEntity = new HashSet<OWLNamedIndividual>();
		
		OWLClass firstSystemEvent = this.getOWLClass(EFBO_V_URI, "System-1_Event");
		firstSystemEntity = efboStatusReportManager.getOWLNamedIndividuals(firstSystemEvent);
		
		OWLClass secondSystemEvent = this.getOWLClass(EFBO_V_URI, "System-2_Event");
		secondSystemEntity = efboStatusReportManager.getOWLNamedIndividuals(secondSystemEvent);		
	}
	
	public void printEntityBySystem() throws Exception
	{
		TableBuilder tb = new TableBuilder();
			
		OWLObjectProperty hasMappingEvent = efboStatusReportManager.getOWLObjectProperty(EFBO_V_URI, "hasConsistentEventFlow");
		
		//Set <OWLNamedIndividual> events = new HashSet<OWLNamedIndividual>();
		//events = efboStatusReportManager.getOWLNamedIndividuals(hasMappingEvent);	
		Set <OWLExpressionAxiom> a = efboStatusReportManager.getOWLNamedIndividuals(hasMappingEvent);	
		
		String bar = "|";
		tb.addRow("---------------------------------", "+", "--------------------------", "+", "------------------------------");
		tb.addRow("System-I Events", bar, "Object Property" , bar, "System-II Events");
		tb.addRow("---------------------------------", "+", "--------------------------", "+", "------------------------------");
		for (OWLExpressionAxiom i: a)// firstSystemEntity)
		{
			String subjectLabel = efboStatusReportManager.getLabel(i.subject);
			String objectLabel = efboStatusReportManager.getLabel(i.object);
			String propertyLabel = efboStatusReportManager.getLabel(i.objectProperty);
				
			
			tb.addRow("|" + subjectLabel, bar, propertyLabel , bar, objectLabel);
		}
		tb.addRow("---------------------------------", "+", "--------------------------", "+", "------------------------------");
		System.out.println(tb.toString());
		
		efboStatusReportManager.printOntologyMetrics();
		//System.out.println(efboStatusReportManager.getOWLIndividualAxioms());
		printQueryResult();
		
	}
	
	public void printQueryResult() throws Exception
	{
	String location =  System.getProperty("user.dir") + "/Resources/Ontologies/Test-007/EFBO_Test-007_Merged.owl";
	
	OntModel model = ModelFactory.createOntologyModel();//(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
	model.read(EFBO_V_URI, "RDF/XML");
	FileManager.get().readModel( model, location );
	
	//OntModel model1 = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM_MICRO_RULE_INF, model );
	
	//OntClass paperClass = base.getOntClass( namespace + "Paper" );
    //Individual paper = base.createIndividual( namespace + "paper1", paperClass );
	//model.ge
	
		String fileLocation = System.getProperty("user.dir") + "/Resources/SPARQL-Queries/" + "ex.rq";
		String sparqlQuery = loadSPARQLScript(fileLocation);
		System.out.println(sparqlQuery);
		
				
		Query query = QueryFactory.create(sparqlQuery) ;
	    QueryExecution qe = QueryExecutionFactory.create(query, model);
	    ResultSet results = qe.execSelect();
	    
			
		ResultSetFormatter.out(System.out, results, query);
		qe.close();
		
	}
	
	public String loadSPARQLScript(String fileLocation) throws Exception
	{
		File file= new File(fileLocation);
		FileReader fr = new FileReader(file);		 
		BufferedReader br = new BufferedReader(fr);
		 
		String s;
		 
		StringBuffer str = new StringBuffer("");
		 
		while((s= br.readLine())!=null)
		  str.append(s+"\n");	
		br.close();
		return str.toString();
	}
	
		
	private OWLClass getOWLClass(String classURI, String className)
	{
		IRI classIRI = IRI.create(classURI + "#" + className);		
		OWLClass owlClass = efboMergedInferredOntology.getOWLOntologyManager().getOWLDataFactory().getOWLClass(classIRI);
		System.out.println(owlClass);
		return owlClass;
	}
	
	

/*
 * Print formatting is based on the following Class. 
 * Source: https://www.ksmpartners.com/2013/08/nicely-formatted-tabular-output-in-java/
 * */
public class TableBuilder
{
    List<String[]> rows = new LinkedList<String[]>();
 
    public void addRow(String... cols)
    {
        rows.add(cols);
    }
 
    private int[] colWidths()
    {
        int cols = -1;
 
        for(String[] row : rows)
            cols = Math.max(cols, row.length);
 
        int[] widths = new int[cols];
 
        for(String[] row : rows) 
        {
            for(int colNum = 0; colNum < row.length; colNum++) 
            {
                widths[colNum] =
                    Math.max(
                        widths[colNum],
                        StringUtils.length(row[colNum]));
            }
        }
 
        return widths;
    }
 
    @Override
    public String toString()
    {
        StringBuilder buf = new StringBuilder();
 
        int[] colWidths = colWidths();
 
        for(String[] row : rows) 
        {
            for(int colNum = 0; colNum < row.length; colNum++) 
            {
                buf.append(
                    StringUtils.rightPad(
                        StringUtils.defaultString(
                            row[colNum]), colWidths[colNum]));
                buf.append(' ');
            }
 
            buf.append('\n');
        }
 
        return buf.toString();
    }
 
}



}