package com.aspiresys.networklayer.network.core

import com.aspiresys.networklayer.network.error.NetworkError
import com.aspiresys.networklayer.network.models.BaseErrorVO
import com.google.gson.Gson
import com.google.gson.JsonElement

/*
  * @created 07/01/2020 - 12:09 PM
  * @project ZC-Network-Client
  * @author Paras
*/
interface NetworkListener {
    fun onSuccess(response: JsonElement?, requestCode: Int)
    fun onError(error: NetworkError)

    fun buildNetworkError(httpCode: Int, data: ByteArray): NetworkError {
        val baseErrorVO: BaseErrorVO
        return try {
            baseErrorVO = Gson().fromJson(String(data), BaseErrorVO::class.java)
            NetworkError(httpCode, baseErrorVO)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkError(0)
        }
    }
}