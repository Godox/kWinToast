package icu.ootime.jwintoast

import icu.ootime.jwintoast.presets.WinToastLib
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.annotation.*

@Properties(inherit = [WinToastLib::class])
@Namespace("WinToastLib")
class WinToastTemplate : Pointer {
    constructor() {
        allocate()
    }

    constructor(@Cast("WinToastTemplate::WinToastTemplateType") winToastTemplateType: Int) {
        allocate(winToastTemplateType)
    }
    //    public WinToastTemplate(Pointer p) { super(p); }
    /**
     * Native array allocator. Access with [Pointer.position].
     */
    //    public WinToastTemplate(long size) { super((Pointer)null); allocateArray(size); }
    //    private native void allocateArray(long size);
    external fun allocate()
    external fun allocate(@Cast("WinToastTemplate::WinToastTemplateType") winToastTemplateType: Int)
    external fun setFirstLine(@Const @StdWString text: CharPointer?)
    external fun setTextField(
        @Const @StdWString text: CharPointer?,
        @Cast("WinToastTemplate::TextField") FirstLine: Int
    )

    external fun addAction(@Const @StdWString label: CharPointer?)
    external fun setAttributionText(@Const @StdWString text: CharPointer?)
    external fun setDuration(@Cast("WinToastTemplate::Duration") duration: Int)
    external fun setImagePath(@Const @StdWString imagepath: CharPointer?)
    external fun setExpiration(millisecondsFromNow: Int)
    external fun LoadStringToXml(@Const @StdWString strxml: CharPointer?)
    external fun setInitNotificationData(@ByRef hStringMap: HStringMap?)
    fun setPosition(position: Long): WinToastTemplate {
        return super.position(position)
    }

    object Duration {
        /**
         * enum WinToastLib::WinToastTemplate::Duration
         */
        const val System = 0
        const val Short = 1
        const val Long = 2
    }

    object AudioOption {
        /**
         * enum WinToastLib::WinToastTemplate::AudioOption
         */
        const val Default = 0
        const val Silent = 1
        const val Loop = 2
    }

    object TextField {
        /**
         * enum WinToastLib::WinToastTemplate::TextField
         */
        const val FirstLine = 0
        const val SecondLine = 1
        const val ThirdLine = 2
    }

    object WinToastTemplateType {
        /**
         * enum WinToastLib::WinToastTemplate::WinToastTemplateType
         */
        const val TOASTIMAGEANDTEXT01 = 0
        const val TOASTIMAGEANDTEXT02 = 1
        const val TOASTIMAGEANDTEXT03 = 2
        const val TOASTIMAGEANDTEXT04 = 3
        const val TOASTTEXT01 = 4
        const val TOASTTEXT02 = 5
        const val TOASTTEXT03 = 6
        const val TOASTTEXT04 = 7
    }

    companion object {
        init {
            Loader.load()
        }
    }
}