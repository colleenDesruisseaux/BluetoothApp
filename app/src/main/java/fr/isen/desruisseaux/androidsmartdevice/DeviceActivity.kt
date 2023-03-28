package fr.isen.desruisseaux.androidsmartdevice

import android.bluetooth.BluetoothDevice
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityDeviceBinding
import fr.isen.desruisseaux.androidsmartdevice.databinding.ActivityScanBinding

class DeviceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDeviceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeviceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nameDevice.text = intent.getStringExtra("device")
        clickLight()
    }

    private fun clickLight(){

        binding.led1.setOnClickListener{
            if (binding.led1.imageTintList == getColorStateList(R.color.ledOn)) {
                binding.led1.imageTintList = getColorStateList(R.color.black)
            } else {
                binding.led1.imageTintList = getColorStateList(R.color.ledOn)
            }
        }

        binding.led2.setOnClickListener{
            if (binding.led2.imageTintList == getColorStateList(R.color.ledOn)) {
                binding.led2.imageTintList = getColorStateList(R.color.black)
            } else {
                binding.led2.imageTintList = getColorStateList(R.color.ledOn)
            }
        }

        binding.led3.setOnClickListener{
            if (binding.led3.imageTintList == getColorStateList(R.color.ledOn)) {
                binding.led3.imageTintList = getColorStateList(R.color.black)
            } else {
                binding.led3.imageTintList = getColorStateList(R.color.ledOn)
            }
        }
    }
}