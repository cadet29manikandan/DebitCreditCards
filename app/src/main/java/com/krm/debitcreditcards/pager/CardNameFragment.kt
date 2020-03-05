package com.krm.debitcreditcards.pager

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.krm.debitcreditcards.CreditCardUtils
import com.krm.debitcreditcards.R

class CardNameFragment : CreditCardFragment() {
    private var mCardNameView: EditText? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        group: ViewGroup?,
        state: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.lyt_card_holder_name, group, false)
        mCardNameView = v.findViewById<View>(R.id.card_name) as EditText
        var name: String? = ""
        if (arguments != null && arguments!!.containsKey(CreditCardUtils.EXTRA_CARD_HOLDER_NAME)) {
            name = arguments!!.getString(CreditCardUtils.EXTRA_CARD_HOLDER_NAME)
        }
        if (name == null) {
            name = ""
        }
        mCardNameView!!.setText(name)
        mCardNameView!!.addTextChangedListener(this)
        return v
    }

    override fun afterTextChanged(s: Editable) {
        onEdit(s.toString())
        if (s.length == resources.getInteger(R.integer.card_name_len)) {
            onComplete()
        }
    }

    override fun focus() {
        if (isAdded) {
            mCardNameView!!.selectAll()
        }
    }
}