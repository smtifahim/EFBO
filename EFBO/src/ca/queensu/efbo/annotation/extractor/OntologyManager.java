/**
 * 
 */
package ca.queensu.efbo.annotation.extractor;


import java.io.File; 
import java.util.ArrayList; 
import java.util.Arrays; 
import java.util.HashMap; 
import java.util.HashSet; 
import java.util.List; 
import java.util.Map; 
import java.util.Set; 
 
import org.semanticweb.owlapi.apibinding.OWLManager; 
import org.semanticweb.owlapi.model.AddAxiom; 
import org.semanticweb.owlapi.model.IRI; 
import org.semanticweb.owlapi.model.OWLAnnotation; 
import org.semanticweb.owlapi.model.OWLAnnotationAssertionAxiom; 
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationValue;
import org.semanticweb.owlapi.model.OWLClass; 
import org.semanticweb.owlapi.model.OWLClassExpression; 
import org.semanticweb.owlapi.model.OWLDataFactory; 
import org.semanticweb.owlapi.model.OWLEntity; 
import org.semanticweb.owlapi.model.OWLLiteral; 
import org.semanticweb.owlapi.model.OWLObjectProperty; 
import org.semanticweb.owlapi.model.OWLOntology; 
import org.semanticweb.owlapi.model.OWLOntologyCreationException; 
import org.semanticweb.owlapi.model.OWLOntologyManager; 
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.search.EntitySearcher;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary; 
 
public class OntologyManager 
{ 
 private static String DB_ID_PATTERN = "(\\w*):(\\d*)"; 
 private String ontologyURI = null; 
 private String ontologyName = null; 
 private File ontologyFile = null; 
 private OWLDataFactory factory = null; 
 private OWLOntology ontology = null; 
 private OWLOntologyManager manager = null; 
 private Set<String> synonymsProperties; 
 
 private Set<String> owlObjectProperties; 
 { 
  owlObjectProperties = new HashSet<String>( 
    Arrays.asList("http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#is_associated_with")); 
 } 
 
 { 
  new HashSet<String>(Arrays.asList("http://purl.obolibrary.org/obo/", 
    "http://ncicb.nci.nih.gov/xml/owl/EVS/Thesaurus.owl#DEFINITION")); 
 } 
 
 private Map<String, OWLClass> hashToRetrieveClass = new HashMap<String, OWLClass>(); 
 
 public OntologyManager()
 {
	 this.manager = OWLManager.createOWLOntologyManager();
	 this.factory = manager.getOWLDataFactory();
 }
 
 public OntologyManager(OWLOntologyManager manager, OWLDataFactory factory) 
 { 
  this.manager = manager; 
  this.factory = factory; 
 } 
 
 public OntologyManager(String ontologyName, File ontologyFile) throws OWLOntologyCreationException 
 { 
  this.ontologyFile = ontologyFile; 
  this.manager = OWLManager.createOWLOntologyManager(); 
  this.factory = manager.getOWLDataFactory(); 
  this.ontologyName = ontologyName; 
  this.ontology = manager.loadOntologyFromOntologyDocument(ontologyFile); 
  this.ontologyURI = ontology.getOntologyID().getOntologyIRI().toString(); 
 } 
 
 public void loadOntology(String ontologyName, File ontologyFile) throws OWLOntologyCreationException 
 { 
  this.ontologyName = ontologyName; 
  this.ontologyFile = ontologyFile; 
  this.ontology = manager.loadOntologyFromOntologyDocument(ontologyFile); 
  this.ontologyURI = ontology.getOntologyID().getOntologyIRI().toString(); 
 } 
 
 public void loadOntology(String ontologyName, String ontologyURI) throws OWLOntologyCreationException 
 { 
  this.ontologyName = ontologyName; 
  this.ontologyURI = ontologyURI;
  IRI documentIRI = IRI.create(ontologyURI);
  this.ontology = manager.loadOntology(documentIRI); 
  this.preProcessing();
 } 
 
 public void preProcessing() 
 { 
  for (OWLClass cls : ontology.getClassesInSignature()) 
  { 
   hashToRetrieveClass.put(getLabel(cls).trim().toLowerCase(), cls); 
  } 
 } 
 
 public OWLOntology getLoadedOntology()
 {
	 return ontology;
 }
 
