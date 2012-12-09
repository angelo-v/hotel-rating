package hotel.rating

class HotelRating {

    String ratedHotelUri
    int rating

    String comment

    static constraints = {
        rating (min: 1, max: 10)
        comment (nullable: true)
    }
}
