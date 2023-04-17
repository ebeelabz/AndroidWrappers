package com.aspiresys.networklayer.network.error

import android.os.Parcel
import android.os.Parcelable
import com.aspiresys.networklayer.network.models.BaseErrorVO
import com.aspiresys.networklayer.network.utils.ErrorCode.CUSTOM_SERVER_ERROR
import com.aspiresys.networklayer.network.utils.ErrorCode.SERVER_ERROR
import com.aspiresys.networklayer.network.utils.ErrorString


class NetworkError constructor(
    var httpCode: Int,
    var error: BaseErrorVO? = null
) : Parcelable {
    init {
        if (httpCode == SERVER_ERROR) {
            this.error?.errorCode = CUSTOM_SERVER_ERROR
        }
    }

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readParcelable(BaseErrorVO::class.java.classLoader)
    ) {
    }

    constructor(httpCode: Int, errorMsg: String) : this(httpCode) {
        error = BaseErrorVO()
        error!!.msg = errorMsg
    }

    constructor(httpCode: Int) : this(httpCode, null) {
        this.httpCode = httpCode
        this.error = BaseErrorVO()
        this.error!!.msg = ErrorString.SERVER_ERROR
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(httpCode)
        parcel.writeParcelable(error, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NetworkError> {
        override fun createFromParcel(parcel: Parcel): NetworkError {
            return NetworkError(parcel)
        }

        override fun newArray(size: Int): Array<NetworkError?> {
            return arrayOfNulls(size)
        }
    }
}