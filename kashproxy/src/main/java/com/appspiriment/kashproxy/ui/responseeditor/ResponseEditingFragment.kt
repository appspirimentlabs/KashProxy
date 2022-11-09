package com.appspiriment.kashproxy.ui.responseeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appspiriment.kashproxy.databinding.FragmentMapResponseEditorBinding
import com.appspiriment.kashproxy.utils.customview.KashProxyEditText
import com.appspiriment.kashproxy.utils.extentions.observeData


internal class ResponseEditingFragment : Fragment() {

    private val viewModel: ResponseEditingViewModel by viewModels()
    private val args by navArgs<ResponseEditingFragmentArgs>()
    private var binding: FragmentMapResponseEditorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentMapResponseEditorBinding.inflate(inflater, container, false).apply {
        viewModel = this@ResponseEditingFragment.viewModel
        lifecycleOwner = this@ResponseEditingFragment
    }.also {
        binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

        observeData(viewModel.saveMappingItem) {
            setFragmentResult(
                ResponseEditingViewModel.REQ_EDIT_MAPPING_ITEM,
                Bundle().apply {
                    putParcelable(
                        ResponseEditingViewModel.REQ_EDIT_MAPPING_ITEM,
                        it
                    )
                })
            findNavController().navigateUp()
        }

    }


    private fun initialize() {
        viewModel.let {
            it.initialize(args.mappingItem)
            binding?.run {
                txtSuccessResponse.setPasteListener { view -> it.onSuccessResponseChanged(view.text?.toString()) }
                tvHttpCode.setPasteListener { view -> it.httpCodeChanged(view.text?.toString()) }
                txtErrorResponse.setPasteListener { view -> it.onErrorResponseChanged(view.text?.toString()) }
            }
        }

        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setHomeButtonEnabled(true)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}