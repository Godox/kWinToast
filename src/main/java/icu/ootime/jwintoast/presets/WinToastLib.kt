package icu.ootime.jwintoast.presets

import org.bytedeco.javacpp.annotation.*
import org.bytedeco.javacpp.tools.Info
import org.bytedeco.javacpp.tools.InfoMap
import org.bytedeco.javacpp.tools.InfoMapper

@Properties(
    target = "icu.ootime.jwintoast",
    global = "icu.ootime.jwintoast.global.WinToastLib",
    value = [Platform(
        value = ["windows-x86", "windows-x86_64"],
        include = ["wintoastlib.cpp", "DesktopNotificationManagerCompat.cpp"]
    )]
)
@Namespace("WinToastLib")
open class WinToastLib : InfoMapper {
    override fun map(infoMap: InfoMap) {
        infoMap.put(Info("WinToastLib").pointerTypes("WinToastLib").skip())
        infoMap.put(
            Info(
                "DesktopNotificationManagerCompat::RegisterComServer",
                "DesktopNotificationManagerCompat::EnsureRegistered",
                "DesktopNotificationManagerCompat::IsRunningAsUwp",
                "DesktopNotificationManagerCompat::s_registeredAumidAndComServer",
                "DesktopNotificationManagerCompat::s_aumid",
                "DesktopNotificationManagerCompat::s_registeredActivator",
                "DesktopNotificationManagerCompat::s_hasCheckedIsRunningAsUwp",
                "DesktopNotificationManagerCompat::s_isRunningAsUwp",
                "DesktopNotificationManagerCompat::RegisterAumidAndComServer",
                "DesktopNotificationManagerCompat::RegisterActivator",
                "DesktopNotificationManagerCompat::CreateToastNotifier",
                "DesktopNotificationManagerCompat::CreateXmlDocumentFromString",
                "DesktopNotificationManagerCompat::CreateToastNotification",
                "DesktopNotificationManagerCompat::get_History",
                "DesktopNotificationManagerCompat::CanUseHttpImages"
            ).skip()
        )
        infoMap.put(
            Info(
                "DllImporter::f_SetCurrentProcessExplicitAppUserModelID", "DllImporter::f_PropVariantToString",
                "DllImporter::f_RoGetActivationFactory",
                "DllImporter::f_WindowsCreateStringReference",
                "DllImporter::f_WindowsGetStringRawBuffer",
                "DllImporter::f_WindowsDeleteString",
                "DllImporter::initialize"
            ).skip()
        )
        infoMap.put(Info("DEFAULT_SHELL_LINKS_PATH", "DEFAULT_LINK_FORMAT", "STATUS_SUCCESS").define(false).skip())
        infoMap.put(
            Info(
                "Util::getRealOSVersion",
                "Util::defaultExecutablePath",
                "Util::defaultShellLinksDirectory",
                "Util::defaultShellLinkPath",
                "Util::AsString",
                "Util::setNodeStringValue",
                "Util::setEventHandlers",
                "Util::addAttribute",
                "Util::createElement"
            ).skip()
        )
        infoMap.put(Info("RtlGetVersionPtr").pointerTypes("RtlGetVersionPtr").skip())
        infoMap.put(Info("WinToastStringWrapper").pointerTypes("WinToastStringWrapper").skip())
        infoMap.put(Info("InternalDateTime").pointerTypes("InternalDateTime").skip())
        infoMap.put(Info("f_WindowsGetStringRawBuffer").pointerTypes("f_WindowsGetStringRawBuffer").skip())
        infoMap.put(Info("f_WindowsDeleteString").pointerTypes("f_WindowsDeleteString").skip())
        infoMap.put(Info("f_WindowsCreateStringReference").pointerTypes("f_WindowsCreateStringReference").skip())
        infoMap.put(
            Info("f_SetCurrentProcessExplicitAppUserModelID").pointerTypes("f_SetCurrentProcessExplicitAppUserModelID")
                .skip()
        )
        infoMap.put(Info("f_RoGetActivationFactory").pointerTypes("f_RoGetActivationFactory").skip())
        infoMap.put(Info("f_PropVariantToString").pointerTypes("f_PropVariantToString").skip())


        //        infoMap.put(new Info("IWinToastHandler").purify(false).virtualize());
//        infoMap.put(new Info("WinToastLib::IWinToastHandler::toastActivated").annotations("const "));
    }
}