package com.jyothi.coffeeshopapp.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jyothi.coffeeshopapp.Adapter.CartAdapter
import com.jyothi.coffeeshopapp.Helper.ChangeNumberItemsListener
import com.jyothi.coffeeshopapp.Helper.ManagementCart
import com.jyothi.coffeeshopapp.databinding.ActivityCartBinding

class CartActivity : BaseActivity() {  // Ensure BaseActivity is correctly imported or extend AppCompatActivity
    lateinit var binding: ActivityCartBinding
    lateinit var managementCart: ManagementCart
    private var tax: Double = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        managementCart = ManagementCart(this)

        calculateCart()
        setVariable()
        initCartList()
    }

    private fun initCartList() {
        with(binding) {
            cartView.layoutManager = LinearLayoutManager(this@CartActivity, LinearLayoutManager.VERTICAL, false)
            cartView.adapter = CartAdapter(managementCart.getListCart(), this@CartActivity, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    calculateCart()  // Update the cart when changes are made
                }
            })
        }
    }

    private fun setVariable() {
        binding.backBtn.setOnClickListener { finish() }
    }

    private fun calculateCart() {
        val percentTax = 0.02
        val delivery = 15.00
        val totalFee = managementCart.getTotalFee()

        // Format values with two decimal places
        tax = String.format("%.2f", totalFee * percentTax).toDouble()
        val total = String.format("%.2f", totalFee + tax + delivery).toDouble()
        val itemTotal = String.format("%.2f", totalFee).toDouble()

        with(binding) {
            totalFeeTxt.text = "$$itemTotal"
            taxTxt.text = "$$tax"
            deliveryTxt.text = "$$delivery"
            totalTxt.text = "$$total"
        }
    }
}
