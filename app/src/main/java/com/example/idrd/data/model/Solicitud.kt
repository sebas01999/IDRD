package com.example.idrd.data.model


import java.io.Serializable
import java.util.*

class Solicitud : Serializable {
    var naturaleza:String = "DEFAULT NATURALEZA"
    var numUsers:Int = 0
    var fecha:Date? = null
    var duracionH:Int = 0
    var idParque:String = "DEFAULT ID PARQUE"
    var idUser:String = "DEFAULT ID USER"
    var estado:String = "En espera"
    var id:String="DEFAULT"
    var nombre:String="DEFAULT NOMBRE PARQUE"
    var url:String="DEFAULT FOTO"

}