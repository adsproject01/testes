package ads.testing;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import de.uni_stuttgart.vis.vowl.owl2vowl.Owl2Vowl;

public class TryingOwl2Vowl {

	public static void main(String[] args) {
		try {
			OWLOntologyManager m = OWLManager.createOWLOntologyManager();
			File file = new File("ADS.owl");
			OWLOntologyManager ontologyManager = OWLManager.createOWLOntologyManager();
		    OWLOntology ontology;
			ontology = ontologyManager.loadOntologyFromOntologyDocument(file);
			Owl2Vowl o2v = new Owl2Vowl(ontology);
			System.out.println(o2v.getJsonAsString());
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
