package com.example.idrd.domain.interactor.crud_TiposParques

import com.example.idrd.data.model.TiposParque
import com.example.idrd.presentation.agregarTiposParques.exceptions.FirebaseAgregarTipoExceptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class CrudTiposParquesInteractorImpl:CrudTiposParquesInteractor {

    override suspend fun addTipoParque(tipoParque: TiposParque): Unit = suspendCancellableCoroutine { continuacion ->

            val db = Firebase.firestore.collection("TiposParque").document()
            tipoParque.id = db.id
            db.set(tipoParque).addOnCompleteListener {
                if (it.isSuccessful){
                    continuacion.resume(Unit)
                }else{
                    continuacion.resumeWithException(FirebaseAgregarTipoExceptions(it.exception?.message))
                }
            }
        }

        override suspend fun editTipoParque(tipoParque: TiposParque): Unit = suspendCancellableCoroutine { continuacion ->

            val db = Firebase.firestore
            db.collection("TiposParque").document(tipoParque.id).set(tipoParque).addOnCompleteListener {
                if (it.isSuccessful){
                    continuacion.resume(Unit)
                }else{
                    continuacion.resumeWithException(FirebaseAgregarTipoExceptions(it.exception?.message))
                }
            }
        }

        override suspend fun borarTipoParque(tipoParque: TiposParque): Unit = suspendCancellableCoroutine { continuacion ->

            val db = Firebase.firestore
            db.collection("TiposParque").document(tipoParque.id).delete().addOnCompleteListener {
                if (it.isSuccessful) {
                    continuacion.resume(Unit)
                } else {
                    continuacion.resumeWithException(FirebaseAgregarTipoExceptions(it.exception?.message))
                }
            }
        }

    }