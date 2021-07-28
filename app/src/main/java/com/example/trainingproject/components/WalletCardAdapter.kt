package com.example.trainingproject.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.models.Account
import java.util.*
import kotlin.collections.ArrayList

class WalletCardAdapter() :
    RecyclerView.Adapter<WalletCardAdapter.ViewHolder>(), Filterable {
    private var dataSet = ArrayList<Account>()
    private var dataSetAll = ArrayList<Account>()

    fun setUpdatedData(items: ArrayList<Account>) {
        this.dataSet = items
        this.dataSetAll = ArrayList(dataSet)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Account) {
            if (data.tokens.accountType
                    .equals("KK")
            ){
                itemView.findViewById<TextView>(R.id.txtCardType).text =
                    "kanoo Kash Kard"

            }
             else "The Sand Dollar Card"
            itemView.findViewById<TextView>(R.id.txtCardOwner).text =
                "${data.tokens.firstName} ${data.tokens.lastName}"
            itemView.findViewById<TextView>(R.id.txtLast4).text = data.tokens.last4
            itemView.findViewById<TextView>(R.id.txtGiftBalance).text =
                "Level ${data.tokens.accountStatus + 1} Verified"
            itemView.findViewById<TextView>(R.id.txtBalance).text = getCurrency(data) + "${data.tokens.balance}"
        }

        fun getCurrency(account : Account) : String {
            var currency = "B$"
            when(account.tokens.currency){
                "BSD" -> {
                    currency = "B$"
                }
                //TODO: OTHER CURRENCY
            }
            return currency
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.wallet_card_item, parent, false)

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
            var list =  ArrayList<Account>()
            if(constraint.toString().isEmpty()){
                list.addAll(dataSetAll)
            }
            else {
                for(account : Account in dataSetAll){
                    if(account.tokens.firstName.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        || account.tokens.last4.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        || account.tokens.lastName.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                    ){
                        list.add(account)
                    }
                }
            }

            var result = FilterResults()
            result.values = list

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataSet.clear()
            dataSet.addAll(results?.values as ArrayList<Account>)
            notifyDataSetChanged()
        }

    }
}