package hotel.rating



import grails.test.mixin.*
import org.junit.*

@TestFor (HotelRating)
class HotelRatingTests {

    @Before
    void setUp () {
        mockForConstraintsTests(HotelRating)
    }

    void testUriNotNullable () {
        def hotelRating = new HotelRating ()
        assertFalse hotelRating.validate ()
        assertEquals 'nullable', hotelRating.errors['ratedHotelUri']
    }

    void testRatingMinConstraint () {
        def hotelRating = new HotelRating (ratedHotelUri: 'http://example.com/hotel/1')
        assertFalse hotelRating.validate ()
        assertEquals 'min', hotelRating.errors['rating']
        hotelRating.rating = 1
        assertTrue ('Rating should be valid now', hotelRating.validate ())
    }

    void testRatingMaxConstraint () {
        def hotelRating = new HotelRating (ratedHotelUri: 'http://example.com/hotel/1', rating: 11)
        assertFalse hotelRating.validate ()
        assertEquals 'max', hotelRating.errors['rating']
        hotelRating.rating = 10
        assertTrue ('Rating should be valid now', hotelRating.validate ())
    }
}
