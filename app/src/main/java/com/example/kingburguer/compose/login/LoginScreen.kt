package com.example.kingburguer.compose.login

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.kingburguer.R
import com.example.kingburguer.compose.login.component.KingButton
import com.example.kingburguer.compose.login.component.KingTextField
import com.example.kingburguer.compose.login.component.KingTextTitle
import com.example.kingburguer.ui.theme.KingBurguerTheme

@Composable
fun LoginScreen(
    contentPadding: PaddingValues
) {
    Surface(
        modifier = Modifier.fillMaxSize().padding(contentPadding)
    ) {
        val scrollState = rememberScrollState()
        var passwordHidden by remember { mutableStateOf(true) }

        Column(

        ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp)
        ) {

            KingTextTitle(text = stringResource(R.string.login))

            KingTextField(
                value = "todo",
                label = R.string.email,
                placeholder = R.string.hint_email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ) {

            }

            KingTextField(
                value = "todo",
                label = R.string.password,
                placeholder = R.string.hint_password,
                keyboardType = KeyboardType.Password,
                ofuscate = passwordHidden,
                imeAction = ImeAction.Done,
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

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = true,
                    onCheckedChange = {}
                )

                Text(stringResource(id = R.string.remember_me))

            }

            KingButton(
                text = stringResource(id = R.string.send),
                enabled = true
            ) {

            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.have_account))
                TextButton(
                    onClick = {}
                ) {
                    Text(stringResource(id = R.string.sign_up))

                }
            }
            }


            Image(
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.BottomCenter,
                painter = painterResource(R.drawable.cover3),
                contentDescription = stringResource(id = R.string.hamburguer)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    KingBurguerTheme {
        LoginScreen(
            contentPadding = PaddingValues()
        )
    }
}