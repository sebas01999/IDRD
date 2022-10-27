package com.example.idrd.domain.interactor.cambiarcontraInteractor

import com.example.idrd.presentation.cambiar_contraseña.exceptions.Firebasecambiar_contraseñaExceptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CambiarcontraInteractorImpl: CambiarcontraInteractor {

    override suspend fun Cambiarcon(): Unit= suspendCancellableCoroutine{ continuation->

        val user = Firebase.auth.currentUser

        Firebase.auth.sendPasswordResetEmail(user?.email.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(Firebasecambiar_contraseñaExceptions(it.exception?.message))
            }
        }

    }


}