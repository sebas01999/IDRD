package com.example.idrd.domain.interactor.aceptarrechazarInteractor

import com.example.idrd.presentation.aceptar_rechazar.exceptions.FirebaseaceptarRechazarExceptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class AceptarrechazarInteractorImpl:AceptarrechazarInteractor {

    override suspend fun editsolicitud(estadosol: String, idsol: String) : Unit= suspendCancellableCoroutine { continuation->

        val db= Firebase.firestore
        db.collection("solicitudes").document(idsol).update("estado",estadosol).addOnCompleteListener {

            if(it.isSuccessful){
                continuation.resume(Unit)
            }else{

                continuation.resumeWithException(FirebaseaceptarRechazarExceptions(it.exception?.message))

            }

        }
    }

}