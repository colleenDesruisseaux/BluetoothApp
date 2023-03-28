package fr.isen.desruisseaux.androidsmartdevice


import android.bluetooth.BluetoothDevice
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import fr.isen.desruisseaux.androidsmartdevice.databinding.CellScanBinding

class ScanAdapter(val nom : ArrayList<BluetoothDevice>, var onDeviceClickListener: (BluetoothDevice) -> Unit): RecyclerView.Adapter<ScanAdapter.CellViewHolder>() {

    class CellViewHolder(binding: CellScanBinding) : RecyclerView.ViewHolder(binding.root) {
        val textView : TextView = binding.ble
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : CellViewHolder {
        val binding = CellScanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CellViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return nom.size
    }

    override fun onBindViewHolder(holder: CellViewHolder, position: Int) {
        holder.textView.text = nom[position].address
        holder.itemView.setOnClickListener {
            onDeviceClickListener(nom[position])
        }
    }

    fun addDevice(device: BluetoothDevice) {
        var shouldAddDevice = true
        nom.forEachIndexed { index, bluetoothDevice ->
            if (bluetoothDevice.address == device.address) {
                nom[index] = device
            }
        }
        if (shouldAddDevice) {
            nom.add(device)
        }
    }
}