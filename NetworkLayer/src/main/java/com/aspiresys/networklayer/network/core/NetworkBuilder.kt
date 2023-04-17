package com.aspiresys.networklayer.network.core


import android.content.Context
import com.aspiresys.networklayer.network.utils.RequestType
import okhttp3.Interceptor

class NetworkBuilder {
    private lateinit var context: Context
    private var requestCode: Int = 0
    private var headerParams: HashMap<String, String>? = null
    private var requestParams: HashMap<String, Any> = hashMapOf()
    private var bodyParams: HashMap<String, Any> = hashMapOf()
    private var listener: NetworkListener? = null
    private lateinit var url: String
    private lateinit var requestType: RequestType
    private var baseUrl: String? = null
    private var interceptors: MutableList<Interceptor> = mutableListOf()

    fun setContext(context: Context): NetworkBuilder =
        apply { this.context = context }

    fun setHeaderParams(headerParams: HashMap<String, String>): NetworkBuilder =
        apply { this.headerParams = headerParams }

    fun setRequestCode(requestCode: Int): NetworkBuilder =
        apply { this.requestCode = requestCode }

    fun setRequestParams(requestParams: HashMap<String, Any>): NetworkBuilder =
        apply { this.requestParams = requestParams }

    fun setBodyParams(bodyParams: HashMap<String, Any>): NetworkBuilder =
        apply { this.bodyParams = bodyParams }

    fun setListener(listener: NetworkListener): NetworkBuilder =
        apply { this.listener = listener }

    fun addInterceptor(interceptor: Interceptor): NetworkBuilder =
        apply { this.interceptors.add(interceptor) }

    fun setBaseUrl(baseUrl: String) =
        apply { this.baseUrl = baseUrl }

    fun setUrl(url: String): NetworkBuilder =
        apply { this.url = url }

    fun setRequestType(requestType: RequestType) =
        apply { this.requestType = requestType }

    fun request() {
        NetworkManager.request(
            context,
            requestCode,
            requestType,
            headerParams,
            requestParams,
            listener,
            url,
            bodyParams,
            interceptors
        )
    }
}