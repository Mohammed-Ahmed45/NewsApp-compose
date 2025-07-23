package com.mohamed.newsapp.utils

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mohamed.newsapp.R
import com.mohamed.newsapp.ui.ui.theme.Colors

@Composable
fun ErrorDialog(
    errorState: MutableIntState,
    onRetryClick: () -> Unit
)
{
    if (errorState.intValue != R.string.empty)
    {
        AlertDialog(

            textContentColor = Color.Black,
            onDismissRequest =
            { errorState.intValue = R.string.empty },
            confirmButton = {
                TextButton(
                    onClick = {
                        onRetryClick()
                        errorState.intValue = R.string.empty
                    }
                ) {
                    Text(stringResource(R.string.retry), color = Colors.Green)
                }
            }, text = {
                Text(text = stringResource(id = errorState.intValue))
            },
            dismissButton = {
                TextButton(
                    onClick = {

                        errorState.intValue = R.string.empty
                    }
                ) {
                    Text(stringResource(R.string.Cancel), color = Colors.Green)
                }
            }

        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun Error()
{
    val errorState =
            remember { mutableIntStateOf(R.string.check_your_internet_connection) }
    ErrorDialog(errorState = errorState) {}
}