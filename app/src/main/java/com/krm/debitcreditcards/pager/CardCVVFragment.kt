package com.krm.debitcreditcards.pager

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.InputFilter.LengthFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.krm.debitcreditcards.CardSelector
import com.krm.debitcreditcards.CreditCardUtils
import com.krm.debitcreditcards.R

class CardCVVFragment : CreditCardFragment() {
    private var mCardCVVView: EditText? = null
    private var mMaxCVV = CardSelector.CVV_LENGHT_DEFAULT
    override fun onCreateView(
        inflater: LayoutInflater,
        group: ViewGroup?,
        state: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.lyt_card_cvv, group, false)
        mCardCVVView = v.findViewById<View>(R.id.card_cvv) as EditText
        var cvv: String? = null
        if (arguments != null && arguments!!.containsKey(CreditCardUtils.EXTRA_CARD_CVV)) {
            cvv = arguments!!.getString(CreditCardUtils.EXTRA_CARD_CVV)
        }
        if (cvv == null) {
            cvv = ""
        }
        mCardCVVView!!.setText(cvv)
        mCardCVVView!!.addTextChangedListener(this)
        return v
    }

    override fun afterTextChanged(s: Editable) {
        onEdit(s.toString())
        if (s.length == mMaxCVV) {
            onComplete()
        }
    }

    override fun focus() {
        if (isAdded) {
            mCardCVVView!!.selectAll()
        }
    }

    fun setMaxCVV(maxCVVLength: Int) {
        if (mCardCVVView != null && mCardCVVView!!.text.toString().length > maxCVVLength) {
            mCardCVVView!!.setText(mCardCVVView!!.text.toString().substring(0, maxCVVLength))
        }
        val filterArray = arrayOfNulls<InputFilter>(1)
        filterArray[0] = LengthFilter(maxCVVLength)
        mCardCVVView!!.filters = filterArray
        mMaxCVV = maxCVVLength
        var hintCVV = ""
        for (i in 0 until maxCVVLength) {
            hintCVV += "X"
        }
        mCardCVVView!!.hint = hintCVV
    }
}