package dnu.fpm.tsptw.ui.fragment.createnewtrip

import android.annotation.SuppressLint
import android.view.View
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.model.Point
import dnu.fpm.tsptw.databinding.ItemCreatePointBinding
import dnu.fpm.tsptw.ui.base.BaseAdapter
import dnu.fpm.tsptw.ui.base.ViewHolder


class CreateNewTripAdapter(
    private val points: ArrayList<Point>,
    val listener: OnChangePointListener
) :
    BaseAdapter<ItemCreatePointBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.item_create_point
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(
        holder: ViewHolder<ItemCreatePointBinding>,
        @SuppressLint("RecyclerView") position: Int
    ) {
        holder.binding.latitudeEditText.setText(points[position].latitude.toString())
        holder.binding.longitudeEditText.setText(points[position].longitude.toString())
        holder.binding.removeImageView.setOnClickListener {
            listener.onDeletePoint(position)
            notifyDataSetChanged()
        }
        holder.binding.addNewPointButton.setOnClickListener {
            listener.onAddNewPoint()
            notifyDataSetChanged()
        }
        holder.binding.latitudeEditText.doOnTextChanged { text, start, before, count ->
            listener.onUpdateLatitude(text.toString(), position)
        }
        holder.binding.longitudeEditText.doOnTextChanged { text, start, before, count ->
            listener.onUpdateLongitude(text.toString(), position)
        }
        holder.binding.addNewPointButton.visibility =
            if (position == points.size - 1) View.VISIBLE else View.GONE
    }

    override fun getItemCount(): Int {
        return points.size
    }
}

public interface OnChangePointListener {
    fun onDeletePoint(position: Int)
    fun onUpdateLatitude(latitude: String, position: Int)
    fun onUpdateLongitude(longitude: String, position: Int)
    fun onAddNewPoint()
}