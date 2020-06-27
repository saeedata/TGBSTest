package org.technical.android.ui.fragment.feedDetails

import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.sdaggerAnnotations.BindModule
import com.tgbs.test.R
import com.tgbs.test.databinding.FragmentFeedDetailsWebViewBinding
import org.technical.android.di.data.InjectionViewModelProvider
import org.technical.android.entity.database.Feed
import org.technical.android.ui.base.BaseFragment
import org.technical.android.util.progresshandler.ViewProgressHandler
import timber.log.Timber
import javax.inject.Inject


@BindModule
class FragmentFeedDetails :
    BaseFragment<FragmentFeedDetailsWebViewBinding, FragmentFeedDetailsViewModel>(),
    View.OnClickListener {

    @Inject
    lateinit var mViewModelFactoryActivity: InjectionViewModelProvider<FragmentFeedDetailsViewModel>

    override fun getLayoutId() = R.layout.fragment_feed_details_web_view

    private var feed: Feed? =null

    val loading = ViewProgressHandler({
        binding.pbLoading.visibility = View.VISIBLE
    }, {
        binding.pbLoading.visibility = View.GONE
    })

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = mViewModelFactoryActivity.get(this, FragmentFeedDetailsViewModel::class)

        feed = arguments?.getParcelable(getString(R.string.extra_fragment_feed_details_feed))

        initViews()


    }

    private fun initViews() {

        checkFavoriteStatus()

        setupWebView()
    }

    private fun checkFavoriteStatus() {

        val disposable =viewModel?.mDataManager?.databaseManager?.feedsDao()
            ?.findByGUIDRx(feed?.guid)?.subscribe({
                binding.favoriteStatus = true
            }, Timber::e)

        viewModel?.addDisposable(disposable)

        binding.fabFavorite.setOnClickListener(this)
    }

    private fun setupWebView() {

        binding.wvFeed.settings.javaScriptEnabled = true

        binding.wvFeed.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView, url: String) {
                loading.dismissProgress()
            }

            override fun onReceivedError(
                view: WebView,
                errorCode: Int,
                description: String,
                failingUrl: String
            ) {
            }
        }

            binding.wvFeed.loadUrl(feed?.link)

    }

    override fun onStart() {
        super.onStart()
        loading.showProgress()
    }

    override fun onStop() {
        super.onStop()
        loading.dismissProgress()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fab_favorite -> {
                if (binding.favoriteStatus==true) {
                    viewModel?.mDataManager?.databaseManager?.feedsDao()?.deleteByGUIDRx(feed?.guid)
                        ?.subscribe({
                            binding.favoriteStatus = false
                            Timber.d("Item with guid = ${feed?.guid} removed from favorites")
                        }, Timber::e)
                } else {
                    viewModel?.mDataManager?.databaseManager?.feedsDao()?.insertRx(feed)
                        ?.subscribe({
                            binding.favoriteStatus = true
                            Timber.d("Item with guid = ${feed?.guid} added to favorites")
                        }, Timber::e)
                }
            }
        }
    }

    companion object {

        fun getInstance(): FragmentFeedDetails {
            return FragmentFeedDetails().apply {

            }
        }
    }

}
