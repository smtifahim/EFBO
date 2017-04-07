/**
 * 
 */
package ca.queensu.efbo.annotation.extractor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;

import org.semanticweb.owlapi.model.IRI;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyAxiom;
import org.semanticweb.owlapi.model.OWLIndividualAxiom;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

/**
 * @author Fahim
 *
 */

public class KnowledgeBaseGenerator
{
	private static final String BASE_URL = "http://cs.queensu.ca/~imam/ontologies/efbo.owl" ;
	private ArrayList<Annotation> annotations;
	private OWLOntology efbo;
	private OWLOntology knowledgeBase;
	
	public KnowledgeBaseGenerator() 
	{
		try {
			this.loadOntology();
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}
	
	private void loadOntology() throws OWLOntologyCreationException
	{
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager(); 
        IRI documentIRI = IRI.create(BASE_URL);
        efbo = manager.loadOntologyFromOntologyDocument(documentIRI);
        System.out.println("Ontology Loaded...");
        System.out.println("Document IRI: " + documentIRI);
        System.out.println("Ontology ID: " + efbo.getOntologyID());
        System.out.println("Format: " + manager.getOntologyFormat(efbo));
        
      //Set<OWLAxiom> axioms = efbo.getAxioms(); 
      //printAxioms(axioms); 
        Set<OWLClass> owlClasses = efbo.getClassesInSignature(); 
        printOWLClasses(owlClasses); 
        
        Set<OWLObjectProperty> owlObjectProperties = efbo.getObjectPropertiesInSignature();
        printOWLObjectProperties(owlObjectProperties);
        
        printLogicalAxioms(efbo.getLogicalAxioms());
   }
	
	private static void printOWLObjectProperties(Set<OWLObjectProperty> owlObjectProperties)
	{
		  System.out.println("ALL Object Properites (" + owlObjectProperties.size() + ")"); 
		  for (OWLObjectProperty p : owlObjectProperties) 
		  { 
		   System.out.println(p.toString()); 
		  } 
		  System.out.println("-----------------------------------"); 
	}
	
	private static void printLogicalAxioms(Set<OWLLogicalAxiom> logicalAxioms) 
	{ 
		  System.out.println("ALL LOGICAL AXIOMS (" + logicalAxioms.size() + ")"); 
		 
	} 
		 
	private static void printOWLClasses(Set<OWLClass> classes) 
	{ 
		  System.out.println("ALL CLASSES (" + classes.size() + ")"); 
		  for (OWLClass c : classes) 
		  { 
		   System.out.println(c.toString()); 
		  } 
		  System.out.println("-----------------------------------"); 
	} 
		 
   private static void printAxioms(Set<OWLAxiom> axioms) 
	{ 
	   	  Set<OWLAxiom> axIndividual = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axDataProperty = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axObjectProperty = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axClass = new HashSet<OWLAxiom>(); 
		  Set<OWLAxiom> axOther = new HashSet<OWLAxiom>(); 
		 
		  for (OWLAxiom axiom : axioms) 
		  { 
		   axiom.getSignature(); 
		   if ((axiom instanceof OWLClassAxiom))
		   		{axClass.add(axiom);} 
		   	else if (axiom instanceof OWLDataPropertyAxiom)
		   		{axDataProperty.add(axiom);} 
		   		else if (axiom instanceof OWLObjectPropertyAxiom)
		   			{axDataProperty.add(axiom);} 
		   			else if (axiom instanceof OWLIndividualAxiom) 
		   				{axIndividual.add(axiom);}
		   				else axOther.add(axiom); 
		  } 
		 
		  System.out.println("ALL AXIOMS (" + axioms.size() + ")"); 
		  
		  for (OWLAxiom ax : axIndividual)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: Individual"; 
		   System.out.println(line); 
		  } 
		  
		  for (OWLAxiom ax : axDataProperty)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: DataProperty"; 
		   System.out.println(line); 
		  } 
		  
		  for (OWLAxiom ax : axObjectProperty) 
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: ObjectProperty"; 
		   System.out.println(line); 
		  } 
		  
		  for (OWLAxiom ax : axClass)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: Class"; 
		   System.out.println(line); 
		  }
		  
		  for (OWLAxiom ax : axOther)
		  { 
		   String line; 
		   line = ax.toString() + " TYPE: Other"; 
		   System.out.println(line); 
		  } 
		  
		  System.out.println("-----------------------------------"); 
	} 
	
	private void processKnowledgeBase()
	{
		
	}
	
	private void saveKnowledgeBase()
	{
		
	}

	public ArrayList<Annotation> getAnnotations() {
		return annotations;
	}

	public void setAnnotations(ArrayList<Annotation> annotations) {
		this.annotations = annotations;
	}

	public OWLOntology getEfbo() {
		return efbo;
	}

	public void setEfbo(OWLOntology efbo) {
		this.efbo = efbo;
	}

	public OWLOntology getKnowledgeBase() {
		return knowledgeBase;
	}

	public void setKnowledgeBase(OWLOntology knowledgeBase) {
		this.knowledgeBase = knowledgeBase;
	}
		
}
