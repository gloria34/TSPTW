package dnu.fpm.tsptw.ui.adapter

import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.ItemPointBinding
import dnu.fpm.tsptw.ui.base.BaseAdapter
import dnu.fpm.tsptw.ui.base.ViewHolder

class PointsAdapter(val points: ArrayList<Point>) : BaseAdapter<ItemPointBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.item_point
    }

    override fun onBindViewHolder(holder: ViewHolder<ItemPointBinding>, position: Int) {
        holder.binding.point = points[position]
    }

    override fun getItemCount(): Int {
        return points.size
    }
}