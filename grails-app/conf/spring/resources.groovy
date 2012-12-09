import de.datenwissen.util.groovyrdf.jena.JenaRdfLoader
import de.datenwissen.util.groovyrdf.core.RdfNamespace

// Place your Spring DSL code here
beans = {
    rdfLoader (JenaRdfLoader) {}
    gr (RdfNamespace, 'http://purl.org/goodrelations/v1#')
    acco (RdfNamespace, 'http://purl.org/acco/ns#')
    geo (RdfNamespace, 'http://www.geonames.org/ontology#')
    foaf (RdfNamespace, 'http://xmlns.com/foaf/0.1/')
}
