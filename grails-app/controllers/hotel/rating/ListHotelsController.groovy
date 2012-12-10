package hotel.rating

import de.datenwissen.util.groovyrdf.core.RdfData
import de.datenwissen.util.groovyrdf.core.RdfDataFormat
import de.datenwissen.util.groovyrdf.core.RdfResource
import de.datenwissen.util.groovyrdf.jena.JenaRdfLoader
import de.datenwissen.util.groovyrdf.core.RdfLoader
import de.datenwissen.util.groovyrdf.core.RdfNamespace

class ListHotelsController {

    RdfLoader rdfLoader

    RdfNamespace gr, acco

    @SuppressWarnings ("GroovyAssignabilityCheck")
    def index () {
        RdfData rdfData = rdfLoader.load('http://hotels.datenwissen.de/')
        List<RdfResource> hotelResources = rdfData.listSubjects (acco.Hotel)
        List hotels = hotelResources.collect { hotelResource ->
            [uri: hotelResource.uri, name: hotelResource (gr.name)]
        }.sort {it.name}
        return [hotels: hotels]
    }

}
