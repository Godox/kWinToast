package icu.ootime.jwintoast.global.tags

import icu.ootime.jwintoast.global.PointerBinding

class WinToastInput(override val id: String) : IWinToastElement {

    override val tag: String = "input"

    @ToastAttribute("type")
    val type: Type by PointerBinding()

    @ToastAttribute("placeHolderContent")
    var placeHolderContent: String by PointerBinding()

    @ToastAttribute("title")
    var title: String by PointerBinding()

    enum class Type {
        TEXT, SELECTION;

        override fun toString(): String {
            return name.lowercase()
        }
    }

}
