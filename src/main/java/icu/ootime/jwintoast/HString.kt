package icu.ootime.jwintoast

import icu.ootime.jwintoast.presets.WinToastLib
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.annotation.*

@Properties(inherit = [WinToastLib::class])
@Name("winrt::hstring")
class HString : Pointer {
    constructor() {
        allocate()
    }

    constructor(@StdWString charPointer: CharPointer?) {
        allocate(charPointer)
    }

    external fun allocate()
    external fun allocate(@StdWString charPointer: CharPointer?)

    companion object {
        init {
            Loader.load()
        }
    }
}