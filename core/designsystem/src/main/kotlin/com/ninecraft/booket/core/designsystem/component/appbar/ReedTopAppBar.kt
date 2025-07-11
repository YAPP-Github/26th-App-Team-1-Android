package com.ninecraft.booket.core.designsystem.component.appbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ninecraft.booket.core.designsystem.R
import com.ninecraft.booket.core.designsystem.theme.ReedTheme
import com.ninecraft.booket.core.designsystem.theme.White

@Composable
fun ReedTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    @DrawableRes startIconRes: Int? = null,
    startIconDescription: String = "",
    startIconOnClick: () -> Unit = {},
    @DrawableRes endIconRes: Int? = null,
    endIconDescription: String = "",
    endIconOnClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(color = White),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (startIconRes != null) {
            IconButton(
                onClick = { startIconOnClick() },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(72.dp),
            ) {
                Icon(
                    painter = painterResource(id = startIconRes),
                    contentDescription = startIconDescription,
                )
            }
        } else {
            Spacer(modifier = Modifier.width(72.dp))
        }

        Text(
            text = title,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            style = ReedTheme.typography.headline2SemiBold,
        )

        if (endIconRes != null) {
            IconButton(
                onClick = { endIconOnClick() },
                modifier = Modifier
                    .fillMaxHeight()
                    .width(72.dp),
            ) {
                Icon(
                    painter = painterResource(id = endIconRes),
                    contentDescription = endIconDescription,
                )
            }
        } else {
            Spacer(modifier = Modifier.width(72.dp))
        }
    }
}

@Composable
fun ReedBackTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackClick: () -> Unit = {},
) {
    ReedTopAppBar(
        modifier = modifier,
        title = title,
        startIconRes = R.drawable.ic_chevron_left,
        startIconDescription = "Back",
        startIconOnClick = onBackClick,
    )
}

@Composable
fun ReedCloseTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onClose: () -> Unit = {},
) {
    ReedTopAppBar(
        modifier = modifier,
        title = title,
        endIconRes = R.drawable.ic_close,
        endIconDescription = "Close",
        endIconOnClick = onClose,
    )
}

@Preview
@Composable
private fun ReedTopAppBarPreview() {
    ReedTheme {
        ReedTopAppBar(
            title = "title",
        )
    }
}

@Preview
@Composable
private fun ReedBackTopAppBarPreview() {
    ReedTheme {
        ReedBackTopAppBar(
            title = "title",
        )
    }
}

@Preview
@Composable
private fun ReedCloseTopAppBarPreview() {
    ReedTheme {
        ReedCloseTopAppBar(
            title = "title",
        )
    }
}
