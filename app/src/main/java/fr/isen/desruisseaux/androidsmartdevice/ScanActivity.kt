package fr.isen.desruisseaux.androidsmartdevice

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityMainBinding
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE){
            val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (bluetoothAdapter?.isEnabled == true) {
            //J'ai le BLE
            Toast.makeText(this, "Bluetooth activé", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Prêt à détecter les appareils !", Toast.LENGTH_LONG).show()
        }   else {
            //Bluetooth accessible mais non activé
            Toast.makeText(this, "Bluetooth accessible mais non activé", Toast.LENGTH_LONG).show()
        }
        buttonListener()
        showDatas()
    }

    private fun showDatas() {
        binding.listBle.layoutManager = LinearLayoutManager(this)
        binding.listBle.adapter = ScanAdapter(arrayListOf("BLE_1", "BLE_2", "BLE_3"))
    }

    private fun buttonListener(){

            binding.scanPause.visibility = View.GONE
            binding.progressBar.visibility = View.GONE
            binding.listBle.visibility = View.GONE

            binding.scanPlay.setOnClickListener {
                    Log.e("test", "click bouton")

                    if (bluetoothAdapter?.isEnabled == true) {
                        Toast.makeText(this, "Scan lancé", Toast.LENGTH_LONG).show()
                        binding.scanPlay.visibility = View.GONE
                        binding.scanPause.visibility = View.VISIBLE
                        binding.etatScan.text = "Scan BLE en cours..."
                        binding.progressBar.visibility = View.VISIBLE
                        binding.bar.visibility = View.GONE
                        binding.listBle.visibility = View.VISIBLE
                    } else {
                        Toast.makeText(this, "Veuillez activer le bluetooth", Toast.LENGTH_LONG).show()
                    }
            }

            binding.scanPause.setOnClickListener {
                Log.e("test", "click bouton")
                Toast.makeText(this, "Scan en pause", Toast.LENGTH_LONG).show()
                binding.etatScan.text = "Lancer le Scan BLE"
                binding.scanPlay.visibility = View.VISIBLE
                binding.scanPause.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
                binding.bar.visibility = View.VISIBLE
                binding.listBle.visibility = View.GONE
            }
    }
}