package com.bulletapps.candypricer.data.datasource.local

import com.bulletapps.candypricer.config.db.UnitsDAO
import com.bulletapps.candypricer.config.db.UserDAO
import com.bulletapps.candypricer.data.entities.UnitEntity
import com.bulletapps.candypricer.data.entities.UserEntity
import javax.inject.Inject

class CandyPricerLocalDataSource @Inject constructor(
    private val unitsDAO: UnitsDAO,
    private val userDAO: UserDAO
) : LocalDataSource {
    override suspend fun createUnits(units: List<UnitEntity>) = unitsDAO.createUnits(units)
    override suspend fun updateUnits(units: List<UnitEntity>) = unitsDAO.updateUnits(units)
    override suspend fun getUnits(): List<UnitEntity>? = unitsDAO.getUnits()
    override suspend fun deleteUnits() = unitsDAO.deleteUnits()

    override suspend fun createUser(user: UserEntity) = userDAO.createUser(user)
    override suspend fun updateUser(user: UserEntity) = userDAO.updateUser(user)
    override suspend fun getUser() = userDAO.getUser()
    override suspend fun deleteUser() = userDAO.deleteUser()
}