package org.technical.android.entity.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bluelinelabs.logansquare.annotation.JsonIgnore
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "feeds")
data class Feed(

    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Long? = null,

    @ColumnInfo(name = "guid")
    var guid: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = null,

    @ColumnInfo(name = "author")
    var author: String? = null,

    @ColumnInfo(name = "publishedDate")
    var publishedDate: String? = null,

    @ColumnInfo(name = "link")
    var link: String? = null,

    @JsonIgnore
    var favoriteStatus :Boolean = false

) : Parcelable {

    override fun equals(other: Any?): Boolean {
        return other is Feed && other.guid == this.guid
    }
}