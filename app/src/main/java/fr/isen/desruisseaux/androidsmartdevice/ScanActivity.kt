package fr.isen.desruisseaux.androidsmartdevice

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityScanBinding

class ScanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScanBinding

    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE){
            val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
            bluetoothManager.adapter
    }

    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (!permissions.containsValue(false)) {
                scanBLEDevices()
            }
        }


    private var scanning = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var adapter: ScanAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showDatas()
        if (bluetoothAdapter?.isEnabled == true) {
            //J'ai le BLE
            Toast.makeText(this, "Bluetooth activé", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "Prêt à détecter les appareils !", Toast.LENGTH_LONG).show()
            scanDeviceWithPermissions()
        }   else {
            //Bluetooth accessible mais non activé
            Toast.makeText(this, "Bluetooth accessible mais non activé", Toast.LENGTH_LONG).show()
        }

        binding.scanPause.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.listBle.visibility = View.GONE
        buttonListener()

    }

    @SuppressLint("MissingPermission")
    override fun onStop() {
        super.onStop()
        if (bluetoothAdapter?.isEnabled == true && allPermissionsGranted()) {
            scanning = false
            bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
        }
    }

    private fun scanDeviceWithPermissions() {
        if (allPermissionsGranted()){
            buttonListener()
        } else {
            //request permission pour TOUTES les permissions
            requestPermissionLauncher.launch(getAllPermissions())
        }
    }

    private fun scanBLEDevices() {
        @SuppressLint("MissingPermission")
            if (!scanning) { // Stops scanning after a pre-defined scan period.
                handler.postDelayed({
                    scanning = false
                    bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
                   // buttonListener()
                    finScan()
                }, SCAN_PERIOD)
                scanning = true
                bluetoothAdapter?.bluetoothLeScanner?.startScan(leScanCallback)
            } else {
                scanning = false
                bluetoothAdapter?.bluetoothLeScanner?.stopScan(leScanCallback)
            }
        buttonListener()
    }

    private val leScanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.d("Scan", "result : $result")
            adapter.addDevice(result.device)
            adapter.notifyDataSetChanged()
        }
    }

    private fun allPermissionsGranted(): Boolean {
        val allPermissions = getAllPermissions()
        return allPermissions.all {
            //it
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun getAllPermissions(): Array<String> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                           Manifest.permission.ACCESS_COARSE_LOCATION,
                           Manifest.permission.BLUETOOTH_SCAN,
                           Manifest.permission.BLUETOOTH_CONNECT)
        } else {
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun showDatas() {
        binding.listBle.layoutManager = LinearLayoutManager(this)

        adapter = ScanAdapter(arrayListOf()) {
            val intent = Intent(this, DeviceActivity::class.java)
            intent.putExtra("device", it.address)
            startActivity(intent)
        }

        binding.listBle.adapter = adapter
    }

    private fun buttonListener(){

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
                        scanBLEDevices()
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

    private fun finScan(){
        binding.etatScan.text = "Lancer le Scan BLE"
        binding.scanPlay.visibility = View.VISIBLE
        binding.scanPause.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
        binding.bar.visibility = View.VISIBLE
    }

    companion object {
        // Stops scanning after 10 seconds.
        val SCAN_PERIOD: Long = 10000
    }
}