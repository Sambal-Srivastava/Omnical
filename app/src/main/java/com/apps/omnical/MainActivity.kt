package com.apps.omnical

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var price = 0.0
    var taxAndHandling = 0.2238
    var priceWTaxAndHanding = 0.0
    var discount = 0.4
    var extraCharge = 0.0
    var myPrice = 0.0
    var additionalCost = 0.3
    var publicPrice = 0.0
    var myProfit = 0.0
    lateinit var context: Context
//    lateinit var resources: Resources
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvLanguage.setOnClickListener {
            //============change the app language==================
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                arrayOf("English", "Spanish")
            )
            tvLanguage.threshold = 1 //will start working from first character

            tvLanguage.dropDownVerticalOffset = 20
            tvLanguage.dropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT
            tvLanguage.dropDownWidth = ViewGroup.LayoutParams.WRAP_CONTENT
            tvLanguage.setAdapter(adapter)
            tvLanguage.showDropDown()
        }

        tvLanguage.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                if (tvLanguage.text.toString().equals("English",  true)){
                    setAppLocale(this, "en")
                }else{
                    setAppLocale(this, "es")

                }
            }

        btnCalculate.setOnClickListener {
            publicPrice = 0.0
            doSomeCalculationals()
        }

//        etPublicPrice.addTextChangedListener()
        etMyDiscount.setText("40")

    }

    /*private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            output.text = s
            if (start == 12) {
                Toast.makeText(applicationContext, "Maximum Limit Reached", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }*/

    @SuppressLint("SetTextI18n")
    private fun doSomeCalculationals() {
        price = etPrice.text.toString().toDouble()
        priceWTaxAndHanding= ((price*taxAndHandling)+price)
        etPriceWTaxHandling.setText("$priceWTaxAndHanding $")

        extraCharge = priceWTaxAndHanding-price

        discount = etMyDiscount.text.toString().toDouble()/100

        myPrice = price-(price*discount)+extraCharge
        etMyPrice.setText("$myPrice $")

        if (publicPrice == 0.0) {
            publicPrice = price+(price*additionalCost)
            etPublicPrice.setText(publicPrice.toString())
        }

        if (!etPublicPrice.text.toString().equals(publicPrice.toString())){
            publicPrice = etPublicPrice.text.toString().toDouble()
        }

        myProfit = publicPrice - myPrice
        tvMyProfit.text = "$myProfit $"


    }


    fun setAppLocale(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = context.resources.configuration
        config.setLocale(locale)
        context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
        finish();
        startActivity(getIntent());
    }
}
