package com.sinhro.angles.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.sinhro.angles.R
import com.sinhro.angles.databinding.FragmentSettingsBinding
import com.sinhro.angles.storage.Storage
import com.sinhro.angles.theme.ThemeController

class SettingsFragment : Fragment() {

    private val settingsViewModel by activityViewModels<SettingsViewModel>()
    private var _binding: FragmentSettingsBinding? = null
    private lateinit var storage: Storage

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)

        storage = Storage(requireContext())

        when (ThemeController(storage).getTheme()) {
            R.style.Theme1 -> {
                binding.radioGroup.check(
                    R.id.radio_button_1
                )
            }
            R.style.Theme2 -> {
                binding.radioGroup.check(
                    R.id.radio_button_2
                )
            }
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button_1 -> {
                    settingsViewModel.setThemeId(R.style.Theme1)
                }
                R.id.radio_button_2 -> {
                    settingsViewModel.setThemeId(R.style.Theme2)
                }
            }
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}