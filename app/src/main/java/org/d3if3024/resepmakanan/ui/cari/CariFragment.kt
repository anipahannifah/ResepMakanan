package org.d3if3024.resepmakanan.ui.cari

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3024.resepmakanan.R
import org.d3if3024.resepmakanan.databinding.FragmentCariBinding
import org.d3if3024.resepmakanan.internet.ApiStatus

class CariFragment : Fragment() {

    private lateinit var binding:FragmentCariBinding
    private lateinit var cariAdapter: CariAdapter
    private val viewModel: CariViewModel by lazy {
        val factory = CariViewModelFactory()
        ViewModelProvider(this, factory).get(CariViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentCariBinding.inflate(layoutInflater,container,false)
        binding.cariButton.setOnClickListener {
            viewModel.getStatus().observe(viewLifecycleOwner,{
                updateProgress(it)
            })
            viewModel.cariResep(binding.cariEditText.text.toString())
           viewModel.getResep().observe(viewLifecycleOwner,{
               if (it!=null)cariAdapter.updateData(it.results)
           })
        }
        cariAdapter =CariAdapter(ResepClickListener { id->
            view?.findNavController()?.navigate(CariFragmentDirections.actionCariFragmentToDetailFragment(2,id))
        })
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter= cariAdapter
            setHasFixedSize(true)
        }
        return binding.root
    }

    private fun updateProgress(status: ApiStatus) {
        when (status) {
            ApiStatus.LOADING -> {
                binding.progressBar.visibility = View.VISIBLE
                binding.notFound.visibility = View.GONE
                binding.networkError.visibility = View.GONE
            }
            ApiStatus.SUCCESS -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.GONE
                binding.notFound.visibility = View.GONE
            }
            ApiStatus.FAILED -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.VISIBLE
                binding.notFound.visibility = View.GONE
            }
            ApiStatus.NOTFOUND -> {
                binding.progressBar.visibility = View.GONE
                binding.networkError.visibility = View.GONE
                binding.notFound.visibility = View.VISIBLE
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getResep().observe(viewLifecycleOwner,{
            if (it!=null)cariAdapter.updateData(it.results)
        })
        viewModel.getStatus().observe(viewLifecycleOwner,{
            updateProgress(it)
        })
    }
}