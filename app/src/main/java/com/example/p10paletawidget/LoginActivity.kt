package com.example.p10paletawidget

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val usr = findViewById<EditText>(R.id.etUsername).text
            val psw = findViewById<EditText>(R.id.etPassword).text

            if(usr.isNotEmpty() || psw.isNotEmpty()){
                if(usr.toString() == "sly" && psw.toString() == "8057") {
                    showDialog(
                        title = "Inicio de sesión exitoso",
                        message = "¡Bienvenido al Balneario Santa Mónica!",
                        onConfirm = {
                            startActivity(Intent(this, MainActivity::class.java))
                            finish()
                        }
                    )
                } else {
                    showDialog(
                        title = "Error de autenticación",
                        message = "Usuario o contraseña incorrectos. Por favor, inténtalo de nuevo."
                    )
                }
            } else {
                showDialog(
                    title = "Falta de información",
                    message = "Por favor, llene todos los campos."
                )
            }
        }
    }

    private fun showDialog(title: String, message: String, onConfirm: (() -> Unit)? = null) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ ->
                onConfirm?.invoke()
                dialog.dismiss()
            }
            .create()
            .show()
    }
}