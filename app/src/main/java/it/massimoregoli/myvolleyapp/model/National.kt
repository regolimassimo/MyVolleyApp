package it.massimoregoli.myvolleyapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Suppress("SpellCheckingInspection")
@Entity
data class National(
    @PrimaryKey
    var id: Int,
    var data: String,
    var ricoverati_con_sintomi: Int,
    var terapia_intensiva: Int,
    var totale_ospedalizzati: Int,
    var isolamento_domiciliare: Int,
    var totale_positivi: Int,
    var variazione_totale_positivi: Int,
)