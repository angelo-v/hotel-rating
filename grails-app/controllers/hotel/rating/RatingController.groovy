package hotel.rating

import de.datenwissen.util.groovyrdf.core.RdfLoader
import de.datenwissen.util.groovyrdf.core.RdfResource
import de.datenwissen.util.groovyrdf.core.RdfNamespace

@SuppressWarnings ("GroovyAssignabilityCheck")
class RatingController {

    RdfLoader rdfLoader
    RdfNamespace gr, acco, geo, foaf

    def show (String uri) {
        def hotelResource = rdfLoader.loadResource (uri)
        String name = hotelResource (gr.name)
        int numberOfRooms = hotelResource (acco.numberOfRooms)
        String cityUri = hotelResource (foaf.based_near)?.uri
        String cityName = loadCityName (cityUri)

        return [
                uri: uri,
                name: name,
                numberOfRooms:
                        numberOfRooms,
                city: [
                        name: cityName,
                        uri: cityUri
                ],
                ratings: HotelRating.findAllByRatedHotelUri (uri)
        ]

    }

    private String loadCityName (String cityUri) {
        if (!cityUri) return null
        RdfResource cityResource = rdfLoader.loadResource (cityUri)
        return getCityName (cityResource)
    }

    private String getCityName (RdfResource cityResource) {
        def germanCityName = first (cityResource (geo.alternateName, 'de'))
        return germanCityName ?: first (cityResource (geo.name))
    }

    private String first (def stringOrCollection) {
        stringOrCollection instanceof Collection ? stringOrCollection.first () : stringOrCollection
    }

    def save (String uri, int rating, String comment) {
        new HotelRating (ratedHotelUri: uri, rating: rating, comment: comment).save ()
        redirect (action: 'show', params: [uri: uri])
    }
}
