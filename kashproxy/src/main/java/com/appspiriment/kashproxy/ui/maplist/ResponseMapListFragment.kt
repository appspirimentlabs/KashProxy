package com.appspiriment.kashproxy.ui.maplist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.appspiriment.kashproxy.BR
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.data.preference.isKashProxyMappingEnabled
import com.appspiriment.kashproxy.data.preference.saveKashProxyMappingEnabled
import com.appspiriment.kashproxy.databinding.FragmentResponseMapListBinding
import com.appspiriment.kashproxy.databinding.LayoutProxyMappingListItemBinding
import com.appspiriment.kashproxy.ui.model.ResponseMappingModel
import com.appspiriment.kashproxy.utils.baseclasses.BaseListAdapter
import com.appspiriment.kashproxy.utils.extentions.observeData

internal class ResponseMapListFragment : Fragment() {

    private val viewModel: ResponseMapListViewModel by viewModels()

    private val adapter by lazy {
        BaseListAdapter<ResponseMappingModel, LayoutProxyMappingListItemBinding>(
            itemLayoutId = R.layout.layout_proxy_mapping_list_item,
            bindingItemId = BR.model,
            bindingMap = hashMapOf(
                BR.viewModel to viewModel
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentResponseMapListBinding.inflate(inflater, container, false).apply {
        viewModel = this@ResponseMapListFragment.viewModel
        lifecycleOwner = viewLifecycleOwner
        rcyMappings.adapter = adapter
    }.root


    override fun onResume() {
        super.onResume()
        viewModel.getMappingList()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.apply {
            setDisplayShowHomeEnabled(false)
            setDisplayHomeAsUpEnabled(false)
        }

        context?.isKashProxyMappingEnabled().let{
            viewModel.mappingEnabled.value = true
        }
        observeData(viewModel.mappingEnabled){
            context?.saveKashProxyMappingEnabled(it)
        }

        observeData(viewModel.mappingList){
            adapter.submitList(it)
        }

        observeData(viewModel.openMappingDetails) {
            findNavController().navigate(
                it?.let {
                    ResponseMapListFragmentDirections.showMappingDetails(it)
                } ?: ResponseMapListFragmentDirections.addMapping()
            )
        }
    }
}