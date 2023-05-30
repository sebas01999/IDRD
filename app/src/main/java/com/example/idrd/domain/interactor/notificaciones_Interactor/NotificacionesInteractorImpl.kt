package com.example.idrd.domain.interactor.notificaciones_Interactor

import com.example.idrd.data.model.Notificacion
import com.example.idrd.presentation.notificaciones.exceptions.FirebaseNotificacionesExceptions
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class NotificacionesInteractorImpl:NotificacionesInteractor {
    override suspend fun notificacion(notificacion: Notificacion, id: String): Unit= suspendCancellableCoroutine { continuation ->
        val ref = FirebaseDatabase.getInstance().getReference("Notificaciones").child(id).push()
        notificacion.id=ref.key.toString()
        ref.setValue(notificacion).addOnCompleteListener {

            if (it.isSuccessful) {
                continuation.resume(Unit)
            } else {
                continuation.resumeWithException(FirebaseNotificacionesExceptions(it.exception?.message))
            }
        }
    }
}