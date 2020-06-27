package org.technical.android.ui.fragment.feedDetails

import io.reactivex.disposables.CompositeDisposable
import org.technical.android.di.data.DataManager
import org.technical.android.ui.base.BaseViewModel


class FragmentFeedDetailsViewModel(
    dataManager: DataManager,
    compositeDisposable: CompositeDisposable
) : BaseViewModel(dataManager, compositeDisposable)