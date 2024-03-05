package com.ykis.ykispam.ui.screens.meter

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.layout.DisplayFeature
import com.ykis.ykispam.core.ext.isTrue
import com.ykis.ykispam.domain.meter.heat.meter.HeatMeterEntity
import com.ykis.ykispam.domain.meter.water.meter.WaterMeterEntity
import com.ykis.ykispam.domain.meter.water.reading.WaterReadingEntity
import com.ykis.ykispam.ui.BaseUIState
import com.ykis.ykispam.ui.components.BaseDualPanelContent
import com.ykis.ykispam.ui.components.DetailPanel
import com.ykis.ykispam.ui.components.EmptyDetail
import com.ykis.ykispam.ui.components.appbars.DetailAppBar
import com.ykis.ykispam.ui.navigation.ContentDetail
import com.ykis.ykispam.ui.navigation.ContentType
import com.ykis.ykispam.ui.navigation.NavigationType
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterDetail
import com.ykis.ykispam.ui.screens.meter.heat.HeatMeterState
import com.ykis.ykispam.ui.screens.meter.heat.reading.HeatReadings
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterDetail
import com.ykis.ykispam.ui.screens.meter.water.WaterMeterState
import com.ykis.ykispam.ui.screens.meter.water.reading.WaterReadings
import com.ykis.ykispam.ui.theme.YkisPAMTheme

@Composable
fun MainMeterScreen(
    modifier: Modifier = Modifier,
    viewModel: MeterViewModel = hiltViewModel(),
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick: () -> Unit,
    contentType: ContentType,
    displayFeature: List<DisplayFeature>
) {

    var selectedTab by rememberSaveable{
        mutableIntStateOf(0)
    }
    val waterMeterState by viewModel.waterMeterState.collectAsStateWithLifecycle()
    val heatMeterState by viewModel.heatMeterState.collectAsStateWithLifecycle()
    val showDetail by viewModel.showDetail.collectAsStateWithLifecycle()
    val contentDetail by viewModel.contentDetail.collectAsStateWithLifecycle()


    if (contentType == ContentType.DUAL_PANE) {
        BaseDualPanelContent(
            displayFeatures = displayFeature,
            firstScreen = {
                MeterListScreen(
                    baseUIState = baseUIState,
                    navigationType = navigationType,
                    onDrawerClick = onDrawerClick,
                    viewModel = viewModel,
                    onWaterMeterClick = { waterMeter ->
                        viewModel.setWaterMeterDetail(
                            waterMeterEntity = waterMeter
                        )
                    },
                    onHeatMeterClick = { heatMeter -> viewModel.setHeatMeterDetail(heatMeterEntity = heatMeter) },
                    waterMeterState = waterMeterState,
                    heatMeterState = heatMeterState,
                    selectedTab = selectedTab,
                    onTabClick = {selectedTab=it}
                )
            },
            secondScreen = {
                DetailPanel(
                    baseUIState = baseUIState,
                    contentType = contentType,
                    contentDetail = contentDetail,
                    onBackPressed = {
                        when (contentDetail) {
                            ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                            ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                            else -> viewModel.closeContentDetail()
                        }
                    },
                    showDetail = showDetail,
                    onActionButtonClick = {
                        if (contentDetail == ContentDetail.WATER_METER) {
                            viewModel.setContentDetail(ContentDetail.WATER_READINGS)
                        } else viewModel.setContentDetail(ContentDetail.HEAT_READINGS)
                    }
                ) {
                    MeterDetailContent(
                        baseUIState = baseUIState,
                        contentDetail = contentDetail,
                        waterMeterState = waterMeterState,
                        viewModel = viewModel,
                        heatMeterState = heatMeterState
                    )
                }
            }
        )
    } else SinglePanelMeter(
        contentDetail = contentDetail,
        baseUIState = baseUIState,
        navigationType = navigationType,
        onDrawerClick = onDrawerClick,
        showDetail = showDetail,
        viewModel = viewModel,
        contentType = contentType,
        waterMeterState = waterMeterState,
        heatMeterState = heatMeterState,
        onWaterMeterClick = { waterMeter -> viewModel.setWaterMeterDetail(waterMeterEntity = waterMeter) },
        onHeatMeterClick = { heatMeter -> viewModel.setHeatMeterDetail(heatMeterEntity = heatMeter) },
        selectedTab = selectedTab,
        onTabClick = {selectedTab=it},
    )
}


@Composable
fun SinglePanelMeter(
    modifier: Modifier = Modifier,
    contentDetail: ContentDetail,
    baseUIState: BaseUIState,
    navigationType: NavigationType,
    onDrawerClick: () -> Unit,
    showDetail: Boolean,
    viewModel: MeterViewModel,
    contentType: ContentType,
    waterMeterState: WaterMeterState,
    heatMeterState: HeatMeterState,
    selectedTab:Int,
    onTabClick:(Int)->Unit,
    onWaterMeterClick: (WaterMeterEntity) -> Unit,
    onHeatMeterClick: (HeatMeterEntity) -> Unit,
) {
    if(!showDetail){
        MeterListScreen(
            baseUIState = baseUIState,
            navigationType = navigationType,
            onDrawerClick = onDrawerClick,
            viewModel = viewModel,
            onHeatMeterClick = onHeatMeterClick,
            onWaterMeterClick = onWaterMeterClick,
            waterMeterState = waterMeterState,
            heatMeterState = heatMeterState,
            selectedTab = selectedTab,
            onTabClick = onTabClick
        )
    }

    AnimatedVisibility(visible = showDetail) {
        BackHandler {
            when (contentDetail) {
                ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                else -> viewModel.closeContentDetail()
            }
        }
        Column(
            modifier = modifier.background(
                MaterialTheme.colorScheme.background
            )
        ) {
            DetailAppBar(
                contentType = contentType,
                baseUIState = baseUIState,
                contentDetail = contentDetail,
                onActionButtonClick = {
                    if (contentDetail == ContentDetail.WATER_METER) {
                        viewModel.setContentDetail(ContentDetail.WATER_READINGS)
                    } else viewModel.setContentDetail(ContentDetail.HEAT_READINGS)
                },
                onBackPressed = {
                    when (contentDetail) {
                        ContentDetail.WATER_READINGS -> viewModel.setContentDetail(ContentDetail.WATER_METER)
                        ContentDetail.HEAT_READINGS -> viewModel.setContentDetail(ContentDetail.HEAT_METER)
                        else -> viewModel.closeContentDetail()
                    }
                }
            )
            MeterDetailContent(
                baseUIState = baseUIState,
                contentDetail = contentDetail,
                waterMeterState = waterMeterState,
                viewModel = viewModel,
                heatMeterState =  heatMeterState
            )
        }
    }


}

