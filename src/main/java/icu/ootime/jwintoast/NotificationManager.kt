package icu.ootime.jwintoast

import icu.ootime.jwintoast.global.tags.WinToastController
import org.bytedeco.javacpp.CharPointer
import java.io.File
import java.io.FileNotFoundException


object NotificationManager {

    var appName: String = "kWinToast"
        set(value) {
            winToast.setAppName(CharPointer(value))
            field = value
        }

    val winToast = WinToast.instance()!!.apply {
        setAppName(CharPointer(appName))
        val aumi = configureAUMI(
            CharPointer("KWinToast"),
            CharPointer("ProductName"),
            CharPointer("SubProduct"),
            CharPointer("VersionInformation")
        )
        setAppUserModelId(aumi)
        if (!initialize()) {
            throw UnsupportedOperationException()
        }
    }


    fun loadFromFile(controller: WinToastController, path: String): String {
        val fileContent = File(this::class.java.getResource("/notif/$path")?.toURI() ?: throw FileNotFoundException())
        return ToastXmlParser(fileContent.readText(), controller).parseAndProcessXml()
    }

}