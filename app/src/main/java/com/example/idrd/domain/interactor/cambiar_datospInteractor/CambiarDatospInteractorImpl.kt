package com.example.idrd.domain.interactor.cambiar_datospInteractor

import com.example.idrd.data.model.Users
import com.example.idrd.presentation.cambiar_datospersonales.exceptions.FirebasecambiarDatospExceptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CambiarDatospInteractorImpl: CambiarDatospInteractor{

    override suspend fun cambiarDatosp(datosp: Users): Unit= suspendCancellableCoroutine{ continuation ->
        val db= Firebase.firestore

        db.collection("Users").document(datosp.id).set(datosp).addOnCompleteListener { cont->
            if(cont.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebasecambiarDatospExceptions(cont.exception?.message))
            }

        }

    }

}