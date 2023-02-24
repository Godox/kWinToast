package icu.ootime.jwintoast.global

import icu.ootime.jwintoast.HString
import icu.ootime.jwintoast.HStringMap
import icu.ootime.jwintoast.NotificationManager.winToast
import icu.ootime.jwintoast.global.tags.IWinToastElement
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.IntPointer
import kotlin.reflect.KProperty

class PointerBinding<T : Any> {

    lateinit var id: String

    private lateinit var value: T

    private val charPointer by lazy { CharPointer(id) }

    operator fun getValue(thisRef: IWinToastElement, property: KProperty<*>): T {
        return value
    }

    operator fun setValue(thisRef: IWinToastElement, property: KProperty<*>, value: T) {
        HStringMap().apply {
            put(HString(charPointer), HString(CharPointer(value.toString())))
            winToast.update(this, IntPointer(0))
        }
        initValue(value)
    }

    @Suppress("UNCHECKED_CAST")
    internal fun initValue(value: Any) {
        this.value = value as T
    }

}