import icu.ootime.jwintoast.NotificationManager
import icu.ootime.jwintoast.global.tags.WinToastProgressBar
import icu.ootime.jwintoast.global.tags.WinToastController
import org.junit.Test
import java.util.*
import kotlin.concurrent.timerTask

class test {
//    @Test
//    fun main() {
////        WinToastLib iWinToastHandler=new WinToastLib();
//////        iWinToastHandler.toastActivated(1);
////        WinToastLib.IWinToastHandler dd=new WinToastLib.IWinToastHandler();
//////        iWinToastHandler(dd);
////        dd.toastActivated(1);
//        try {
//            val winToastTemplate = WinToastTemplate(WinToastTemplate.WinToastTemplateType.TOASTIMAGEANDTEXT04)
//            //            winToastTemplate.setFirstLine(new CharPointer("d"));
//            println(winToastTemplate.address())
//            val winToast = instance()
//            val iWinToastHandler: IWinToastHandler = object : IWinToastHandler() {
//                override fun toastActivated() {
//                    println("toastActivated")
//                }
//
//                override fun toastActivated(actionIndex: Int) {
//                    println("toastActivated-----$actionIndex")
//                }
//
//                override fun toastDismissed(state: Int) {
//                    println("toastDismissed-----$state")
//                }
//
//                override fun toastFailed() {
//                    println("toastFailed")
//                }
//            }
//            winToast!!.setAppName(CharPointer("Lol"))
//            val aumi = winToast.configureAUMI(
//                CharPointer("DDD"),
//                CharPointer("ProductName"),
//                CharPointer("SubProduct"),
//                CharPointer("VersionInformation")
//            )
//            //        System.out.println(aumi.getString());
//            winToast.setAppUserModelId(aumi)
//            println(winToast.initialize())
//
////            winToastTemplate.setTextField(new CharPointer("点对点"),WinToastTemplate.TextField.FirstLine);
////            winToastTemplate.addAction(new CharPointer("我是按钮啊"));
////            winToastTemplate.addAction(new CharPointer("我是按钮啊"));
////            winToastTemplate.setDuration(WinToastTemplate.Duration.Short);
////            winToastTemplate.setTextField(new CharPointer("关于2019年中秋节放假安排的通知根据国务院办公厅通知精神，现将2019年中秋节放假安排通知如下:9月13日（星期五）放假调休，与周末连休。请广大市民提前安排好工作生活，节日期间注意安全，度过一个欢乐、祥和的节日假期。"),WinToastTemplate.TextField.SecondLine);
////            winToastTemplate.setImagePath(new CharPointer("C:\\Users\\TK\\Desktop\\a.jpg"));
//            winToastTemplate.setExpiration(10000)
//            winToastTemplate.LoadStringToXml(CharPointer("<toast><visual><binding template=\"ToastGeneric\"><text>Downloadingyourweeklyplaylist...</text><progress title=\"Weeklyplaylist\" value=\"{aavalue}\" valueStringOverride=\"{ddww}\" status=\"Downloading...\"/></binding></visual></toast>"))
//            val hStringMap4 = HStringMap()
//            hStringMap4.put(HString(CharPointer("ddww")), HString(CharPointer("10/90 20")))
//            winToastTemplate.setInitNotificationData(hStringMap4)
//            //            winToastTemplate.addAction(new CharPointer("是"));
////            winToastTemplate.addAction(new CharPointer("否"));
//            val intPointer = IntPointer(0)
//            winToast.setAppGroup(CharPointer("ddd"))
//            winToast.setAppTag(CharPointer("aaa"))
//            val uid = winToast.showToast(winToastTemplate, iWinToastHandler, intPointer)
//            Thread.sleep(1000)
//            //            coventHString coventHString=new coventHString();
//            val hStringMap = HStringMap()
//            hStringMap.put(HString(CharPointer("aavalue")), HString(CharPointer("0.1")))
//            hStringMap.put(HString(CharPointer("ddww")), HString(CharPointer("1ssss")));
//
//            val intPointer1 = IntPointer(0)
//            println(winToast.update(hStringMap, intPointer1))
//            println(intPointer1.get())
//            println("返回错误:" + winToast.strerror(intPointer.get())!!.string)
//            println(winToast.address())
//            Thread.sleep(2000)
//            val hStringMap2 = HStringMap()
//            hStringMap2.put(HString(CharPointer("aavalue")), HString(CharPointer("1")))
//            hStringMap2.put(HString(CharPointer("ddww")), HString(CharPointer("1ssss")))
//            println(winToast.update(hStringMap2, intPointer1))
//            //            System.out.println(winToast.hideToast(uid));
//            Thread.sleep(15000)
//            println("未意外推出")
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    class MyToastController : WinToastController("load") {
        lateinit var progressBar: WinToastProgressBar

        override fun onToastActivated(actionIndex: Int) {
            println("ACTIVATED $actionIndex")
        }
    }

    @Test
    fun `test new parser`() {
        NotificationManager.loadFromFile(MyToastController(), "load.xml").also {
            println(it)
        }
    }

    @Test
    fun `test from xml`() {
//        val toast = NotificationManager.loadFromFile(MyToastController(), "load.xml")
        val myToastController = MyToastController()
        myToastController.show()
        var i = 0.0
        Timer().scheduleAtFixedRate(timerTask {
            i += 0.01
            myToastController.progressBar.apply {
                value = (i % 1)
                valueStringOverride = (i * 100).toString() + " / 100"
            }
        }, 0, 50)
        Thread.sleep(10000)
        myToastController.hide()
    }

}