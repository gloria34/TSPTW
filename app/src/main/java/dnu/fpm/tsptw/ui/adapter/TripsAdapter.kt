package dnu.fpm.tsptw.ui.adapter

import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.entity.DataSet
import dnu.fpm.tsptw.databinding.ItemTripBinding
import dnu.fpm.tsptw.ui.base.BaseAdapter
import dnu.fpm.tsptw.ui.base.ViewHolder

class TripsAdapter(val datasets: ArrayList<DataSet>, val onTripClickListener: OnTripClickListener) :
    BaseAdapter<ItemTripBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.item_trip
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemTripBinding>, position: Int) {
        holder.binding.trip = datasets[position]
        holder.binding.root.setOnClickListener {
            onTripClickListener.onTripClick(position)
        }
    }

    override fun getItemCount(): Int {
        return datasets.size
    }
}

public interface OnTripClickListener {
    fun onTripClick(position: Int)
}