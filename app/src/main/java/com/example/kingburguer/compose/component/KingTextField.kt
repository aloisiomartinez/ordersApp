package com.example.kingburguer.compose.component

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun KingTextField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    ofuscate: Boolean = false,
    error: String? = null,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit) ?= null,
    onValueChange: (String) -> Unit,
) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        onValueChange = onValueChange,
        value = value,
        maxLines = 1,
        label = {
            Text(stringResource(id = label))
        },
        placeholder = {
            Text(stringResource(id = placeholder))
        },
        isError = error != null,
        supportingText = {
            error?.let { msg ->
                Text (msg)
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        trailingIcon = trailingIcon,
        visualTransformation = if (ofuscate) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        }
    )

}

@Preview(showBackground = true)
@Composable
fun KingTextFieldPreview() {
    KingBurguerTheme {
        KingTextField(
            value = "todo",
            label = R.string.email,
            placeholder =  R.string.hint_email,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            error = "Erro de teste",
            ofuscate = TODO(),
            modifier = TODO(),
            trailingIcon = TODO(),
            onValueChange = TODO()
        )
    }
}