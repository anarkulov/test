package com.erzhan.test.extentions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.dataVersion

fun <T> LiveData<T>.observeFresh(owner: LifecycleOwner, observer: Observer<in T>) {
    // Extension function to get LiveData's version, will explain in below.
    val sinceVersion = this.dataVersion()
    this.observe(owner, FreshObserver<T>(observer, this, sinceVersion))
}

class FreshObserver<T>(
    private val delegate: Observer<in T>,
    private val liveData: LiveData<*>,
    private val sinceVersion: Int
) : Observer<T> {

    override fun onChanged(t: T) {
        if (liveData.dataVersion() > sinceVersion) {
            delegate.onChanged(t)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (delegate != (other as FreshObserver<*>).delegate) return false
        return true
    }

    override fun hashCode(): Int {
        return delegate.hashCode()
    }
}