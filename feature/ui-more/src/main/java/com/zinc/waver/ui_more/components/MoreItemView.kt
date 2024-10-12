package com.zinc.waver.ui_more.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zinc.waver.ui.design.theme.Gray1
import com.zinc.waver.ui.design.theme.Gray10
import com.zinc.waver.ui.design.theme.Gray3
import com.zinc.waver.ui.presentation.component.MyText
import com.zinc.waver.ui.util.dpToSp
import com.zinc.waver.ui_more.R
import com.zinc.waver.ui_more.models.MoreItemType
import com.zinc.waver.ui_more.models.UIMoreItemData

@Composable
internal fun MoreItemsView(itemClicked: (MoreItemType) -> Unit) {
    val itemList = buildList {
        add(
            UIMoreItemData(
                text = stringResource(R.string.moreMenuAlarm),
                type = MoreItemType.ALARM
            )
        )
        add(
            UIMoreItemData(
                text = stringResource(R.string.moreMenuBlock),
                type = MoreItemType.BLOCK
            )
        )
        add(
            UIMoreItemData(
                text = stringResource(R.string.moreMenuCs),
                type = MoreItemType.QNA
            )
        )
        add(
            UIMoreItemData(
                text = stringResource(R.string.moreMenuAppInfo),
                type = MoreItemType.APP_INFO
            )
        )
        add(
            UIMoreItemData(
                text = stringResource(R.string.moreMenuLogout),
                type = MoreItemType.LOGOUT
            )
        )
    }
    Column(modifier = Modifier.fillMaxWidth()) {
        itemList.forEach {
            MoreItemView(it) { type ->
                itemClicked(type)
            }
        }
    }
}

@Composable
private fun MoreItemView(
    itemData: UIMoreItemData,
    itemClicked: (MoreItemType) -> Unit
) {

    MoreItemView(
        itemTitle = itemData.text,
        itemClicked = {
            itemClicked(itemData.type)
        })

}

@Composable
internal fun MoreItemView(
    itemTitle: String,
    itemClicked: () -> Unit

) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Button(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(0.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Gray1, contentColor = Gray1),
            contentPadding = PaddingValues(0.dp),
            onClick = {
                itemClicked()
            },
            elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp),
        ) {
            MyText(
                text = itemTitle,
                fontSize = dpToSp(dp = 16.dp),
                color = Gray10,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 17.5.dp, horizontal = 28.dp)
            )
        }

        Divider(color = Gray3, thickness = 1.dp)
    }
}

@Preview
@Composable
private fun MoreItemPreview() {
    MoreItemView(
        itemData = UIMoreItemData(text = "알람설정", type = MoreItemType.ALARM),
        itemClicked = {})
}