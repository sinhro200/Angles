package com.sinhro.angles.ui

//import org.graalvm.compiler.replacements.BoxingSnippets.doubleValue
import androidx.fragment.app.Fragment
import com.sinhro.angles.MainActivity
import java.text.NumberFormat


fun Float.toPrettyString(maxSymbolsAfterPoint: Int = 3): String {
    if (this.isFinite()) {
        val nf: NumberFormat = NumberFormat.getNumberInstance()
        nf.maximumFractionDigits = maxSymbolsAfterPoint
        val rounded: String = nf.format(this)
        return rounded
    }else{
        return ""
    }
//    return String.format("%.${symbolsAfterPoint}f", this)
}

fun Fragment.requireMainActivity(): MainActivity {
    return activity as MainActivity
}