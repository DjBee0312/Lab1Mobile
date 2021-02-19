package ua.kpi.comsys.ip8421

data class Book(
        var title: String = "No Title",
        var subtitle: String = "",
        var authors: String = "unknown author",
        var publisher: String = "unknown publisher",
        var isbn13: String = "???",
        var pages: String = "",
        var year: String = "no date",
        var rating: String = "not rated",
        var desc: String = "",
        var price: String = "Out of Stock",
        var image: String = ""
    ) {}