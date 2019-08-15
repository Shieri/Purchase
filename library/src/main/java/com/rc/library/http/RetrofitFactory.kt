package com.library

import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.rc.library.BuildConfig
import com.rc.library.http.JsonUtils
import com.rc.library.http.LenientGsonConverterFactory

import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.ArrayList
import java.util.Date
import java.util.concurrent.TimeUnit

import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.LineNumberReader


open class RetrofitFactory {

    private var mRetrofit: Retrofit? = null
    private val builder: OkHttpClient.Builder

    /**
     * 创建Retrofit
     * @param url               baseUrl
     */
    constructor(url: String) {
        builder = OkHttpClient.Builder()
        //拦截日志，依赖
        builder.addInterceptor(initLogInterceptor())
        val build = builder.build()
        //添加请求头拦截器
        //builder.addInterceptor(InterceptorUtils.getRequestHeader());
        //添加统一请求拦截器
        //builder.addInterceptor(InterceptorUtils.commonParamsInterceptor());
        //设置缓存
        //builder.addNetworkInterceptor(InterceptorUtils.getCacheInterceptor());
        //builder.addInterceptor(InterceptorUtils.getCacheInterceptor())

        initBuilder(url, build)
    }

    /*
    日志拦截器
 */
    private fun initLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }


    /**
     * 创建Retrofit
     * @param url               baseUrl
     */
    constructor(url: String, interceptors: ArrayList<Interceptor>?) {
        builder = OkHttpClient.Builder()
        //拦截日志，依赖
      //  builder.addInterceptor(InterceptorUtils.getHttpLoggingInterceptor(BuildConfig.DEBUG))
        val build = builder.build()
       // builder.addInterceptor(InterceptorUtils.getCacheInterceptor())
        if (interceptors != null && interceptors.size > 0) {
            for (interceptor in interceptors) {
                builder.addInterceptor(interceptor)
            }
        }
        //添加自定义CookieJar
      //  InterceptorUtils.addCookie(builder)
        initBuilder(url, build)
    }


    private fun initBuilder(url: String, build: OkHttpClient) {
        initSSL()
        initTimeOut()
        if (BuildConfig.DEBUG) {
            //不需要错误重连
            builder.retryOnConnectionFailure(false)
        } else {
            //错误重连
            builder.retryOnConnectionFailure(true)
        }
        //获取实例
        mRetrofit = Retrofit.Builder()//设置OKHttpClient,如果不设置会提供一个默认的
            //设置baseUrl
            .baseUrl(url)
            //添加转换器Converter(将json 转为JavaBean)，用来进行响应数据转化(反序列化)的ConvertFactory
            .addConverterFactory(GsonConverterFactory.create())
            //添加自定义转换器
            //.addConverterFactory(buildGsonConverterFactory())
            //添加rx转换器，用来生成对应"Call"的CallAdapter的CallAdapterFactory
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(build)
            .build()
    }


    fun <T> create(service: Class<T>): T {
        return mRetrofit!!.create(service)
    }


    /**
     * 初始化完全信任的信任管理器
     */
    private fun initSSL() {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {

                }

                override fun getAcceptedIssuers(): Array<X509Certificate> {
                    return arrayOf()
                }
            })

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())
            val sslSocketFactory = sslContext.socketFactory
            builder.sslSocketFactory(sslSocketFactory)
            builder.hostnameVerifier { hostname, session -> true }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    /**
     * 设置读取超时时间，连接超时时间，写入超时时间值
     */
    private fun initTimeOut() {
        builder.readTimeout(20000, TimeUnit.SECONDS)
        builder.connectTimeout(10000, TimeUnit.SECONDS)
        builder.writeTimeout(20000, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
    }

    companion object {


        fun getInstance(url: String): RetrofitFactory {
            //synchronized 避免同时调用多个接口，导致线程并发
            val instance: RetrofitFactory
            synchronized(RetrofitFactory::class.java) {
                instance = RetrofitFactory(url)
            }
            return instance
        }

        fun getInstance(url: String, interceptors: ArrayList<Interceptor>): RetrofitFactory {
            //synchronized 避免同时调用多个接口，导致线程并发
            val instance: RetrofitFactory
            synchronized(RetrofitFactory::class.java) {
                instance = RetrofitFactory(url, interceptors)
            }
            return instance
        }


        /**
         * 构建GSON转换器
         * 这里，你可以自己去实现
         * @return GsonConverterFactory
         */
        private fun buildGsonConverterFactory(): GsonConverterFactory {
            val builder = GsonBuilder()
            builder.setLenient()
            // 注册类型转换适配器
            builder.registerTypeAdapter(Date::class.java,
                JsonDeserializer { json, typeOfT, context -> if (null == json) null else Date(json.asLong) })

            val gson = builder.create()
            return GsonConverterFactory.create(gson)
        }
    }


}
