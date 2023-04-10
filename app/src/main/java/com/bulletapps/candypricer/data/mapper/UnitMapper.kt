package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.entities.UnitEntity
import com.bulletapps.candypricer.data.response.UnitResponse
import com.bulletapps.candypricer.domain.model.UnitModel
import com.bulletapps.candypricer.presentation.util.formatUnit
import com.bulletapps.candypricer.presentation.util.orNegative

object UnitMapper {

    fun UnitResponse?.toUnitModel() = UnitModel(
        id = this?.id.orNegative(),
        label = this?.name.orEmpty()
    )

    fun UnitEntity?.toUnitModel() = UnitModel(
        id = this?.id.orNegative(),
        label = this?.name.orEmpty()
    )

    fun UnitModel.toUnitEntity() = UnitEntity(
        id = this.id,
        name = this.label
    )

    fun List<UnitResponse>?.toUnitModelList(): List<UnitModel> {
        return this?.map { it.toUnitModel() }.orEmpty()
    }

    fun List<UnitEntity>?.toUnitModel(): List<UnitModel> {
        return this?.map { it.toUnitModel() }.orEmpty()
    }

    fun List<UnitModel>?.toUnitEntity(): List<UnitEntity> {
        return this?.map { it.toUnitEntity() }.orEmpty()
    }
}