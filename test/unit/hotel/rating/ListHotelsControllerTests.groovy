package hotel.rating

import de.datenwissen.util.groovyrdf.core.RdfLoader
import de.datenwissen.util.groovyrdf.core.RdfNamespace
import de.datenwissen.util.groovyrdf.jena.JenaRdfBuilder
import grails.test.mixin.TestFor

@TestFor (ListHotelsController)
class ListHotelsControllerTests {

    void testIndex () {
        controller.acco = new RdfNamespace('http://example.com/acco/')
        controller.gr = new RdfNamespace('http://example.com/gr/')
        controller.rdfLoader = [
                load: { String uri ->
                    return new JenaRdfBuilder ().build {
                        "http://example.com/hotel/1" {
                            a "http://example.com/acco/Hotel"
                            "http://example.com/gr/name" "Hotel 1"
                        }
                        "http://example.com/hotel/2" {
                            a "http://example.com/acco/Hotel"
                            "http://example.com/gr/name" "Hotel 2"
                        }
                    }
                }
        ] as RdfLoader

        def data = controller.index ()
        assertEquals ([hotels: [
                [uri: 'http://example.com/hotel/1', name: 'Hotel 1'],
                [uri: 'http://example.com/hotel/2', name: 'Hotel 2'],
        ]], data)
    }
}
