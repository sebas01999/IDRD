package com.example.idrd.data.model

import java.io.Serializable
import java.util.*

class Evento: Serializable {
    var id:String="DEFAULT ID"
    var activo:Boolean=true
    var imageUrl:String = "DEFAULT URL"
    var eventoDes:String = "DEFAULT EVENTO"
    var color:Int = -1
    var idParque:String = "DEFAULT PARQUE"
    var nombreParque:String = "DEFAULT PARQUE"
    var fecha: Date? = null
    var duracionH:Int = 0
    var calificacion:String="DEFAULT CALIFICACIÓN"

}