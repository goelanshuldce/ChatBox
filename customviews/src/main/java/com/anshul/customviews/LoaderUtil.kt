package com.anshul.customviews

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AlertDialog

/**
 * @author anshulgoel
 * at 03/09/20, 3:45 PM
 * for ChatBook
 */
object LoaderUtil {
    private var dialog: Dialog? = null
    private fun createLoaderDialog(context: Context) {
        if (dialog == null) {
            val builder: AlertDialog.Builder = AlertDialog.Builder(context)
            builder.setView(R.layout.layout_progressbar)
            dialog = builder.create()
            dialog!!.setCancelable(false)
        }
    }

    fun showLoaderDialog(context: Context) {
        if (dialog == null) {
            createLoaderDialog(context)
        }
        dialog!!.show()
    }

    fun hideLoaderDialog() {
        if (dialog != null) {
            dialog!!.dismiss()
            dialog = null
        }
    }
}