package com.bulletapps.candypricer.data.datasource.local

import com.bulletapps.candypricer.data.response.UnitResponse

class CandyPricerLocalDataSource: LocalDataSource {

    override suspend fun getUnits(): List<UnitResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updateUnits(units: List<UnitResponse>) {
        TODO("Not yet implemented")
    }
}