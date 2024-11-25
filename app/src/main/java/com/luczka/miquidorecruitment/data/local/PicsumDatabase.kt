package com.luczka.miquidorecruitment.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PicsumImageEntity::class],
    version = 1
)
abstract class PicsumDatabase : RoomDatabase() {

    abstract val dao: PicsumDao
}