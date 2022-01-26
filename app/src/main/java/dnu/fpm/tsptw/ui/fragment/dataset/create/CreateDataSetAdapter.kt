package dnu.fpm.tsptw.ui.fragment.dataset.create

import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.ItemPointBinding
import dnu.fpm.tsptw.ui.base.BaseAdapter
import dnu.fpm.tsptw.ui.base.ViewHolder

class CreateDataSetAdapter(private var _points: ArrayList<Point>) :
    BaseAdapter<ItemPointBinding>() {

    var points
        get() = _points
        set(value) {
            _points = value
        }

    override fun onBindViewHolder(holder: ViewHolder<ItemPointBinding>, position: Int) {
        holder.binding.point = _points[position]
    }

    override fun getItemCount(): Int = _points.size

    override fun getLayoutId(): Int = R.layout.item_point

}