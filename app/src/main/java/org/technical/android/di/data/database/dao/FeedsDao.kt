package org.technical.android.di.data.database.dao


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import org.technical.android.entity.database.Feed

@Dao
abstract class FeedsDao {

    @Query("SELECT * FROM feeds")
    abstract fun findAllList(): List<Feed>

    @Query("SELECT * FROM feeds")
    abstract fun findAllLiveData(): LiveData<List<Feed>>

    @Query("SELECT * FROM feeds WHERE guid = :guid LIMIT 1")
    abstract fun findByGUID(guid: String?): Feed?

    @Query("SELECT * FROM feeds WHERE guid = :guid LIMIT 1")
    abstract fun findByGUIDRx(guid: String?): Maybe<Feed>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(feed: Feed?): Completable

    @Delete
    abstract fun delete(feed: Feed): Single<Int>

    @Query("DELETE FROM feeds WHERE guid = :guid")
    abstract fun deleteByGUID(guid: String?): Completable

    @Query("DELETE FROM feeds")
    abstract fun deleteAll(): Completable


    val tabledChangedLiveData:MutableLiveData<Pair<Int,Feed?>> by lazy {
        MutableLiveData<Pair<Int,Feed?>>()
    }

    fun insertRx(feed:Feed?):Completable{
        return insert(feed).doOnComplete {
            tabledChangedLiveData.postValue(Pair(STATUS_INSERT,feed))
            findAllLiveData()
        }
    }

    fun deleteByGUIDRx(guid: String?):Completable{
        return deleteByGUID(guid).doOnComplete {
            tabledChangedLiveData.postValue(Pair(STATUS_DELETE,Feed(guid=guid)))
            findAllLiveData()
        }
    }

    fun deleteAllRx(): Completable {
        return deleteAll().doOnComplete { findAllLiveData() }
    }

    companion object{

        const val STATUS_INSERT=0
        const val STATUS_DELETE=1
    }
}

