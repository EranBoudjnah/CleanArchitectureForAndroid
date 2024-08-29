package com.mitteloupe.whoami.history.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel

class HistoryAdapter(private val userEventListener: UserEventListener) :
    RecyclerView.Adapter<HistoryItemViewHolder>() {
    private val historyRecords: MutableList<HistoryRecordUiModel> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return HistoryItemViewHolder(
            view,
            object : HistoryItemViewHolder.UserEventListener {
                override fun onDeleteClick(record: HistoryRecordUiModel) {
                    userEventListener.onDeleteClick(record)
                }
            }
        )
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

    interface UserEventListener {
        fun onDeleteClick(record: HistoryRecordUiModel)
    }
}
