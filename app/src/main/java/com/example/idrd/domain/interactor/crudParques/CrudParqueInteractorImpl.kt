package com.example.idrd.domain.interactor.crudParques

import android.net.Uri
import android.util.Log
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Users
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


class CrudParqueInteractorImpl:CrudParqueInteractor {
    override suspend fun addParque(parque: Parque, uri: Uri): Unit =
        suspendCancellableCoroutine { continuacion ->
            val db = Firebase.firestore
            var imagen = FirebaseStorage.getInstance().reference.child(parque.nombre)
            imagen.putFile(uri).addOnSuccessListener(OnSuccessListener {
                imagen.downloadUrl.addOnSuccessListener {
                    parque.imageUrl = it.toString()
                    var ref = db.collection("Parques").document()
                    parque.id = ref.id
                    ref.set(parque).addOnCompleteListener { cont ->
                        if (cont.isSuccessful) {

                            continuacion.resume(Unit)
                        } else {
                            continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
                        }
                    }
                }
            })
        }

    override suspend fun editParque(parque: Parque): Unit = suspendCancellableCoroutine { continuacion ->
            val db = Firebase.firestore

            db.collection("Parques").document(parque.id).set(parque).addOnCompleteListener { cont ->
                if (cont.isSuccessful) {
                    continuacion.resume(Unit)
                } else {
                    continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
                }
            }
    }

    override suspend fun editParqueFoto(parque: Parque, uri: Uri): Unit = suspendCancellableCoroutine { continuacion->
        val db = Firebase.firestore
        var imagen = FirebaseStorage.getInstance().reference.child(parque.nombre)
        imagen.putFile(uri).addOnSuccessListener(OnSuccessListener {
            imagen.downloadUrl.addOnSuccessListener {
                parque.imageUrl = it.toString()
                db.collection("Parques").document(parque.id).set(parque).addOnCompleteListener { cont ->
                    if (cont.isSuccessful) {
                        continuacion.resume(Unit)
                    } else {
                        continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
                    }
                }
            }
        })

    }

    override suspend fun borarParque(parque: Parque):Unit= suspendCancellableCoroutine {continuacion->
        val db = Firebase.firestore

        db.collection("Parques").document(parque.id).delete().addOnCompleteListener { cont ->
            if (cont.isSuccessful) {
                continuacion.resume(Unit)
            } else {
                continuacion.resumeWithException(FirebaseAgregarExceptions(cont.exception?.message))
            }
        }
    }

}