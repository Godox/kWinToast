package icu.ootime.jwintoast

import icu.ootime.jwintoast.global.PointerBinding
import icu.ootime.jwintoast.global.tags.*
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.traversal.DocumentTraversal
import org.w3c.dom.traversal.NodeFilter
import org.w3c.dom.traversal.NodeIterator
import org.xml.sax.InputSource
import java.io.StringWriter
import java.lang.reflect.InvocationTargetException
import java.util.logging.Level
import java.util.logging.Logger
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.Transformer
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.KTypeProjection
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.declaredMembers
import kotlin.reflect.full.isSubtypeOf
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmErasure

@Suppress("UNCHECKED_CAST")
class ToastXmlParser(private val xmlInputString: String, private val controller: WinToastController) {

    private val log = Logger.getLogger("ToastXmlParser")

    fun parseAndProcessXml(): String {
        val document: Document = readXml(xmlInputString)
        processXmlDocument(document)
        return writeToString(document)
    }

    private fun writeToString(document: Document): String {
        val transformer: Transformer = TransformerFactory.newInstance().newTransformer().apply {
            setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes")
            setOutputProperty(OutputKeys.METHOD, "xml")
            setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1")
            setOutputProperty("{https://xml.apache.org/xslt}indent-amount", "4")
            setOutputProperty(OutputKeys.INDENT, "yes")
        }
        val sw = StringWriter()
        val sr = StreamResult(sw)
        transformer.transform(DOMSource(document), sr)
        return sw.toString()
    }

    private fun processXmlDocument(document: Document): Document {
        val traversal = document as DocumentTraversal
        val iterator: NodeIterator = traversal.createNodeIterator(
            document.documentElement, NodeFilter.SHOW_ELEMENT, null, true
        )

        var n = iterator.nextNode()
        while (n != null) {
            n as Element
            processNode(n)
            n = iterator.nextNode()
        }
        return document
    }

    private fun readXml(xmlInputString: String): Document {
        return DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(InputSource(xmlInputString.reader()))
    }

    private fun processNode(n: Element) {
        if (n.hasAttribute("id")) {
            val id = n.getAttribute("id")
            val type = findTypeCorrelationOrNull(n.tagName)
                ?: return log.log(Level.SEVERE, "Could not find type matching with element ${n.tagName}")
            val toastElement = createElement(type, id)
            toastElement::class.declaredMemberProperties.map { it to it.annotations.firstOfTypeOrNull<ToastAttribute>() }
                .filter { it.second != null }
                .forEach { (field, annot) ->
                    val attrName = annot!!.name
                    val initialValue = n.getAttribute(attrName)
                    val newValue = "${id}_${attrName}"
                    injectInitialValue(controller, toastElement, field, newValue, initialValue)
                    if (field is KMutableProperty<*>) {
                        n.setAttribute(attrName, "{$newValue}")
                    }
                }
            injectToController(id, toastElement)
        }
    }

    private fun <T : IWinToastElement> injectInitialValue(
        controller: WinToastController,
        instance: IWinToastElement,
        field: KProperty1<T, *>,
        pointerName: String,
        initialValue: String
    ) {
        field.isAccessible = true
        val pointerBinding = field.getDelegate(instance as T)
        if (pointerBinding is PointerBinding<*>) {
            pointerBinding.id = pointerName
            pointerBinding.initValue(convertToFieldType(field, initialValue))
        }
        controller.initialValues[pointerName] = initialValue
    }

    private fun <T : IWinToastElement> convertToFieldType(field: KProperty1<T, *>, initialValue: String): Any {
        val type = field.returnType
        return when {
            type.isSubtypeOf(Enum::class.createType(listOf(KTypeProjection.STAR))) -> {
                try {
                    field.returnType.jvmErasure.declaredMembers.first { it.name == "valueOf" }
                        .call(initialValue.uppercase())!!
                } catch (e: InvocationTargetException) {
                    throw e.targetException
                }
            }

            type == Boolean::class.createType() -> initialValue.toBoolean()
            type == Int::class.createType() -> initialValue.toInt()
            type == Double::class.createType() -> initialValue.toDouble()
            type == String::class.createType() -> initialValue
            else -> throw UnsupportedOperationException("Type $type is not supported")
        }
    }

    private fun createElement(type: Class<out IWinToastElement>, id: String): IWinToastElement {
        return type.getConstructor(String::class.java).newInstance(id)
    }

    private fun injectToController(id: String, type: IWinToastElement) {
        runCatching {
            controller::class.java.getDeclaredField(id)
        }.onSuccess {
            it.set(controller, type)
        }.onFailure {
            log.warning("")
        }
    }

    private fun findTypeCorrelationOrNull(xmlTag: String): Class<out IWinToastElement>? {
        return when (xmlTag) {
            "progress" -> WinToastProgressBar::class
            "input" -> WinToastInput::class
            else -> null
        }?.java
    }

}