package com.ykis.ykispam.ui.screens.service.list

import com.ykis.ykispam.domain.service.ServiceEntity

data class TotalDebtState(
    val totalDebt : ServiceEntity = ServiceEntity(),
    val isLoading: Boolean = true
)