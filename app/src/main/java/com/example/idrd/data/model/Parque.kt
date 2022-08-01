package com.example.idrd.data.model

import com.google.firebase.firestore.GeoPoint
import java.io.Serializable

class Parque: Serializable {
    var id:String="DEFAULT ID"
    var imageUrl:String = "DEFAULT URL"
    var nombre:String = "DEFAULT NOMBRE"
    var tipo:String = "DEFAULT TIPO"
    var ubicacion:String = "DEFAULT UBICACION"
    var calificacion:String = "DEFAULT CALIFICACION"
    var horario:String = "DEFAULT HORARIO"
    var descripcion:String = "DEFAULT DESCRIPCION"
    var idAdmin:String = "DEFAULT IDADMIN"
    var cedula:String="DEFAULT CEDULA"
    var nombreAdmin:String="DEFAULT NOMBRE ADMIN"
    var locali:GeoPoint= GeoPoint(0.0,0.0)
}
