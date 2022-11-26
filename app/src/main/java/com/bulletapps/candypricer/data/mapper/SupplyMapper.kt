package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitModel
import com.bulletapps.candypricer.data.response.SupplyResponse
import com.bulletapps.candypricer.domain.model.SupplyModel
import com.bulletapps.candypricer.presentation.util.orNegative
import com.bulletapps.candypricer.presentation.util.orZero

object SupplyMapper {

    fun SupplyResponse.toSupplyModel() = SupplyModel(
        id = this.id.orNegative(),
        name = this.name.orEmpty(),
        quantity = this.quantity.orZero(),
        price = this.value.orZero(),
        unit = this.unit.toUnitModel()
    )

    fun List<SupplyResponse>?.toSupplyModelList(): List<SupplyModel> {
        return this?.map { it.toSupplyModel() }.orEmpty()
    }
}