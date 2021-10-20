package com.colearn.data

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/*
 *  Generated by https://www.jsonschema2pojo.org/
 */
class ProfileImage : Parcelable {
    @SerializedName("small")
    @Expose
    var small: String? = null

    @SerializedName("medium")
    @Expose
    var medium: String? = null

    @SerializedName("large")
    @Expose
    var large: String? = null

    protected constructor(`in`: Parcel) {
        small = `in`.readValue(String::class.java.classLoader) as String?
        medium = `in`.readValue(String::class.java.classLoader) as String?
        large = `in`.readValue(String::class.java.classLoader) as String?
    }

    constructor() {}

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(small)
        dest.writeValue(medium)
        dest.writeValue(large)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {
        val CREATOR: Parcelable.Creator<ProfileImage?> = object : Parcelable.Creator<ProfileImage?> {
            override fun createFromParcel(`in`: Parcel): ProfileImage? {
                return ProfileImage(`in`)
            }

            override fun newArray(size: Int): Array<ProfileImage?> {
                return arrayOfNulls(size)
            }
        }
    }
}