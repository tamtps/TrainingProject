package com.example.trainingproject.components

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.trainingproject.R
import com.example.trainingproject.databinding.ActivityForgotBinding
import com.example.trainingproject.models.Country
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*
import kotlin.collections.ArrayList

class CountryPickerAdapter(binding: ActivityForgotBinding, dialog: Dialog) :
    RecyclerView.Adapter<CountryPickerAdapter.MyView>(), Filterable {
    private var dataSet = ArrayList<Country>()
    private lateinit var dataSetAll: ArrayList<Country>
    private var dia = dialog
    private var bind = binding

    fun setUpdatedData(items: ArrayList<Country>) {
        this.dataSet = items
        this.dataSetAll = ArrayList(dataSet)
        notifyDataSetChanged()
    }

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Country) {
            itemView.findViewById<TextView>(R.id.txtCountryName).text =
                "${data.name} (${data.alpha2Code})"
            itemView.findViewById<TextView>(R.id.txtCountryNumber).text = data.callingCodes
            Picasso.get().load(data.flag)
                .into(itemView.findViewById<CircleImageView>(R.id.imgCountryFlag))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.country_code_item, parent, false)
        return MyView(view)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bind(dataSet[position])
        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                dataSet.forEach {
                    it.favorite = false
                }
                dataSet[position].favorite = true
                Picasso.get().load(dataSet[position].flag).into(bind.imgFlagSelect)
                bind.txtCountryCodeSelect.text = dataSet[position].callingCodes

                notifyDataSetChanged()
                dia.dismiss()
            }

        })
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getFilter(): Filter {
        return countryFilter
    }

    private var countryFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var list = ArrayList<Country>()
            if (constraint.toString().isEmpty()) {
                list.addAll(dataSetAll)
            } else {
                for (country: Country in dataSetAll) {
                    if (country.name.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        || country.alpha2Code.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                        || country.callingCodes.toLowerCase(Locale.ROOT)
                            .contains(constraint.toString().toLowerCase(Locale.ROOT))
                    ) {
                        list.add(country)
                    }
                }
            }

            var result: FilterResults = FilterResults()
            result.values = list

            return result
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            dataSet.clear()
            dataSet.addAll(results?.values as ArrayList<Country>)
            notifyDataSetChanged()
        }

    }

}