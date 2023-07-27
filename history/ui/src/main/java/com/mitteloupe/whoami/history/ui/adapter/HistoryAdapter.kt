package com.mitteloupe.whoami.history.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mitteloupe.whoami.history.ui.adapter.HistoryAdapter.HistoryItemViewHolder
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel
import com.mitteloupe.whoami.home.ui.R

class HistoryAdapter : RecyclerView.Adapter<HistoryItemViewHolder>() {
    private val historyRecords: MutableList<HistoryRecordUiModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        holder.bind(historyRecords[position])
    }

    override fun getItemCount() = historyRecords.size

    fun setItems(historyRecords: List<HistoryRecordUiModel>) {
        val oldCount = itemCount
        with(this.historyRecords) {
            clear()
            notifyItemRangeRemoved(0, oldCount)
            addAll(historyRecords)
            notifyItemRangeInserted(0, itemCount)
        }
    }

    class HistoryItemViewHolder(itemView: View) : ViewHolder(itemView) {
        private val ipAddressLabel: TextView by lazy {
            itemView.findViewById(R.id.history_ip_address_label)
        }

        fun bind(historyRecord: HistoryRecordUiModel) {
            ipAddressLabel.text = historyRecord.ipAddress
        }
    }
}
