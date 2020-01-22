package com.zip.challenge.core.ui

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.zip.challenge.R
import com.zip.shared.util.extensions.trimParanthesis

object CompanyUIHelper {

    //Helper function to display change percent and color
    fun showChangePercent(textView: TextView, change: String?) {
        val changePercent = change.trimParanthesis()
        textView.text = changePercent
        val context = textView.context
        if (changePercent.contains("+")) {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.green)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                ContextCompat.getDrawable(context, R.drawable.ic_arrow_upward_24dp),
                null
            )
        } else if (changePercent.contains("-")) {
            textView.setTextColor(
                ContextCompat.getColor(context, R.color.red)
            )
            textView.setCompoundDrawablesWithIntrinsicBounds(
                null, null,
                ContextCompat.getDrawable(context, R.drawable.ic_arrow_downward_24dp),
                null
            )
        } else {
            textView.text = ""
            textView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        }
    }
}