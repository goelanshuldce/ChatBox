package com.anshul.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData

/**
 * @author anshulgoel
 * at 04/09/20, 9:43 AM
 * for ChatBook
 */

/**
 * CombinedLiveData is a helper class to combine results from multiple LiveData sources.
 * @param liveDatas Variable number of LiveData arguments.
 * @param combine   Function reference that will be used to combine all LiveData data.
 * @param R         The type of data returned after combining all LiveData data.
 */
class CombinedLiveData<R>(
    vararg liveDatas: LiveData<*>,
    private val combine: (datas: List<Any?>) -> R
) : MediatorLiveData<R>() {

    private val datas: MutableList<Any?> = MutableList(liveDatas.size) { null }

    init {
        for (i in liveDatas.indices) {
            super.addSource(liveDatas[i]) {
                datas[i] = it
                value = combine(datas)
            }
        }
    }
}