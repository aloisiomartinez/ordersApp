package com.example.kingburguer.compose.login

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun LoginScreen() {
    Surface(
        modifier = Modifier.fillMaxSize().padding(12.dp)
    ) {
        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(scrollState)
        ) {
            KingTextField(
                value = "todo",
                label = R.string.email,
                placeholder = R.string.hint_email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                modifier = Modifier.padding(10.dp)
            ) {

            }

            KingTextField(
                value = "todo",
                label = R.string.password,
                placeholder = R.string.hint_password,
                keyboardType = KeyboardType.Password,
                ofuscate = passwordHidden,
                imeAction = ImeAction.Done,
                modifier = Modifier.padding(10.dp),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            passwordHidden = !passwordHidden
                        }
                    ) {
                        val image = if (passwordHidden) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        }

                        val description = if (passwordHidden) {
                            stringResource(id = R.string.show_password)
                        } else {
                            stringResource(id = R.string.hide_password)
                        }

                        Icon(
                            imageVector = image,
                            contentDescription = description
                        )
                    }

                }
            ) {

            }

            OutlinedButton(
                onClick = {},
                enabled = true
            ) {
                Text(
                    stringResource(id = R.string.send)
                )
            }
        }
    }
}

@Composable
fun KingTextField(
    value: String,
    @StringRes label: Int,
    @StringRes placeholder: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    ofuscate: Boolean = false,
    modifier: Modifier = Modifier,
    trailingIcon: @Composable (() -> Unit) ?= null,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        onValueChange = onValueChange,
        value = value,
        maxLines = 1,
        label = {
            Text(stringResource(id = label))
        },
        placeholder = {
            Text(stringResource(id = placeholder))
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
fun LoginScreenPreview() {
    KingBurguerTheme {
        LoginScreen()
    }
}