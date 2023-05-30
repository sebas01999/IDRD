package com.example.idrd.domain.interactor.registroEntradaInteractor

import com.example.idrd.data.model.Aforo
import com.example.idrd.presentation.codigo_QR.exceptions.CodigoQrExceptions
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class RegistroEntradaInteractorImpl : RegistroEntradaInteractor {


    override suspend fun registroAforo(aforo:Aforo):Unit= suspendCancellableCoroutine { continuation->
        aforo.idUser= FirebaseAuth.getInstance().currentUser?.uid.toString()
        val db = FirebaseFirestore.getInstance().collection("Aforo").document()
        aforo.id=db.id
        db.set(aforo).addOnCompleteListener {
            if(it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(CodigoQrExceptions(it.exception?.message))
            }
        }

    }

    suspend fun getAforoUser(idParque: String): Boolean = suspendCoroutine { continuation ->
        val idUser = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val fechaActual = Timestamp.now().toDate()
        var aforoDisponible = false

        FirebaseFirestore.getInstance().collection("Aforo")
            .whereEqualTo("idParque", idParque)
            .whereEqualTo("idUser", idUser)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (documento in querySnapshot) {
                    val aforo: Aforo = documento.toObject(Aforo::class.java)
                    if (fechaActual.day == aforo.fecha?.day && fechaActual.month == aforo.fecha?.month && fechaActual.year == aforo.fecha?.year) {
                        aforoDisponible = true
                        break
                    }
                }

                continuation.resume(aforoDisponible)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }
}