package com.example.idrd.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.idrd.R

class BaseCod {
    fun transactionFragmet(transaction: FragmentTransaction, fragmento: Fragment){
        transaction.replace(R.id.container, fragmento)

        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}