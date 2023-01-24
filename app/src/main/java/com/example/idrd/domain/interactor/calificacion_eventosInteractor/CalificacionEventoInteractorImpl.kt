package com.example.idrd.domain.interactor.calificacion_eventosInteractor

import com.example.idrd.data.model.Calificacion
import com.example.idrd.data.model.CalificacionEvento
import com.example.idrd.domain.interactor.calificacionInteractor.CalificacionInteractor
import com.example.idrd.presentation.agregarCalificacion.exceptions.FirebaseCalificacionExceptiones
import com.example.idrd.presentation.agregarCalificacionEvento.exceptions.FirebaseCalificacionEventosExceptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CalificacionEventoInteractorImpl:CalificacionEventoInteractor{

    override suspend fun sendCalificacionEvento(calificacionEvento: CalificacionEvento):Unit= suspendCancellableCoroutine {continuation->

        val db = Firebase.firestore
        val auth = FirebaseAuth.getInstance()
        calificacionEvento.idUser=auth.currentUser?.uid.toString()
        var ref=db.collection("CalificacionesEventos").document()
        calificacionEvento.id=ref.id
        ref.set(calificacionEvento).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseCalificacionEventosExceptions(it.exception?.message))
            }
        }
    }

    override suspend fun EditCalificacionEvento(calificacionEvento: CalificacionEvento):Unit= suspendCancellableCoroutine{continuation->

        val db = Firebase.firestore
        db.collection("CalificacionesEventos").document(calificacionEvento.id).set(calificacionEvento).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseCalificacionEventosExceptions(it.exception?.message))
            }
        }
    }

    override suspend fun EditCalificacionEventoGeneral(calificacionEvento: String, idEvento: String):Unit= suspendCancellableCoroutine {continuation->

        val db = Firebase.firestore
        db.collection("Eventos").document(idEvento).update("calificacion", calificacionEvento).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseCalificacionEventosExceptions(it.exception?.message))

            }
        }

    }


}