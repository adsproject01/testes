package ads.testing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.OWLXMLDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonDocumentFormat;
import org.semanticweb.owlapi.formats.RDFJsonLDDocumentFormat;
import org.semanticweb.owlapi.model.OWLDocumentFormat;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class Check {
    public static void main(String[] args) throws Exception {
        InputStream inputstream= new FileInputStream(new File("PMOEA.owl"));
        OutputStream outputstream= new FileOutputStream(new File("PMOEA3.json"));
        //OWLDocumentFormat ontologyFormat = new RDFJsonLDDocumentFormat();
        //OWLDocumentFormat ontologyFormat = new RDFJsonDocumentFormat();
        OWLDocumentFormat ontologyFormat = new OWLXMLDocumentFormat();
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = manager.loadOntologyFromOntologyDocument(inputstream);
        manager.saveOntology( ontology, ontologyFormat, outputstream );
        System.out.println("done?");
    }
}
