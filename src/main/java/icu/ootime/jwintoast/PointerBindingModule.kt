package icu.ootime.jwintoast

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.module.SimpleModule
import icu.ootime.jwintoast.global.PointerBinding
import icu.ootime.jwintoast.global.tags.IWinToastElement
import kotlin.properties.Delegates
import kotlin.reflect.*
import kotlin.reflect.full.createType
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.getExtensionDelegate
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

class PointerBindingModule(val impl: Class<out IWinToastElement>) : SimpleModule() {

    init {
        addDeserializer(IWinToastElement::class.java, IWinToastElementDeserializer())
//        addDeserializer(PointerBinding::class.java, PointerBindingDeserializer())
//        addSerializer(PointerBindingSerializer::class.java)
    }


    inner class IWinToastElementDeserializer : JsonDeserializer<IWinToastElement>() {
        override fun deserialize(p0: JsonParser, p1: DeserializationContext): IWinToastElement {
            val instance = impl.getConstructor().newInstance()
            val tree = p1.readTree(p0)
            tree.fields().forEach { field ->
                instance::class.memberProperties.firstOrNull { it.name == field.key }?.let {
                    tree.findValue("id").textValue()
                    if (it is KMutableProperty<*>) {
//                        findDelegatingPropertyInstances(instance, PointerBinding::class)
                        //TODO get delegator instance and set id attribute
                        it.setter.call(instance, retreiveTyppedField(it, field.value))
                    }
                }
            }
            return instance
        }

        private fun retreiveTyppedField(it: KMutableProperty<*>, value: JsonNode): Any? {
            return when (it.returnType) {
                String::class.createType() -> value.textValue()
                Double::class.createType() -> value.doubleValue()
                Int::class.createType() -> value.intValue()
                else -> null
            }
        }

    }

//    data class DelegatedProperty<T : Any, DELEGATE : Any>(val property: KProperty1<T, *>, val delegatingToInstance: DELEGATE)
//
//    fun <T : Any, DELEGATE : Any> findDelegatingPropertyInstances(instance: T, delegatingTo: KClass<DELEGATE>): List<DelegatedProperty<T, DELEGATE>> {
//        return instance::class.declaredMemberProperties.mapNotNull { prop ->
//            val javaField = prop.javaField
//            if (javaField != null && delegatingTo.java.isAssignableFrom(javaField.type)) {
//                javaField.isAccessible = true // is private, have to open that up
//                @Suppress("UNCHECKED_CAST")
//                val delegateInstance = javaField.get(instance) as DELEGATE
//                DelegatedProperty(prop, delegateInstance)
//            } else {
//                null
//            }
//        }
//    }

}
