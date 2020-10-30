package com.palchak.sergey.daggerhiltplayground.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BlogCacheEntity::class], version = 1)
abstract class BlogDatabase : RoomDatabase() {

    abstract fun blogDao(): BlogDao

    companion object {

        const val TABLE_NAME = "blog_db"
    }
}