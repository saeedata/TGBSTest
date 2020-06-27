package org.technical.android.ui.fragment.feedList

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.sdaggerAnnotations.BindModule
import com.tgbs.test.R
import com.tgbs.test.databinding.FragmentFeedListBinding
import com.tgbs.test.databinding.ItemFeedBinding
import org.technical.android.di.data.InjectionViewModelProvider
import org.technical.android.di.data.database.dao.FeedsDao
import org.technical.android.entity.database.Feed
import org.technical.android.ui.activity.favorites.ActivityFavorites
import org.technical.android.ui.adapter.MyBindingAdapter
import org.technical.android.ui.base.BaseFragment
import org.technical.android.ui.fragment.feedDetails.FragmentFeedDetails
import org.technical.android.util.liveData.combineWith
import org.technical.android.util.progresshandler.ViewProgressHandler
import timber.log.Timber
import javax.inject.Inject


@BindModule
class FragmentFeedList :
    BaseFragment<FragmentFeedListBinding, FragmentFeedListViewModel>(), View.OnClickListener {

    @Inject
    lateinit var mViewModelFactoryActivity: InjectionViewModelProvider<FragmentFeedListViewModel>

    override fun getLayoutId() = R.layout.fragment_feed_list

    private val feedList = ArrayList<Feed>()

    val loading = ViewProgressHandler({
        binding.pbLoading.visibility = View.VISIBLE
    }, {
        binding.pbLoading.visibility = View.GONE
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = mViewModelFactoryActivity.get(this, FragmentFeedListViewModel::class)

        initViews()

        observeLiveData()

        getFeeds()

    }

    private fun initViews() {

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        binding.rvList.itemAnimator = null
        binding.rvList.adapter = MyBindingAdapter<Feed, ItemFeedBinding>(
            context = context,
            items = feedList,
            layout = R.layout.item_feed,
            onBindViewHolder = { item, position, binder ->

                if( arguments?.getString(getString(R.string.extra_fragment_feed_list_source)) == getString(R.string.extra_fragment_feed_list_source_database)) {
                    item.favoriteStatus = true
                }

                binder.feed = item

                binder.root.setOnClickListener {
                    it.findNavController()
                        .navigate(FragmentFeedListDirections.actionFeedListToFeedDetails(item))
                }

                binder.imgFav.setOnClickListener {
                    if (item.favoriteStatus) {
                        viewModel?.mDataManager?.databaseManager?.feedsDao()
                            ?.deleteByGUIDRx(item.guid)
                            ?.subscribe({
                                Timber.d("Item with guid = ${item.guid} removed from favorites")
                            }, Timber::e)
                    } else {
                        viewModel?.mDataManager?.databaseManager?.feedsDao()?.insertRx(item)
                            ?.subscribe({
                                Timber.d("Item with guid = ${item.guid} added to favorites")
                            }, Timber::e)
                    }
                }
            })
    }

    private fun observeLiveData() {
        viewModel?.feedsLiveData?.observe(viewLifecycleOwner, Observer { feeds ->
            feeds?.forEach { rawFeed ->
                val storedFeed =
                    viewModel?.mDataManager?.databaseManager?.feedsDao()?.findByGUID(rawFeed.guid)
                if (storedFeed != null) {
                    rawFeed.favoriteStatus = true
                }
                feedList.add(rawFeed)
            }
            binding.rvList.adapter?.notifyDataSetChanged()
        })

        viewModel?.mDataManager?.databaseManager?.feedsDao()?.tabledChangedLiveData?.observe(
            this,
            Observer { change ->
                when (change.first) {
                    FeedsDao.STATUS_INSERT -> {
                        feedList.indexOfFirst { it.guid == change.second?.guid }.takeIf { it != -1 }
                            ?.let { addedIndex ->
                                feedList[addedIndex].favoriteStatus = true
                                binding.rvList.adapter?.notifyItemChanged(addedIndex)
                            }
                    }
                    FeedsDao.STATUS_DELETE -> {
                        when (arguments?.getString(getString(R.string.extra_fragment_feed_list_source))) {
                            getString(R.string.extra_fragment_feed_list_source_database) -> {
                                feedList.indexOfFirst { it.guid == change.second?.guid }
                                    .takeIf { it != -1 }?.let { deletedIndex ->
                                    feedList.removeAt(deletedIndex)
                                    binding.rvList.adapter?.notifyItemRemoved(deletedIndex)
                                }
                            }
                            else -> {
                                feedList.indexOfFirst { it.guid == change.second?.guid }
                                    .takeIf { it != -1 }
                                    ?.let { deletedIndex ->
                                        feedList[deletedIndex].favoriteStatus = false
                                        binding.rvList.adapter?.notifyItemChanged(deletedIndex)
                                    }
                            }
                        }
                    }
                }
            })
    }

    private fun getFeeds() {
        if (feedList.isEmpty()) {
            when (arguments?.getString(getString(R.string.extra_fragment_feed_list_source))) {
                getString(R.string.extra_fragment_feed_list_source_google) -> {
                    viewModel?.getGoogleFeeds(
                        loading,
                        apiKey = getString(R.string.googleFeedsApiKey)
                    )
                }
                getString(R.string.extra_fragment_feed_list_source_instagram) -> {
                    viewModel?.getInstagramFeeds(loading)
                }
                getString(R.string.extra_fragment_feed_list_source_database) -> {
                    viewModel?.getFavoriteFeeds()
                }
            }
        } else {
            loading.dismissProgress()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {

        }
    }

    companion object {

        fun getInstance(): FragmentFeedList {
            return FragmentFeedList().apply {

            }
        }
    }

}
