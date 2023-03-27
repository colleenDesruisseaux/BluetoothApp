package fr.isen.desruisseaux.androidsmartdevice

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import fr.isen.desruisseaux.androidsmartdevice.databinding.CellScanBinding

class ScanAdapter(val nom : ArrayList<String>):RecyclerView.Adapter<ScanAdapter.CellViewHolder>() {

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
        holder.textView.text = nom[position]
    }

}