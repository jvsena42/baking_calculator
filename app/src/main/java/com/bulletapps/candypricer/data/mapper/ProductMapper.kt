package com.bulletapps.candypricer.data.mapper

import com.bulletapps.candypricer.data.mapper.UnitMapper.toUnitModel
import com.bulletapps.candypricer.data.response.ProductResponse
import com.bulletapps.candypricer.data.response.SupplyAmountResponse
import com.bulletapps.candypricer.domain.model.ProductModel
import com.bulletapps.candypricer.domain.model.ProductSupplyModel
import com.bulletapps.candypricer.presentation.util.orNegative
import com.bulletapps.candypricer.presentation.util.orZero

fun ProductResponse.toProductModel() = ProductModel(
    id = id.orNegative(),
    name = name.orEmpty(),
    unit = unit.toUnitModel(),
    quantity = quantity.orZero(),
    profitMargin = profitMargin.orZero(),
    laborValue = laborValue.orZero(),
    variableExpenses = variableExpenses.orZero(),
    supplies = supplies.toProductSupplyModel(),
    totalSpendsValue = totalSpendsValue.orZero(),
    unitSaleValue = unitSaleValue.orZero(),
    totalSaleValue = totalSaleValue.orZero()
)

fun List<ProductResponse>?.toProductModelList(): List<ProductModel> {
    return this?.map { it.toProductModel() }.orEmpty()
}

fun SupplyAmountResponse.toProductSupplyModel() = ProductSupplyModel(
    supplyId = this.supply.id.orZero(),
    name = this.supply.name.orEmpty(),
    quantity = this.quantity
)

fun List<SupplyAmountResponse>?.toProductSupplyModel(): List<ProductSupplyModel> {
    return this?.map { it.toProductSupplyModel() }.orEmpty()
    }
