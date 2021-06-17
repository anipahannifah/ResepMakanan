package org.d3if3024.resepmakanan.ui.detail

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.Html
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import org.d3if3024.resepmakanan.R
import org.d3if3024.resepmakanan.data.ResepDb
import org.d3if3024.resepmakanan.databinding.FragmentDetailBinding
import org.d3if3024.resepmakanan.internet.ApiStatus
import org.d3if3024.resepmakanan.ui.ResepFragmentDirections


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by lazy {
        val db=ResepDb.getInstance(requireContext())
        val factory=DetailViewModelFactory(db.dao)
        ViewModelProvider(this,factory).get(DetailViewModel::class.java)
    }
    private var judul = ""
    private var image=""
    private var deskripsi=""
    private var link=""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = FragmentDetailBinding.inflate(layoutInflater,container,false)

        binding.aksiButton.setOnClickListener {
            if (binding.aksiButton.text.equals("Hapus")){
                val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity as AppCompatActivity)
                builder.setTitle("Hapus Resep")

                builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                    viewModel.hapusResep(args.idLocal)
                    Toast.makeText(activity as AppCompatActivity,"Sukses hapus!!", Toast.LENGTH_LONG).show()
                    view?.findNavController()?.navigate(R.id.action_detailFragment_to_resepFragment2)
                })
                builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

                builder.show()

            }else{
                viewModel.simpanResep(judul,deskripsi,link, image)
                Toast.makeText(activity as AppCompatActivity,"Sukses menyimpan!!", Toast.LENGTH_LONG).show()
            }
        }

        binding.editButton.setOnClickListener {
            val builder: AlertDialog.Builder = android.app.AlertDialog.Builder(activity as AppCompatActivity)
            builder.setTitle("Edit Judul")

            val input = EditText(activity as AppCompatActivity)
            input.setHint("Masukan judul")
            input.inputType = InputType.TYPE_CLASS_TEXT
            builder.setView(input)
            builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                var m_Text = input.text.toString()
                viewModel.ubahJudul(args.idLocal, m_Text)
                Toast.makeText(activity as AppCompatActivity,"Sukses menyimpan!!", Toast.LENGTH_LONG).show()
            })
            builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

            builder.show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (args.tipe == 2){
            viewModel.cariDetail(args.id)
            viewModel.getStatus().observe(viewLifecycleOwner,{
                updateProgress(it)
            })
            binding.aksiButton.text = "Simpan"
            binding.aksiButton.visibility = View.VISIBLE
            viewModel.getResep().observe(viewLifecycleOwner,{
                if (it!=null){
                    judul = it.title
                    binding.judulTextView.text = "Judul: ${judul}"
                    image = it.image
                    Glide.with(binding.imageView.context)
                        .load(image)
                        .error(R.drawable.ic_baseline_broken_image_24)
                        .into(binding.imageView)
                    deskripsi = it.summary
                    binding.deskripsiTextView.text = Html.fromHtml(deskripsi)
                    link = it.sourceUrl
                    binding.linkTextView.text = "Link: ${link}"
                }
            })
            viewModel.getStatus().observe(viewLifecycleOwner,{
                updateProgress(it)
            })
        }else{
            binding.aksiButton.text = "Hapus"
            binding.aksiButton.visibility = View.VISIBLE
            binding.editButton.visibility = View.VISIBLE
            viewModel.dapatkanDetail(args.idLocal)
            viewModel.resepDetail.observe(viewLifecycleOwner, {
                if (it!=null){
                    judul = it.judul.toString()
                    binding.judulTextView.text = "Judul: ${judul}"
                    image = it.image.toString()
                    Glide.with(binding.imageView.context)
                            .load(image)
                            .error(R.drawable.ic_baseline_broken_image_24)
                            .into(binding.imageView)
                    deskripsi = it.deskripsi.toString()
                    binding.deskripsiTextView.text = Html.fromHtml(deskripsi)
                    link = it.link.toString()
                    binding.linkTextView.text = "Link: ${link}"
                }
            })
            viewModel.getStatus().observe(viewLifecycleOwner,{
                updateProgress(it)
            })
        }
        viewModel.getStatus().observe(viewLifecycleOwner,{
            updateProgress(it)
        })
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility =View.VISIBLE
                binding.content.visibility= View.INVISIBLE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.content.visibility= View.VISIBLE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility= View.GONE
                binding.networkError.visibility = View.VISIBLE
                binding.content.visibility= View.INVISIBLE
            }
        }
    }
}