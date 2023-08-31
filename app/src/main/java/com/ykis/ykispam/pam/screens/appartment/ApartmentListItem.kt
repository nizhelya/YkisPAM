package com.ykis.ykispam.pam.screens.appartment

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ykis.ykispam.R
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity


@Composable
fun ApartmentListItem(
    apartment: ApartmentEntity,
    isSelectable: Boolean = false,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    popUpScreean: (String) -> Unit,
    onAppartmentChange: () -> Unit,
    viewModel: ApartmentViewModel = hiltViewModel(),
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val semanticsModifier =
        if (isSelectable)
            modifier
                .padding(horizontal = 16.dp, vertical = 4.dp)
                .semantics { selected = isSelected }
        else modifier.padding(horizontal = 16.dp, vertical = 4.dp)

    Card(
        modifier = Modifier.padding(8.dp, 0.dp, 8.dp, 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (expanded) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.surfaceVariant
        )
        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onAppartmentChange() }
//                    .padding(4.dp)

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column(
                        modifier = Modifier
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = if (expanded) {
                            Alignment.CenterHorizontally
                        } else {
                            Alignment.Start
                        }


                    ) {
                        Text(
                            text = apartment.address,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge,
                            color = if (expanded) {
                                Color(0xFFFFB945)
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },

                            )
                    }
                    IconButton(
                        onClick = { expanded = !expanded }
                    ) {
                        Icon(
                            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expanded) {
                                stringResource(R.string.show_less)
                            } else {
                                stringResource(R.string.show_more)
                            }
                        )
                    }

                }
                if (expanded) {

//                    Row(
//                        modifier = Modifier
//                            .padding(all = 12.dp)
//
//                    ) {
//                        LabelField(
//                            appartment.fio,
//                            R.string.employer_text,
//                            R.string.employer_none,
//                            modifier = Modifier
//                        )
//                    }

                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 16.dp, end = 12.dp, bottom = 8.dp)

                    ) {
                        Card(
                            modifier = Modifier,
                            colors= CardDefaults.cardColors(MaterialTheme.colorScheme.surface),
                            elevation = CardDefaults.cardElevation(10.dp),
                            shape = RoundedCornerShape(5.dp)
                        ) {

                            Row(
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        top = 16.dp,
                                        end = 16.dp,
                                        bottom = 16.dp
                                    )

                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Row(//Ф.И.О.
                                        modifier = Modifier
                                            .padding(
                                                start = 0.dp,
                                                top = 4.dp,
                                                end = 0.dp,
                                                bottom = 4.dp
                                            )

                                    ) {
                                        Text(
                                            text = stringResource(R.string.employer_text),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color(0xFFFFB945)
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 8.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
//                                        horizontalArrangement = Arrangement.Center


                                    ) {
                                        Text(
                                            text = apartment.nanim,
//                                            color = MaterialTheme.colors.primary,
                                            style = MaterialTheme.typography.bodyMedium,
                                            modifier = Modifier.padding(start = 8.dp),
//                                            textAlign = TextAlign.Center
                                        )

                                    }
                                    Row(
//Площадь заголовок
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 8.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,

                                        ) {
                                        Text(
                                            text = stringResource(R.string.area_flat),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color(0xFFFFB945)

                                        )
                                    }
                                    Row(//Площадь данные
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(0.25f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start


                                        ) {

                                            Text(
                                                text = stringResource(R.string.area_full),
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.primary,

                                                )
                                            Text(
                                                text = apartment.areaFull.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                style = MaterialTheme.typography.bodyMedium,

                                                )
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.25f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {

                                            Text(
                                                text = stringResource(R.string.area_life),
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.primary,

                                                )
                                            Text(
                                                text = apartment.areaLife.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.25f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {

                                            Text(
                                                text = stringResource(R.string.area_extra),
                                                color = MaterialTheme.colorScheme.primary,
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                            Text(
                                                text = apartment.areaDop.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.25f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {

                                            Text(
                                                text = stringResource(R.string.area_otopl),
                                                color = MaterialTheme.colorScheme.primary,
                                                style = MaterialTheme.typography.titleSmall
                                            )
                                            Text(
                                                text = apartment.areaOtopl.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                style = MaterialTheme.typography.bodyMedium,
                                            )
                                        }
                                    }
                                    Row(//Состав семьи заголовок
                                        modifier = Modifier
                                            .padding(
                                                start = 2.dp,
                                                top = 8.dp,
                                                end = 0.dp,
                                                bottom = 4.dp
                                            )

                                    ) {
                                        Text(
                                            text = stringResource(R.string.compound_text),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color(0xFFFFB945)

                                        )
                                    }
                                    Row(//Состав семьи данные
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(0.33f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.tenant_text),
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.titleSmall
                                                )
                                                Text(
                                                    text = apartment.tenant.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    textAlign = TextAlign.Center,
                                                )
                                            }
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.33f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.podnan_text),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    style = MaterialTheme.typography.titleSmall
                                                )
                                                Text(
                                                    text = apartment.podnan.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.33f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.absent_text),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    style = MaterialTheme.typography.titleSmall
                                                )
                                                Text(
                                                    text = apartment.absent.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        }

                                    }
                                    Row(//БТИ заголовок
                                        modifier = Modifier
                                            .padding(
                                                start = 2.dp,
                                                top = 8.dp,
                                                end = 0.dp,
                                                bottom = 4.dp
                                            )

                                    ) {
                                        Text(
                                            text = stringResource(R.string.data_bti),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color(0xFFFFB945)

                                        )


                                    }
                                    Row(//БТИ данные 1 ряд
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(0.33f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {

                                                Text(
                                                    text = stringResource(R.string.rooms),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    style = MaterialTheme.typography.titleSmall
                                                )
                                                Text(
                                                    text = apartment.room.toString(),
//                                                color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.33f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start

                                        ) {
                                            Row(//БТИ данные 2 ряд
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.private_text),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    style = MaterialTheme.typography.titleSmall
                                                )
                                                Text(
                                                    text = if (apartment.privat.toString() == "1") {
                                                        stringResource(R.string.private_ok)
                                                    } else {
                                                        stringResource(R.string.private_no)
                                                    },
//                                                color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        }
                                        Column(
                                            modifier = Modifier.weight(0.33f),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start
                                        ) {
                                            Row(//БТИ данные 2 ряд
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.elevator_text),
                                                    modifier = Modifier.padding(end = 4.dp),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.titleSmall
                                                )
                                                Text(
                                                    text = if (apartment.lift.toString() == "1") {
                                                        stringResource(R.string.private_ok)
                                                    } else {
                                                        stringResource(R.string.private_no)
                                                    },
//                                                color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        }
                                    }
                                    Row(//БТИ данные 2 ряд
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start


                                        ) {
                                            Row(//БТИ данные 2 ряд
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {


                                                Text(
                                                    text = stringResource(R.string.order_text),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    modifier = Modifier.padding(end = 4.dp)
                                                )
                                                Text(
                                                    text = apartment.order,
//                                                    color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }

                                        }

                                    }
                                    Row(//БТИ данные 3 ряд
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start


                                        ) {
                                            Row(//БТИ данные 2 ряд
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {


                                                Text(
                                                    text = stringResource(R.string.data_order),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    modifier = Modifier.padding(end = 4.dp)
                                                )
                                                Text(
                                                    text = apartment.dataOrder,
//                                                    color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }
                                        }

                                    }
                                    Row(//контактные данные заголовок
                                        modifier = Modifier
                                            .padding(
                                                start = 2.dp,
                                                top = 8.dp,
                                                end = 0.dp,
                                                bottom = 4.dp
                                            )

                                    ) {
                                        Text(
                                            text = stringResource(R.string.contacts),
                                            style = MaterialTheme.typography.titleSmall,
                                            color = Color(0xFFFFB945)

                                        )


                                    }

                                    Row(//контактные данные 1 ряд
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start


                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {


                                                Text(
                                                    text = stringResource(R.string.phone_text),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    modifier = Modifier.padding(end = 4.dp)
                                                )
                                                Text(
                                                    text = apartment.phone,
//                                                    color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }

                                        }

                                    }
                                    Row(//контактные данные 1 ряд
                                        modifier = Modifier
                                            .fillMaxWidth(1f)
                                            .padding(
                                                start = 4.dp,
                                                top = 0.dp,
                                                end = 4.dp,
                                                bottom = 4.dp
                                            ),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.Center

                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .weight(1f)
                                                .padding(start = 8.dp),
                                            verticalArrangement = Arrangement.Center,
                                            horizontalAlignment = Alignment.Start


                                        ) {

                                            Row(//контактные данные 2 ряд
                                                modifier = Modifier
                                                    .fillMaxWidth(1f),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.Start
                                            ) {


                                                Text(
                                                    text = stringResource(R.string.email_colon),
                                                    color = MaterialTheme.colorScheme.primary,
                                                    style = MaterialTheme.typography.titleSmall,
                                                    modifier = Modifier.padding(end = 4.dp)
                                                )
                                                Text(
                                                    text = apartment.email,
//                                                    color = MaterialTheme.colors.primary,
                                                    style = MaterialTheme.typography.bodyMedium,
                                                )
                                            }

                                        }

                                    }

                                }
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(start = 12.dp, top = 0.dp, end = 12.dp, bottom = 8.dp)

                    ) {
                        DeleteAppartment(apartment.address) {
                            viewModel.deleteFlat(apartment.addressId, popUpScreean)
                        }

                    }
                }
            }
        }

    }
}