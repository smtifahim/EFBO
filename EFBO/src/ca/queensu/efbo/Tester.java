package ca.queensu.efbo;
import java.util.ArrayList;

import ca.queensu.efbo.annotation.extractor.Annotation;
import ca.queensu.efbo.annotation.extractor.AnnotationExtractor;
import ca.queensu.efbo.annotation.extractor.EFBOKnowledgeBaseGenerator;


public class Tester 
{

	public static void main(String[] args) throws Exception
	{
		//new Console();
		AnnotationExtractor annot = new AnnotationExtractor();
		ArrayList <Annotation> annotations = annot.getExtractedAnnotations();
		
		EFBOKnowledgeBaseGenerator efboKBGenerator1 = new EFBOKnowledgeBaseGenerator();
		efboKBGenerator1.processExtractedAnnotations(annotations);
		efboKBGenerator1.getEFBOManager().printOntologyMetrics();
		efboKBGenerator1.getEFBOManager().printAllIndividuals();
		System.exit(1);
	
	}
}
