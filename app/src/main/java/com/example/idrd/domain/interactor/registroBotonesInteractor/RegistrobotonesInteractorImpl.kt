package com.example.idrd.domain.interactor.registroBotonesInteractor

import com.example.idrd.presentation.registro_botones.exceptions.FirebaseRegistroBotonesExceptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RegistrobotonesInteractorImpl : RegistrobotonesInteractor {


    override suspend fun Registrobotones(aforo: Int, id: String):Unit= suspendCancellableCoroutine { continuation->


        val ref=FirebaseDatabase.getInstance().getReference("Parques")
        ref.child(id).setValue(aforo).addOnCompleteListener {

            if(it.isSuccessful){
                continuation.resume(Unit)
            }else{
                continuation.resumeWithException(FirebaseRegistroBotonesExceptions(it.exception?.message))
            }
        }

    }
}