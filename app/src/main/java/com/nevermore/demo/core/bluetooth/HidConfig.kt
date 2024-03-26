package com.nevermore.demo.core.bluetooth

object HidConfig {
    const val MOUSE_NAME = "VV Mouse";

    const val DESCRIPTION = "VV for you";

    const val PROVIDER = "VV";

    val MOUSE_COMBO = byteArrayOf(
        0x05, 0x01,              // USAGE_PAGE (Generic Desktop)
        0x09, 0x02,              // USAGE (Mouse)
        0xa1.toByte(), 0x01,              // COLLECTION (Application)
        0x85.toByte(), 0x04,              // REPORT_ID (4)
        0x09, 0x01,              //  USAGE (Pointer)
        0xa1.toByte(), 0x00,              //  COLLECTION (Physical)
        0x05, 0x09,              //   USAGE_PAGE (Button)
        0x19, 0x01,              //   USAGE_MINIMUM (Button 1)
        0x29, 0x02,              //   USAGE_MAXIMUM (Button 2)
        0x15, 0x00,              //   LOGICAL_MINIMUM (0)
        0x25, 0x01,              //   LOGICAL_MAXIMUM (1)
        0x95.toByte(), 0x03,              //   REPORT_COUNT (3)
        0x75, 0x01,              //   REPORT_SIZE (1)
        0x81.toByte(), 0x02,              //   INPUT (Data,Var,Abs)
        0x95.toByte(), 0x01,              //   REPORT_COUNT (1)
        0x75, 0x05,              //   REPORT_SIZE (5)
        0x81.toByte(), 0x03,              //   INPUT (Cnst,Var,Abs)
        0x05, 0x01,              //   USAGE_PAGE (Generic Desktop)
        0x09, 0x30,              //   USAGE (X)
        0x09, 0x31,              //   USAGE (Y)
        0x09, 0x38,              //   USAGE (Wheel)
        0x15, 0x81.toByte(),              //   LOGICAL_MINIMUM (-127)
        0x25, 0x7F,              //   LOGICAL_MAXIMUM (127)
        0x75, 0x08,              //   REPORT_SIZE (8)
        0x95.toByte(), 0x03,              //   REPORT_COUNT (3)
        0x81.toByte(), 0x06,              //   INPUT (Data,Var,Rel)
        //水平滚轮
        0x05, 0x0c,              //   USAGE_PAGE (Consumer Devices)
        0x0a, 0x38, 0x02, //   USAGE (AC Pan)
        0x15, 0x81.toByte(),              //   LOGICAL_MINIMUM (-127)
        0x25, 0x7f,              //   LOGICAL_MAXIMUM (127)
        0x75, 0x08,              //   REPORT_SIZE (8)
        0x95.toByte(), 0x01,              //   REPORT_COUNT (1)
        0x81.toByte(), 0x06,              //   INPUT (Data,Var,Rel)

        0xc0.toByte(),                           //  END_COLLECTION
        0xc0.toByte(),                           // END_COLLECTION
    )
}
