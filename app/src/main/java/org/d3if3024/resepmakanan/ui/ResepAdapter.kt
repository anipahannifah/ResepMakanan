package org.d3if3024.resepmakanan.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if3024.resepmakanan.R
import org.d3if3024.resepmakanan.data.ResepEntity
import org.d3if3024.resepmakanan.databinding.ResepItemBinding

class ResepAdapter( val resepClickListener: ResepClickListener) :RecyclerView.Adapter<ResepAdapter.ViewHolder>() {
    private val data = mutableListOf<ResepEntity?>()

    fun updateData(newData: List<ResepEntity?>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ResepItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position], resepClickListener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(private val binding: ResepItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ResepEntity?, resepClickListener: ResepClickListener) = with(binding) {
            val judul= if(item?.judul.isNullOrEmpty())"" else item?.judul
            val image = if(item?.image.isNullOrEmpty())"" else item?.image
            Glide.with(imageView.context)
                .load(image)
                .error(R.drawable.ic_baseline_broken_image_24)
                .into(imageView)
            binding.judulTextView.text=judul
            resepItem.setOnClickListener {
                item?.id?.let { it1 -> resepClickListener.onClick(it1) }
            }
        }
    }
}

class ResepClickListener(val clickListener: (id: Long) -> Unit){
    fun onClick(id: Long) = clickListener(id)
}