package com.example.idrd.domain.interactor.crudEventos

import android.net.Uri
import com.example.idrd.data.model.Evento
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CrudEventosInteractorImpl:CrudEventoInteractor {
    override suspend fun addEventos(evento: Evento, uri: Uri):Unit=
        suspendCancellableCoroutine{ continuacion->
            val db = Firebase.firestore
            var imagen = FirebaseStorage.getInstance().reference.child(evento.eventoDes)
            imagen.putFile(uri).addOnSuccessListener(OnSuccessListener {
                imagen.downloadUrl.addOnSuccessListener {
                    evento.imageUrl = it.toString()
                    var ref = db.collection("Eventos").document()
                    evento.id = ref.id
                    ref.set(evento).addOnCompleteListener { cont ->
                        if (cont.isSuccessful) {
                            continuacion.resume(Unit)
                        } else {
                            continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
                        }
                    }
                }
            })
    }

    override suspend fun editEvento(evento: Evento): Unit= suspendCancellableCoroutine {continuacion->
        val db = Firebase.firestore

        db.collection("Eventos").document(evento.id).set(evento).addOnCompleteListener { cont ->
            if (cont.isSuccessful) {
                continuacion.resume(Unit)
            } else {
                continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
            }
        }
    }

    override suspend fun editEventoFoto(evento: Evento, uri: Uri):Unit= suspendCancellableCoroutine {continuacion->
        val db = Firebase.firestore
        var imagen = FirebaseStorage.getInstance().reference.child(evento.eventoDes)
        imagen.putFile(uri).addOnSuccessListener(OnSuccessListener {
            imagen.downloadUrl.addOnSuccessListener {
                evento.imageUrl = it.toString()
                db.collection("Eventos").document(evento.id).set(evento).addOnCompleteListener { cont ->
                    if (cont.isSuccessful) {
                        continuacion.resume(Unit)
                    } else {
                        continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
                    }
                }
            }
        })

    }

    override suspend fun borarEvento(evento: Evento):Unit= suspendCancellableCoroutine {continuacion->
        val db = Firebase.firestore

        db.collection("Eventos").document(evento.id).delete().addOnCompleteListener { cont ->
            if (cont.isSuccessful) {
                continuacion.resume(Unit)
            } else {
                continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
            }
        }
    }
}