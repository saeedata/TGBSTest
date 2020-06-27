package org.technical.android.ui.fragment.feedList

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import io.reactivex.disposables.CompositeDisposable
import org.technical.android.di.data.DataManager
import org.technical.android.entity.database.Feed
import org.technical.android.entity.network.response.Article
import org.technical.android.entity.network.response.RssFeedJsonResponse
import org.technical.android.entity.network.response.RssFeedXmlResponse
import org.technical.android.entity.network.response.RssItem
import org.technical.android.ui.base.BaseViewModel
import org.technical.android.util.progresshandler.ProgressHandler
import timber.log.Timber
import java.net.URLEncoder


class FragmentFeedListViewModel(
    dataManager: DataManager,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(dataManager, compositeDisposable) {

    val feedsLiveData: MutableLiveData<List<Feed>> by lazy {
        MutableLiveData<List<Feed>>()
    }

    fun getGoogleFeeds(
        progressHandler: ProgressHandler?,
        apiKey: String,
        query: String = "everything",
        date: String? = null,
        sortBy: String? = "publishedAt",
        page: Int? = null
    ) {
        disposable[0]?.dispose()
        disposable[0] = makeRequest(
            mDataManager.networkManager.getGoogleFeedsApi().getFeeds(
                query = query,
                apiKey = apiKey,
                date = date,
                sortBy = sortBy,
                page = page
            ), progressHandler
        ).map {res->
            convertJsonModelToFeed(res.articles)
        }.subscribe(feedsLiveData::postValue, Timber::e)
        addDisposable(disposable[0])
    }

    fun getInstagramFeeds(progressHandler: ProgressHandler? ) {
        disposable[1]?.dispose()
        disposable[1] = makeRequest(
            mDataManager.networkManager.getInstagramFeedsFeedApi().getFeeds(), progressHandler
        ).map {res->
            convertXmlModelToFeed(res.channel?.item)
        }.subscribe(feedsLiveData::postValue, Timber::e)
        addDisposable(disposable[1])
    }


    fun getFavoriteFeeds( ) {
        feedsLiveData.postValue(mDataManager.databaseManager.feedsDao().findAllList())
    }


    private fun convertJsonModelToFeed(jsonFeed: List<Article>?): List<Feed> {
        return ArrayList<Feed>().apply {
            jsonFeed?.forEach { item ->
                add(
                    Feed(
                        guid = item.title,
                        title = item.title,
                        description = item.description,
                        imageUrl = item.urlToImage,
                        author = item.author,
                        publishedDate = item.publishedAt,
                        link = item.url
                    )
                )
            }
        }
    }

    private fun convertXmlModelToFeed(jsonFeed: List<RssItem>?): List<Feed> {
        return ArrayList<Feed>().apply {
            jsonFeed?.forEach { item ->
                add(
                    Feed(
                        guid = item.guid,
                        title = item.title,
//                        description = item.description,
                        imageUrl = item.url?.replace("&amp;", "&"),
                        author = item.creator,
                        publishedDate = item.pubDate,
                        link = item.link
                    )
                )
            }
        }
    }
}