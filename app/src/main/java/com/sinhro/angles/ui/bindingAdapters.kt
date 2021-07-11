package com.sinhro.angles.ui

import android.view.View
import androidx.databinding.BindingAdapter
import com.sinhro.angles.MainActivity
import com.sinhro.angles.databinding.NamedEditValueBinding

@BindingAdapter("app:setValueTitle")
fun bindTitle(view: View, value: String) {
    val binding = NamedEditValueBinding.inflate((view.context as MainActivity).layoutInflater)
    binding.valueTitle = value

//    (view as? NamedEditValueBinding)?.let {
//        it.valueTitle = value
//    }

}