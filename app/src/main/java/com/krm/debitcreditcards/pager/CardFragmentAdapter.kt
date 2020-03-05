package com.krm.debitcreditcards.pager

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class CardFragmentAdapter(fm: FragmentManager?, args: Bundle?) :
    FragmentStatePagerAdapter(fm!!), IActionListener {
    fun focus(position: Int) {
        (getItem(position) as IFocus).focus()
    }

    interface ICardEntryCompleteListener {
        fun onCardEntryComplete(currentIndex: Int)
        fun onCardEntryEdit(currentIndex: Int, entryValue: String?)
    }

    private val mCardNumberFragment: CardNumberFragment
    private val mCardExpiryFragment: CardExpiryFragment
    private val mCardCVVFragment: CardCVVFragment = CardCVVFragment()
    private val mCardNameFragment: CardNameFragment
    private var mCardEntryCompleteListener: ICardEntryCompleteListener? = null
    fun setOnCardEntryCompleteListener(listener: ICardEntryCompleteListener?) {
        mCardEntryCompleteListener = listener
    }

    override fun getItem(position: Int): Fragment {
        return arrayOf<Fragment>(
            mCardNumberFragment,
            mCardExpiryFragment,
            mCardCVVFragment,
            mCardNameFragment
        )[position]
    }

    override fun getCount(): Int {
        return 4
    }

    override fun onActionComplete(fragment: CreditCardFragment?) {
        val index = getIndex(fragment!!)
        if (index >= 0 && mCardEntryCompleteListener != null) {
            mCardEntryCompleteListener!!.onCardEntryComplete(index)
        }
    }

    private fun getIndex(fragment: CreditCardFragment): Int {
        var index = -1
        when {
            fragment === mCardNumberFragment -> {
                index = 0
            }
            fragment === mCardExpiryFragment -> {
                index = 1
            }
            fragment === mCardCVVFragment -> {
                index = 2
            }
            fragment === mCardNameFragment -> {
                index = 3
            }
        }
        return index
    }

    fun setMaxCVV(maxCVV: Int) {
        mCardCVVFragment.setMaxCVV(maxCVV)
    }

    override fun onEdit(fragment: CreditCardFragment?, edit: String?) {
        val index = getIndex(fragment!!)
        if (index >= 0 && mCardEntryCompleteListener != null) {
            mCardEntryCompleteListener!!.onCardEntryEdit(index, edit)
        }
    }

    override fun restoreState(
        parcelable: Parcelable?,
        classLoader: ClassLoader?
    ) {
        //do nothing here! no call to super.restoreState(parcelable, classLoader);
    }

    init {
        mCardCVVFragment.arguments = args
        mCardNameFragment = CardNameFragment()
        mCardNameFragment.arguments = args
        mCardNumberFragment = CardNumberFragment()
        mCardNumberFragment.arguments = args
        mCardExpiryFragment = CardExpiryFragment()
        mCardExpiryFragment.arguments = args
        mCardNameFragment.setActionListener(this)
        mCardNumberFragment.setActionListener(this)
        mCardExpiryFragment.setActionListener(this)
        mCardCVVFragment.setActionListener(this)
    }
}