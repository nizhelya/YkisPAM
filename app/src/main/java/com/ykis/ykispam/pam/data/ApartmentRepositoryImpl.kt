package com.ykis.ykispam.pam.data

import com.ykis.ykispam.pam.data.cache.apartment.ApartmentCache
import com.ykis.ykispam.pam.data.cache.family.FamilyCache
import com.ykis.ykispam.pam.data.cache.heat.meter.HeatMeterCache
import com.ykis.ykispam.pam.data.cache.heat.reading.HeatReadingCache
import com.ykis.ykispam.pam.data.cache.payment.PaymentCache
import com.ykis.ykispam.pam.data.cache.service.ServiceCache
import com.ykis.ykispam.pam.data.cache.user.UserCache
import com.ykis.ykispam.pam.data.cache.water.meter.WaterMeterCache
import com.ykis.ykispam.pam.data.cache.water.reading.WaterReadingCache
import com.ykis.ykispam.pam.domain.apartment.ApartmentEntity
import com.ykis.ykispam.pam.domain.apartment.ApartmentRepository
import com.ykis.ykispam.pam.domain.type.Either
import com.ykis.ykispam.pam.domain.type.Failure
import com.ykis.ykispam.pam.domain.type.flatMap
import com.ykis.ykispam.pam.domain.type.map
import com.ykis.ykispam.pam.domain.type.onNext
import com.ykis.ykispam.pam.data.remote.GetSimpleResponse
import com.ykis.ykispam.pam.data.remote.appartment.ApartmentRemote
import javax.inject.Inject


class ApartmentRepositoryImpl @Inject constructor(
    private val apartmentRemote: ApartmentRemote,
    private val apartmentCache: ApartmentCache,
    private val familyCache: FamilyCache,
    private val serviceCache: ServiceCache,
    private val paymentCache: PaymentCache,
    private val waterMeterCache: WaterMeterCache,
    private val heatMeterCache: HeatMeterCache,
    private val waterReadingCache: WaterReadingCache,
    private val heatReadingCache: HeatReadingCache,
    private val userCache: UserCache
) : ApartmentRepository {
    private val addressIdList = mutableListOf<Int>()

    override suspend fun getApartmentsByUser(needFetch: Boolean): Either<Failure, List<ApartmentEntity>> {
        return userCache.getCurrentUser()
            .flatMap {
                if (needFetch) {
                    return@flatMap apartmentRemote.getApartmentsByUser(it.uid)

                        .map { it.sortedBy { it.address } }
                        .onNext {
                            apartmentCache.deleteAllApartments()
                        }
                        .onNext {
                            for (i in it) {
                                addressIdList.add(i.addressId)
                            }
                            familyCache.deleteFamilyFromFlat(addressIdList)
                            serviceCache.deleteServiceFromFlat(addressIdList)
                            paymentCache.deletePaymentFromFlat(addressIdList)
                            waterMeterCache.deleteWaterMeter(addressIdList)
                            heatMeterCache.deleteHeatMeter(addressIdList)
                            waterReadingCache.deleteReadingFromFlat(addressIdList)
                            heatReadingCache.deleteReadingFromFlat(addressIdList)
                            addressIdList.clear()
                        }
                        .onNext {
                            it.map {
                                apartmentCache.addApartmentByUser(listOf(it))
                            }
                        }
                } else {
                    return@flatMap Either.Right(apartmentCache.getApartmentsByUser())
                        .map { it.sortedBy { it.address } }
                }
            }
    }


    override fun deleteFlatByUser(
        addressId: Int
    ): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap apartmentRemote.deleteFlatByUser(
                    addressId,
                    it.uid
                )
            }
    }

    override fun updateBti(
        addressId: Int,
        phone: String,
        email: String
    ): Either<Failure, GetSimpleResponse> {
        return userCache.getCurrentUser()
            .flatMap {
                return@flatMap apartmentRemote.updateBti(
                    addressId,
                    phone,
                    email,
                    it.uid
                )
            }
    }

    override fun getFlatById(addressId: Int): Either<Failure, ApartmentEntity> {
        return userCache.getCurrentUser()
            .flatMap { return@flatMap apartmentRemote.getFlatById(addressId, it.uid) }
    }
}