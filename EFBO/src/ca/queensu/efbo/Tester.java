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
		
	 new EFBOKnowledgeBaseGenerator();
	

		/*
		AnnotationExtractor annot = new AnnotationExtractor();
		ArrayList <Annotation> annotations = annot.getExtractedAnnotations();
		
	
			for (Annotation annotation: annotations)
			{
			System.out.println("\n\n");
			System.out.println(annotation.getFileLocation());
			System.out.println(annotation.getLineNumber());
			System.out.println(annotation.getSubject());
			System.out.println(annotation.getPredicate());
			System.out.println(annotation.getObject());
			}

			System.out.println(annot.getExtractedAnnotations());*/
	
	}
}
