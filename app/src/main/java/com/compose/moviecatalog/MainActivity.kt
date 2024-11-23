package com.compose.moviecatalog

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.compose.moviecatalog.ui.theme.MovieCatalogTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase



class MainActivity : ComponentActivity() {
    val  db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieCatalogTheme {
                    addCity("Colima", 100000)
                    ShowCities(db)
                }
            }
        }
    }





@Composable
fun addCity(cityName: String, inhabitants: Int) {
    val db = FirebaseFirestore.getInstance()
    val city = hashMapOf(
        "nombre" to cityName,
        "habitantes" to inhabitants
    )
    db.collection("ciudades")
        .add(city)
        .addOnSuccessListener { documentReference ->
            Log.d("Firebase", "Documento agregado con ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.e("Firebase", "Error al agregar documento", e)
        }
}
// Función composable que muestra la lista de ciudades
@Composable
fun ShowCities(db: FirebaseFirestore) {
    val  db = FirebaseFirestore.getInstance()
    db.collection("ciudades").get()
        .addOnSuccessListener { result ->
            for (document in result) {
                document?.let {
                    Log.d("Firebase", "DocumentSnapshot data: ${document.data}")
                    val nombre = document.getString("nombre")
                    val habitantes = document.getLong("habitantes")
                    Log.d("Firebase", "Nombre: $nombre")
                    Log.d("Firebase", "Habitantes: $habitantes")
                }
            }
        }
        .addOnFailureListener { error ->
            Log.e("Firebase", "Error al obtener documentos", error)
        }
}
