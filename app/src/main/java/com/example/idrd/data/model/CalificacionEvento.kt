package com.example.idrd.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

class CalificacionEvento {
    var id:String="DEFAULT ID"
    var comentario:String = "DEFAULT COMENTARIO"
    var idEvento:String = "DEFAULT IDEVENTO"
    var idUser:String = "DEFAULT IDUSER"
    @ServerTimestamp
    var fecha: Date? = null
    var estrellas:Int = 1

}