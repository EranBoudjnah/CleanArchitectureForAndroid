package com.mitteloupe.whoami.history.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mitteloupe.whoami.history.ui.R
import com.mitteloupe.whoami.history.ui.model.HistoryRecordUiModel

class HistoryItemViewHolder(itemView: View, private val userEventListener: UserEventListener) :
    RecyclerView.ViewHolder(itemView) {
    private val card: CardView by lazy {
        itemView.findViewById(R.id.history_record_card)
    }

    private val ipAddressLabel: TextView by lazy {
        itemView.findViewById(R.id.history_ip_address_label)
    }

    private val locationLabel: TextView by lazy {
        itemView.findViewById(R.id.history_location_label)
    }

    private val deleteButton: View by lazy {
        itemView.findViewById<View>(R.id.history_delete_button).apply {
            setOnClickListener {
                userEventListener.onDeleteClick(currentRecord)
            }
        }
    }

    private lateinit var currentRecord: HistoryRecordUiModel

    fun bind(historyRecord: HistoryRecordUiModel) {
        deleteButton.accessToInitialize()
        currentRecord = historyRecord
        ipAddressLabel.text = historyRecord.ipAddress
        locationLabel.text = historyRecord.location
        card.isSelected = historyRecord.isHighlighted
    }

    interface UserEventListener {
        fun onDeleteClick(record: HistoryRecordUiModel)
    }

    @Suppress("UnusedReceiverParameter")
    private fun View.accessToInitialize() = Unit
}
