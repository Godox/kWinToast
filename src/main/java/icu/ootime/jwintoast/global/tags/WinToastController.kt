package icu.ootime.jwintoast.global.tags

import icu.ootime.jwintoast.*
import icu.ootime.jwintoast.NotificationManager.winToast
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.IntPointer

open class WinToastController(val xmlFilename: String) {

    var id: String = ""

    internal var uid = -1

    internal var initialValues : MutableMap<String, String> = mutableMapOf()

    internal val template = WinToastTemplate(WinToastTemplate.WinToastTemplateType.TOASTIMAGEANDTEXT04)

    init {
        val xml = NotificationManager.loadFromFile(this, "$xmlFilename.xml")
        template.setExpiration(1000000)
        template.setDuration(-1)
        winToast.setAppTag(CharPointer("kWinToast"))
        template.LoadStringToXml(CharPointer(xml))
        injectInitialValues()
    }

    private fun injectInitialValues() {
        val hStringMap = HStringMap()
        initialValues.forEach {
            hStringMap.put(
                HString(CharPointer(id + "_" + it.key)),
                HString(CharPointer((it.value)))
            )
        }
        template.setInitNotificationData(hStringMap)
    }

    fun show() {
        val erro = IntPointer()
        uid = winToast.showToast(template, winToastHandler, erro)
    }

    fun hide() {
        winToast.hideToast(uid)
    }

    private val winToastHandler = object : IWinToastHandler() {

        override fun toastActivated() = onToastActivated(-1)

        override fun toastActivated(actionIndex: Int) = onToastActivated(actionIndex)

        override fun toastDismissed(state: Int) = onToastDismissed(state)

        override fun toastFailed() = onToastFailed()

    }

    open fun onToastActivated(actionIndex: Int) = Unit

    open fun onToastDismissed(state: Int) = Unit

    open fun onToastFailed() = Unit


}
