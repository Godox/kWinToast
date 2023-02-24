package icu.ootime.jwintoast.global.tags


import icu.ootime.jwintoast.global.PointerBinding

class WinToastProgressBar(override var id: String) : IWinToastElement {

    override val tag: String = "progress"

    @ToastAttribute("title")
    var title: String by PointerBinding()

    @ToastAttribute("status")
    var status: String by PointerBinding()

    @ToastAttribute("value")
    var value: Double by PointerBinding()

    @ToastAttribute("valueStringOverride")
    var valueStringOverride: String by PointerBinding()

}