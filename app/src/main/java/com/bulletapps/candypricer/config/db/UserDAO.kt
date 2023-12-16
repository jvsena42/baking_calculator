package com.bulletapps.candypricer.config.db

import androidx.room.*
import com.bulletapps.candypricer.data.entities.UserEntity

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createUser(user: UserEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateUser(user: UserEntity)

    @Query("SELECT * FROM user LIMIT 1")
    fun getUser(): UserEntity?

    @Query("DELETE FROM user")
    fun deleteUser()
}