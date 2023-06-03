package com.zinc.berrybucket.ui.presentation.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import com.zinc.berrybucket.ui.presentation.component.SearchEditView

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    goTGoEvent: () -> Unit
) {
    val viewModel: SearchViewModel = hiltViewModel()
    val searchRecommendItems by viewModel.searchRecommendItems.observeAsState()
    val searchResultItems by viewModel.searchResultItems.observeAsState()
    viewModel.loadSearchRecommendItems()

    val listScrollState = rememberLazyListState()
    val searchWord = remember { mutableStateOf("") }
    var isScrolled = listScrollState.firstVisibleItemIndex != 0


    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (topAppBar, searchResultView) = createRefs()

        SearchTopAppBar(
            listState = listScrollState,
            title = searchWord.value,
            closeClicked = {
                goTGoEvent.invoke()
            },
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(topAppBar) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )

        LazyColumn(
            modifier = Modifier
                .constrainAs(searchResultView) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(topAppBar.bottom)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                },
            contentPadding = PaddingValues(
                top = 16.dp, bottom = 70.dp
            ),
            state = listScrollState
        ) {
            item {
                SearchEditView(
                    onImeAction = {
                        viewModel.loadSearchResult(searchWord.value)
                    },
                    searchTextChange = {
                        searchWord.value = it
                    },
                    currentSearchWord = searchWord
                )
            }

            // 최근 검색어 + 추천 키워드 화면
            item {
                if (searchWord.value.isEmpty() && searchResultItems == null) {
                    searchRecommendItems?.let {
                        RecommendKeyWordView(
                            searchItems = it,
                            itemClicked = { selectWord ->
                                searchWord.value = selectWord
                                viewModel.loadSearchResult(selectWord)
                            },
                            recentItemDelete = { deleteItem ->
                                viewModel.deleteRecentWord(deleteItem)
                            }
                        )
                    }
                }
            }


            item {
                searchResultItems?.let {
                    SearchResultView(
                        resultItems = it,
                        modifier = Modifier.animateItemPlacement(),
                    )
                }
            }
        }
    }
}
