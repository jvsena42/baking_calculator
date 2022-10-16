package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.model.UnitModel
import com.bulletapps.candypricer.presentation.util.formatUnit

object UnitMapper {

    fun UnitResponse.toUnitModel() = UnitModel(
        id = this.id,
        label = this.name.formatUnit()
    )

    fun List<UnitResponse>?.toUnitModelList(): List<UnitModel> {
        return this?.map { it.toUnitModel() }.orEmpty()
    }
}