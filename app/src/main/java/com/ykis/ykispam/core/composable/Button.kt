/*
Copyright 2022 Google LLC

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package com.ykis.ykispam.core.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter.State.Empty.painter

@Composable
fun BasicLinkButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
  TextButton(onClick = action, modifier = modifier) {
    Text(
      text = stringResource(text),
      color= MaterialTheme.colorScheme.onSurface,
      style = MaterialTheme.typography.titleSmall,
      textAlign = TextAlign.Center,
      fontStyle = FontStyle.Italic,
      textDecoration = TextDecoration.Underline

    )
  }
}
@Composable
fun BasicButton(@StringRes text: Int, modifier: Modifier, action: () -> Unit) {
  Button(
    onClick = action,
    modifier = modifier,
    colors =
    ButtonDefaults.buttonColors(
      containerColor = MaterialTheme.colorScheme.primary,
      contentColor = MaterialTheme.colorScheme.onPrimary
    )
  ) {
    Text(text = stringResource(text), fontSize = 16.sp)
  }
}

@Composable
fun BasicImageButton(@StringRes text: Int,@DrawableRes img:Int, modifier: Modifier, action: () -> Unit) {
  Button(
    onClick = action,
    modifier = modifier,
    shape = RoundedCornerShape(20.dp),
    colors =
    ButtonDefaults.buttonColors(
      containerColor = Color(0xFF3A4C2B),
      contentColor = Color(0xFFFFB945),
    )
  ) {

    Text(text = stringResource(text), fontSize = 16.sp,modifier = Modifier.padding(end = 2.dp))
    Image(painter = painterResource(img),
    contentDescription = null
    )
  }
}

@Composable
fun DialogConfirmButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors =
      ButtonDefaults.buttonColors(
        containerColor = Color(0xFF3A4C2B),
        contentColor = Color(0xFFFFB945)
      )
  ) {
    Text(text = stringResource(text))
  }
}

@Composable
fun DialogCancelButton(@StringRes text: Int, action: () -> Unit) {
  Button(
    onClick = action,
    colors =
      ButtonDefaults.buttonColors(
        containerColor = Color(0xFF516440),
        contentColor = Color(0xFFFFB945)
      )
  ) {
    Text(text = stringResource(text))
  }
}
