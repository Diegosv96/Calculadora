package com.training.actividad1

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Results : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val resultAnual = findViewById<TextView>(R.id.salarioAnual)
        val resultMensual = findViewById<TextView>(R.id.salarioMensual)
        val resultIRPF = findViewById<TextView>(R.id.irpf)
        val resultDeducciones = findViewById<TextView>(R.id.deducciones)

        val salarioBruto = intent.extras?.getString("salarioBruto").orEmpty()
        val salarioNeto = intent.extras?.getString("salarioNeto").orEmpty()
        val retencionIrpf = intent.extras?.getString("retencionIrpf").orEmpty()
        val deducciones = intent.extras?.getString("deducciones").orEmpty()
        val salarioMensual = intent.extras?.getString("salarioMensual").orEmpty()

        resultAnual.text = "$salarioNeto €"
        resultMensual.text = "$salarioMensual €"
        resultIRPF.text = "$retencionIrpf %"
        resultDeducciones.text = "$deducciones %"

        findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.botonVolver).setOnClickListener{
            val int = Intent(this,MainActivity::class.java)
            startActivity(int)
        }
    }
}