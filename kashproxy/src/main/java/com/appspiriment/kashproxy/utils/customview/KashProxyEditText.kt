package com.appspiriment.kashproxy.utils.customview

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter


internal class KashProxyEditText(context: Context, attrs: AttributeSet?) :
    AppCompatEditText(context, attrs) {
    private var cutActionListener: OnCutListener? = null
    private var copyActionListener: OnCopyListener? = null
    private var pasteActionListener: OnPasteListener? = null


    fun setCutActionListener(listener: OnCutListener?) {
        this.cutActionListener = listener
    }

    fun setCopyActionListener(listener: OnCopyListener?) {
        this.copyActionListener = listener
    }

    fun setPasteActionListener(listener: OnPasteListener) {
        this.pasteActionListener = listener
    }
    fun setCutListener(listener: ((AppCompatEditText) -> Unit)?) {
        this.cutActionListener = object : KashProxyEditText.OnCutListener{
            override fun onCut(view: AppCompatEditText) {
                listener?.invoke(view)
            }
        }
    }

    fun setCopyListener(listener: ((AppCompatEditText) -> Unit)?) {
        this.copyActionListener = object : KashProxyEditText.OnCopyListener{
            override fun onCopy(view: AppCompatEditText) {
                listener?.invoke(view)
            }
        }
    }

    fun setPasteListener(listener: ((AppCompatEditText) -> Unit)?) {
        this.pasteActionListener = object : KashProxyEditText.OnPasteListener{
            override fun onPaste(view: AppCompatEditText) {
                listener?.invoke(view)
            }
        }
    }

    override fun onTextContextMenuItem(id: Int): Boolean {
        val consumed = super.onTextContextMenuItem(id)
        when (id) {
            android.R.id.cut -> cutActionListener?.onCut(this)
            android.R.id.copy -> copyActionListener?.onCopy(this)
            android.R.id.paste -> pasteActionListener?.onPaste(this)
        }
        return consumed
    }

    interface OnCutListener {
        fun onCut(view: AppCompatEditText)
    }

    interface OnCopyListener {
        fun onCopy(view: AppCompatEditText)
    }

    interface OnPasteListener {
        fun onPaste(view: AppCompatEditText)
    }
}
