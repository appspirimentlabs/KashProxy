package com.appspiriment.kashproxy.ui.rewritemappingsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import com.appspiriment.kashproxy.databinding.FragmentResponseCodeMapSheetBinding
import com.appspiriment.kashproxy.utils.extentions.observeData
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class ResponseStatusRewriteSheet : BottomSheetDialogFragment() {

    private val viewModel: ResponseStatusRewriteViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentResponseCodeMapSheetBinding.inflate(inflater, container, false).apply {
        viewModel = this@ResponseStatusRewriteSheet.viewModel
        lifecycleOwner = this@ResponseStatusRewriteSheet
        ibtnClose.setOnClickListener { dismiss() }
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData(viewModel.saveMapping) {
            setFragmentResult(
                REQ_GET_CODE_REWRITE, Bundle().apply {
                    putString(CODE_REWRITE_FROM, viewModel.fromCode.value)
                    putString(CODE_REWRITE_TO, viewModel.toCode.value)
                }
            )
            dismiss()
        }

    }

    companion object {
        const val REQ_GET_CODE_REWRITE = "reqCodeRewrite"
        const val CODE_REWRITE_FROM = "fromCode"
        const val CODE_REWRITE_TO = "toCode"
    }
}