package com.ykis.ykispam.firebase.screens.sign_up.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R.color as AppColor
import com.ykis.ykispam.R.string as AppText


@Composable
fun AgreementChekBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier,

    ) {
    Card(
        modifier = Modifier
            .widthIn(0.dp, 480.dp)
            .padding(4.dp),
//        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background),

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
//            modifier = Modifier.widthIn(0.dp, 480.dp)

        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = onCheckedChange,
//                modifier = Modifier.padding(8.dp, end=8.dp),
//                colors = CheckboxDefaults.colors(
//                    checkedColor = colorResource(AppColor.white)
//                )
            )
            Text(
                text = if (checked) {
                    stringResource(AppText.agreement_check_checked)
                } else {
                    stringResource(AppText.agreement_check)
                },
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 2.dp, end = 16.dp),
            )

        }

    }
}