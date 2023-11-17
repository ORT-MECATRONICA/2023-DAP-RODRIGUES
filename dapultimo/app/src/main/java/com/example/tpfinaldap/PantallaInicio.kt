package com.example.tpfinaldap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dap.R
import com.example.tpfinaldap.recycleviewclasses.Comidahe
import com.example.tpfinaldap.recycleviewclasses.ComidaAdapter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PantallaInicio : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var Comidalist: ArrayList<Comidahe>
    private var db = Firebase.firestore
    private lateinit var botonAdd: Button
    private lateinit var idActual: String
    private lateinit var idCompartido: sharedData

    private lateinit var adapter : ComidaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val view = inflater.inflate(R.layout.fragment_pantalla_inicio, container, false)

        db = FirebaseFirestore.getInstance()

        recyclerView = view.findViewById(R.id.recyclerComida)
        recyclerView.layoutManager = LinearLayoutManager(context)
        Comidalist = arrayListOf()
        botonAdd = view.findViewById(R.id.botonAñadir)

        initRecyclerView()

        botonAdd.setOnClickListener {
            findNavController().navigate(R.id.action_pantallaInicio_to_subirComida)
        }
        return view
    }

    private fun initRecyclerView() {
        db.collection("Comida").get().addOnSuccessListener {
            if (!it.isEmpty) {
                for (data in it.documents) {
                    val comidaita:Comidahe? = data.toObject<Comidahe>(Comidahe::class.java)
                    Comidalist.add(comidaita!!)
                }

                adapter = ComidaAdapter(Comidalist,
                    onDeleteClick = {position -> deleteComida(position)
                },
                    onEditClick = {position -> editComida(position)
                },
                    onItemClick = {position -> seeData(position)})

                recyclerView.adapter = adapter
            }
        }.addOnFailureListener {
            Toast.makeText(context, it.toString(),Toast.LENGTH_SHORT).show()
        }
    }

    fun seeData(position:Int) {

        idActual = Comidalist[position].id.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idActual

        findNavController().navigate(R.id.action_pantallaInicio_to_dataComida)
    }

    fun editComida(position: Int) {
        idActual = Comidalist[position].id.toString()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.value = idActual

        findNavController().navigate(R.id.action_pantallaInicio_to_edit)
    }

    fun deleteComida (position : Int){

        db.collection("Comida").document(Comidalist[position].id.toString()).delete()
            .addOnSuccessListener {
                Toast.makeText(requireContext(),"Comida Eliminada", Toast.LENGTH_SHORT).show()
                adapter.notifyItemRemoved(position)
                Comidalist.removeAt(position)
            }
            .addOnFailureListener { Toast.makeText(requireContext(),"No se puedo eliminar ", Toast.LENGTH_SHORT).show() }
    }
}