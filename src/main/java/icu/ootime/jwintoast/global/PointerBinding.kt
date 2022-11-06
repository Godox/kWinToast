package icu.ootime.jwintoast.global

import icu.ootime.jwintoast.HString
import icu.ootime.jwintoast.HStringMap
import icu.ootime.jwintoast.NotificationManager.winToast
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.IntPointer
import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class PointerBinding<T : Any> {

    var id: String = ""

    val pointer: CharPointer by lazy { CharPointer(id) }

    internal lateinit var value: T

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        winToast.update(HStringMap().apply {
            put(HString(pointer), HString(CharPointer(value.toString())))
        }, IntPointer(0))
        this.value = value
    }

}