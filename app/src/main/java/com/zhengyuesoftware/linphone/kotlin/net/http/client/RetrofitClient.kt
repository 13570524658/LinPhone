package com.zhengyuesoftware.linphone.kotlin.net.http.client

import com.google.gson.*
import com.zhengyuesoftware.linphone.kotlin.net.http.api.RequestService
import com.zhengyuesoftware.linphone.kotlin.net.http.interceptor.HandleErrorInterceptor
import com.zhengyuesoftware.linphone.kotlin.net.http.log.HttpLog
import com.zhengyuesoftware.linphone.kotlin.net.http.uri.Uri.base_url
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

/**
 * 创建日期：2020/10/26 on 15:43
 * github地址:https://github.com/13570524658
 * 作者: Administrator
 * 描述:
 */
object RetrofitClient {
    private val TAG = RetrofitClient::class.java.simpleName
    private var mGson= Gson()
    //日志显示级别
    private var mLevel = HttpLoggingInterceptor.Level.BODY
    //新建log拦截器
    private  var  mLoggingInterceptor =  HttpLoggingInterceptor(HttpLog()).setLevel(
        HttpLoggingInterceptor.Level.BODY);

    //定制OkHttp
    fun okhttp(): OkHttpClient? {
        mLoggingInterceptor.level = mLevel
        //定制OkHttp
        val httpClientBuilder = OkHttpClient.Builder()
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(mLoggingInterceptor)
        httpClientBuilder.addInterceptor(HandleErrorInterceptor())
        httpClientBuilder.addInterceptor(Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
//                .addHeader(Header.XXDeviceType, Header.android)
//                .addHeader(Header.XXtoken, Header.token)
            val request = requestBuilder.build()
            return@Interceptor  chain.proceed(request)
        })
        return httpClientBuilder.build()
    }


    open val requestService: RequestService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create(buildGson()))
            .client(okhttp())
            .build()
        return@lazy retrofit.create(RequestService::class.java)
    }

    /**
     * 增加后台返回""和"null"的处理
     * 1.int=>0
     * 2.double=>0.00
     * 3.long=>0L
     *
     * @return
     */

    fun buildGson(): Gson? {
        if (mGson == null) {
            mGson = GsonBuilder()
                .registerTypeAdapter(Int::class.java, IntegerDefault0Adapter())
                .registerTypeAdapter(Int::class.javaPrimitiveType, IntegerDefault0Adapter())
                .registerTypeAdapter(Double::class.java, DoubleDefault0Adapter())
                .registerTypeAdapter(Double::class.javaPrimitiveType, DoubleDefault0Adapter())
                .registerTypeAdapter(Long::class.java, LongDefault0Adapter())
                .registerTypeAdapter(Long::class.javaPrimitiveType, LongDefault0Adapter())
                .create()
        }
        return mGson
    }

    class IntegerDefault0Adapter : JsonSerializer<Int?>, JsonDeserializer<Int?> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type?, context: JsonDeserializationContext?): Int {
            try {
                if (json.asString == "" || json.asString == "null") { //定义为int类型,如果后台返回""或者null,则返回0
                    return 0
                }
            } catch (ignore: Exception) {
            }
            return try {
                json.asInt
            } catch (e: NumberFormatException) {
                throw JsonSyntaxException(e)
            }
        }

        override fun serialize(src: Int?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class DoubleDefault0Adapter : JsonSerializer<Double?>, JsonDeserializer<Double> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Double {
            try {
                if (json.asString == "" || json.asString == "null") { //定义为double类型,如果后台返回""或者null,则返回0.00
                    return 0.00
                }
            } catch (ignore: java.lang.Exception) {
            }
            return try {
                json.asDouble
            } catch (e: java.lang.NumberFormatException) {
                throw JsonSyntaxException(e)
            }
        }

        override fun serialize(src: Double?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }

    class LongDefault0Adapter : JsonSerializer<Long?>, JsonDeserializer<Long> {
        @Throws(JsonParseException::class)
        override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Long {
            try {
                if (json.asString == "" || json.asString == "null") { //定义为long类型,如果后台返回""或者null,则返回0
                    return 0L
                }
            } catch (ignore: java.lang.Exception) {
            }
            return try {
                json.asLong
            } catch (e: java.lang.NumberFormatException) {
                throw JsonSyntaxException(e)
            }
        }

        override fun serialize(src: Long?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
            return JsonPrimitive(src)
        }
    }
}