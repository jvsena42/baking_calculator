package com.bulletapps.candypricer.data.datasource.local

import com.bulletapps.candypricer.data.entities.UnitEntity

interface LocalDataSource {
    suspend fun createUnits(units: List<UnitEntity>)
    suspend fun updateUnits(units: List<UnitEntity>)
    suspend fun getUnits(): List<UnitEntity>?
}