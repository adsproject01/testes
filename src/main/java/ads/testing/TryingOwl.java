package ads.testing;

import java.io.File;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class TryingOwl {
	public static void main(String[] args) {
		OWLOntologyManager m = OWLManager.createOWLOntologyManager();
		try {
			OWLOntology o = m.createOntology();
			File file = new File("boop");
			m.saveOntology(o, IRI.create(file));
		} catch (OWLOntologyCreationException | OWLOntologyStorageException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
