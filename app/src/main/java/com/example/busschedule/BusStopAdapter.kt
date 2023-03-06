package com.example.busschedule


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class BusStopAdapter(private val onItemClicked: (Schedule) -> Unit):
    ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    //CONTENEDOR DE VISTAS
    inner class BusStopViewHolder( private var binding: BusStopItemBinding ) :
        RecyclerView.ViewHolder(binding.root) {

        //SE CONFIGURAN LOS VIEWS DE LA VISTA
        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule) {
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text = SimpleDateFormat("h:mm a")
                .format( Date(schedule.arrivalTime.toLong() * 1000) )
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {

        //SE INFLA LA VISTA
        val view = BusStopItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        //SE CREA EL VIEWHOLDER
        val viewHolder = BusStopViewHolder(view)

        //MEDIANTE itenView DE LOS VIEWHOLDERS CONFIGURAMOS EL EVENTO CLICK
        //ESTA PROPIEDAD NOS RETORNA LA VIEW
        viewHolder.itemView.setOnClickListener {
            //SE OBTIENE LA POSICION A PARTIR DEL VIEWHOLDER
            val position = viewHolder.adapterPosition
            //SE LLAMA LA EJECUSIÓN DE LA LAMBDA
            onItemClicked( getItem(position) )
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        //SE LLAMA AL MÉTODO DEL VIEWHOLDER Y SE PASA EL ITEM
        holder.bind( getItem(position) )
    }


    companion object DiffCallback : DiffUtil.ItemCallback<Schedule>(){

        override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
            return oldItem == newItem
        }

    }



}