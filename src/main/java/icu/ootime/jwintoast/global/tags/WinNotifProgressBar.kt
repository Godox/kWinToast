package icu.ootime.jwintoast.global.tags

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import icu.ootime.jwintoast.global.PointerBinding

@JacksonXmlRootElement(localName = "progress")
class WinNotifProgressBar : IWinToastElement() {

    @get:JacksonXmlProperty(localName = "title", isAttribute = true)
    var title: String by PointerBinding()

    @get:JacksonXmlProperty(localName = "status", isAttribute = true)
    var status: String by PointerBinding()

    @get:JacksonXmlProperty(localName = "value", isAttribute = true)
    var value: String by PointerBinding()

    @get:JacksonXmlProperty(localName = "valueStringOverride", isAttribute = true)
    var valueStringOverride: String by PointerBinding()

}

fun main() {
    WinNotifProgressBar().title = ""
}