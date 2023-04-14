package com.example.idrd.base

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.idrd.data.database.RepoEventos
import com.example.idrd.data.database.Reposolicitud
import com.example.idrd.data.model.Evento
import com.example.idrd.data.model.Parque
import com.example.idrd.data.model.Solicitud
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ValidarSolicitud {
    var fecha: Date? = null
    var idParque: String? = null
    var context: Context? = null
    var date: String? = null
    var duracionH: String? = null
    var parque: Parque? = null

    constructor() {

    }


    constructor(fecha: Date?, idParque: String, context: Context, date: String, duracionH: String, parque: Parque?) {

        this.fecha = fecha
        this.idParque = idParque
        this.context = context
        this.date = date
        this.duracionH = duracionH
        this.parque = parque
    }
    private val reposolcitud= Reposolicitud()
    private val repo= RepoEventos()
    suspend fun validarsolicitud():Boolean = suspendCancellableCoroutine{continuation->
        var horas1 = Integer.parseInt(duracionH)
        val dateDuracion= formatedDate(date!!, (fecha?.hours!! +horas1).toString()+':'+fecha?.minutes.toString())
        reposolcitud.getSolicitudAdminData(idParque!!).observeForever{solicitud1->
            solicitud1.let {

                var lista = it.filter { x-> x.estado!="Rechazada" } as MutableList<Solicitud>
                if (!lista.isEmpty()){

                    for (item in lista){
                        if(checkDate(fecha!!, item.fecha)==0){
                            Toast.makeText(context,"Esta fecha ya esta reservada",Toast.LENGTH_SHORT).show()
                            continuation.resume(false)
                            return@observeForever
                        }

                        val fechaDuracion= formatedDate(date!!, (item.fecha?.hours!!+item.duracionH).toString()+':'+item.fecha?.minutes.toString())

                        if (fecha?.day == item.fecha!!.day  && fecha?.month == item.fecha!!.month  && fecha?.year == item.fecha!!.year ){
                            if (checkDate(fecha!!,item.fecha)>0 && checkDate(fecha!!, fechaDuracion)<0){
                                Toast.makeText(context,"Esta fecha ya esta reservada",Toast.LENGTH_SHORT).show()
                                continuation.resume(false)
                                return@observeForever
                            }
                            if (checkDate(dateDuracion,item.fecha)>0 && checkDate(dateDuracion, fechaDuracion)<0){
                                Toast.makeText(context,"Esta fecha ya esta reservada",Toast.LENGTH_SHORT).show()
                                continuation.resume(false)
                                return@observeForever
                            }
                        }

                    }
                    continuation.resume(true)
                    return@observeForever
                }

            }

        }

    }
    suspend fun validarevento():Boolean = suspendCancellableCoroutine{continuation->
        var horas1 = Integer.parseInt(duracionH)
        val dateDuracion= formatedDate(date!!, (fecha?.hours!! +horas1).toString()+':'+fecha?.minutes.toString())
        repo.getEventosDataAdmin(idParque!!).observeForever { evento1->
            evento1.let {
                for (item in it){

                    if(checkDate(fecha!!, item.fecha)==0){
                        Toast.makeText(context,"Esta fecha ya esta reservada para un evento",Toast.LENGTH_SHORT).show()
                        continuation.resume(false)
                        return@observeForever
                    }

                    val fechaDuracion= formatedDate(date!!, (item.fecha?.hours!!+item.duracionH).toString()+':'+item.fecha?.minutes.toString())

                    if (fecha!!.day == item.fecha!!.day  && fecha!!.month == item.fecha!!.month  && fecha!!.year == item.fecha!!.year ){
                        if (checkDate(fecha!!,item.fecha)>0 && checkDate(fecha!!, fechaDuracion)<0){
                            Toast.makeText(context,"Esta fecha ya esta reservada para un evento",Toast.LENGTH_SHORT).show()
                            continuation.resume(false)
                            return@observeForever
                        }
                        if (checkDate(dateDuracion,item.fecha)>0 && checkDate(dateDuracion, fechaDuracion)<0){
                            Toast.makeText(context,"Esta fecha ya esta reservada para un evento",Toast.LENGTH_SHORT).show()
                            continuation.resume(false)
                            return@observeForever
                        }
                    }
                }
                continuation.resume(true)
                return@observeForever
            }

        }

    }
    suspend fun validarParque(numUsers:String,hour:String):Boolean = suspendCancellableCoroutine{continuation->
        FirebaseFirestore.getInstance().collection("Parques").document(idParque!!).get().addOnSuccessListener { result->
            val parqueDev: Parque? = result.toObject(Parque::class.java)
            if (parqueDev != null) {
                parqueDev.id=result.id
                parque=parqueDev

                if (validarHorario(hour) && validarAforo(numUsers) ){
                    continuation.resume(true)
                }else{
                    continuation.resume(false)
                }
            }
        }

    }
    fun validarAforo(numUsers:String):Boolean{
        val aforoMaxI = Integer.parseInt(parque!!.aforoMaximo)
        val numUserI = Integer.parseInt(numUsers)
        return if (numUserI>aforoMaxI){
            Toast.makeText(context,"El numero de usuarios excede el aforo maximo",Toast.LENGTH_SHORT).show()
            false
        }else{
            true
        }
    }
    fun validarHorario(hour:String):Boolean{
        val calendar =  Calendar.getInstance()
        calendar.time=fecha
        val diaSemana=calendar.get(Calendar.DAY_OF_WEEK) - 1


        if (parque!!.horarios[diaSemana]=="DEFAULT"){
            Toast.makeText(context,"El dia seleccionado no abre el parque",Toast.LENGTH_SHORT).show()
            return false
        }else if (parque!!.horarios[diaSemana]!="Abierto las 24 horas"){

            var list:List<String> = parque!!.horarios[diaSemana].split("-")
            if (checkHour(list.get(0),hour)){
                Toast.makeText(context,"La hora seleccionada no cumple con el horario",Toast.LENGTH_SHORT).show()
                return false
            }
            if (checkHour(hour,list.get(1))){
                Toast.makeText(context,"La hora seleccionada no cumple con el horario",Toast.LENGTH_SHORT).show()
                return false
            }

        }

        return true

    }
    fun checkDate(date: Date, date2: Date?): Int {
        return date.compareTo(date2)
    }
    fun formatedDate(date: String, hour: String): Date {
        val obtenida= SimpleDateFormat("dd/MM/yyyy HH:mm").parse(date+" "+hour);
        return obtenida
    }
    fun checkHour(hourA: String, hourC: String): Boolean {
        val formatedH = SimpleDateFormat("HH:mm")
        val horaApertura= formatedH.parse(hourA)
        val hora= formatedH.parse(hourC)

        return horaApertura.after(hora)
    }
}