import hotel.rating.HotelRating

class BootStrap {

    def init = { servletContext ->
        new HotelRating(
                ratedHotelUri: 'http://hotels.datenwissen.de/hotel/1#it',
                rating: 3,
                comment: 'Unfreundliches Personal und schlechtes Essen. Einzig die gut ausgestattete Hotelbar erhöht meine Bewertung auf 3.'
        ).save(failOnError: true)
        new HotelRating(
                ratedHotelUri: 'http://hotels.datenwissen.de/hotel/1#it',
                rating: 7,
                comment: 'Hatte 3 angenehme Tage. Bequeme Betten!'
        ).save(failOnError: true)
        new HotelRating(
                ratedHotelUri: 'http://hotels.datenwissen.de/hotel/2#it',
                rating: 8
        ).save(failOnError: true)
        new HotelRating(
                ratedHotelUri: 'http://hotels.datenwissen.de/hotel/3#it',
                rating: 5,
                comment: 'Bin zufrieden, meine Ansprüche sind aber auch nicht sehr hoch.'
        ).save(failOnError: true)
        new HotelRating(
                ratedHotelUri: 'http://hotels.datenwissen.de/hotel/5#it',
                rating: 9,
                comment: 'Der Gasthof zeichnet sich durch hohe Servicequalität aus. Mir hat es sehr gefallen und ich habe endlich München kennen gelernt.'
        ).save(failOnError: true)
    }
    def destroy = {
    }
}
