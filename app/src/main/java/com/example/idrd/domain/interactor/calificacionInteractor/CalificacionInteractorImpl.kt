package com.example.idrd.domain.interactor.calificacionInteractor

import com.example.idrd.data.model.Calificacion
import com.example.idrd.presentation.agregarCalificacion.exceptions.FirebaseCalificacionExceptiones
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CalificacionInteractorImpl:CalificacionInteractor {
    override suspend fun sendCalificacion(calificacion: Calificacion):Unit= suspendCancellableCoroutine {continuation->
        val db = Firebase.firestore
        val auth= FirebaseAuth.getInstance()
        calificacion.idUser=auth.currentUser?.uid.toString()
        var ref=db.collection("Calificaciones").document()
        calificacion.id=ref.id
        ref.set(calificacion).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseCalificacionExceptiones(it.exception?.message))
            }
        }
    }

    override suspend fun EditCalificacion(calificacion: Calificacion):Unit= suspendCancellableCoroutine {continuation->

        val db = Firebase.firestore
        db.collection("Calificaciones").document(calificacion.id).set(calificacion).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseCalificacionExceptiones(it.exception?.message))
            }
        }
    }
}