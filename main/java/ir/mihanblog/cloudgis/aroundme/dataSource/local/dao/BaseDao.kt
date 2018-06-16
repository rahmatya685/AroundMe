package com.edsab.pm.dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update

interface BaseDao<T> {
    /**
     * Insert an object in the Database.
     *
     * @param obj the object to be inserted.
     */
    @Insert
    fun insert(obj: T)

    /**
     * Insert an array of objects in the Database.
     *
     * @param obj the objects to be inserted.
     */
    @Insert
    fun insert(vararg obj: T)


    /**
     * Update an object from the Database.
     *
     * @param obj the object to be updated
     */
    @Update
    fun update(obj: T)

    /**
     * Delete an object from the Database
     *
     * @param obj the object to be deleted
     */
    @Delete
    fun delete(obj: T)


    @Delete
    fun delete(vararg obj: T)
}