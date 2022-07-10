package com.zinc.berrybucket.ui.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.zinc.berrybucket.R
import com.zinc.berrybucket.ui.presentation.BucketSelected
import com.zinc.berrybucket.ui.presentation.SearchEvent
import com.zinc.berrybucket.ui.presentation.common.IconButton
import com.zinc.berrybucket.ui.presentation.common.IconToggleButton
import com.zinc.berrybucket.ui.presentation.feed.Feed
import com.zinc.berrybucket.ui.presentation.my.BottomSheetScreenType
import com.zinc.berrybucket.ui.presentation.my.MyScreen
import com.zinc.berrybucket.ui.presentation.search.RecommendScreen

fun NavGraphBuilder.addHomeGraph(
    onBucketSelected: (BucketSelected, NavBackStackEntry) -> Unit,
    onSearchEvent: (SearchEvent, NavBackStackEntry) -> Unit,
    bottomSheetClicked: (BottomSheetScreenType) -> Unit,
    modifier: Modifier = Modifier
) {
    composable(HomeSections.MY.route) { from ->
        MyScreen(
            onBucketSelected = {
                onBucketSelected.invoke(it, from)
            },
            bottomSheetClicked = {
                bottomSheetClicked.invoke(it)
            }
        )
    }
    composable(HomeSections.FEED.route) { from ->
        Feed()
    }
    composable(HomeSections.SEARCH.route) { from ->
        RecommendScreen(
            onSearchEvent = {
                onSearchEvent.invoke(it, from)
            }
        )
    }
    composable(HomeSections.MORE.route) {
        //  MORE(modifier)
    }
}

enum class HomeSections(
    @StringRes val title: Int,
    @DrawableRes val enableIcon: Int,
    @DrawableRes val disableIcon: Int,
    val route: String
) {
    MY(
        R.string.my,
        enableIcon = R.drawable.my_icon_enable,
        disableIcon = R.drawable.my_icon_disable,
        route = "home/my"
    ),
    FEED(
        R.string.feed,
        enableIcon = R.drawable.feed_icon_enable,
        disableIcon = R.drawable.feed_icon_disable,
        route = "home/feed"
    ),
    SEARCH(
        R.string.search,
        enableIcon = R.drawable.search_icon_enable,
        disableIcon = R.drawable.search_icon_disable,
        route = "home/search"
    ),
    MORE(
        R.string.appMore,
        enableIcon = R.drawable.more_icon_enable,
        disableIcon = R.drawable.more_icon_disable,
        route = "home/more"
    )
}


@Composable
fun HomeBottomBar(
    tabs: List<HomeSections>,
    currentRoute: String,
    navigateToRoute: (String) -> Unit,
    goToWrite: () -> Unit
) {
    val currentSection = tabs.first { it.route == currentRoute }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp),
        shape = RectangleShape,
        elevation = 8.dp
    ) {
        ConstraintLayout {
            val (tabView, writeButtonView) = createRefs()

            LazyRow(modifier = Modifier
                .padding(start = 8.dp)
                .constrainAs(tabView) {
                    top.linkTo(parent.top)
                    end.linkTo(writeButtonView.start)
                    linkTo(
                        parent.start,
                        writeButtonView.start,
                        bias = 0f
                    )
                }) {
                items(items = tabs, itemContent = { tab ->
                    TabIcon(tab = tab,
                        isSelected = tab == currentSection,
                        selectChanged = {
                            navigateToRoute.invoke(tab.route)
                        })
                })
            }

            IconButton(
                modifier = Modifier.constrainAs(writeButtonView) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                },
                onClick = { goToWrite() },
                image = R.drawable.add_bucket_icon,
                contentDescription = stringResource(id = R.string.write)
            )
        }
    }

}

@Composable
private fun TabIcon(
    modifier: Modifier = Modifier,
    tab: HomeSections,
    isSelected: Boolean,
    selectChanged: (Boolean) -> Unit
) {
    IconToggleButton(
        modifier = modifier
            .padding(start = 16.dp, end = 16.dp, top = 14.dp)
            .then(Modifier.size(36.dp)),
        checked = isSelected,
        onCheckedChange = {
            selectChanged(it)
        },
        image = if (isSelected) tab.enableIcon else tab.disableIcon,
        contentDescription = stringResource(id = tab.title)
    )

}