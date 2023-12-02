package com.zinc.berrybucket.ui.presentation.detail.component.mention

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.zinc.berrybucket.ui.presentation.detail.model.OpenDetailEditTextViewEvent
import com.zinc.berrybucket.ui.presentation.detail.model.TaggedTextInfo

@Composable
fun CommentEditTextView2(
    originText: String,
    newTaggedInfo: TaggedTextInfo? = null,
    commentEvent: (OpenDetailEditTextViewEvent) -> Unit
) {
    AndroidView(modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            CommentEditTextAndroidView(
                context,
                originText = originText,
                commentEvent = commentEvent
            )
        }, update = {
            it.updateText(originText, newTaggedInfo = newTaggedInfo)
        })
}