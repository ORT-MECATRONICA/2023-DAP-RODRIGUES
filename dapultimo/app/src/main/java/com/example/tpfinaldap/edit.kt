package com.example.tpfinaldap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dap.R
//import com.dap.recycleviewclasses.SuperHero
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class edit : Fragment() {

    private lateinit var idCompartido: sharedData
    private var db = Firebase.firestore
    private lateinit var ComidaNueva: EditText
    private lateinit var Saludable: EditText
    private lateinit var Vegano: EditText
    private lateinit var photoNuevo: EditText
    private lateinit var descriptionNuevo: EditText

    private lateinit var ID: String

    private lateinit var botonSubirDatos: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_comida, container, false)

        ComidaNueva = view.findViewById(R.id.comidaNueva)
        Saludable = view.findViewById(R.id.Color)
        Vegano = view.findViewById(R.id.saludableNuevo)
        photoNuevo = view.findViewById(R.id.photoNuevo)
        descriptionNuevo = view.findViewById(R.id.descriptionNuevo)

        botonSubirDatos = view.findViewById(R.id.botonSubirDatos)

        db = FirebaseFirestore.getInstance()

        idCompartido = ViewModelProvider(requireActivity()).get(sharedData::class.java)
        idCompartido.dataID.observe(viewLifecycleOwner) { data1 ->

        db.collection("Comida").document(data1).get().addOnSuccessListener {

            ComidaNueva.setText(it.data?.get("Nombre").toString())
            Saludable.setText(it.data?.get("Saludable").toString())
            Vegano.setText(it.data?.get("Vegano").toString())
            photoNuevo.setText(it.data?.get("Foto").toString())
            descriptionNuevo.setText(it.data?.get("description").toString())
            ID = it.data?.get("id").toString()

        }.addOnFailureListener {
            Toast.makeText(context, "no se encontraron datos", Toast.LENGTH_SHORT).show()
        }

        botonSubirDatos.setOnClickListener {

            val comidanuevo = hashMapOf(
                "Nombre" to ComidaNueva.text.toString(),
                "Saludable" to Saludable.text.toString(),
                "Vegano" to Vegano.text.toString(),
                "photo" to photoNuevo.text.toString(),
                "description" to descriptionNuevo.text.toString(),
                "id" to ID

            )

            db.collection("Comida").document(data1).set(comidanuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(context, "no se pudo subir", Toast.LENGTH_SHORT).show()
                }

            findNavController().navigate(R.id.action_edit_to_pantallaInicio)
        } }

        return view
    }
}