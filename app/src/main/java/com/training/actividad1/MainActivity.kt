package com.training.actividad1

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import com.training.actividad1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.botonCalcular).setOnClickListener{
            val salario = findViewById<EditText>(R.id.salario).text.toString().toDoubleOrNull() ?: 0.0
            val pagas = findViewById<EditText>(R.id.pagas).text.toString().toIntOrNull() ?: 1
            val edad = findViewById<EditText>(R.id.edad).text.toString().toIntOrNull() ?: 0
            val grupo = findViewById<EditText>(R.id.grupoProf).text.toString()
            val grado = findViewById<EditText>(R.id.gradoDisc).text.toString().toIntOrNull() ?: 0
            val estado = findViewById<EditText>(R.id.estadoCivil).text.toString()
            val hijos = findViewById<EditText>(R.id.numeroHijos).text.toString().toIntOrNull() ?: 0

            val retencion = calcularRetencion(salario, grupo)
            val deducciones = calcularDeducciones(edad, grado, estado, hijos)
            val salarioNeto = calcularSalarioNeto(salario, retencion, deducciones)
            val salarioMensual = salarioNeto / pagas

            val intent = Intent(this, Results::class.java)
            intent.putExtra("salarioBruto", salario.toString())
            intent.putExtra("salarioNeto", salarioNeto.toString())
            intent.putExtra("retencionIrpf", (retencion * 100).toString())
            intent.putExtra("deducciones", (deducciones * 100).toString())
            intent.putExtra("salarioMensual", salarioMensual.toString())

            startActivity(intent)

        }
    }

    fun calcularRetencion(salario: Double, grupo: String): Double {
        val baseRetencion = when (grupo) {
            "1" -> 0.20
            "2" -> 0.15
            "3" -> 0.10
            else -> 0.12
        }
        return baseRetencion
    }

    fun calcularDeducciones(edad: Int, grado: Int, estado: String, hijos: Int): Double {
        var reduccion = 0.0

        if (grado > 33) {
            reduccion += 0.05
        }

        if (estado.equals("casado", ignoreCase = true)) {
            reduccion += 0.03
        }

        reduccion += hijos * 0.01

        if (edad > 65) {
            reduccion += 0.02
        }

        return reduccion
    }

    fun calcularSalarioNeto(salario: Double, retencion: Double, deducciones: Double): Double {
        val retencionFinal = (salario * (retencion - deducciones)).coerceAtLeast(0.0)
        return salario - retencionFinal
    }

}