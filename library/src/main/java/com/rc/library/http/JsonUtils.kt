package com.rc.library.http


import com.google.gson.*
import java.lang.annotation.Documented
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.RetentionPolicy.*
import java.lang.reflect.Field


/**
 * <pre>
 * @author yangchong
 * blog  :
 * time  : 2018/6/6
 * desc  : json工具类
 * revise:
</pre> *
 */
object JsonUtils {


    private var gson: Gson? = null

    /**
     * 第一种方式
     * @return              Gson对象
     */
    val json: Gson?
        get() {
            if (gson == null) {
                val builder = GsonBuilder()
                builder.setLenient()
                builder.setFieldNamingStrategy(AnnotateNaming())
                builder.serializeNulls()
                gson = builder.create()
            }
            return gson
        }

    private class AnnotateNaming : FieldNamingStrategy {
        override fun translateName(field: Field): String {
            val a = field.getAnnotation(ParamNames::class.java)
            return a?.value ?: FieldNamingPolicy.IDENTITY.translateName(field)
        }
    }

    /**
     * 第二种方式
     * @return              Gson对象
     */
    fun getGson(): Gson? {
        if (gson == null) {
            gson = GsonBuilder()
                .setLenient()// json宽松
                .enableComplexMapKeySerialization()//支持Map的key为复杂对象的形式
                .serializeNulls() //智能null
                .setPrettyPrinting()// 调教格式
                .disableHtmlEscaping() //默认是GSON把HTML转义的
                .registerTypeAdapter(Int::class.javaPrimitiveType, JsonDeserializer { json, typeOfT, context ->
                    //根治服务端int 返回""空字符串
                    //try catch不影响效率
                    try {
                        return@JsonDeserializer json.asInt
                    } catch (e: NumberFormatException) {
                        return@JsonDeserializer 0
                    }
                })
                .create()
        }
        return gson
    }


    @Documented
   // @Retention(RUNTIME)
    @Target(AnnotationTarget.FIELD)
    internal annotation class ParamNames(val value: String)


}
