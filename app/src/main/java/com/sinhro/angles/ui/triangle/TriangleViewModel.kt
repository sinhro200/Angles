package com.sinhro.angles.ui.triangle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sinhro.angles.model.Angle
import com.sinhro.angles.ui.toPrettyString
import java.lang.Math.toDegrees
import java.util.*
import kotlin.math.atan
import kotlin.math.sqrt

class TriangleViewModel : ViewModel() {

    private val observer = Observer<Float> {
        if (!changedProgrammatically)
            checkReadyAndCalc()
    }

    private val _a = MutableLiveData<Float>().also {
        it.observeForever(observer)
    }
    val a: LiveData<Float> = _a
    private val _aText = MutableLiveData<String>()
    val aText: LiveData<String> = _aText

    private val _b = MutableLiveData<Float>().also {
        it.observeForever(observer)
    }
    val b: LiveData<Float> = _b
    private val _bText = MutableLiveData<String>()
    val bText: LiveData<String> = _bText

    private val _c = MutableLiveData<Float>().also {
        it.observeForever(observer)
    }
    val c: LiveData<Float> = _c
    private val _cText = MutableLiveData<String>()
    val cText: LiveData<String> = _cText

    enum class EnteredField {
        A, B, C;
    }

    private val queue = LinkedList<EnteredField>()
    private var changedProgrammatically = false

    private val _calculatingField = MutableLiveData<EnteredField>()
    val lastCalculatedField : LiveData<EnteredField> = _calculatingField


    private val _alpha = MutableLiveData<Float?>()
    private val _beta = Transformations.map(_alpha) {
        if (it == null)
            null
        else
            90 - it
    }


    val alpha: LiveData<Angle?> = Transformations.map(_alpha) {
        if (it == null)
            null
        else
            Angle.fromFloat(it)
    }

    val beta: LiveData<Angle?> = Transformations.map(_beta) {
        if (it == null)
            null
        else
            Angle.fromFloat(it)
    }

    fun setA(a: Float?) {
        if (changedProgrammatically) {
            changedProgrammatically = false
            return
        }

        val postA =
            if (a != null && !a.isFinite())
                null
            else
                a

        if (!queue.contains(EnteredField.A)) {
            queue.addFirst(EnteredField.A)
            if (queue.size > 2)
                queue.removeLast()

            if (_b.value == null && _c.value != null) {
                queue.addFirst(EnteredField.C)
                if (queue.size > 2)
                    queue.removeLast()
            }else if (_c.value == null && _b.value != null) {
                queue.addFirst(EnteredField.B)
                if (queue.size > 2)
                    queue.removeLast()
            }
        }

        changedProgrammatically = false
        _a.postValue(postA)
    }

    fun setB(b: Float?) {
        if (changedProgrammatically) {
            changedProgrammatically = false
            return
        }

        val postB =
            if (b != null && !b.isFinite())
                null
            else
                b

        if (!queue.contains(EnteredField.B)) {
            queue.addFirst(EnteredField.B)
            if (queue.size > 2)
                queue.removeLast()

            if (_a.value == null && _c.value != null) {
                queue.addFirst(EnteredField.C)
                if (queue.size > 2)
                    queue.removeLast()
            }else if (_c.value == null && _a.value != null) {
                queue.addFirst(EnteredField.A)
                if (queue.size > 2)
                    queue.removeLast()
            }
        }

        changedProgrammatically = false
        _b.postValue(postB)
    }

    fun setC(c: Float?) {
        if (changedProgrammatically) {
            changedProgrammatically = false
            return
        }
        val postC =
            if (c != null && !c.isFinite())
                null
            else
                c

        if (!queue.contains(EnteredField.C)) {
            queue.addFirst(EnteredField.C)
            if (queue.size > 2)
                queue.removeLast()

            if (_a.value == null && _b.value != null) {
                queue.addFirst(EnteredField.B)
                if (queue.size > 2)
                    queue.removeLast()
            }else if (_b.value == null && _a.value != null) {
                queue.addFirst(EnteredField.A)
                if (queue.size > 2)
                    queue.removeLast()
            }
        }

        changedProgrammatically = false
        _c.postValue(postC)
    }

    private fun checkReadyAndCalc() {
        println(queue.joinToString(separator = ","))
        val a: Float
        val b: Float
        val c: Float

        if (queue.size == 2) {
            if (!queue.contains(EnteredField.C)) {
                //must calc C
                _calculatingField.postValue(EnteredField.C)
                if (_a.value == null) {
                    changedProgrammatically = true
                    _aText.postValue("")
                    return
                }
                if (_b.value == null) {
                    changedProgrammatically = true
                    _bText.postValue("")
                    return
                }
                a = _a.value!!
                b = _b.value!!
                c = sqrt((a * a + b * b).toDouble()).toFloat()
                changedProgrammatically = true
                _c.value = c
                _cText.postValue(c.toPrettyString())
                fieldsReady(a, b, c)
            } else if (!queue.contains(EnteredField.B)) {
                //must calc B
                _calculatingField.postValue(EnteredField.B)
                if (_a.value == null) {
                    changedProgrammatically = true
                    _aText.postValue("")
                    return
                }
                if (_c.value == null) {
                    changedProgrammatically = true
                    _cText.postValue("")
                    return
                }
                a = _a.value!!
                c = _c.value!!
                b = sqrt((c * c - a * a).toDouble()).toFloat()
                changedProgrammatically = true
                _b.value = b
                _bText.postValue(b.toPrettyString())
                fieldsReady(a, b, c)
            } else if (!queue.contains(EnteredField.A)) {
                //must calc A
                _calculatingField.postValue(EnteredField.A)
                if (_b.value == null) {
                    changedProgrammatically = true
                    _bText.postValue("")
                    return
                }
                if (_c.value == null) {
                    changedProgrammatically = true
                    _cText.postValue("")
                    return
                }
                b = _b.value!!
                c = _c.value!!
                a = sqrt((c * c - b * b).toDouble()).toFloat()
                changedProgrammatically = true
                _a.value = a
                _aText.postValue(a.toPrettyString())
                fieldsReady(a, b, c)
            }
        }
    }

    private fun fieldsReady(a: Float, b: Float, c: Float) {
        calcAngle(a, b)
    }

    private fun calcAngle(a: Float, b: Float) {
        _alpha.postValue(toDegrees(atan(a / b).toDouble()).toFloat())
    }

    override fun onCleared() {
        _a.removeObserver(observer)
        _b.removeObserver(observer)
        _c.removeObserver(observer)
        super.onCleared()
    }
}