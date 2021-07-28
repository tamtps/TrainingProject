package com.example.trainingproject.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.models.Coupon
import com.squareup.picasso.Picasso

class WalletCouponAdapter(val context: Context, val listCoupon : List<Coupon>) :
    RecyclerView.Adapter<WalletCouponAdapter.ViewHolder>() {
    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var imageShow : ImageView = itemView.findViewById(R.id.image_show)
        var meichantName : TextView = itemView.findViewById(R.id.meichant_name)
        var expireDate : TextView = itemView.findViewById(R.id.expire_date)
        var percentOff : TextView = itemView.findViewById(R.id.percent_off)
        var txt1 : TextView = itemView.findViewById(R.id.txt1)
        var txt2 : TextView = itemView.findViewById(R.id.txt2)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        var inflater: LayoutInflater = LayoutInflater.from(parent.context)
        var view : View = inflater.inflate(R.layout.item_wallet_coupon, parent, false)
        var viewHolder = ViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var coupons : Coupon = listCoupon.get(position)
        Picasso.get().load(coupons.imageShow).fit().centerCrop().into(holder.imageShow)
        holder.meichantName.text = coupons.merchantName
        holder.expireDate.text = "Valid Until: "+ coupons.expireDate.substring(0,10)

        if (coupons.percentOff == 0){
            holder.percentOff.visibility = View.INVISIBLE
            holder.txt1.visibility = View.INVISIBLE
            holder.txt2.visibility = View.INVISIBLE
        }else
        {
            holder.percentOff.visibility = View.VISIBLE
            holder.txt1.visibility = View.VISIBLE
            holder.txt2.visibility = View.VISIBLE
            holder.percentOff.text = coupons.percentOff.toString()
        }
    }

    override fun getItemCount(): Int {
        return listCoupon.size
    }
}