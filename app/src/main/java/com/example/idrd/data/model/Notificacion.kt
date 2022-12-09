package com.example.idrd.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.io.Serializable
import java.util.*

class Notificacion:Serializable {
    var Titulo:String=""
    var Texto:String=""
    @ServerTimestamp
    var Fecha: Date? =null
    var visto: Boolean=false
}