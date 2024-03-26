package com.nevermore.base.observable

import androidx.lifecycle.LifecycleOwner

/**
 * <pre>
 *      author: xuchuanting
 *      create on: 2022/2/21 11:35
 *      description:
 *</pre>
 *
 */
object EventObservable {

    private val observable: BaseObservable<OnEventListener> = BaseObservable()


    @JvmStatic
    fun notifyEvent(event: String) {
        observable.notifyObservers {
            it.onEventChanged(event)
        }
    }


    @JvmStatic
    fun whenEventChange(lifecycleOwner: LifecycleOwner, event: String, action: () -> Unit) {
        observable.addLifecycleObserver(lifecycleOwner) {
            if (it == event) {
                action()
            }
        }
    }
}

fun interface OnEventListener {
    fun onEventChanged(event: String)
}