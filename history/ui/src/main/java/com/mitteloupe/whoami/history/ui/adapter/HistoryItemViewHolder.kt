package com.mitteloupe.whoami.history.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel

class HistoryItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val ipAddressLabel: TextView by lazy {
        itemView.findViewById(R.id.history_ip_address_label)
    }

    private val locationLabel: TextView by lazy {
        itemView.findViewById(R.id.history_location_label)
    }

    fun bind(historyRecord: HistoryRecordUiModel) {
        ipAddressLabel.text = historyRecord.ipAddress
        locationLabel.text = historyRecord.location
    }
}
