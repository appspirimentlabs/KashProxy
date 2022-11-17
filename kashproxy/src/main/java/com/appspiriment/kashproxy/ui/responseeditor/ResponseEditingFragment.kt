package com.appspiriment.kashproxy.ui.responseeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appspiriment.kashproxy.databinding.KashproxyFragmentMapResponseEditorBinding
import com.appspiriment.kashproxy.ui.di.createWithFactory
import com.appspiriment.kashproxy.utils.extentions.observeData


internal class ResponseEditingFragment : Fragment() {

    private val args by navArgs<ResponseEditingFragmentArgs>()
    private var binding: KashproxyFragmentMapResponseEditorBinding? = null


    private val viewModel: ResponseEditingViewModel by lazy {
        ViewModelProvider(
            this,
            createWithFactory {
                ResponseEditingViewModel(args.mappingItem)
            }
        ).get(ResponseEditingViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = KashproxyFragmentMapResponseEditorBinding.inflate(inflater, container, false).apply {
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