package com.zinc.berrybucket.ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.zinc.berrybucket.compose.ui.BucketSelected
import com.zinc.berrybucket.compose.ui.my.MySections
import com.zinc.berrybucket.compose.ui.my.MyTabLayer
import com.zinc.berrybucket.compose.ui.my.MyTopLayer
import com.zinc.berrybucket.compose.ui.my.MyViewPager
import com.zinc.berrybucket.databinding.LayoutMyViewBinding
import com.zinc.berrybucket.presentation.my.viewModel.MyViewModel
import com.zinc.domain.models.TopProfile

class MyView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    lateinit var binding: LayoutMyViewBinding

    init {
        setUpViews()
    }

    private fun setUpViews() {
        binding = LayoutMyViewBinding.inflate(LayoutInflater.from(context), this, true)
        binding.profileComposeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }
        binding.tabComposeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }
        binding.pagerComposeView.apply {
            setViewCompositionStrategy(
                ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
        }
    }

    fun setProfileInfo(profileInfo: TopProfile) {
        binding.profileComposeView.setContent {
            MyTopLayer(profileInfo = profileInfo)
        }
    }

    @OptIn(ExperimentalPagerApi::class)
    fun setTabView(
        tabItems: Array<MySections>,
        pagerState: PagerState,
        viewModel: MyViewModel,
        onBucketSelected: (BucketSelected) -> Unit
    ) {
        binding.tabComposeView.setContent {
            MyTabLayer(tabItems, pagerState)
        }
        binding.pagerComposeView.setContent {
            MyViewPager(
                tabItems = tabItems,
                pagerState = pagerState,
                viewModel = viewModel,
                onBucketSelected = onBucketSelected
            )
        }
    }

}