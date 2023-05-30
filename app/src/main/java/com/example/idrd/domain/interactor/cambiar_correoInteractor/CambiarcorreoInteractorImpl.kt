package com.example.idrd.domain.interactor.cambiar_correoInteractor

import com.example.idrd.presentation.cambiar_correo.exceptions.FirebasecambiarCorreoExceptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CambiarcorreoInteractorImpl:CambiarcorreoInteractor {

    override suspend fun cambiarCorreo(correo: String): Unit= suspendCancellableCoroutine { continuation->

        val user = Firebase.auth.currentUser
        val db= Firebase.firestore
        val updatecorreo= db.collection("Users").document(user!!.uid)

        user!!.updateEmail(correo).addOnCompleteListener { task ->

                if (task.isSuccessful) {
                    updatecorreo.update("correo",correo).addOnCompleteListener {
                        if (it.isSuccessful){
                            continuation.resume(Unit)
                        }else{
                            continuation.resumeWithException(FirebasecambiarCorreoExceptions(task.exception?.message))
                        }
                    }
                }else{

                    continuation.resumeWithException(FirebasecambiarCorreoExceptions(task.exception?.message))
                }
            }
    }
}