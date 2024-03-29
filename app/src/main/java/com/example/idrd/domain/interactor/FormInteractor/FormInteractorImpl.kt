package com.example.idrd.domain.interactor.FormInteractor

import com.example.idrd.data.model.Solicitud
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import com.example.idrd.presentation.form.exceptions.FirebaseFormExceptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FormInteractorImpl: FormInteractor {
    override suspend fun sendRequest(solicitud: Solicitud):Unit= suspendCancellableCoroutine { continuation ->
        val auth= FirebaseAuth.getInstance()
        val db = Firebase.firestore

        solicitud.idUser= auth.currentUser?.uid.toString()

        val ref= db.collection("solicitudes").document()
                solicitud.id=ref.id
            ref.set(solicitud).addOnCompleteListener {
            if (it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseFormExceptions(it.exception?.message))
            }
        }

    }

    override suspend fun deleteRequest(solicitud: Solicitud):Unit= suspendCancellableCoroutine { continuacion->
        val db = Firebase.firestore

        db.collection("solicitudes").document(solicitud.id).delete().addOnCompleteListener { cont ->
            if (cont.isSuccessful) {
                continuacion.resume(Unit)
            } else {
                continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
            }
        }
    }

    override suspend fun editRequest(solicitud: Solicitud):Unit= suspendCancellableCoroutine { continuacion->
        val db = Firebase.firestore

        db.collection("solicitudes").document(solicitud.id).set(solicitud).addOnCompleteListener { cont ->
            if (cont.isSuccessful) {
                continuacion.resume(Unit)
            } else {
                continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
            }
        }
    }
}