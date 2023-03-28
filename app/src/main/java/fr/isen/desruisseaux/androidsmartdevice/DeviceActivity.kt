package fr.isen.desruisseaux.androidsmartdevice

import android.annotation.SuppressLint
import android.bluetooth.*
import android.content.ComponentName
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.core.view.isVisible
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityDeviceBinding
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityScanBinding

@SuppressLint("MissingPermission")
class DeviceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceBinding
    private val bluetoothAdapter: BluetoothAdapter? by lazy(LazyThreadSafetyMode.NONE){
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }
    private var cptClick = 0

    var bluetoothGatt: BluetoothGatt? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var recup = intent.getParcelableExtra<BluetoothDevice>("device")
        binding.nameDevice.text
        clickLight()
        bluetoothGatt = recup?.connectGatt(this, false, bluetoothGattCallback)
        bluetoothGatt?.connect()
    }


    override fun onStop() {
        super.onStop()
        bluetoothGatt?.close()
    }
    private fun clickLight(){

        binding.led1.setOnClickListener{
            if (binding.led1.imageTintList == getColorStateList(R.color.ledOn)) {
                binding.led1.imageTintList = getColorStateList(R.color.black)
            } else {
                binding.led1.imageTintList = getColorStateList(R.color.ledOn)
                cptClick++
                binding.nbClick.text = "Nombre de click: $cptClick"
            }
        }

        binding.led2.setOnClickListener{
            if (binding.led2.imageTintList == getColorStateList(R.color.ledOn)) {
                binding.led2.imageTintList = getColorStateList(R.color.black)
            } else {
                binding.led2.imageTintList = getColorStateList(R.color.ledOn)
                cptClick++
                binding.nbClick.text = "Nombre de click: $cptClick"
            }
        }

        binding.led3.setOnClickListener{
            if (binding.led3.imageTintList == getColorStateList(R.color.ledOn)) {
                binding.led3.imageTintList = getColorStateList(R.color.black)
            } else {
                binding.led3.imageTintList = getColorStateList(R.color.ledOn)
                cptClick++
                binding.nbClick.text = "Nombre de click: $cptClick"
            }
        }
    }

    private val bluetoothGattCallback = object : BluetoothGattCallback() {
        override fun onConnectionStateChange(gatt: BluetoothGatt?, status: Int, newState: Int) {
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                Log.e("test", "BLE trouvé")
                runOnUiThread {
                    displayContentConnected()
                }
                // successfully connected to the GATT Server
            } else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                Log.e("test", "Echec")
            }
        }
    }

    private fun displayContentConnected(){
        binding.affLED.text = "Affichage des LEDs"
        binding.loading.isVisible = false
        binding.led1.isVisible = true
        binding.led2.isVisible = true
        binding.led3.isVisible = true
    }
}