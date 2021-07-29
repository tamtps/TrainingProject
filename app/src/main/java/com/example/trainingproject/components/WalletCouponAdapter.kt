package com.example.trainingproject.components

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.models.Coupon
import com.example.trainingproject.models.TransactionDetail
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class WalletCouponAdapter() :
    RecyclerView.Adapter<WalletCouponAdapter.ViewHolder>(), Filterable {
    private var dataSet = ArrayList<Coupon>()
    private var dataSetAll = ArrayList<Coupon>()

    fun setUpdatedData(items: ArrayList<Coupon>) {
        this.dataSet = items
        this.dataSetAll = ArrayList(dataSet)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Coupon) {
            Picasso.get().load(data.imageShow).into(itemView.findViewById<ImageView>(R.id.image_show))
            itemView.findViewById<TextView>(R.id.meichant_name).text = data.merchantName
            itemView.findViewById<TextView>(R.id.expire_date).text = "Valid Until: "+ data.expireDate.substring(0,10)

            val percentOff = itemView.findViewById<TextView>(R.id.percent_off)
            val txt1 = itemView.findViewById<TextView>(R.id.txt1)
            val txt2 = itemView.findViewById<TextView>(R.id.txt2)
            if (data.percentOff == 0){
                percentOff.visibility = View.INVISIBLE
                txt1.visibility = View.INVISIBLE
                txt2.visibility = View.INVISIBLE
            }else
            {
                percentOff.visibility = View.VISIBLE
                txt1.visibility = View.VISIBLE
                txt2.visibility = View.VISIBLE
                percentOff.text = data.percentOff.toString()
            }
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_wallet_coupon, parent, false)

        return WalletCouponAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getFilter(): Filter {
        return couponFilter
    }

    private var couponFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var list =  ArrayList<Coupon>()
            if(constraint.toString().isEmpty()){
                list.addAll(dataSetAll)
            }
            else {
                for(coupon : Coupon in dataSetAll){
                    if(coupon.merchantName.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        || coupon.percentOff.toString().toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                    ){
                        list.add(coupon)
                    }
                }
            }

            var result = FilterResults()
            result.values = list

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataSet.clear()
            dataSet.addAll(results?.values as ArrayList<Coupon>)
            notifyDataSetChanged()
        }

    }
}