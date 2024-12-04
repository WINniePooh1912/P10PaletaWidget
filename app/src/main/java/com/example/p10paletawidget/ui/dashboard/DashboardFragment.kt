package com.example.p10paletawidget.ui.dashboard

import android.app.DatePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.p10paletawidget.databinding.FragmentDashboardBinding
import com.example.p10paletawidget.Balneario
import com.example.p10paletawidget.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private val reservaciones = mutableListOf<Balneario>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)

        val btnGuardar: Button = view.findViewById(R.id.btnGuardar)

        val calendar: ImageButton = view.findViewById(R.id.imageCalendar)
        val etNombre: EditText = view.findViewById(R.id.etNombre)
        val etBoletos: EditText = view.findViewById(R.id.etBoletos)
        val spnPaquetes: Spinner = view.findViewById(R.id.spnPaquetes)

        val progressBar: ProgressBar = view.findViewById(R.id.progressBar)
        val response: TextView = view.findViewById(R.id.txtResponse)

        var paqueteSel = "Económico"
        var fecha : String = ""

        GlobalScope.launch {
            delay(5000)
            progressBar.visibility = View.INVISIBLE
            response.visibility = View.INVISIBLE
        }

        val lstProductos = resources.getStringArray(R.array.paquetes)
        val adaptProductos = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, lstProductos)
        spnPaquetes.adapter = adaptProductos
        spnPaquetes.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                p0: AdapterView<*>?, p1: View?, p2:
                Int, p3: Long
            ) {
                paqueteSel = lstProductos[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        calendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(), DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    fecha = "$day / ${month + 1} / $year"

                    val builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Fecha seleccionada")
                    builder.setMessage(fecha)
                    builder.setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
                    builder.setNegativeButton("Cancelar") { dialog, _ ->
                        Toast.makeText(requireContext(), "Seleccione otra fecha, por favor", Toast.LENGTH_SHORT).show()
                        fecha = ""
                    }
                    val dialog = builder.create()
                    dialog.show()
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val boletos = etBoletos.text.toString()

            if (nombre.isNotEmpty() && boletos.isNotEmpty() && fecha.isNotEmpty()) {
                // Mostrar barra de progreso
                progressBar.visibility = View.VISIBLE
                response.visibility = View.VISIBLE

                // Simular proceso de guardado con un delay
                Handler(Looper.getMainLooper()).postDelayed({
                    reservaciones.add(Balneario(nombre, boletos, paqueteSel, fecha))
                    progressBar.visibility = View.GONE
                    response.visibility = View.GONE

                    // Mostrar cuadro de diálogo de éxito
                    showDialog(
                        title = "Registro exitoso",
                        message = "Gracias por su reservación, '$nombre' se ha registrado con éxito."
                    ) {
                        // Limpiar campos después de cerrar el diálogo
                        etNombre.text.clear()
                        etBoletos.text.clear()
                        etNombre.requestFocus()
                    }
                }, 2000) // 2 segundos de delay
            } else {
                // Mostrar cuadro de diálogo de error
                showDialog(
                    title = "Error",
                    message = "Por favor, completa todos los campos antes de continuar."
                )
            }
        }

        return view
    }

    private fun showDialog(title: String, message: String, onConfirm: (() -> Unit)? = null) {
        AlertDialog.Builder(requireContext())
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