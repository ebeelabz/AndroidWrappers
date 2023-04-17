package com.aspiresys.networklayer.network.models

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName


open class BaseErrorVO() : Parcelable {
    @SerializedName(value = "status")
    var status: Int = 0

    @SerializedName(value = "error_code")
    var errorCode: Int = 0

    @SerializedName(value = "error_title")
    var errorTitle: String? = null

    @SerializedName("msg")
    var msg: String? = null

    @SerializedName("httpStatusCode")
    var httpStatusCode: Int = 0

    constructor(parcel: Parcel) : this() {
        status = parcel.readInt()
        errorCode = parcel.readInt()
        errorTitle = parcel.readString()
        msg = parcel.readString()
        httpStatusCode = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(status)
        parcel.writeInt(errorCode)
        parcel.writeString(errorTitle)
        parcel.writeString(msg)
        parcel.writeInt(httpStatusCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseErrorVO> {
        override fun createFromParcel(parcel: Parcel): BaseErrorVO {
            return BaseErrorVO(parcel)
        }

        override fun newArray(size: Int): Array<BaseErrorVO?> {
            return arrayOfNulls(size)
        }
    }
}