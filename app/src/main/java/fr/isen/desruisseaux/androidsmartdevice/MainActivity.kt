package fr.isen.desruisseaux.androidsmartdevice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        buttonListener()
    }

    private fun buttonListener(){
        binding.start.setOnClickListener {
            val intent = Intent(this, ScanActivity::class.java)
            startActivity(intent)
        }

        binding.regles.setOnClickListener {
            binding.textRegles.isVisible = true
            binding.textRegles.text=" * Scanner et détecter les appareils\n\n * Sélectionner un appareil\n\n * Connexion établie\n\n * Click sur une LED pour l'allumer\n   sur la carte STM32"
        }
    }
}