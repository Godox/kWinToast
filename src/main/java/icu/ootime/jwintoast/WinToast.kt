package icu.ootime.jwintoast

import icu.ootime.jwintoast.presets.WinToastLib
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.IntPointer
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.annotation.*

@NoOffset
@Properties(inherit = [WinToastLib::class])
class WinToast : Pointer() {
    init {
        allocate()
    }

    @Cast("uint32_t")
    external fun showToast(
        @Const @ByRef winToastTemplate: WinToastTemplate,
        iWinToastHandler: IWinToastHandler,
        @Cast("WinToastLib::WinToast::WinToastError *") erro: IntPointer
    ): Int

    //    public native void notifier2(@Cast("bool *") boolean sessed,@ByRef HString hString);
    external fun setAppName(@Const @StdWString name: CharPointer?)
    external fun setAppTag(@Const @StdWString tagname: CharPointer?)
    external fun setAppGroup(@Const @StdWString groupname: CharPointer?)
    @Cast("::NotificationUpdateResult")
    external fun update(
        @ByRef hStringMap: HStringMap?,
        @Cast("WinToastLib::WinToast::WinToastError *") erro: IntPointer?
    ): Int

    @StdWString
    external fun configureAUMI(
        @Const @StdWString companyName: CharPointer?,
        @Const @StdWString productName: CharPointer?,
        @Const @StdWString subProduct: CharPointer?,
        @Const @StdWString versionInformation: CharPointer?
    ): CharPointer?

    external fun setAppUserModelId(@Const @StdWString appUserModelId: CharPointer?)
    external fun initialize(): Boolean
    external fun clear()
    @StdWString
    @Const
    external fun strerror(@Cast("WinToastLib::WinToast::WinToastError") code: Int): CharPointer?
    val isCompatible: Boolean
        external get
    val isSupportingModernFeatures: Boolean
        external get

    external fun hideToast(id: Int): Boolean

    //    public native @Cast("uint32_t") int showToast(@Const @ByRef WinToastTemplate winToastTemplate, @ByRef IWinToastHandler handler);
    external fun allocate()

    companion object {
        init {
            Loader.load()
        }

        @JvmStatic
        external fun instance(): WinToast?
    }
}