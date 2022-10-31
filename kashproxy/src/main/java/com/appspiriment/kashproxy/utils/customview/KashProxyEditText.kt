package com.appspiriment.kashproxy.utils.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText


internal class KashProxyEditText(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {
    var cutListener: onCutListener? = null
    var copyListener: onCopyListener? = null
    var pasteListener: onPasteListener? = null


    fun setCutActionListener(listener: onCutListener?) {
        this.cutListener = listener
    }

    fun setCopyActionListener(listener: onCopyListener?) {
        this.copyListener = listener
    }

    fun setPasteActionListener(listener: onPasteListener?) {
        this.pasteListener = listener
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            android.R.id.cut -> cutListener?.onCut(this)
            android.R.id.copy -> copyListener?.onCopy(this)
            android.R.id.paste -> pasteListener?.onPaste(this)
        }
        return consumed
    }

    interface onCutListener {
        fun onCut(view: AppCompatEditText)
    }

    interface onCopyListener {
        fun onCopy(view: AppCompatEditText)
    }

    interface onPasteListener {
        fun onPaste(view: AppCompatEditText)
    }
}