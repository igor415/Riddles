package com.varivoda.igor.zagonetke.ui.shared

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.preference.PreferenceManager
import com.varivoda.igor.zagonetke.R


class Utils {

    companion object{
        fun getSettings(context: Context,text: String): Boolean{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getBoolean(text, true)
        }

        fun getSelectedSound(context: Context,text: String): String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(text,"")
        }

        private fun getSelectedToastDesign(context: Context, text: String): String?{
            val prefs = PreferenceManager.getDefaultSharedPreferences(context)
            return prefs.getString(text,"default")
        }

        fun showCoreDialog(context: Context,dialogView: View): AlertDialog? {
            val builder = AlertDialog.Builder(context)
            builder.setView(dialogView)
            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            val window = dialog.window
            val wlp = window!!.attributes
            wlp.gravity = Gravity.BOTTOM
            wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
            window.attributes = wlp
            return dialog
        }

        fun releaseMedia(mediaPlayers: List<MediaPlayer>){
            mediaPlayers.forEach {
                it.release()
            }
        }

        fun showDefaultDialog(
            context: Context,
            text: String,
            negativeButtonClick: (dialog: DialogInterface, which: Int) -> Unit,
            positiveButtonClick: (dialog: DialogInterface, which: Int) -> Unit

        ){
            val builder = AlertDialog.Builder(context, R.style.AlertDialogTheme)
            with(builder)
            {
                setMessage(text)
                setPositiveButton(context.getString(R.string.yes_resource), DialogInterface.OnClickListener(function = positiveButtonClick))
                setNegativeButton(context.getString(R.string.no_resource), DialogInterface.OnClickListener(function = negativeButtonClick))
                show()
            }
        }

        fun showSelectedToast(context: Context,text: String){
            when(getSelectedToastDesign(context,context.getString(R.string.choose_design_for_toast_resource))){
                "default" -> context.toast(text)
                "dark" -> context.styleableToast(text,R.style.darkToast)
                "light" -> context.styleableToast(text,R.style.lightToast)
            }
        }
    }


}