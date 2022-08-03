package com.example.idrd.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

class Calificacion: Serializable {
    var id:String="DEFAULT ID"
    var comentario:String = "DEFAULT COMENTARIO"
    var idParque:String = "DEFAULT IDPARQUE"
    var idUser:String = "DEFAULT IDUSER"
    @ServerTimestamp var fecha: Date? = null
    var estrellas:Int = 1

}