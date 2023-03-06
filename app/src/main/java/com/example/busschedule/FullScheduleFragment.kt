/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.busschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.databinding.FullScheduleFragmentBinding
import com.example.busschedule.viewmodels.BusScheduleViewModel
import com.example.busschedule.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FullScheduleFragment: Fragment() {

    private val viewModel: BusScheduleViewModel by activityViewModels<BusScheduleViewModel> {
        BusScheduleViewModelFactory(
            (activity?.application as BusScheduleApplication).dataBase.scheduleDao()
        )
    }

    //VARIABLE DE VINCULACIÓN PROTEGIDA
    private var _binding: FullScheduleFragmentBinding? = null
    private val binding get() = _binding!!

    //RECYCLER VIEW DEL XML
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //SE INFLA EL XML
        _binding = FullScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SE ASIGNAN LOS VALORES
        recyclerView = binding.recyclerView
        //ESTO SE PUEDE HACER DESDE XML
        recyclerView.layoutManager = LinearLayoutManager( requireContext() )

        //SE CONFIGURA EL CUERPO DE LA LAMBDA
        val busStopAdapter = BusStopAdapter{
            //SE NAVEGA A LA SIGUIENTE PAGINA PASANDO stopName
            val action = FullScheduleFragmentDirections.actionFullScheduleFragmentToStopScheduleFragment(
                it.stopName
            )
            findNavController().navigate(action)
        }

        //SE ASIGNA EL ADAPATADOR
        recyclerView.adapter = busStopAdapter

        //SE LLAMA AL MÉTODO DESDE UNA CORRUTINA PERO NO ES BUENA PRACTICA
/*        GlobalScope.launch(Dispatchers.IO) {
            busStopAdapter.submitList( viewModel.fullSchedule() )
        }*/

        lifecycle.coroutineScope.launch {

            viewModel.fullSchedule()
                .collect(){ schedules ->
                    busStopAdapter.submitList(
                        schedules
                    )
                }

        }

    }

    //SE LIBERA EL RECURSO CUANDO SE DESTRUYE EL FRAGMENTO
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
