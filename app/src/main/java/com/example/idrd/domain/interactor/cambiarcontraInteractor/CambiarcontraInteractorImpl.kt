package com.example.idrd.domain.interactor.cambiarcontraInteractor

import com.example.idrd.presentation.cambiar_contraseña.exceptions.Firebasecambiar_contraseñaExceptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CambiarcontraInteractorImpl: CambiarcontraInteractor {

    override suspend fun Cambiarcon(contranueva: String): Unit= suspendCancellableCoroutine{ continuation->

        val user = Firebase.auth.currentUser

        user!!.updatePassword(contranueva).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Unit)
                }else{
                    continuation.resumeWithException(Firebasecambiar_contraseñaExceptions(task.exception?.message))
                }
            }

    }


}