package com.ykis.ykispam.ui.screens.meter.heat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ykis.ykispam.R
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.ykispam.ui.components.LabelTextWithText
import com.ykis.ykispam.ui.theme.YkisPAMTheme


@Composable
fun HeatMeterItem(
    modifier: Modifier = Modifier,
    heatMeter : HeatMeterEntity
) {
    val statusText :String
    val alpha: Float
    when {
        heatMeter.spisan.isTrue() ->{
            statusText = stringResource(R.string.written_off)
            alpha =0.5f
        }
        heatMeter.out.isTrue() -> {
            statusText = stringResource(R.string.on_the_test)
            alpha =0.5f
        }
        else -> {
            statusText = stringResource(R.string.works)
            alpha = 1f
        }
    }
    OutlinedCard(modifier = modifier) {
        Column(
            modifier = Modifier.alpha(alpha)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(48.dp),
                    painter = painterResource(id = R.drawable.ic_heat_meter),
                    contentDescription = null
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = heatMeter.model,
                        style = MaterialTheme.typography.titleLarge
                    )
                    LabelTextWithText(
                        labelText = stringResource(id = R.string.number_colon),
                        valueText = heatMeter.number
                    )
                    Text(
                        text = statusText
                    )

                }
                Icon(
                    modifier = Modifier.padding(end = 24.dp),
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null
                )
            }
        }
    }

}
@Preview(showBackground = true)
@Composable
private fun PreviewHeatMeter() {
    YkisPAMTheme {
        HeatMeterItem(
            heatMeter = HeatMeterEntity(
                model = "Heat Turbo 1343",
                number = "1332342342"
            )
        )
    }
}