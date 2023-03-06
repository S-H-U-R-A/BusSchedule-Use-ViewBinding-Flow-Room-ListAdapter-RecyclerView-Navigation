package com.example.busschedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.databinding.StopScheduleFragmentBinding
import com.example.busschedule.viewmodels.BusScheduleViewModel
import com.example.busschedule.viewmodels.BusScheduleViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class StopScheduleFragment: Fragment() {

    private val viewModel: BusScheduleViewModel by activityViewModels<BusScheduleViewModel> {
        BusScheduleViewModelFactory(
            (requireActivity().applicationContext as BusScheduleApplication).dataBase.scheduleDao()
        )
    }

    //PROPIEDAD ESTATICA
    companion object {
        var STOP_NAME = "stopName"
    }

    //VARIABLE DE VINCULACIÓN PROTEGIDA
    private var _binding: StopScheduleFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView

    private lateinit var stopName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //SE RECUPERAN LOS ARGUMENTOS PASADOS
        arguments?.let {
            stopName = it.getString(STOP_NAME).toString()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //SE INFLA EL DISEÑO
        _binding = StopScheduleFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //SE ASIGNAN LOS VALORES
        recyclerView = binding.recyclerView
        //ESTO SE PUEDE HACER DESDE EL XML
        recyclerView.layoutManager = LinearLayoutManager( requireContext() )

        val busStopAdapter = BusStopAdapter {}
        recyclerView.adapter = busStopAdapter

/*        GlobalScope.launch(Dispatchers.IO) {
            busStopAdapter.submitList( viewModel.scheduleForStopName(stopName) )
        }*/


        lifecycle.coroutineScope.launch {
            viewModel.scheduleForStopName(stopName).collect(){ schedules ->
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
