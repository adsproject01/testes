package ads.testing;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.swrlapi.factory.SWRLAPIFactory;
import org.swrlapi.parser.SWRLParseException;
import org.swrlapi.sqwrl.SQWRLQueryEngine;
import org.swrlapi.sqwrl.SQWRLResult;
import org.swrlapi.sqwrl.exceptions.SQWRLException;

public class TryingSwrl {
	public static void main(String[] args) {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		try {
			File file = new File("ADS.owl");
			OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		    OWLOntology ontology =  ontologyManager.loadOntologyFromOntologyDocument(file);
		    
		    // Create SQWRL query engine using the SWRLAPI
		    SQWRLQueryEngine queryEngine = SWRLAPIFactory.createSQWRLQueryEngine(ontology);
			
			String numberOfObjectives = "2";
			String query = "Algorithm(?alg) ^ "   
			    	+ "minObjectivesAlgorithmIsAbleToDealWith(?alg,?min) ^ swrlb:lessThanOrEqual(?min,"+numberOfObjectives+")"
			    	+ "maxObjectivesAlgorithmIsAbleToDealWith(?alg,?max) ^ swrlb:greaterThanOrEqual(?max,"+numberOfObjectives+")"
			    	+ " -> sqwrl:select(?alg) ^ sqwrl:orderBy(?alg)";  
			SQWRLResult result = queryEngine.runSQWRLQuery("q1", query);
			System.out.println("Query: \n" + query + "\n");
			System.out.println("Result: ");
		    /*while (result.next()) {
		    	System.out.println(result.getNamedIndividual("alg").getShortName());
		    }
		    System.out.println(ontology.getOntologyID().getOntologyIRI().get().toString());*/
			OWLDocumentFormat ontologyFormat = new RDFJsonLDDocumentFormat();
			OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
			//OWLOntology ontology2 = manager.loadOntologyFromOntologyDocument(inputstream);
			File outputstream = new File("nyeh.json");
			manager.saveOntology(ontology, ontologyFormat, IRI.create(outputstream) );
		} catch (OWLOntologyCreationException | SQWRLException | SWRLParseException | OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
