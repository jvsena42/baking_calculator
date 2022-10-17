package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitModel
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.util.orNegative
import com.bulletapps.candypricer.presentation.util.orZero
import com.bulletapps.candypricer.presentation.util.round

object SupplyMapper {

    fun SupplyResponse?.toSupplyModel() = SupplyModel(
        id = this?.id.orNegative(),
        name = this?.name.orEmpty(),
        quantity = this?.quantity.orZero().round(),
        value = this?.value.orZero().round(),
        unit = this?.unit.toUnitModel()
    )

    fun List<SupplyResponse>?.toSupplyModelList(): List<SupplyModel> {
        return this?.map { it.toSupplyModel() }.orEmpty()
    }
}