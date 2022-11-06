package icu.ootime.jwintoast

import icu.ootime.jwintoast.presets.WinToastLib
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.annotation.*

@NoOffset
@Properties(inherit = [WinToastLib::class]) //@Namespace("WinToastLib")
open class IWinToastHandler : Pointer() {
    init {
        allocate()
    }

    external fun allocate()
    @Virtual(true)
    @Const(false, false, true)
    open external fun toastActivated()
    @Virtual(true)
    @Const(false, false, true)
    open external fun toastActivated(actionIndex: Int)
    @Virtual(true)
    @Const(false, false, true)
    open external fun toastDismissed(@Cast("IWinToastHandler::WinToastDismissalReason") state: Int)
    @Virtual(true)
    @Const(false, false, true)
    open external fun toastFailed()

    companion object {
        init {
            Loader.load()
        }
    }
}