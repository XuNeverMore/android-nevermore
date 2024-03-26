package com.nevermore.demo.core.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import java.util.concurrent.Executors
import kotlin.experimental.or

private const val TAG = "BluetoothHIDController"

@RequiresApi(Build.VERSION_CODES.P)

@SuppressLint("MissingPermission")
class BluetoothHIDController(val context: Context) {

    private val bleMgr = ContextCompat.getSystemService(context, BluetoothManager::class.java)
    private val qosSettings = BluetoothHidDeviceAppQosSettings(
        BluetoothHidDeviceAppQosSettings.SERVICE_BEST_EFFORT,
        800, 9, 0, 11250, BluetoothHidDeviceAppQosSettings.MAX
    );

    private val mouseSdpSettings = BluetoothHidDeviceAppSdpSettings(
        HidConfig.MOUSE_NAME, HidConfig.DESCRIPTION, HidConfig.PROVIDER,
        BluetoothHidDevice.SUBCLASS1_MOUSE, HidConfig.MOUSE_COMBO
    );

    private var hidDevice: BluetoothHidDevice? = null
    private var mHostDevice: BluetoothDevice? = null
    fun startService() {
        bleMgr ?: return
        val adapter = bleMgr.adapter
        adapter.getProfileProxy(context, object : BluetoothProfile.ServiceListener {
            override fun onServiceConnected(profile: Int, proxy: BluetoothProfile?) {
                Log.i(TAG, "onServiceConnected: $profile,proxy:$proxy")
                proxy ?: return
                if (proxy is BluetoothHidDevice) {
                    hidDevice = proxy
                    register()
                }
            }

            override fun onServiceDisconnected(profile: Int) {
                Log.i(TAG, "onServiceDisconnected: $profile")
            }
        }, BluetoothProfile.HID_DEVICE)
    }


    private fun register() {
        hidDevice?.registerApp(
            mouseSdpSettings,
            null,
            qosSettings,
            Executors.newCachedThreadPool(),
            object :
                BluetoothHidDevice.Callback() {
                override fun onAppStatusChanged(
                    pluggedDevice: BluetoothDevice?,
                    registered: Boolean
                ) {
                    super.onAppStatusChanged(pluggedDevice, registered)
                    Log.i(TAG, "onAppStatusChanged: ")
                    if (registered) {
                        pluggedDevice ?: return
                        if (hidDevice != null) {
                            if (hidDevice!!.getConnectionState(pluggedDevice) != BluetoothProfile.STATE_CONNECTED) {
                                hidDevice!!.connect(pluggedDevice)
                            }
                        }
                    }

                }

                override fun onConnectionStateChanged(device: BluetoothDevice?, state: Int) {
                    super.onConnectionStateChanged(device, state)
                    Log.i(TAG, "onConnectionStateChanged: $device,state:$state")
                    if (state == BluetoothProfile.STATE_CONNECTED) {
                        mHostDevice = device;
                    }
                    if (state == BluetoothProfile.STATE_DISCONNECTED) {
                        mHostDevice = null;
                    }
                }
            })
    }

    private var mLeftClick = false
    private var mRightClick = false

    fun sendLeftClick(click: Boolean) {
        mLeftClick = click
        senMouse(0x00.toByte(), 0x00.toByte())
    }

    fun sendRightClick(click: Boolean) {
        mRightClick = click
        senMouse(0x00.toByte(), 0x00.toByte())
    }

    fun byte2hex(a: ByteArray): String {
        /*StringBuilder sb = new StringBuilder(a.length * 2);
        for (byte b : a)
            sb.append(String.format("%02x", b & 0xff));
        return sb.toString();*/
        var hexString = ""
        for (i in a.indices) {
            val thisByte = String.format("%x", a[i])
            hexString += thisByte
        }
        return hexString
    }

    fun senMouse(dx: Byte, dy: Byte) {
        if (hidDevice == null) {
            Log.e(TAG, "senMouse failed,  hid device is null!")
            return
        }
        if (mHostDevice == null) {
            Log.e(TAG, "senMouse failed,  hid device is not connected!")
            return
        }
        val bytes = ByteArray(5)
        //bytes[0]字节：bit0: 1表示左键按下 0表示左键抬起 | bit1: 1表示右键按下 0表示右键抬起 | bit2: 1表示中键按下 | bit7～3：补充的常数，无意义，这里为0即可
        bytes[0] = (bytes[0] or if (mLeftClick) 1 else 0).toByte()
        bytes[0] = (bytes[0] or ((if (mRightClick) 1 else 0).shl(1)).toByte())
        bytes[1] = dx
        bytes[2] = dy
        Log.d(
            TAG,
            "senMouse   Left:" + mLeftClick + ",Right:" + mRightClick + ",bytes: " + byte2hex(bytes)
        )
        hidDevice?.sendReport(mHostDevice, 4, bytes)
    }

    fun sendWheel(hWheel: Byte, vWheel: Byte) {
        if (hidDevice == null) {
            Log.e(TAG, "sendWheel failed,  hid device is null!")
            return
        }
        if (mHostDevice == null) {
            Log.e(TAG, "sendWheel failed,  hid device is not connected!")
            return
        }
        val bytes = ByteArray(5)
        bytes[3] = vWheel //垂直滚轮
        bytes[4] = hWheel //水平滚轮
        Log.d(TAG, "sendWheel vWheel:$vWheel,hWheel：$hWheel")
        hidDevice?.sendReport(mHostDevice, 4, bytes)
    }

}