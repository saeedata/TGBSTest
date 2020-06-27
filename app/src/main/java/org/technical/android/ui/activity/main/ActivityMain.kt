package org.technical.android.ui.activity.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sdaggerAnnotations.BindModule
import com.tgbs.test.R
import com.tgbs.test.databinding.ActivityMainBinding
import org.technical.android.di.data.InjectionViewModelProvider
import org.technical.android.ui.activity.favorites.ActivityFavorites
import org.technical.android.ui.base.BaseActivity
import javax.inject.Inject


@BindModule
class ActivityMain : BaseActivity<ActivityMainBinding, ActivityMainViewModel>(),
    View.OnClickListener {

    @Inject
    lateinit var mViewModelFactoryActivity: InjectionViewModelProvider<ActivityMainViewModel>

    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = mViewModelFactoryActivity.get(this, ActivityMainViewModel::class)

        initViews()

    }

    private fun initViews() {

        binding.fabFavoriteList.setOnClickListener(this)

        setUpNavigation()

    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            binding.bnvMain,
            (supportFragmentManager.findFragmentById(R.id.nh_fragment) as NavHostFragment).navController
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.fab_favorite_list -> {
                ActivityFavorites.navigate(this)
            }
        }
    }

    companion object {

        fun navigate(context: Context) {
            val i = Intent(context, ActivityMain::class.java).apply {

            }
            context.startActivity(i)
        }
    }
}
