package co.id.bismalabs.githubusersearch.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.bismalabs.githubusersearch.adapter.FollowersAdapter
import co.id.bismalabs.githubusersearch.databinding.FragmentFollowersBinding
import co.id.bismalabs.githubusersearch.model.DetailFollowersItem
import co.id.bismalabs.githubusersearch.viewmodel.MainViewModel

class FollowersFragment(val name: String) : Fragment() {

    private var _binding: FragmentFollowersBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: FollowersAdapter
    private val listUser = ArrayList<DetailFollowersItem>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        showLoading(true)

        mainViewModel.setFollowers(name)

        adapter = FollowersAdapter(listUser)
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.adapter = adapter

        mainViewModel.getFollowers().observe(viewLifecycleOwner,{followers ->
            if (followers != null) {
                showLoading(false)
                listUser.addAll(followers)
                adapter = FollowersAdapter(listUser)
                adapter.notifyDataSetChanged()

                binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
                binding.rvUser.adapter = adapter
            }
        })

    }

    private fun showLoading(state: Boolean) {
        with(binding) {
            if (state) {
                progress.visibility = View.VISIBLE
            } else {
                progress.visibility = View.GONE
            }
        }
    }
}