 public Set<OWLAnnotationAssertionAxiom> getAllAnnotationAxiom(OWLClass cls) 
 { 
  Set<OWLAnnotationAssertionAxiom> axioms = new HashSet<OWLAnnotationAssertionAxiom>(); 
  for (OWLAnnotationProperty annotation : cls.getAnnotationPropertiesInSignature()) 
  { 
   axioms.add(factory.getOWLAnnotationAssertionAxiom(cls.getIRI(), (OWLAnnotation) annotation)); 
  } 
  return axioms; 
 } 
 
 public Set<OWLClass> getRootClasses() 
 { 
  Set<OWLClass> listOfTopClasses = new HashSet<OWLClass>(); 
  for (OWLClass cls : ontology.getClassesInSignature()) 
  { 
   if (ontology.getSubClassAxiomsForSubClass(cls).size() == 0 
     && ontology.getEquivalentClassesAxioms(cls).size() == 0) listOfTopClasses.add(cls); 
  } 
  return listOfTopClasses; 
 } 
 
 public OWLClass getTopClass() 
 { 
  return factory.getOWLThing(); 
 } 
 
 public List<Set<OWLClass>> getAssociatedClasses(OWLClass cls) 
 { 
  List<Set<OWLClass>> alternativeDefinitions = new ArrayList<Set<OWLClass>>(); 
  for (OWLSubClassOfAxiom axiom : ontology.getSubClassAxiomsForSubClass(cls)) 
  { 
   Set<OWLClass> associatedTerms = new HashSet<OWLClass>(); 
   OWLClassExpression expression = axiom.getSuperClass(); 
   if (expression.isAnonymous()) 
   { 
    for (OWLObjectProperty property : expression.getObjectPropertiesInSignature()) 
    { 
     if (owlObjectProperties.contains(property.getIRI().toString())) 
     { 
      for (OWLClass associatedClass : expression.getClassesInSignature()) 
      { 
       associatedTerms.add(associatedClass); 
      } 
     } 
    } 
   } 
   alternativeDefinitions.add(associatedTerms); 
  } 
  return alternativeDefinitions; 
 } 
 
 public Set<OWLClass> getChildClass(OWLClass cls) 
 { 
  Set<OWLClass> listOfClasses = new HashSet<OWLClass>(); 
  for (OWLSubClassOfAxiom axiom : ontology.getSubClassAxiomsForSuperClass(cls)) 
  { 
   OWLClassExpression expression = axiom.getSubClass(); 
   if (!expression.isAnonymous()) 
   { 
    OWLClass asOWLClass = expression.asOWLClass(); 
    listOfClasses.add(asOWLClass); 
   } 
  } 
  return listOfClasses; 
 } 
 
 // TODO: what if the ontology terms have multiple IDs? 
 public String getId(OWLClass entity) 
 { 
  for (OWLAnnotationProperty owlObjectProperty : ontology.getAnnotationPropertiesInSignature()) 
  { 
   if (ifExistsAnnotation(owlObjectProperty.toString(), "id")) 
   { 
    for (String annotation : getAnnotation(entity, owlObjectProperty.getIRI().toString())) 
    { 
     return annotation; 
    } 
   } 
  } 
  return ""; 
 } 
 
 private boolean ifExistsAnnotation(String propertyUrl, String keyword) 
 { 
  String pattern = "[\\W_]*" + keyword + "[\\W_]*"; 
  // Use # as the separator 
  String[] urlFragments = propertyUrl.split("[#/]"); 
  if (urlFragments.length > 1) 
  { 
   String label = urlFragments[urlFragments.length - 1].replaceAll("[\\W]", "_"); 
   for (String token : label.split("_")) 
   { 
    if (token.matches(pattern)) return true; 
   } 
  } 
  return false; 
 } 
 
 public Set<String> getSynonyms(OWLClass cls) 
 { 
  Set<String> listOfSynonyms = new HashSet<String>(); 
  for (String eachSynonymProperty : synonymsProperties) 
  { 
   listOfSynonyms.addAll(getAnnotation(cls, eachSynonymProperty)); 
  } 
  listOfSynonyms.add(getLabel(cls)); 
  return listOfSynonyms; 
 } 
 
public String getLabel(OWLEntity entity) 
 { 
  for (String annotation : getAnnotation(entity, OWLRDFVocabulary.RDFS_LABEL.toString())) 
  { 
   return annotation; 
  } 
  return extractOWLClassId(entity); 
 } 
 
