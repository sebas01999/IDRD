package com.example.idrd.domain.interactor.notificaciones_Interactor

import com.example.idrd.data.model.Notificacion
import com.example.idrd.presentation.notificaciones.exceptions.Firebase_notificaciones_Exceptions
import com.example.idrd.presentation.registro_botones.exceptions.FirebaseRegistroBotonesExceptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class Notificaciones_InteractorImpl:Notificaciones_Interactor {
    override suspend fun notificacion(notificacion: Notificacion, Id: String): Unit= suspendCancellableCoroutine { continuation ->
        val ref = FirebaseDatabase.getInstance().getReference("Notificaciones")
        ref.child(Id).push().setValue(notificacion).addOnCompleteListener {

            if (it.isSuccessful) {
                continuation.resume(Unit)
            } else {
                continuation.resumeWithException(Firebase_notificaciones_Exceptions(it.exception?.message))
            }
        }
    }
}