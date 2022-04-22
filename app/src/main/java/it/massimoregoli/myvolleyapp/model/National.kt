package it.massimoregoli.myvolleyapp.model

data class National(
    var data: String,
    var ricoverati_con_sintomi: Int,
    var terapia_intensiva: Int,
    var totale_ospedalizzati: Int,
    var isolamento_domiciliare: Int,
    var totale_positivi: Int,
    var variazione_totale_positivi: Int,
)