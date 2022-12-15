package com.example.idrd.data.model

import com.google.firebase.Timestamp
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.Serializable
import java.util.*

class Notificacion:Serializable {
    var id:String=""
    var Titulo:String=""
    var Texto:String=""
    var Fecha: Date =Timestamp.now().toDate()
    var visto: Boolean=false
}