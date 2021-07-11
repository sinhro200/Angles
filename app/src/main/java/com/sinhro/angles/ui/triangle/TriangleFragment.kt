package com.sinhro.angles.ui.triangle

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sinhro.angles.databinding.FragmentTriangleBinding
import com.sinhro.angles.storage.Storage
import com.sinhro.angles.ui.requireMainActivity
import com.sinhro.angles.ui.toPrettyString

class TriangleFragment : Fragment() {

    private val triangleViewModel: TriangleViewModel by activityViewModels<TriangleViewModel>()
    private var _binding: FragmentTriangleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTriangleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        triangleViewModel.alpha.observe(viewLifecycleOwner, {
            binding.fragmentTriangleAlphaLayout.apply {
                valueDeg = it?.degree
                valueMin = it?.minute
                valueSec = it?.second
            }
        })
        triangleViewModel.beta.observe(viewLifecycleOwner, {
            binding.fragmentTriangleBetaLayout.apply {
                valueDeg = it?.degree
                valueMin = it?.minute
                valueSec = it?.second
            }
        })

        triangleViewModel.aText.observe(viewLifecycleOwner, {
            binding.namedEditValueA.namedEditValueEditText.setText(it)
        })
        triangleViewModel.bText.observe(viewLifecycleOwner, {
            binding.namedEditValueB.namedEditValueEditText.setText(it)
        })
        triangleViewModel.cText.observe(viewLifecycleOwner, {
            binding.namedEditValueC.namedEditValueEditText.setText(it)

        })

        triangleViewModel.lastCalculatedField.observe(viewLifecycleOwner) {
            it?.let {
                setCalculatingField(it)
            }
        }

        binding.namedEditValueA.namedEditValueEditText.addTextChangedListener {
            it.toString().toFloatOrNull().let { aValue ->
                triangleViewModel.setA(aValue)
            }
        }
        binding.namedEditValueB.namedEditValueEditText.addTextChangedListener {
            it.toString().toFloatOrNull().let { bValue ->
                triangleViewModel.setB(bValue)
            }
        }
        binding.namedEditValueC.namedEditValueEditText.addTextChangedListener {
            it.toString().toFloatOrNull().let { aValue ->
                triangleViewModel.setC(aValue)
            }
        }
        return root
    }

    private fun setCalculatingField(f: TriangleViewModel.EnteredField) {
        binding.namedEditValueA.namedEditValueTitleTv.paintFlags =
            binding.namedEditValueA.namedEditValueTitleTv.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        binding.namedEditValueB.namedEditValueTitleTv.paintFlags =
            binding.namedEditValueB.namedEditValueTitleTv.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        binding.namedEditValueC.namedEditValueTitleTv.paintFlags =
            binding.namedEditValueC.namedEditValueTitleTv.paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
        when (f) {
            TriangleViewModel.EnteredField.A -> {
                binding.namedEditValueA.namedEditValueTitleTv.paintFlags =
                    binding.namedEditValueA.namedEditValueTitleTv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
            TriangleViewModel.EnteredField.B -> {
                binding.namedEditValueB.namedEditValueTitleTv.paintFlags =
                    binding.namedEditValueB.namedEditValueTitleTv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
            TriangleViewModel.EnteredField.C -> {
                binding.namedEditValueC.namedEditValueTitleTv.paintFlags =
                    binding.namedEditValueC.namedEditValueTitleTv.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }

    override fun onResume() {
        super.onResume()
        requireMainActivity().storage.lastOpenedPage = Storage.Page.TrianglePage
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        triangleViewModel.a.value?.let {
            binding.namedEditValueA.namedEditValueEditText.setText(it.toPrettyString())
        }
        triangleViewModel.b.value?.let {
            binding.namedEditValueB.namedEditValueEditText.setText(it.toPrettyString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}