@Composable
fun MeterDetailContent(
    baseUIState: BaseUIState,
    contentDetail: ContentDetail,
    waterMeterState: WaterMeterState,
    viewModel: MeterViewModel,
    heatMeterState: HeatMeterState
) {
    Crossfade(targetState = contentDetail, label = "") { targetState ->
        when (targetState) {
            ContentDetail.WATER_METER -> {
                WaterMeterDetail(
                    waterMeterEntity = waterMeterState.selectedWaterMeter,
                    baseUIState = baseUIState,
                    getLastReading = {
                        viewModel.getLastWaterReading(
                            vodomerId = waterMeterState.selectedWaterMeter.vodomerId,
                            uid = baseUIState.uid!!
                        )
                    },
                    lastReading = waterMeterState.lastWaterReading,
                    isWorking =!waterMeterState.selectedWaterMeter.spisan.isTrue()&& !waterMeterState.selectedWaterMeter.out.isTrue(),
                    isLastReadingLoading = waterMeterState.isLastReadingLoading,
                    newWaterReading = waterMeterState.newWaterReading,
                    onNewReadingChange = {newValue -> viewModel.onNewWaterReadingChange(newValue.filter { it.isDigit() })},
                    addReading = {viewModel.addWaterReading(
                        uid = baseUIState.uid.toString(),
                        currentValue = waterMeterState.lastWaterReading.current,
                        newValue = waterMeterState.newWaterReading.toInt(),
                        vodomerId = waterMeterState.selectedWaterMeter.vodomerId
                    )},
                    navigateToReadings = {
                        viewModel.setContentDetail(ContentDetail.WATER_READINGS)
                    },
                    deleteReading = {
                        viewModel.deleteLastWaterReading(
                            waterMeterState.lastWaterReading.vodomerId,
                            waterMeterState.lastWaterReading.pokId,
                            baseUIState.uid.toString()
                        )
                    }
                )
            }

            ContentDetail.HEAT_METER -> {
                HeatMeterDetail(
                    heatMeterEntity = heatMeterState.selectedHeatMeter,
                    baseUIState = baseUIState,
                    getLastHeatReading = {
                        viewModel.getLastHeatReading(baseUIState.uid!!, heatMeterState.selectedHeatMeter.teplomerId)
                    },
                    lastHeatReading = heatMeterState.lastHeatReading,
                    isWorking = !heatMeterState.selectedHeatMeter.spisan.isTrue()&& !heatMeterState.selectedHeatMeter.out.isTrue(),
                    navigateToReadings = {
                        viewModel.setContentDetail(ContentDetail.HEAT_READINGS)
                    },
                    newHeatReading = heatMeterState.newHeatReading,
                    onNewReadingChange = {
                            newValue -> viewModel.onNewHeatReadingChange(newValue.filter { it.isDefined() })
                    },
                    addReading = {viewModel.addHeatReading(
                        uid = baseUIState.uid.toString(),
                        teplomerId = heatMeterState.selectedHeatMeter.teplomerId,
                        currentValue = heatMeterState.lastHeatReading.current,
                        newValue = heatMeterState.newHeatReading.toDouble()
                    )},
                    deleteReading ={
                        viewModel.deleteLastHeatReading(
                            readingId = heatMeterState.lastHeatReading.pokId,
                            teplomerId = heatMeterState.selectedHeatMeter.teplomerId,
                            uid = baseUIState.uid.toString()
                        )
                    }
                )
            }

            ContentDetail.WATER_READINGS -> {
                WaterReadings(
                    baseUIState = baseUIState,
                    waterMeterState = waterMeterState,
                    getWaterReadings = {
                        viewModel.getWaterReadings(
                            baseUIState.uid!!,
                            waterMeterState.selectedWaterMeter.vodomerId
                        )
                    }
                )
            }

            ContentDetail.HEAT_READINGS -> {
                HeatReadings(
                    baseUIState = baseUIState,
                    heatMeterState = heatMeterState,
                    getHeatReadings = {viewModel.getHeatReadings(baseUIState.uid.toString() , heatMeterState.selectedHeatMeter.teplomerId)}
                )
            }

            else -> EmptyDetail()
        }
    }
}


@Preview
@Composable
private fun PreviewMainMeterScreen() {
    YkisPAMTheme {
       WaterMeterDetail(
           baseUIState = BaseUIState(),
           waterMeterEntity = WaterMeterEntity(),
           lastReading = WaterReadingEntity(),
           getLastReading = { /*TODO*/ },
           onNewReadingChange = {},
           newWaterReading = "124",
           addReading = { /*TODO*/ },
           deleteReading = { /*TODO*/ },
           isWorking = true,
           navigateToReadings = { /*TODO*/ },
           isLastReadingLoading =  false
       )
    }
}