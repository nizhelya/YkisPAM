package com.ykis.ykispam.pam.screens.bti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.BaseUIState
import com.ykis.ykispam.R
import com.ykis.ykispam.core.composable.BasicImageButton
import com.ykis.ykispam.core.composable.EmailField
import com.ykis.ykispam.core.composable.PhoneField
import com.ykis.ykispam.navigation.ContentDetail
import com.ykis.ykispam.navigation.ContentType
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.screens.appbars.DetailAppBar
import com.ykis.ykispam.theme.YkisPAMTheme


@ExperimentalMaterial3Api
@Composable
fun BtiPanelContent(
    modifier: Modifier = Modifier,
    contentType: ContentType,
    contentDetail: ContentDetail,
    baseUIState: BaseUIState,
    onBackPressed: () -> Unit,
    viewModel: BtiViewModel = hiltViewModel(),
) {

    LaunchedEffect(key1 = baseUIState.apartment) {
        viewModel.initialize(baseUIState.apartment)
    }
    val contactUiState by viewModel.contactUiState

    BtiContent(
        modifier = modifier,
        baseUIState = baseUIState,
        contentType = contentType,
        contentDetail = contentDetail,
        contactUiState = contactUiState,
        onBackPressed = onBackPressed,
        onEmailChange = viewModel::onEmailChange,
        onPhoneChange = viewModel::onPhoneChange,
        onUpdateBti = { viewModel.onUpdateBti() },

        )
}

    @OptIn(ExperimentalComposeUiApi::class)
    @ExperimentalMaterial3Api
    @Composable
    fun BtiContent(
        modifier: Modifier = Modifier,
        baseUIState: BaseUIState,
        contentType: ContentType,
        contentDetail: ContentDetail,
        contactUiState: ContactUIState,
        onBackPressed: () -> Unit,
        onEmailChange: (String) -> Unit,
        onPhoneChange: (String) -> Unit,
        onUpdateBti: () -> Unit,
    ) {
        val keyboard = LocalSoftwareKeyboardController.current
        Column(
        modifier = Modifier
            .padding(4.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        DetailAppBar(modifier, contentType, baseUIState, contentDetail) {
            onBackPressed()
        }


        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.employer_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline

                    )


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 4.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier
                            .padding(end = 4.dp),

                        imageVector = Icons.TwoTone.Person,
                        contentDescription = "Info",
                        tint = MaterialTheme.colorScheme.outline
                    )
                    Text(
                        text = baseUIState.apartment.nanim,
                        style = MaterialTheme.typography.bodyLarge

                    )

                }
            }
        }

        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.compound_text),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)

                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.tenant_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.tenant.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.podnan_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.podnan.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.absent_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.absent.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }


                }
            }
        }

        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.area_flat),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)

                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_full),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.areaFull.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_life),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.areaLife.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.5f),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_extra),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.areaDop.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.5f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.area_otopl),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.areaOtopl.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }

                }
            }
        }
        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.data_bti),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)

                    )

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(0.33f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.rooms),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.room.toString(),
                                style = MaterialTheme.typography.bodyLarge,

                                )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.33f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.private_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = if (baseUIState.apartment.privat.toString() == "1") {
                                    stringResource(R.string.private_ok)
                                } else {
                                    stringResource(R.string.private_no)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                    Column(
                        modifier = Modifier.weight(0.33f)
                    ) {
                        Row(//Ф.И.О.
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.elevator_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = if (baseUIState.apartment.lift.toString() == "1") {
                                    stringResource(R.string.private_ok)
                                } else {
                                    stringResource(R.string.private_no)
                                },
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.order_text),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline

                            )
                            Text(
                                text = baseUIState.apartment.order,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(//площадь
                            modifier = Modifier
                                .padding(
                                    start = 4.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(
                                        end = 8.dp,
                                    ),
                                text = stringResource(R.string.data_order),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.outline
                            )
                            Text(
                                text = baseUIState.apartment.dataOrder,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }

                }
            }
        }
        Card(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.contacts),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier
                            .padding(4.dp)
                            .weight(1f),

                        )
                    BasicImageButton(
                        R.string.update,
                        R.drawable.ic_check,
                        modifier = Modifier,
                    )
                    {
                        keyboard?.hide()
                        onUpdateBti()
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            PhoneField(contactUiState.phone, onPhoneChange, modifier)

                        }
                    }


                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(
                                    start = 0.dp,
                                    top = 4.dp,
                                    end = 4.dp,
                                    bottom = 4.dp
                                ),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically

                        ) {

                            EmailField(contactUiState.email, onEmailChange, modifier)

                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@ExperimentalMaterial3Api
@Composable
fun BtiContentPreview() {
    YkisPAMTheme {
        BtiContent(
            modifier = Modifier,
            baseUIState = BaseUIState(apartment = ApartmentEntity()),
            contentType = ContentType.SINGLE_PANE,
            contentDetail = ContentDetail.BTI,
            contactUiState = ContactUIState(addressId = 6314, address = "Гр.Десанту 21 кв.71", email = "nizelskiy.sergey@gmail.com"),
            onBackPressed = { },
            onEmailChange = { },
            onPhoneChange = { },
            onUpdateBti = {},
        )
    }
}