package com.example.busschedule.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao
import kotlinx.coroutines.flow.Flow

class BusScheduleViewModel(private val scheduleDao: ScheduleDao) : ViewModel(){

    fun fullSchedule(): Flow< List<Schedule> >  = scheduleDao.getAll()

    fun scheduleForStopName(name: String): Flow< List<Schedule> >  = scheduleDao.getByStopName(name)

}

class BusScheduleViewModelFactory(
    private val scheduleDao: ScheduleDao
) : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        //SE DETERMINA SI modelClass es subClase de BusScheduleViewModel
        //SI ES VERDADERO modelClass EN EFECTO ES SUBCLASE Y PODEMOS CREAR EL VIEWMODEL
        if( modelClass.isAssignableFrom( BusScheduleViewModel::class.java ) ){
            @Suppress( "UNCHECKED_CAST" )
            return BusScheduleViewModel( scheduleDao ) as T
        }

        //DE LO CONTRARIO LANZAMOS UNA EXCEPCIÓN DE QUE LA CLASE A CREAR NO ES COMPATIBLE
        throw IllegalArgumentException( "Unknown ViewModel class" )
    }

}