package com.nevermore.base.observable

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *     @author: xuchuanting
 *     create on 2021/8/30 14:20
 *     description:
 * </pre>
 */
open class BaseObservable<T> {

    private var changed = false
    private var obs: MutableList<T> = mutableListOf()


    @Synchronized
    fun addObserver(o: T) {
        if (!obs.contains(o)) {
            obs.add(o)
        }
    }


    @Synchronized
    fun deleteObserver(o: T) {
        obs.remove(o)
    }


    fun notifyObservers(dispatcher: Dispatcher<T>) {
        setChanged()
        synchronized(this) {
            if (!hasChanged()) return
            clearChanged()
        }
        for (i in obs.indices.reversed()) {
            val observer = obs[i]
            dispatcher.onDispatchObserver(observer)
        }
    }


    fun addLifecycleObserver(lifecycleOwner: LifecycleOwner, o: T) {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            addObserver(o)
            val lifecycleEventObserver = object : LifecycleEventObserver {
                override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        deleteObserver(o)
                        lifecycleOwner.lifecycle.removeObserver(this)
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)

        }
    }

    /**
     * Clears the observer list so that this object no longer has any observers.
     */
    @Synchronized
    fun deleteObservers() {
        obs.clear()
    }


    @Synchronized
    fun setChanged() {
        changed = true
    }


    @Synchronized
    fun clearChanged() {
        changed = false
    }


    @Synchronized
    fun hasChanged(): Boolean {
        return changed
    }

    /**
     * Returns the number of observers of this <tt>Observable</tt> object.
     *
     * @return  the number of observers of this object.
     */
    @Synchronized
    fun countObservers(): Int {
        return obs.size
    }
}

//fun <T> BaseObservable<T>.