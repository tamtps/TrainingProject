package com.example.trainingproject.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.models.Account
import com.example.trainingproject.models.TransactionDetail
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class GiftCardAdapter() :
    RecyclerView.Adapter<GiftCardAdapter.ViewHolder>(), Filterable {
    private var dataSet = ArrayList<TransactionDetail>()
    private var dataSetAll = ArrayList<TransactionDetail>()

    fun setUpdatedData(items: ArrayList<TransactionDetail>) {
        this.dataSet = items
        this.dataSetAll = ArrayList(dataSet)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: TransactionDetail) {
            Picasso.get().load(data.imgCard).into(itemView.findViewById<ImageView>(R.id.imgGifCard))
            itemView.findViewById<TextView>(R.id.txtCardNum).text =
                data.cardNum
            itemView.findViewById<TextView>(R.id.txtGiftBalance).text = getCurrency(data) + data.cert_value

        }

        fun getCurrency(transaction : TransactionDetail) : String {
            var currency = "$"
            when(transaction.currency){
                "12" -> {
                    currency = "$"
                }
                //TODO: OTHER CURRENCY
            }
            return currency
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.gift_card_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getFilter(): Filter {
        return cardFilter
    }

    private var cardFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var list =  ArrayList<TransactionDetail>()
            if(constraint.toString().isEmpty()){
                list.addAll(dataSetAll)
            }
            else {
                for(transaction : TransactionDetail in dataSetAll){
                    if(transaction.cardNum.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        || transaction.cert_value.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                    ){
                        list.add(transaction)
                    }
                }
            }

            var result = FilterResults()
            result.values = list

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataSet.clear()
            dataSet.addAll(results?.values as ArrayList<TransactionDetail>)
            notifyDataSetChanged()
        }

    }
}