package com.nevermore.base.os

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

/**
 *
 * @author xct
 * create on: 2023/3/17 18:59
 *
 */
class XBroadcastReceiver(
    private val context: Context
) : BroadcastReceiver(), LifecycleEventObserver {

    private val actionMap = mutableMapOf<String, ((intent: Intent) -> Unit)>()

    fun addAction(action: String, listener: (intent: Intent) -> Unit) {
        actionMap[action] = listener
    }

    override fun onReceive(context: Context, intent: Intent) {
        val action = intent.action ?: return
        actionMap[action]?.invoke(intent)
    }

    fun register() {
        context.registerReceiver(this, IntentFilter().apply {
            actionMap.keys.forEach { action ->
                addAction(action)
            }
        })
    }

    fun unregister() {
        context.unregisterReceiver(this)
    }

    fun autoRegister(lifecycleOwner: LifecycleOwner) {
        if (lifecycleOwner.lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            lifecycleOwner.lifecycle.addObserver(this)
        }
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_RESUME -> register()
            Lifecycle.Event.ON_PAUSE -> unregister()
            Lifecycle.Event.ON_DESTROY -> source.lifecycle.removeObserver(this)
            else -> {}
        }
    }


}