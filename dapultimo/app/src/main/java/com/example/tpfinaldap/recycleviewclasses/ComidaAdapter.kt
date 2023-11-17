package com.example.tpfinaldap.recycleviewclasses

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dap.R

//import com.example.tpfinaldap.R


class ComidaAdapter(
    comidalist: ArrayList<Comidahe>,
    private val onDeleteClick : (Int)->Unit,
    private val onEditClick : (Int) -> Unit,
    private val onItemClick: (Int) -> Unit

): RecyclerView.Adapter<ComidaAdapter.ComidaViewHolder>(){
    private var comidalist: ArrayList<Comidahe>

    init {
        this.comidalist = comidalist
    }

    class ComidaViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val comidita= view.findViewById<TextView>(R.id.nombreComida)
        val saludables= view.findViewById<TextView>(R.id.Saludable)
        val colore = view.findViewById<TextView>(R.id.Colorin)
        val photo = view.findViewById<ImageView>(R.id.photo_comida)
        val editar = view.findViewById<Button>(R.id.botonEditar)
        val eliminar = view.findViewById<Button>(R.id.botonEliminar)

        fun render(comidaheModel: Comidahe){
            comidita.text = comidaheModel.comida //aca esta el problema Ale.
            colore.text = comidaheModel.color
            saludables.text = comidaheModel.saludable
            Glide.with(photo.context).load(comidaheModel.photo).into(photo)
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComidaViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ComidaViewHolder(layoutInflater.inflate(R.layout.item_comida, parent, false))
    }
    override fun onBindViewHolder(holder: ComidaViewHolder, position: Int) {
        val item = comidalist[position]
        holder.render(item)
        holder.eliminar.setOnClickListener {
            onDeleteClick(position)
        }
        holder.editar.setOnClickListener {
            onEditClick(position)
        }
        holder.itemView.setOnClickListener {
            onItemClick(position)
        }
        holder.photo.setOnClickListener {
            onItemClick(position)
        }
    }
    override fun getItemCount(): Int = comidalist.size

}