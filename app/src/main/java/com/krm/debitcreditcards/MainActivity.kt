package com.krm.debitcreditcards

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private val createNewCard = 0
    private var cardContainer: LinearLayout? = null
    private var addCardButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialize()
        listeners()
    }

    private fun initialize() {
        addCardButton = findViewById<View>(R.id.add_card) as Button
        cardContainer = findViewById<View>(R.id.card_container) as LinearLayout
        populate()
    }

    private fun populate() {
        val sampleCreditCardView = CreditCardView(this)
        val name = "Manikandan KRM"
        val cvv = "945"
        val expiry = "03/20"
        val cardNumber = "1234123412341234"
        sampleCreditCardView.cvv = cvv
        sampleCreditCardView.cardHolderName = name
        sampleCreditCardView.setCardExpiry(expiry)
        sampleCreditCardView.cardNumber = cardNumber
        cardContainer!!.addView(sampleCreditCardView)
        val index = cardContainer!!.childCount - 1
        addCardListener(index, sampleCreditCardView)
    }

    private fun listeners() {
        addCardButton!!.setOnClickListener {
            val intent = Intent(this@MainActivity, CardEditActivity::class.java)
            startActivityForResult(intent, createNewCard)
        }
    }

    private fun addCardListener(index: Int, creditCardView: CreditCardView) {
        creditCardView.setOnClickListener { v ->
            val mCreditCardView = v as CreditCardView
            val cardNumber = mCreditCardView.cardNumber
            val expiry = mCreditCardView.expiry
            val cardHolderName = mCreditCardView.cardHolderName
            val cvv = mCreditCardView.cvv
            val intent = Intent(this@MainActivity, CardEditActivity::class.java)
            intent.putExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME, cardHolderName)
            intent.putExtra(CreditCardUtils.EXTRA_CARD_NUMBER, cardNumber)
            intent.putExtra(CreditCardUtils.EXTRA_CARD_EXPIRY, expiry)
            intent.putExtra(
                CreditCardUtils.EXTRA_CARD_SHOW_CARD_SIDE,
                CreditCardUtils.CARD_SIDE_BACK
            )
            intent.putExtra(CreditCardUtils.EXTRA_VALIDATE_EXPIRY_DATE, false)

            intent.putExtra(
                CreditCardUtils.EXTRA_ENTRY_START_PAGE,
                CreditCardUtils.CARD_CVV_PAGE
            )
            startActivityForResult(intent, index)
        }
    }

    public override fun onActivityResult(
        reqCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(reqCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val name = data!!.getStringExtra(CreditCardUtils.EXTRA_CARD_HOLDER_NAME)
            val cardNumber = data.getStringExtra(CreditCardUtils.EXTRA_CARD_NUMBER)
            val expiry = data.getStringExtra(CreditCardUtils.EXTRA_CARD_EXPIRY)
            val cvv = data.getStringExtra(CreditCardUtils.EXTRA_CARD_CVV)
            if (reqCode == createNewCard) {
                val creditCardView = CreditCardView(this)
                creditCardView.cvv = cvv
                creditCardView.cardHolderName = name
                creditCardView.setCardExpiry(expiry)
                creditCardView.cardNumber = cardNumber
                cardContainer!!.addView(creditCardView)
                val index = cardContainer!!.childCount - 1
                addCardListener(index, creditCardView)
            } else {
                val creditCardView =
                    cardContainer!!.getChildAt(reqCode) as CreditCardView
                creditCardView.setCardExpiry(expiry)
                creditCardView.cardNumber = cardNumber
                creditCardView.cardHolderName = name
                creditCardView.cvv = cvv
            }
        }
    }
}