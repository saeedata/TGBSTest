package org.technical.android.entity.network.response

import com.tickaroo.tikxml.annotation.*


@Xml(name = "rss")
class RssFeedXmlResponse(

    @Element
    var channel: RssChannel? = null

)

@Xml(name = "channel")
class RssChannel(

    @PropertyElement
    var title: String? = null,

    @PropertyElement
    var description: String? = null,

    @PropertyElement
    var link: String? = null,

    @PropertyElement
    var generator: String? = null,

    @PropertyElement
    var lastBuildDate: String? = null,

    @PropertyElement
    var language: String? = null,

    @Element
    var image: RssImage? = null,

    @Element
    var item: List<RssItem>? = null

)

@Xml(name = "image")
class RssImage(

    @PropertyElement
    var url: String? = null,

    @PropertyElement
    var title: String? = null,

    @PropertyElement
    var link: String? = null

)

@Xml(name = "item")
class RssItem(

    @PropertyElement
    var guid: String? = null,

    @PropertyElement
    var title: String? = null,

    @PropertyElement
    var link: String? = null,

    @PropertyElement
    var pubDate: String? = null,

    @PropertyElement(name = "dc:creator")
    var creator: String? = null,

    @Path( "media:content")
    @Attribute
    var url: String? = null,

    @PropertyElement
    var description: String? = null

)
