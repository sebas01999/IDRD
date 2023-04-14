package com.example.idrd.domain.interactor.recuperarConInteractor

import com.example.idrd.presentation.recuperar.exceptions.FirebaseRecuperarExceptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RecuperarConInteractorImpl : RecuperarConInteractor{
    override suspend fun recuperarCon(correo: String):Unit= suspendCancellableCoroutine {continuation->
        Firebase.auth.sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(Unit)
                }else{
                    continuation.resumeWithException(FirebaseRecuperarExceptions(task.exception?.message))
                }
            }
    }

}