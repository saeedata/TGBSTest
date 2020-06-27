package org.technical.android.util


import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

abstract class EndlessRecyclerOnScrollListener : RecyclerView.OnScrollListener {

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var firstVisibleItem: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    var visibleThreshold =
        2 // The minimum amount of items to have below your current scroll position before loading more.

    var isLandscape = false
    var currentPage = 1
    var totalPages: Long = 0
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var isUseLinearLayoutManager: Boolean = false
    private var isUseGridLayoutManager: Boolean = false

    constructor(linearLayoutManager: LinearLayoutManager) {
        this.mLayoutManager = linearLayoutManager
        isUseLinearLayoutManager = true

    }

    constructor(gridLayoutManager: GridLayoutManager) {
        this.mLayoutManager = gridLayoutManager
        isUseGridLayoutManager = true

    }

    fun refresh() {
        currentPage = 1
        totalPages = 0
        previousTotal = 0
        loading = true
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        visibleItemCount = recyclerView.childCount
        totalItemCount = mLayoutManager?.itemCount ?: 0


        if (isUseLinearLayoutManager && mLayoutManager is LinearLayoutManager) {
            firstVisibleItem =
                (mLayoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        }

        if (isUseGridLayoutManager && mLayoutManager is GridLayoutManager) {
            firstVisibleItem = (mLayoutManager as GridLayoutManager).findFirstVisibleItemPosition()
        }

        if (loading) {
            if (isLandscape) {
                if (totalItemCount >= previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            } else {
                if (totalItemCount > previousTotal) {
                    loading = false
                    previousTotal = totalItemCount
                }
            }

        }
        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold && currentPage < totalPages) {
            // End has been reached

            // Do something
            currentPage++

            onLoadMore(currentPage)

            loading = true
        }
    }

    abstract fun onLoadMore(currentPage: Int)

}

