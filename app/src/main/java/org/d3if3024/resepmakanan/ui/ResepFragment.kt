package org.d3if3024.resepmakanan.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3024.resepmakanan.R
import org.d3if3024.resepmakanan.data.ResepDb
import org.d3if3024.resepmakanan.databinding.FragmentResepBinding

class ResepFragment : Fragment() {

    private lateinit var binding: FragmentResepBinding
    private val viewModel: ResepViewModel by lazy {
        val db= ResepDb.getInstance(requireContext())
        val factory=ResepViewModelFactory(db.dao)
        ViewModelProvider(this,factory).get(ResepViewModel::class.java)
    }
    private lateinit var myAdapter : ResepAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentResepBinding.inflate(layoutInflater, container, false)
        myAdapter = ResepAdapter(ResepClickListener{ id ->
            view?.findNavController()?.navigate(ResepFragmentDirections.actionResepFragmentToDetailFragment(1,0,id))
        })
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter =myAdapter
            setHasFixedSize(true)
        }

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.resep.observe(viewLifecycleOwner, {
            binding.emptyView.visibility =if(it.isEmpty())View.VISIBLE else View.GONE
            myAdapter.updateData(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_cari){
            view?.findNavController()?.navigate(R.id.action_resepFragment_to_cariFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}