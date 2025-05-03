package com.example.data.api.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SourcesResponse(

    @field:SerializedName("sources")
    val sources: List<SourcesItem>? = null,

    @field:SerializedName("status")
    val status: String? = null
) : Parcelable