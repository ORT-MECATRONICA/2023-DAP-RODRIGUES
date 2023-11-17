package com.example.tpfinaldap

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.tpfinaldap.recycleviewclasses.Comidahe
import com.dap.R
import com.example.tpfinaldap.viewmodels.DataComidasViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataComida : Fragment() {

    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore

    private lateinit var ComidaData: TextView
    private lateinit var saludableData: TextView
    private lateinit var vegiData: TextView
    private lateinit var photoData: ImageView
    private lateinit var descriptionData: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data_comida, container, false)

        ComidaData = view.findViewById(R.id.superheroData)
        saludableData = view.findViewById(R.id.realNameData)
        vegiData = view.findViewById(R.id.publisherData)
        photoData = view.findViewById(R.id.photoData)
        descriptionData = view.findViewById(R.id.descriptionData)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

            db.collection("Comida").document(data1).get().addOnSuccessListener {

                ComidaData.text = (it.data?.get("Nombre").toString())
                saludableData.text = (it.data?.get("Saludable").toString())
                vegiData.text = (it.data?.get("Color").toString())
                Glide.with(photoData.context).load(it.data?.get("Foto").toString()).into(photoData)
                descriptionData.text = (it.data?.get("description").toString())

            }.addOnFailureListener {
                Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
            }
        }
        return view
    }

}