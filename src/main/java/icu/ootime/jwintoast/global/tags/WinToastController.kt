package icu.ootime.jwintoast.global.tags

import icu.ootime.jwintoast.IWinToastHandler
import icu.ootime.jwintoast.NotificationManager
import icu.ootime.jwintoast.NotificationManager.winToast
import icu.ootime.jwintoast.NotificationManager.winToastHandler
import icu.ootime.jwintoast.NotificationManager.winToastTemplate
import icu.ootime.jwintoast.WinToastTemplate
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.IntPointer
import kotlin.properties.Delegates

open class WinToastController(val xmlFilename: String) {

    var id: String = ""

    internal var uid = -1

    fun show() {
        val xml = NotificationManager.loadFromFile(this, "$xmlFilename.xml")
        val template = WinToastTemplate(winToastTemplate)
        template.setExpiration(10000)
        template.LoadStringToXml(CharPointer(xml))
        uid = winToast.showToast(template, winToastHandler, IntPointer())
    }

    fun hide() {
        winToast.hideToast(uid)
    }


}
