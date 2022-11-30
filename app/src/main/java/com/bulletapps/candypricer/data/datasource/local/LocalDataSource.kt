package com.bulletapps.candypricer.data.datasource.local

import com.bulletapps.candypricer.data.response.UnitResponse

interface LocalDataSource {
    suspend fun getUnits(): List<UnitResponse>

    suspend fun updateUnits(units: List<UnitResponse>)
}