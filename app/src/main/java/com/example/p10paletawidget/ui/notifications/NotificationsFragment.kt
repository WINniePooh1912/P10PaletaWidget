package com.example.p10paletawidget.ui.notifications

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.p10paletawidget.R
import com.example.p10paletawidget.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val mcVideo = MediaController(requireContext())
        mcVideo.setAnchorView(binding.videoView)
        binding.videoView.setMediaController(mcVideo)

        val ruta = Uri.parse("android.resource://" + requireContext().packageName + "/" + R.raw.balneario)
        binding.videoView.setVideoURI(ruta)
        binding.videoView.requestFocus()
        binding.videoView.start()

        binding.ratingToalla.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            showDialog(
                title = "Calificación del servicio",
                message = "Has calificado 'Tobogan' con $rating estrellas."
            )
            Toast.makeText(requireContext(), "Tobogan: $rating/${ratingBar.numStars}", Toast.LENGTH_SHORT).show() }

        binding.ratingSnorkel.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            showDialog(
                title = "Calificación del servicio",
                message = "Has calificado 'Área de niños' con $rating estrellas."
            )
            Toast.makeText(requireContext(), "Área de niños: $rating/${ratingBar.numStars}", Toast.LENGTH_SHORT).show() }

        binding.ratingBloqueador.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            showDialog(
                title = "Calificación del servicio",
                message = "Has calificado 'Alberca' con $rating estrellas."
            )
            Toast.makeText(requireContext(), "Alberca: $rating/${ratingBar.numStars}", Toast.LENGTH_SHORT).show() }

        binding.ratingGoggles.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            showDialog(
                title = "Calificación del servicio",
                message = "Has calificado 'Comida' con $rating estrellas."
            )
            Toast.makeText(requireContext(), "Comida: $rating/${ratingBar.numStars}", Toast.LENGTH_SHORT).show() }

        return binding.root
    }

    private fun showDialog(title: String, message: String) {
        AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}