 private Set<String> getAnnotation(OWLEntity entity, String property) 
 { 
  Set<String> annotations = new HashSet<String>(); 
  try 
  { 
   OWLAnnotationProperty owlAnnotationProperty = factory.getOWLAnnotationProperty(IRI.create(property)); 
   
   for(OWLAnnotation a : EntitySearcher.getAnnotations(entity, ontology, owlAnnotationProperty)) 
   	{
	    OWLAnnotationValue value = a.getValue();
	    if(value instanceof OWLLiteral) 
	    {
	       annotations.add(((OWLLiteral) value).getLiteral());	    	   
	    }
   	}
  } 
  
  catch (Exception e) 
  { 
   throw new RuntimeException("Failed to get label for OWL Entity " + entity); 
  } 
  return annotations; 
 } 
 
 public Map<String, Set<String>> getAllDatabaseIds(OWLClass entity) 
 { 
  Map<String, Set<String>> dbAnnotations = new HashMap<String, Set<String>>(); 
 
  for (OWLAnnotationProperty annotation : entity.getAnnotationPropertiesInSignature()) 
  { 
   if (((OWLAnnotation) annotation).getValue() instanceof OWLLiteral) 
   { 
    OWLLiteral val = (OWLLiteral) ((OWLAnnotation) annotation).getValue(); 
    String value = val.getLiteral().toString(); 
    if (value.matches(DB_ID_PATTERN)) 
    { 
     String databaseName = value.replaceAll(DB_ID_PATTERN, "$1"); 
     if (!dbAnnotations.containsKey(databaseName)) 
     { 
      dbAnnotations.put(databaseName, new HashSet<String>()); 
     } 
     dbAnnotations.get(databaseName).add(value.replaceAll(DB_ID_PATTERN, "$2")); 
    } 
   } 
  } 
  return dbAnnotations; 
 } 
 
public String extractOWLClassId(OWLEntity cls) 
 { 
  StringBuilder stringBuilder = new StringBuilder(); 
  String clsIri = cls.getIRI().toString(); 
  // Case where id is separated by # 
  String[] split = null; 
  if (clsIri.contains("#")) 
  { 
   split = clsIri.split("#"); 
  } 
  else 
  { 
   split = clsIri.split("/"); 
  } 
  stringBuilder.append(split[split.length - 1]); 
  return stringBuilder.toString(); 
 } 
 
 public String getOntologyIRI() 
 { 
  return ontologyURI; 
 } 
 
 public String getOntologyName() 
 { 
  return ontologyName; 
 } 
 
 public String getOntologyFilePath() 
 { 
  return ontologyFile.getAbsolutePath(); 
 } 
 
 public Map<String, OWLClass> getHashToRetrieveClass() 
 { 
  return hashToRetrieveClass; 
 } 
 
 public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSuperClass(OWLClass cls) 
 { 
  return ontology.getSubClassAxiomsForSuperClass(cls); 
 } 
 
 public Set<OWLSubClassOfAxiom> getSubClassAxiomsForSubClass(OWLClass cls) 
 { 
  return ontology.getSubClassAxiomsForSubClass(cls); 
 } 
 
 public void addSynonymsProperties(Set<String> synonymsProperties) 
 { 
  this.synonymsProperties.addAll(synonymsProperties); 
 } 
 
 public OWLClass createClass(String iri, Set<OWLClass> rootClasses) 
 { 
  OWLClass owlClass = factory.getOWLClass(IRI.create(iri)); 
  for (OWLClass rootClass : rootClasses) 
  { 
   if (rootClass != owlClass) addClass(rootClass, owlClass); 
  } 
  return owlClass; 
 } 
 
 public void addClass(OWLClass cls, OWLClass parentClass) 
 { 
  if (parentClass == null) parentClass = factory.getOWLThing(); 
  manager.applyChange(new AddAxiom(ontology, factory.getOWLSubClassOfAxiom(cls, parentClass))); 
 } 
 
 public long count() 
 { 
  return ontology.getClassesInSignature().size(); 
 } 
 
 public Set<OWLClass> getAllclasses() 
 { 
  return ontology.getClassesInSignature(); 
 } 
}