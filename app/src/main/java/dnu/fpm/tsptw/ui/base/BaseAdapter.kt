package dnu.fpm.tsptw.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseAdapter<B : ViewDataBinding?> :
    RecyclerView.Adapter<ViewHolder<B>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(bindView(inflater, parent)!!.root)
    }

    private fun bindView(inflater: LayoutInflater?, parent: ViewGroup): B {
        return DataBindingUtil.inflate(
            inflater!!, getLayoutId(), parent, false
        )
    }

    abstract fun getLayoutId(): Int
}
