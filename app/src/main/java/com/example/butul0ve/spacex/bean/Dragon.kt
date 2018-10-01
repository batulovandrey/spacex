package com.example.butul0ve.spacex.bean

import com.google.gson.annotations.SerializedName

data class Dragon(@SerializedName("id") var id: String,
                  @SerializedName("name") var name: String,
                  @SerializedName("type") var type: String,
                  @SerializedName("active") var isActive: Boolean,
                  @SerializedName("wikipedia") var wiki: String,
                  @SerializedName("description") var description: String)