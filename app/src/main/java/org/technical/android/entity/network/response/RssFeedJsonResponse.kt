package org.technical.android.entity.network.response


import com.bluelinelabs.logansquare.annotation.JsonField
import com.bluelinelabs.logansquare.annotation.JsonObject
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable

@Parcelize
@JsonObject
data class RssFeedJsonResponse(

    @JsonField(name = ["articles"])
    var articles: List<Article>?=null,

    @JsonField(name = ["status"])
    var status: String?=null,

    @JsonField(name = ["totalResults"])
    var totalResults: Int?=null

) : Parcelable

@Parcelize
@JsonObject
data class Article(

    @JsonField(name = ["author"])
    var author: String?=null,

    @JsonField(name = ["content"])
    var content: String?=null,

    @JsonField(name = ["description"])
    var description: String?=null,

    @JsonField(name = ["publishedAt"])
    var publishedAt: String?=null,

    @JsonField(name = ["source"])
    var source: Source?=null,

    @JsonField(name = ["title"])
    var title: String?=null,

    @JsonField(name = ["url"])
    var url: String?=null,

    @JsonField(name = ["urlToImage"])
    var urlToImage: String?=null

) : Parcelable

@Parcelize
@JsonObject
data class Source(

    @JsonField(name = ["id"])
    var id: Int?=null,

    @JsonField(name = ["name"])
    var name: String?=null

) : Parcelable