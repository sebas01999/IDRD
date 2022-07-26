package com.example.idrd.domain.interactor.registerInteractor

import com.example.idrd.data.model.Users
import com.example.idrd.presentation.login.exceptions.FirebaseLoginException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class RegisterInteractorImpl: RegisterInteractor
{
    override suspend fun signUp(users: Users,password: String):Unit= suspendCancellableCoroutine  { continuation ->

        val auth= FirebaseAuth.getInstance()
        val db = Firebase.firestore
        auth.createUserWithEmailAndPassword(users.correo, password).addOnCompleteListener { itSignUp ->
            if (itSignUp.isSuccessful){
                users.id=auth.currentUser?.uid.toString()
                db.collection("Users").document(users.id).set(users).addOnCompleteListener { exito ->
                    if (exito.isSuccessful){
                        continuation.resume(Unit)
                    }else{
                        continuation.resumeWithException(FirebaseLoginException(exito.exception?.message))
                    }
                }

            }else{
                continuation.resumeWithException(FirebaseLoginException(itSignUp.exception?.message))
            }
        }
    }
}