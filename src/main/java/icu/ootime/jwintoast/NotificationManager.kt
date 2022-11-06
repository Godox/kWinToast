package icu.ootime.jwintoast

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.TextNode
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import icu.ootime.jwintoast.global.tags.IWinToastElement
import icu.ootime.jwintoast.global.tags.WinNotifProgressBar
import icu.ootime.jwintoast.global.tags.WinToastController
import org.bytedeco.javacpp.CharPointer
import org.bytedeco.javacpp.IntPointer
import java.io.File
import java.io.FileNotFoundException

object NotificationManager {

    val winToastTemplate = WinToastTemplate.WinToastTemplateType.TOASTIMAGEANDTEXT04

    val winToastHandler = object : IWinToastHandler() {
        override fun toastActivated() {
            println("toastActivated")
        }

        override fun toastActivated(actionIndex: Int) {
            println("toastActivated-----$actionIndex")
        }

        override fun toastDismissed(state: Int) {
            println("toastDismissed-----$state")
        }

        override fun toastFailed() {
            println("toastFailed")
        }
    }

    val winToast = WinToast.instance()!!.apply {
        setAppName(CharPointer("My application"))
        val aumi = configureAUMI(
            CharPointer("DDD"),
            CharPointer("ProductName"),
            CharPointer("SubProduct"),
            CharPointer("VersionInformation")
        )
        setAppUserModelId(aumi)
        if (!initialize()) {
            throw UnsupportedOperationException()
        }
    }


    fun loadFromFile(controller: WinToastController, path: String): String {
        val fileContent = File(this::class.java.getResource("/notif/$path")?.toURI() ?: throw FileNotFoundException())
        val tree = parseTree(XmlMapper().readTree(fileContent), controller)
//        return XmlMapper().registerModule(PointerBindingModule()).writer().withRootName("toast")
//            .writeValueAsString(tree.also { tree ->
//
//            })
        TODO()
    }

    fun parseTree(tree: JsonNode, controller: WinToastController, toastElements: MutableList<IWinToastElement> = mutableListOf()) {
//        val mapper = XmlMapper().registerModule(PointerBindingModule(it))
        tree.findValue("binding")?.fields()?.asSequence()?.toList()?.filter { it.value.get("id") != null }?.forEach {
            findTypeCorrelationOrNull(it.key)?.let { typeClass ->
                toastElements.add(ObjectMapper().registerModule(PointerBindingModule(typeClass)).readValue(it.value.toPrettyString(), IWinToastElement::class.java))
            }
            parseTree(it.value, controller)
        }
        tree.findValues("progress").map { element ->
            val id = element.get("id").textValue()

            WinNotifProgressBar::class.java.getConstructor().newInstance().also { instance ->
                element.fields().forEach { map ->

                    WinNotifProgressBar::class.java.declaredFields.firstOrNull { it.name == map.key }
                        ?.let {
                            val xmlValue = id + "_" + map.key
                            map.setValue(TextNode("{$xmlValue}"))
//                            it.set(instance, PointerBinding(map.value.textValue(), xmlValue))
                        }

                }
                controller::class.java.fields.firstOrNull { it.name == id }?.set(controller, instance)
            }
        }
    }

    fun buildFromXml(notifXml: String) {
        val winToastTemplate = WinToastTemplate(WinToastTemplate.WinToastTemplateType.TOASTIMAGEANDTEXT04)
        //            winToastTemplate.setFirstLine(new CharPointer("d"));
        println(winToastTemplate.address())
        val winToast = WinToast.instance()
        val iWinToastHandler: IWinToastHandler = object : IWinToastHandler() {
            override fun toastActivated() {
                println("toastActivated")
            }

            override fun toastActivated(actionIndex: Int) {
                println("toastActivated-----$actionIndex")
            }

            override fun toastDismissed(state: Int) {
                println("toastDismissed-----$state")
            }

            override fun toastFailed() {
                println("toastFailed")
            }
        }
        winToast!!.setAppName(CharPointer("Bonjour"))
        val aumi = winToast.configureAUMI(
            CharPointer("DDD"),
            CharPointer("ProductName"),
            CharPointer("SubProduct"),
            CharPointer("VersionInformation")
        )
        //        System.out.println(aumi.getString());
        winToast.setAppUserModelId(aumi)
        println(winToast.initialize())

        winToastTemplate.setExpiration(10000)
        winToastTemplate.LoadStringToXml(CharPointer(notifXml))

        val hStringMap4 = HStringMap()
        hStringMap4.put(HString(CharPointer("ddww")), HString(CharPointer("10/90 20")))
        winToastTemplate.setInitNotificationData(hStringMap4)
        //            winToastTemplate.addAction(new CharPointer("是"));
//            winToastTemplate.addAction(new CharPointer("否"));
        val intPointer = IntPointer(0)
        winToast.setAppGroup(CharPointer("ddd"))
        winToast.setAppTag(CharPointer("aaa"))
        val uid = winToast.showToast(winToastTemplate, iWinToastHandler, intPointer)
        Thread.sleep(1000)
        //            coventHString coventHString=new coventHString();
        val hStringMap = HStringMap()
        hStringMap.put(HString(CharPointer("aavalue")), HString(CharPointer("0.1")))
        //            hStringMap.put(new HString(new CharPointer("ddww")),new HString(new CharPointer("1ssss")));
        val intPointer1 = IntPointer(0)
        println(winToast.update(hStringMap, intPointer1))
        println(intPointer1.get())
        println("Oups:" + winToast.strerror(intPointer.get())!!.string)
        println(winToast.address())
        Thread.sleep(2000)
        val hStringMap2 = HStringMap()
        hStringMap2.put(HString(CharPointer("aavalue")), HString(CharPointer("1")))
        hStringMap2.put(HString(CharPointer("ddww")), HString(CharPointer("1ssss")))
        println(winToast.update(hStringMap2, intPointer1))
        //            System.out.println(winToast.hideToast(uid));
        Thread.sleep(15000)
    }

    fun findTypeCorrelationOrNull(xmlTag: String): Class<out IWinToastElement>? {
        return when (xmlTag) {
            "progress" -> WinNotifProgressBar::class
            else -> null
        }?.java
    }

}