package ua.kpi.comsys.ip8421

data class RequestList(
    var error : String,
    var total : String,
    var page : String,
    var books : MutableList<Book>
) {}