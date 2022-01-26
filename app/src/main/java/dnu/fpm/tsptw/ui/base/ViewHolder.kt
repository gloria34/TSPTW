package dnu.fpm.tsptw.ui.base

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class ViewHolder<B : ViewDataBinding?>(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var binding: B = DataBindingUtil.bind(itemView)!!
}