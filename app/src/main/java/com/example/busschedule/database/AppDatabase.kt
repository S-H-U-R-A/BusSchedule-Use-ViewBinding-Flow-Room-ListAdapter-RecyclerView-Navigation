package com.example.busschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao

@Database(
    entities = [Schedule::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase() {

    abstract fun scheduleDao() : ScheduleDao

    //SE CREA LA BASE DE DATOS COMO SINGLETON
    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{

            //SI YA EXISTE LA INSTANCIA LA RETORNA Y SI NO LA CREA
            return INSTANCE ?: synchronized(this){

                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).createFromAsset( //ESTO SE HACE PARA CARGAR LOS DATOS EXISTENTES
                    "database/bus_schedule.db"
                ).build()

                INSTANCE = instance

                instance

            }
        }

    }

}