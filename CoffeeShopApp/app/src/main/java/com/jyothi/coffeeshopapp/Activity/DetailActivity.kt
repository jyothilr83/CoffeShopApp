package com.jyothi.coffeeshopapp.Activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.jyothi.coffeeshopapp.Adapter.SizeAdapter
import com.jyothi.coffeeshopapp.Helper.ManagementCart
import com.jyothi.coffeeshopapp.Model.ItemsModel
import com.jyothi.coffeeshopapp.R
import com.jyothi.coffeeshopapp.databinding.ActivityDetailBinding

class DetailActivity : BaseActivity() {
    lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private lateinit var managementCart: ManagementCart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        managementCart = ManagementCart(this)

        bundle()
        initSizeList()
    }

    private fun initSizeList() {
        val sizeList = arrayListOf("1", "2", "3", "4")
        binding.sizeList.adapter = SizeAdapter(sizeList)
        binding.sizeList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val colorList = arrayListOf<String>()
        item.picUrl?.let {
            colorList.addAll(it)
        }
        if (colorList.isNotEmpty()) {
            Glide.with(this)
                .load(colorList[0])
                .apply(RequestOptions.bitmapTransform(RoundedCorners(100)))
                .into(binding.picMain)
        }
    }

    private fun bundle() {
        binding.apply {
            // Handling the deprecated getParcelableExtra method with version checks
            item = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("object", ItemsModel::class.java) ?: ItemsModel()
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra<ItemsModel>("object") ?: ItemsModel()
            }

            titleTxt.text = item.title
            descriptionTxt.text = item.description
            priceTxt.text = "$${item.price}"
            ratingBar.rating = item.rating.toFloat()

            addToCartBtn.setOnClickListener {
                item.numberInCart = numberItemTxt.text.toString().toInt()
                managementCart.insertItems(item)
            }

            backBtn.setOnClickListener {
                startActivity(Intent(this@DetailActivity, MainActivity::class.java))
            }

            plusCart.setOnClickListener {
                numberItemTxt.text = (item.numberInCart + 1).toString()
                item.numberInCart++
            }

            minusCart.setOnClickListener {
                if (item.numberInCart > 0) {
                    numberItemTxt.text = (item.numberInCart - 1).toString()
                    item.numberInCart--
                }
            }
        }
    }
}
