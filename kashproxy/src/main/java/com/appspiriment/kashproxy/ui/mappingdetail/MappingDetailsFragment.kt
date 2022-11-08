package com.appspiriment.kashproxy.ui.mappingdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.appspiriment.kashproxy.databinding.FragmentResponseMappingBinding
import com.appspiriment.kashproxy.ui.model.MappingItem
import com.appspiriment.kashproxy.ui.responseeditor.ResponseEditingViewModel
import com.appspiriment.kashproxy.utils.alerts.showMessageEvent
import com.appspiriment.kashproxy.utils.extentions.observeData
import com.appspiriment.kashproxy.utils.extentions.openFragmentForResult
import com.appspiriment.kashproxy.utils.navigation.handleNavigation
import com.appspiriment.kashproxy.ui.di.createWithFactory


class MappingDetailsFragment : Fragment() {

    private val args by navArgs<MappingDetailsFragmentArgs>()

    private val viewModel: MappingDetailsViewModel by lazy {
        ViewModelProvider(
            this,
            createWithFactory {
                MappingDetailsViewModel(
                    application = requireActivity().application,
                    url = args.mappingUrl,
                    mapUrlModel = args.mapModel
                )
            }
        ).get(MappingDetailsViewModel::class.java)
    }

    private var binding: FragmentResponseMappingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentResponseMappingBinding.inflate(inflater, container, false).apply {
        viewModel = this@MappingDetailsFragment.viewModel
        lifecycleOwner = this@MappingDetailsFragment
    }.also {
        binding = it
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()

        observeData(viewModel.messageEvent) {
            showMessageEvent(it)
        }

        observeData(viewModel.navigation) {
            handleNavigation(it)
        }

        observeData(viewModel.editMapping) {
            openFragmentForResult(
                MappingDetailsFragmentDirections.showResponseEditor(it),
                ResponseEditingViewModel.REQ_EDIT_MAPPING_ITEM
            ) { bundle ->
                bundle.getParcelable<MappingItem>(ResponseEditingViewModel.REQ_EDIT_MAPPING_ITEM)
                    ?.let { item ->
                        viewModel.httpCode.value = item.httpCode
                        viewModel.successResponse.value = item.successResponse
                        viewModel.errorResponse.value = item.errorResponse
                    }
            }
        }

        observeData(viewModel.deleteMapping) {
//            it?.let { url ->
//                activity?.showAlertDialog(
//                    title = R.string.kash_confirm,
//                    message = R.string.kash_confirm_delete_mapping,
//                    positiveButton = R.string.kash_delete,
//                    negativeButton = R.string.kash_cancel,
//                    positiveClickListen = {
//                        viewModel.viewModelScope.launch {
//                            KashProxyApp.getMappingRepository().deleteProxyMapping(it)
//                            findNavController().navigateUp()
//                        }
//                    }
//                )
//            }
        }
    }


    private fun initialize() {
        binding?.txtUrl?.apply {
            setOnFocusChangeListener { _: View, hasFocus: Boolean ->
                if (!hasFocus) viewModel.formatUrl()

            }

            setPasteListener { view -> viewModel.formatUrl() }
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