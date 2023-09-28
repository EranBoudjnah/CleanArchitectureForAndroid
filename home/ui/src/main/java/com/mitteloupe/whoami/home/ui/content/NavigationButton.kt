package com.mitteloupe.whoami.home.ui.content

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mitteloupe.whoami.home.ui.R

@Composable
fun NavigationButton(
    @DrawableRes iconResourceId: Int,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.tertiary),
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = iconResourceId),
            contentDescription = null,
            modifier = Modifier
                .width(36.dp)
                .height(32.dp)
                .padding(0.dp, 0.dp, 8.dp, 0.dp),
            colorFilter = ColorFilter.tint(
                color = MaterialTheme.colorScheme.onPrimary,
                blendMode = BlendMode.Modulate
            ),
            contentScale = ContentScale.Inside
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview
@Composable
private fun Preview() {
    NavigationButton(iconResourceId = R.drawable.icon_save, label = "Save", onClick = {})
}
