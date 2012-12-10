package hotel.rating



import grails.test.mixin.*
import org.junit.*
import de.datenwissen.util.groovyrdf.core.RdfNamespace
import de.datenwissen.util.groovyrdf.jena.JenaRdfBuilder
import de.datenwissen.util.groovyrdf.core.RdfLoader

@TestFor (RatingController)
@Mock (HotelRating)
class RatingControllerTests {

    @Before
    void setUp () {
        controller.acco = new RdfNamespace('http://example.com/acco/')
        controller.gr = new RdfNamespace('http://example.com/gr/')
        controller.geo = new RdfNamespace('http://example.com/geo/')
        controller.foaf = new RdfNamespace('http://example.com/foaf/')
    }

    void testShowHotelWithoutRatings () {
        mockLoaderForHotelResourceBasedNear ('http://example.com/city/withoutAlternativeNames')
        def data = controller.show('http://example.com/hotel/1')
        assertEquals('http://example.com/hotel/1', data.uri)
        assertEquals('Hotel 1', data.name)
        assertEquals(42, data.numberOfRooms)
        assertEquals([], data.ratings)
    }

    void testShowHotelWithTwoRatings () {
        new HotelRating(ratedHotelUri: 'http://example.com/hotel/1', rating: 3, comment: 'Foo').save(validate: false)
        new HotelRating(ratedHotelUri: 'http://example.com/hotel/1', rating: 6).save(validate: false)
        new HotelRating(ratedHotelUri: 'http://example.com/hotel/2', rating: 1).save(validate: false)
        mockLoaderForHotelResourceBasedNear ('http://example.com/city/withoutAlternativeNames')
        def data = controller.show('http://example.com/hotel/1')
        assertEquals('http://example.com/hotel/1', data.uri)
        assertEquals('Hotel 1', data.name)
        assertEquals(42, data.numberOfRooms)
        assertEquals(2, data.ratings.size())
        assertEquals(3, data.ratings[0].rating)
        assertEquals(6, data.ratings[1].rating)
        assertEquals('Foo', data.ratings[0].comment)
        assertNull('Should not have comment', data.ratings[1].comment)
    }

    void testShowHotelWithDefaultCityName () {
        mockLoaderForHotelResourceBasedNear ('http://example.com/city/withoutAlternativeNames')
        def data = controller.show('http://example.com/hotel/1')
        assertEquals('http://example.com/city/withoutAlternativeNames', data.city.uri)
        assertEquals('City', data.city.name)
    }

    void testShowHotelWithGermanCityNameIfAvailable () {
        mockLoaderForHotelResourceBasedNear ('http://example.com/city/withAlternativeNames')
        def data = controller.show('http://example.com/hotel/1')
        assertEquals('http://example.com/city/withAlternativeNames', data.city.uri)
        assertEquals('Stadt', data.city.name)
    }

    private void mockLoaderForHotelResourceBasedNear (String cityUri) {
        controller.rdfLoader = [
                loadResource: { String uri ->
                    if (uri == "http://example.com/city/withAlternativeNames") {
                        return mockCityResourceWithAlternateNames ()
                    } else if (uri == "http://example.com/city/withoutAlternativeNames") {
                        return mockCityResourceWithoutAlternateNames ()
                    } else {
                        return mockHotelResource (uri, cityUri)
                    }
                }
        ] as RdfLoader
    }

    private def mockHotelResource (String uri, String cityUri) {
        return new JenaRdfBuilder ().build {
            "$uri" {
                "http://example.com/gr/name" "Hotel 1"
                "http://example.com/acco/numberOfRooms" 42
                "http://example.com/foaf/based_near" {
                    "$cityUri" {}
                }
            }
        } (uri)
    }

    private def mockCityResourceWithAlternateNames () {
        return new JenaRdfBuilder ().build {
            "http://example.com/city/withAlternateNames" {
                "http://example.com/geo/name" "City"
                "http://example.com/geo/alternateName" (['Städtchen', 'Stadt'], [lang: 'de'])
                "http://example.com/geo/alternateName" "Ville", [lang: 'fr']
            }
        } ("http://example.com/city/withAlternateNames")
    }

    private def mockCityResourceWithoutAlternateNames () {
        return new JenaRdfBuilder ().build {
            "http://example.com/city/withoutAlternateNames" {
                "http://example.com/geo/name" (['Town', 'City'])
            }
        } ("http://example.com/city/withoutAlternateNames")
    }

    void testSaveRatingWithComment () {
        assertEquals('No rating should be in database yet', 0, HotelRating.count)

        controller.save('http://example.com/hotel/1', 1, 'Foo')

        assertEquals('One rating should be in database', 1, HotelRating.count)
        def rating = HotelRating.findByRatedHotelUri('http://example.com/hotel/1')
        assertNotNull('Rating should have been saved.', rating)
        assertEquals('Foo', rating.comment)
        assertEquals(1, rating.rating)
        assertEquals('/rating/show?uri=http%3A%2F%2Fexample.com%2Fhotel%2F1', response.redirectedUrl)
    }

    void testSaveRatingWithoutComment () {
        assertEquals('No rating should be in database yet', 0, HotelRating.count)

        controller.save('http://example.com/hotel/1', 10, null)

        assertEquals('One rating should be in database', 1, HotelRating.count)
        def rating = HotelRating.findByRatedHotelUri('http://example.com/hotel/1')
        assertNotNull('Rating should have been saved.', rating)
        assertNull('Rating should not have a comment', rating.comment)
        assertEquals(10, rating.rating)
        assertEquals('/rating/show?uri=http%3A%2F%2Fexample.com%2Fhotel%2F1', response.redirectedUrl)
    }

    void testDoNotSaveInvalidRating () {
        assertEquals('No rating should be in database yet', 0, HotelRating.count)
        int maxAllowedRating = HotelRating.constraints.rating.max
        controller.save('http://example.com/hotel/1', maxAllowedRating + 1, 'Foo')
        assertEquals('Rating should not be saved because rating exceeded maximum', 0, HotelRating.count)
        assertEquals('/rating/show?uri=http%3A%2F%2Fexample.com%2Fhotel%2F1', response.redirectedUrl)
    }

}
