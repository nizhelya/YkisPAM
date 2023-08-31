package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.reply.ui.components.ReplySearchBar


@Composable
fun ApartmentList(
    apartments: List<ApartmentEntity>,
    popUpScreean: (String) -> Unit,
    apartmentLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    viewModel:ApartmentViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier,
        state = apartmentLazyListState
    ) {
        item {
            ReplySearchBar(modifier = Modifier.fillMaxWidth())
        }
        items(items = apartments, key = { it.addressId }) {
            ApartmentListItem(
                apartment = it,
                popUpScreean = {},
                onAppartmentChange = {
                    viewModel.getFlatFromCache(it.addressId, popUpScreean)
                })
        }
    }
}