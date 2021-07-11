package com.sinhro.angles.ui.rotationtoolholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.sinhro.angles.databinding.FragmentRotationToolHolderBinding
import com.sinhro.angles.ui.toPrettyString

class RotationToolHolderFragment : Fragment() {

    private val rotationToolHolderViewModel: RotationToolHolderViewModel by activityViewModels()
    private var _binding: FragmentRotationToolHolderBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRotationToolHolderBinding.inflate(inflater, container, false)
        val root: View = binding.root

        rotationToolHolderViewModel.alpha.observe(viewLifecycleOwner, Observer {
            binding.fragmentTriangleAlphaLayout.apply {
                valueDeg = it?.degree
                valueMin = it?.minute
                valueSec = it?.second
            }
        })

        binding.namedEditValueD.namedEditValueEditText.addTextChangedListener {
            it.toString().toFloatOrNull().let { d ->
                rotationToolHolderViewModel.setd(d)
            }
        }
        binding.namedEditValueBigD.namedEditValueEditText.addTextChangedListener {
            it.toString().toFloatOrNull().let { D ->
                rotationToolHolderViewModel.setD(D)
            }
        }
        binding.namedEditValueL.namedEditValueEditText.addTextChangedListener {
            it.toString().toFloatOrNull().let { L ->
                rotationToolHolderViewModel.setL(L)
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rotationToolHolderViewModel.bigD.value?.let {
            binding.namedEditValueBigD.namedEditValueEditText.setText(it.toPrettyString())
        }
        rotationToolHolderViewModel.d.value?.let {
            binding.namedEditValueD.namedEditValueEditText.setText(it.toPrettyString())
        }
        rotationToolHolderViewModel.L.value?.let {
            binding.namedEditValueL.namedEditValueEditText.setText(it.toPrettyString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}