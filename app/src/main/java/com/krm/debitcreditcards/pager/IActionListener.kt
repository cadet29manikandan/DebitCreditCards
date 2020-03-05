package com.krm.debitcreditcards.pager

interface IActionListener {
    fun onActionComplete(fragment: CreditCardFragment?)
    fun onEdit(fragment: CreditCardFragment?, edit: String?)
}