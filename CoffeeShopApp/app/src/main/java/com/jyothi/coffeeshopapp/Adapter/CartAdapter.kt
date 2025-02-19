package com.jyothi.coffeeshopapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.jyothi.coffeeshopapp.Helper.ChangeNumberItemsListener
import com.jyothi.coffeeshopapp.Helper.ManagementCart
import com.jyothi.coffeeshopapp.Model.ItemsModel
import com.jyothi.coffeeshopapp.databinding.ViewholderCartBinding
import com.jyothi.coffeeshopapp.Adapter.CartAdapter

class CartAdapter(
    private val listItemsSelected: ArrayList<ItemsModel>,
    context: Context,
    private var changeNumberItemsListener: ChangeNumberItemsListener? = null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managementCart = ManagementCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val binding = ViewholderCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.ViewHolder, position: Int) {
        val item = listItemsSelected[position]

        // Set item title and price
        holder.binding.titleTxt.text = item.title
        holder.binding.feeEachItem.text = "$${item.price}"

        // Calculate and set the total price for this item (price * numberInCart)
        val totalItemPrice = item.numberInCart * item.price
        holder.binding.totalEachItem.text = "$${String.format("%.2f", totalItemPrice)}"

        // Set the number of items in the cart
        holder.binding.numberItemTxt.text = item.numberInCart.toString()

        // Load the image if available
        if (item.picUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .apply(RequestOptions().transform(CenterCrop()))
                .into(holder.binding.picCart)
        }

        // Handle click event for the plus button to increase item quantity
        holder.binding.plusCartBtn.setOnClickListener {
            managementCart.plusItem(listItemsSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }

        // Handle click event for the minus button to decrease item quantity
        holder.binding.minusCartItem.setOnClickListener {
            managementCart.minusItem(listItemsSelected, position, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener?.onChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = listItemsSelected.size
}
