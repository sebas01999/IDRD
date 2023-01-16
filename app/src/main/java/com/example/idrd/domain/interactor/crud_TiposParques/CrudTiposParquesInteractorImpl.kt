package com.example.idrd.domain.interactor.crud_TiposParques

import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.TiposParque
import com.example.idrd.presentation.agregarParque.exceptions.FirebaseAgregarExceptions
import com.example.idrd.presentation.agregarTiposParques.exceptions.FirebaseAgregarTipoExceptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CrudTiposParquesInteractorImpl:CrudTiposParquesInteractor {

    override suspend fun addTipoParque(tipo_parque: TiposParque): Unit = suspendCancellableCoroutine { continuacion ->

            val db = Firebase.firestore.collection("TiposParque").document()
            tipo_parque.id = db.id
            db.set(tipo_parque).addOnCompleteListener {
                if (it.isSuccessful){
                    continuacion.resume(Unit)
                }else{
                    continuacion.resumeWithException(FirebaseAgregarTipoExceptions(it.exception?.message))
                }
            }
        }

        override suspend fun editTipoParque(tipo_parque: TiposParque): Unit = suspendCancellableCoroutine { continuacion ->

            val db = Firebase.firestore
            db.collection("TiposParque").document(tipo_parque.id).set(tipo_parque).addOnCompleteListener {
                if (it.isSuccessful){
                    continuacion.resume(Unit)
                }else{
                    continuacion.resumeWithException(FirebaseAgregarTipoExceptions(it.exception?.message))
                }
            }
        }

        override suspend fun borarTipoParque(tipo_parque: TiposParque): Unit = suspendCancellableCoroutine { continuacion ->

            val db = Firebase.firestore
            db.collection("TiposParque").document(tipo_parque.id).delete().addOnCompleteListener {
                if (it.isSuccessful) {
                    continuacion.resume(Unit)
                } else {
                    continuacion.resumeWithException(FirebaseAgregarTipoExceptions(it.exception?.message))
                }
            }
        }

    }