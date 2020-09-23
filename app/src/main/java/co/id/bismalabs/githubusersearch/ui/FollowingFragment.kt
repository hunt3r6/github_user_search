package co.id.bismalabs.githubusersearch.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.bismalabs.githubusersearch.R
import co.id.bismalabs.githubusersearch.adapter.FollowersAdapter
import co.id.bismalabs.githubusersearch.adapter.FollowingAdapter
import co.id.bismalabs.githubusersearch.databinding.FragmentFollowersBinding
import co.id.bismalabs.githubusersearch.databinding.FragmentFollowingBinding
import co.id.bismalabs.githubusersearch.model.DetailFollowersItem
import co.id.bismalabs.githubusersearch.model.DetailFollowingItem
import co.id.bismalabs.githubusersearch.viewmodel.MainViewModel

class FollowingFragment(val name: String) : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: FollowingAdapter
    private val listUser = ArrayList<DetailFollowingItem>()
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        showLoading(true)

        mainViewModel.setFollowing(name)

        adapter = FollowingAdapter(listUser)
        adapter.notifyDataSetChanged()

        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.adapter = adapter

        mainViewModel.getFollowing().observe(viewLifecycleOwner,{following ->
            if (following != null) {
                showLoading(false)
                listUser.addAll(following)
                adapter = FollowingAdapter(listUser)
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