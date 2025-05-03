package com.example.data.api.model

import android.util.Log
import com.example.data.R
import java.io.IOException
import java.net.UnknownHostException

fun handleError(throwable: Throwable): Int
{

    return when (throwable)
    {
        is UnknownHostException, is IOException ->
        {
            Log.e("Tag", "handleError:${throwable.message}")
            R.string.check_your_internet_connection
        }

        else ->
        {
            Log.e("Tag", "handleError:${throwable.message}")
            R.string.somthing_went_to_wrong
        }
    }
}