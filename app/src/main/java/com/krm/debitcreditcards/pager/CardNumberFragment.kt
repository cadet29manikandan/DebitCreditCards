package com.krm.debitcreditcards.pager

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.krm.debitcreditcards.CreditCardUtils
import com.krm.debitcreditcards.CreditCardUtils.CardType
import com.krm.debitcreditcards.R

class CardNumberFragment : CreditCardFragment() {
    private var mCardNumberView: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        group: ViewGroup?,
        state: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.lyt_card_number, group, false)
        mCardNumberView = v.findViewById<View>(R.id.card_number_field) as EditText
        var number: String? = ""
        if (arguments != null && arguments!!.containsKey(CreditCardUtils.EXTRA_CARD_NUMBER)) {
            number = arguments!!.getString(CreditCardUtils.EXTRA_CARD_NUMBER)
        }
        if (number == null) {
            number = ""
        }
        mCardNumberView!!.setText(number)
        mCardNumberView!!.addTextChangedListener(this)
        return v
    }

    override fun afterTextChanged(s: Editable) {
        val cursorPosition = mCardNumberView!!.selectionEnd
        val previousLength = mCardNumberView!!.text.length
        val cardNumber = CreditCardUtils.handleCardNumber(s.toString())
        val modifiedLength = cardNumber.length
        mCardNumberView!!.removeTextChangedListener(this)
        mCardNumberView!!.setText(cardNumber)
        val rawCardNumber = cardNumber.replace(CreditCardUtils.SPACE_SEPERATOR, "")
        val cardType = CreditCardUtils.selectCardType(rawCardNumber)
        val maxLengthWithSpaces =
            (if (cardType == CardType.AMEX_CARD) CreditCardUtils.CARD_NUMBER_FORMAT_AMEX else CreditCardUtils.CARD_NUMBER_FORMAT).length
        mCardNumberView!!.setSelection(if (cardNumber.length > maxLengthWithSpaces) maxLengthWithSpaces else cardNumber.length)
        mCardNumberView!!.addTextChangedListener(this)
        if (modifiedLength in (cursorPosition + 1)..previousLength) {
            mCardNumberView!!.setSelection(cursorPosition)
        }
        onEdit(cardNumber)
        if (rawCardNumber.length == CreditCardUtils.selectCardLength(cardType)) {
            onComplete()
        }
    }

    override fun focus() {
        if (isAdded) {
            mCardNumberView!!.selectAll()
        }
    }
}