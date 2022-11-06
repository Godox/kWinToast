package icu.ootime.jwintoast

import icu.ootime.jwintoast.presets.WinToastLib
import org.bytedeco.javacpp.Loader
import org.bytedeco.javacpp.Pointer
import org.bytedeco.javacpp.annotation.*

@Properties(inherit = [WinToastLib::class])
@Name("std::map<winrt::hstring, winrt::hstring>")
class HStringMap : Pointer() {
    init {
        allocate()
    }

    private external fun allocate()
    external fun size(): Long
    external fun clear()
    val isEmpty: Boolean
        get() = size() == 0L

    //    @Index @Name("find") public native @StdWString CharPointer get(@StdWString CharPointer key);
    @ValueSetter
    @Index
    @Name("insert")
    external fun put(@ByRef pointer: HString?, @ByRef bytePointer: HString?)
    @ByVal
    external fun begin(): Iterator?
    @ByVal
    external fun end(): Iterator?

    @NoOffset
    @Name("iterator")
    class Iterator : Pointer {
        constructor(p: Pointer?) : super(p) {}
        constructor() {}

        @Name("operator++")
        @ByRef
        external fun increment(): Iterator?
        @Name("operator==")
        external fun equals(@ByRef it: Iterator?): Boolean //        public native @Name("operator*().first") @MemberGetter @StdWString CharPointer first();
        //        public native @Name("operator*().second") @MemberGetter @StdWString CharPointer second();
    }

    companion object {
        init {
            Loader.load()
        }
    }
}