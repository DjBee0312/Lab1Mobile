package ua.kpi.comsys.ip8421

data class Images(
    var total : String,
    var totalHits : String,
    var hits : MutableList<Image>
){}
