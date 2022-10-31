package com.appspiriment.kashproxy.ui.mappingdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.appspiriment.kashproxy.R
import com.appspiriment.kashproxy.databinding.FragmentResponseMappingBinding
import com.appspiriment.kashproxy.ui.rewritemappingsheet.ResponseStatusRewriteSheet
import com.appspiriment.kashproxy.utils.alerts.showAlertDialog
import com.appspiriment.kashproxy.utils.alerts.showMessageEvent
import com.appspiriment.kashproxy.utils.customview.KashProxyEditText
import com.appspiriment.kashproxy.data.preference.deletePref
import com.appspiriment.kashproxy.utils.extentions.observeData
import com.appspiriment.kashproxy.utils.extentions.openFragmentForResult
import com.appspiriment.kashproxy.utils.navigation.handleNavigation
import com.google.android.material.chip.Chip


class ResponseMappingFragment : Fragment() {

    private val viewModel: ResponseMappingViewModel by viewModels()
    private val args by navArgs<ResponseMappingFragmentArgs>()
    private var binding: FragmentResponseMappingBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentResponseMappingBinding.inflate(inflater, container, false).apply {
        viewModel = this@ResponseMappingFragment.viewModel
        lifecycleOwner = this@ResponseMappingFragment
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

        observeData(viewModel.rewriteMap, ::populateRewriteMap)

        observeData(viewModel.deleteMapping) {
            context?.deletePref(it)
            findNavController().navigateUp()
        }

        observeData(viewModel.addRewriteMap) {
            openFragmentForResult(
                ResponseMappingFragmentDirections.showCodeRewriteSheet(),
                ResponseStatusRewriteSheet.REQ_GET_CODE_REWRITE
            ) { bundle ->
                val fromCode = bundle.getString(ResponseStatusRewriteSheet.CODE_REWRITE_FROM)
                val toCode = bundle.getString(ResponseStatusRewriteSheet.CODE_REWRITE_TO)
                viewModel.addStatusRewriting(fromCode, toCode)
            }
        }
    }


    private fun initialize() {
        viewModel.initialize(args.mappingId)

        binding?.txtUrl?.apply {
            setOnFocusChangeListener { _: View, hasFocus: Boolean ->
                if (!hasFocus) viewModel.formatUrl()

            }

            setPasteActionListener (object : KashProxyEditText.onPasteListener{
                override fun onPaste(view: AppCompatEditText) {
                    viewModel.formatUrl()
                }
            })
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

    private fun populateRewriteMap(rewriteMap: HashMap<Int, Pair<Int, Boolean>>) {
        binding?.containerRewriteMap?.run {
            removeAllViews()
            rewriteMap.forEach { entry ->
                Chip(requireContext()).apply {
                    text = getString(
                        R.string.kash_status_map_chip_text,
                        "${entry.key}",
                        "${entry.value.first}"
                    )
                    isCheckable = true
                    isCloseIconVisible = true
                    isChecked = entry.value.second
                    setCheckedIconResource(R.drawable.ic_tick_success)
                    if (isChecked) {
                        setTextAppearance(R.style.KashChipTextAppearance)
                        setChipBackgroundColorResource(R.color.kash_rewrite_chip_color)
                    } else {
                        setTextAppearance(R.style.KashDisabledChipTextAppearance)
                        setChipBackgroundColorResource(R.color.kash_rewrite_chip_color_disabled)
                    }

                    setOnCloseIconClickListener {
                        requireActivity().showAlertDialog(
                            title = R.string.kash_confirm,
                            message = R.string.kash_confirm_remove_status_mapping,
                            positiveButton = R.string.kash_remove,
                            negativeButton = R.string.kash_cancel,
                            positiveClickListen = {
                                viewModel.rewriteMap.value = rewriteMap.apply { remove(entry.key) }
                            }
                        )

                    }

                    setOnCheckedChangeListener { _: CompoundButton, checked: Boolean ->
                        rewriteMap.getOrDefault(entry.key, null)?.let {
                            rewriteMap[entry.key] = Pair(it.first, checked)
                        }
                        viewModel.rewriteMap.value = rewriteMap
                    }

                }.also {
                    addView(it)
                }
            }
        }
    }
}