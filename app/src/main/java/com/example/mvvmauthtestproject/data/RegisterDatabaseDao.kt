package com.example.mvvmauthtestproject.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RegisterDatabaseDao {

    @Insert
    suspend fun insert(register: RegisterEntity)

    @Query("select * from register_users_table order by userId desc")
    fun getAllUsers(): LiveData<List<RegisterEntity>>

    @Query("SELECT * FROM register_users_table WHERE user_name LIKE :userName")
    suspend fun getUsername(userName: String): RegisterEntity?
}