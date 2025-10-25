package com.example.kingburguer.compose.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    loading: Boolean = false,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            enabled = enabled && !loading,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (!loading) {
                Text(text.uppercase())
            }
        }

        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp)
            )
        }

    }


}


@Preview
@Composable
fun KingButtonPreview() {
    KingBurguerTheme(dynamicColor = false) {
        Column {
            KingButton(
                text ="Ola Mundo",
                enabled = true
            ) {}
            KingButton(
                text = "Ola Mundo",
                enabled = false
            ) {}
            KingButton(
                text = "Ola Mundo",
                loading = true
            ) {}
            KingButton(
                text = "Ola Mundo",
                enabled = false
            ) {}
        }
    }
}