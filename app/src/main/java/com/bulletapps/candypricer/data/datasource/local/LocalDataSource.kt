package com.bulletapps.candypricer.data.datasource.local

import com.bulletapps.candypricer.data.entities.UnitEntity
import com.bulletapps.candypricer.data.entities.UserEntity

interface LocalDataSource {
    suspend fun createUnits(units: List<UnitEntity>)
    suspend fun updateUnits(units: List<UnitEntity>)
    suspend fun getUnits(): List<UnitEntity>?
    suspend fun deleteUnits()

    suspend fun createUser(user: UserEntity)
    suspend fun updateUser(user: UserEntity)
    suspend fun getUser(): UserEntity?
    suspend fun deleteUser()
}