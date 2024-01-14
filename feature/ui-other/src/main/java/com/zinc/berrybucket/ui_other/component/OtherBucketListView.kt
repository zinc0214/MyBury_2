package com.zinc.berrybucket.ui_other.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zinc.berrybucket.ui.design.theme.Gray1
import com.zinc.berrybucket.ui.design.theme.Gray10
import com.zinc.berrybucket.ui.design.theme.Gray3
import com.zinc.berrybucket.ui.design.theme.Gray5
import com.zinc.berrybucket.ui.design.theme.Gray6
import com.zinc.berrybucket.ui.presentation.component.MyText
import com.zinc.berrybucket.ui.util.dpToSp
import com.zinc.berrybucket.ui_other.screen.OtherBucketInfo
import com.zinc.berrybucket.util.shadow

@Composable
fun OtherBucketListView(
    modifier: Modifier,
    bucketList: List<OtherBucketInfo>,
    itemClicked: (Int) -> Unit,
) {

    var data by remember { mutableStateOf(bucketList) }

    LaunchedEffect(bucketList) {
        data = bucketList
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 140.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(data, key = { _, item -> item.bucketId }) { _, bucket ->
            OtherBucketView(
                info = bucket,
                itemClicked = { itemClicked.invoke(it) }
            )
        }
    }
}

@Composable
private fun OtherBucketView(
    info: OtherBucketInfo,
    itemClicked: (Int) -> Unit
) {

    val bucket = remember { mutableStateOf(info) }
    val backgroundColor = remember { mutableStateOf(if (bucket.value.isProgress) Gray1 else Gray3) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                color = Gray5.copy(alpha = 0.2f),
                offsetX = (0).dp,
                offsetY = (0).dp,
                blurRadius = 4.dp,
            )
            .clip(RoundedCornerShape(4.dp))
            .clickable { itemClicked(bucket.value.bucketId) }
            .background(
                color = backgroundColor.value,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        TitleTextView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 21.dp),
            title = bucket.value.title,
            progress = bucket.value.isProgress
        )
    }
}

@Composable
private fun TitleTextView(
    modifier: Modifier,
    title: String, progress: Boolean
) {
    var currentTitle by remember { mutableStateOf(title) }
    var currentProgress by remember { mutableStateOf(progress) }

    LaunchedEffect(title) {
        currentTitle = title
    }

    LaunchedEffect(progress) {
        currentProgress = progress
    }

    MyText(
        modifier = modifier,
        text = currentTitle,
        color = if (currentProgress) Gray10 else Gray6,
        fontSize = dpToSp(14.dp),
    )
}


@Composable
@Preview
private fun OtherBucketPreview() {
    val list = buildList {
        add(
            OtherBucketInfo(
                title = "테스트1", bucketId = 0, isProgress = false
            )
        )
    }
    OtherBucketListView(modifier = Modifier, bucketList = list, itemClicked = {})
}
