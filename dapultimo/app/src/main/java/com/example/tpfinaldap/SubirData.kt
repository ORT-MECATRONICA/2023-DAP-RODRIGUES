package com.example.tpfinaldap

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.dap.R
import com.example.tpfinaldap.viewmodels.ComidaViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SubirData : Fragment() {

    companion object {
        fun newInstance() = SubirData()
    }

    private lateinit var viewModel: ComidaViewModel
    private lateinit var textNombre: EditText
    private lateinit var textColor: EditText
    private lateinit var textSaludable: EditText
    private lateinit var textFoto: EditText
    private lateinit var textDescription: EditText
    private var db = Firebase.firestore
    private lateinit var botonSubir: Button




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_subir_comida, container, false)

        textNombre = view.findViewById(R.id.textComida)
        textSaludable = view.findViewById(R.id.textSaludable)
        textColor = view.findViewById(R.id.textColor)
        textFoto = view.findViewById(R.id.textFoto)
        textDescription = view.findViewById(R.id.textDescription)
        botonSubir = view.findViewById(R.id.botonSubir)

        botonSubir.setOnClickListener {

            val documentId:String = db.collection("Comida").document().id

            val superHeroeNuevo = hashMapOf(
                "Nombre" to textNombre.text.toString(),
                "Color" to textColor.text.toString(),
                "Saludable" to textSaludable.text.toString(),
                "Foto" to textFoto.text.toString(),
                "description" to textDescription.text.toString(),
                "id" to documentId
            )

            db.collection("Comida").document(documentId).set(superHeroeNuevo)
                .addOnSuccessListener {
                    Toast.makeText(context, "subido", Toast.LENGTH_SHORT).show()}
                .addOnFailureListener { e ->
                    Toast.makeText(context, "no se pudo subir",Toast.LENGTH_SHORT).show() }

            findNavController().navigate(R.id.action_subirComida_to_pantallaInicio)
        }

        return view
    }

}