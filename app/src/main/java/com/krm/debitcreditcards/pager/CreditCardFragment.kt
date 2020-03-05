package com.krm.debitcreditcards.pager

import android.text.TextWatcher
import androidx.fragment.app.Fragment

abstract class CreditCardFragment : Fragment(), TextWatcher,
    IFocus {
    private var mActionListener: IActionListener? = null
    fun setActionListener(listener: IActionListener?) {
        mActionListener = listener
    }

    override fun beforeTextChanged(
        s: CharSequence,
        start: Int,
        count: Int,
        after: Int
    ) {
    }

    override fun onTextChanged(
        s: CharSequence,
        start: Int,
        before: Int,
        count: Int
    ) {
    }

    fun onEdit(edit: String?) {
        if (mActionListener != null) {
            mActionListener!!.onEdit(this, edit)
        }
    }

    fun onComplete() {
        if (mActionListener != null) {
            mActionListener!!.onActionComplete(this)
        }
    }
}