package com.appspiriment.kashproxy.demo.ui

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.appspiriment.kashproxy.data.preference.saveKashProxyMappingEnabled
import com.appspiriment.kashproxy.demo.R
import com.appspiriment.kashproxy.demo.databinding.FragmentDemoBinding
import com.appspiriment.kashproxy.utils.alerts.showToast
import com.appspiriment.kashproxy.utils.extentions.observeData


class DemoFragment : Fragment() {

    private val viewModel by viewModels<DemoViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentDemoBinding.inflate(inflater, container, false).apply {
        viewModel = this@DemoFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
    }.root


    override fun onResume() {
        super.onResume()
        viewModel.checkMappingEnabled()
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData(viewModel.mappingEnabled) { context?.saveKashProxyMappingEnabled(it) }

        observeData(viewModel.copyUrl){
            if(it.isNotBlank()) {
                val myClipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Copied", it)
                myClipboard.setPrimaryClip(clip)
                showToast(R.string.url_copied)
            }
        }
    }

}