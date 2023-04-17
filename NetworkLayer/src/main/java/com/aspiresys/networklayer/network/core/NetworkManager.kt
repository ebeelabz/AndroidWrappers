package com.aspiresys.networklayer.network.core

import android.content.Context
import com.aspiresys.networklayer.network.utils.CustomExceptions
import com.aspiresys.networklayer.network.utils.CustomExceptions.NOT_INITIALIZED
import com.aspiresys.networklayer.network.utils.RequestType
import com.google.gson.JsonElement
import okhttp3.Interceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object NetworkManager {
    private lateinit var applicationContext: Context
    private var requestManager: RequestManager? = null
    private var isDebugLogEnabled: Boolean = false
    private var baseUrl: String? = null
    private var interceptors: MutableList<Interceptor> = mutableListOf()
    private var defaultService = true

    fun builder(applicationContext: Context): NetworkManager =
        apply { NetworkManager.applicationContext = applicationContext }

    fun setDebugLog(isDebugLogEnabled: Boolean): NetworkManager =
        apply { NetworkManager.isDebugLogEnabled = isDebugLogEnabled }

    fun addBaseUrl(baseUrl: String?): NetworkManager =
        apply { NetworkManager.baseUrl = baseUrl }

    fun addInterceptor(interceptor: Interceptor): NetworkManager =
        apply { interceptors.add(interceptor) }

    fun build() {
        if (baseUrl == null) throw java.lang.IllegalArgumentException(
            CustomExceptions.NO_BASE_URL
        )
        requestManager = RequestManager.getInstance(
            applicationContext,
            isDebugLogEnabled,
            baseUrl!!,
            interceptors
        )
    }

    fun request(
        context: Context? = null,
        requestCode: Int = -1,
        requestType: RequestType,
        headerParams: HashMap<String, String>? = null,
        requestParams: HashMap<String, Any>,
        listener: NetworkListener? = null,
        url: String,
        bodyParams: HashMap<String, Any>,
        interceptors: MutableList<Interceptor>
    ) {

        if (requestManager == null) throw IllegalArgumentException(NOT_INITIALIZED)

        addHeaderParams(headerParams, applicationContext)

        /*fun isComponentAdded() = (activity == null || !activity.isFinishing
                && (fragment == null || fragment.isAdded))*/

        val reqStartTime = System.currentTimeMillis()
        /*fun invokeTimingEvent(responseStatus: String) {
            analyticsListener?.responseTimeEvent(
                reqStartTime.getTimeDifferenceInMillis(),
                responseStatus,
                requestCode,
                tag
            )
        }*/

        val callback = object : Callback<JsonElement> {
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                if (response.isSuccessful) {
                    //invokeTimingEvent(SUCCESS)
                    /*if (isComponentAdded()) {
                        listener?.onSuccess(response.body(), requestCode)
                    }*/
                    listener?.onSuccess(response.body(), requestCode)
                } else {
                    //invokeTimingEvent(FAILURE)
                    if (response.errorBody() != null) {
                        return try {
                            when (listener) {
                                /*is ZcJavaServiceNetworkListener -> {
                                    val networkError = listener.buildJavaServiceNetworkError(
                                        response.code(), response.errorBody()?.bytes()!!
                                    )
                                    handleJavaServiceNetworkError(
                                        networkError, listener, isComponentAdded(), requestCode, tag
                                    )
                                }*/
                                else -> {
                                    val networkError = listener?.buildNetworkError(
                                        response.code(), response.errorBody()?.bytes()!!
                                    )
                                    /*handleNetworkError(
                                        networkError,
                                        listener,
                                        isComponentAdded(),
                                        requestCode,
                                        tag
                                    )*/
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    } /*else if (isComponentAdded()) {
                        listener?.onError(NetworkError(response.code(), DEFAULT_RETROFIT_ERROR))
                    }*/
                }
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                //invokeTimingEvent(FAILURE)
                /*when (listener) {
                    is ZcJavaServiceNetworkListener -> {
                        val networkError = JavaServiceNetworkError(httpCode = NO_NETWORK)
                        handleJavaServiceNetworkError(
                            networkError,
                            listener,
                            isComponentAdded(),
                            requestCode,
                            tag
                        )
                    }
                    else -> {
                        val networkError = NetworkError(NO_NETWORK)
                        /*handleNetworkError(
                            networkError, listener, isComponentAdded(), requestCode, tag
                        )*/
                    }
                }*/
            }
        }
        val call: Call<JsonElement>?
        if (defaultService) {
            val apiService =
                RequestManager.getInstance(
                    applicationContext,
                    baseUrl = baseUrl!!,
                    interceptor = interceptors
                )
                    .getDefaultApiService()
            call = when (requestType) {
                RequestType.GET -> apiService.getResource(url, requestParams)
                RequestType.POST -> apiService.createResource(url, requestParams, bodyParams)
                RequestType.PUT -> apiService.updateResource(url, requestParams, bodyParams)
                RequestType.DELETE -> apiService.deleteResource(url, requestParams, bodyParams)
                RequestType.PATCH -> apiService.patchResource(url, requestParams)
            }
            call.run { call.enqueue(callback) }
        }
    }

    private fun addHeaderParams(
        headerParams: java.util.HashMap<String, String>?,
        applicationContext: Context
    ) {
        if (headerParams != null) {
            RequestManager.getInstance(
                applicationContext,
                baseUrl = baseUrl!!,
                interceptor = interceptors
            )
                .setHeaderParams(headerParams)
        }
    }

    /*private fun handleJavaServiceNetworkError(
        networkError: JavaServiceNetworkError,
        listener: ZcJavaServiceNetworkListener,
        componentAdded: Boolean,
        requestCode: Int,
        requestTag: String?
    ) {
        if (networkError.error == null) networkError.error = JavaServiceBaseVO()
        if (networkError.error?.details == null) {
            networkError.error?.details = JavaServiceErrorDetailVO(message = SERVER_ERROR)
        }
        networkError.error?.code?.run {
            analyticsListener?.javaServiceFailureEvent(
                networkError, requestCode, requestTag
            )
        }
        if (componentAdded) listener.onJavaServiceNetworkError(networkError)
    }*/

    /*private fun handleNetworkError(
        networkError: NetworkError?,
        listener: NetworkListener?,
        componentAdded: Boolean,
        requestCode: Int,
        requestTag: String?
    ) {
        networkError?.let {
            analyticsListener?.failureEvent(it, requestCode, requestTag)
            if (componentAdded) listener?.onError(it)
        }
    }*/

}