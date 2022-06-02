package dnu.fpm.tsptw.ui.adapter

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import dnu.fpm.tsptw.App
import dnu.fpm.tsptw.R
import dnu.fpm.tsptw.data.enums.Month
import dnu.fpm.tsptw.databinding.ItemMonthBinding
import dnu.fpm.tsptw.ui.base.BaseAdapter
import dnu.fpm.tsptw.ui.base.ViewHolder

class MonthAdapter(var selectedMonth: Int) : BaseAdapter<ItemMonthBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.item_month
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder<ItemMonthBinding>, position: Int) {
        holder.binding.monthTextView.text = Month.values()[position].monthName
        if (position == selectedMonth) {
            holder.binding.monthTextView.setBackgroundResource(R.drawable.bg_month_checked)
            holder.binding.monthTextView.setTextColor(
                ContextCompat.getColor(
                    App.instance,
                    R.color.white
                )
            )
        } else {
            holder.binding.monthTextView.setBackgroundResource(R.drawable.bg_month_unchecked)
            holder.binding.monthTextView.setTextColor(
                ContextCompat.getColor(
                    App.instance,
                    R.color.text
                )
            )
        }
        holder.binding.monthTextView.setOnClickListener {
            selectedMonth = position
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return Month.values().size
    }
}