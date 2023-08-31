package com.ykis.ykispam.core

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.makeText
import androidx.window.layout.FoldingFeature
import com.ykis.ykispam.core.Constants.TAG
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

class Utils {
    companion object {
        fun print(e: Exception) = Log.e(TAG, e.stackTraceToString())

        fun showMessage(
            context: Context,
            message: String?
        ) = makeText(context, message, LENGTH_LONG).show()
    }
}

/**
 * Information about the posture of the device
 */
