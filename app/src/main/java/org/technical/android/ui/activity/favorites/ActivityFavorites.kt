package org.technical.android.ui.activity.favorites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.sdaggerAnnotations.BindModule
import com.tgbs.test.R
import com.tgbs.test.databinding.ActivityFavoritesBinding
import org.technical.android.di.data.InjectionViewModelProvider
import org.technical.android.ui.base.BaseActivity
import javax.inject.Inject


@BindModule
class ActivityFavorites : BaseActivity<ActivityFavoritesBinding, ActivityFavoritesViewModel>(),
    View.OnClickListener {

    @Inject
    lateinit var mViewModelFactoryActivity: InjectionViewModelProvider<ActivityFavoritesViewModel>

    override fun getLayoutId() = R.layout.activity_favorites

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = mViewModelFactoryActivity.get(this, ActivityFavoritesViewModel::class)

        initViews()

    }

    private fun initViews() {

        setUpNavigation()

        setupToolbar()

    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setUpNavigation() {
        NavigationUI.setupWithNavController(
            binding.toolbar,
            (supportFragmentManager.findFragmentById(R.id.nh_fragment) as NavHostFragment).navController
        )
    }

    override fun onClick(view: View?) {
        when (view?.id) {

        }
    }

    companion object {


        fun navigate(context: Context?) {
            val i = Intent(context, ActivityFavorites::class.java).apply {

            }
            context?.startActivity(i)
        }
    }
}
