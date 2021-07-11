package com.sinhro.angles.ui.rotationtoolholder

import androidx.lifecycle.*
import com.sinhro.angles.model.Angle

class RotationToolHolderViewModel : ViewModel() {

    private val observer = Observer<Float> {
        checkReadyAndCalc()
    }

    private val _D = MutableLiveData<Float>().also {
        it.observeForever(observer)
    }
    private val _d = MutableLiveData<Float>().also {
        it.observeForever(observer)
    }
    private val _L = MutableLiveData<Float>().also {
        it.observeForever(observer)
    }

    val bigD : LiveData<Float> = _D
    val d : LiveData<Float> = _d
    val L : LiveData<Float> = _L

    private val _alpha = MutableLiveData<Float?>()

    val alpha: LiveData<Angle?> = Transformations.map(_alpha) {
        if (it == null)
            null
        else
            Angle.fromFloat(it)
    }

    fun setD(d: Float?) {
        _D.postValue(d)
    }

    fun setd(d: Float?) {
        _d.postValue(d)
    }

    fun setL(l: Float?) {
        _L.postValue(l)
    }

    private fun checkReady(
        onReady: (D: Float, d: Float, L: Float) -> Unit,
        onNotReady: () -> Unit
    ) {
        if (_D.value != null && _d.value != null && _L.value != null)
            onReady(_D.value!!, _d.value!!, _L.value!!)
        else
            onNotReady.invoke()
    }

    private fun checkReadyAndCalc() {
        checkReady({ D, d, L ->
            _alpha.postValue(
                Math.toDegrees(Math.atan(((D - d) / 2 / L).toDouble())).toFloat()
            )
        }, {
            _alpha.postValue(null)
        })
    }

    override fun onCleared() {
        _D.removeObserver(observer)
        _d.removeObserver(observer)
        _L.removeObserver(observer)
        super.onCleared()
    }
}