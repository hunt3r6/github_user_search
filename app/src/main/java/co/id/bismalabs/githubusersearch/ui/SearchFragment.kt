package co.id.bismalabs.githubusersearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.bismalabs.githubusersearch.adapter.UserAdapter
import co.id.bismalabs.githubusersearch.databinding.FragmentSearchBinding
import co.id.bismalabs.githubusersearch.model.Item
import co.id.bismalabs.githubusersearch.viewmodel.MainViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private val listUser = ArrayList<Item>()
    private lateinit var adapter: UserAdapter

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        adapter = UserAdapter(listUser)
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {


            override fun onItemClicked(view: View, data: Item) {
                detailUser(view, data)
            }

        })


        with(binding) {

            rvUser.layoutManager = LinearLayoutManager(requireContext())
            rvUser.adapter = adapter

            tlSearch.setEndIconOnClickListener {
                val username = binding.edtSearch.text.toString()
                if (username.isEmpty()) return@setEndIconOnClickListener
                mainViewModel.setSearchUser(username)
                showLoading(true)
                getData()

            }

            swipeRefresh.setOnRefreshListener {
                swipeRefresh.isRefreshing = false
                getData()
            }

        }

    }

    private fun detailUser(view: View, data: Item) {
        val action = SearchFragmentDirections.actionSearchFragmentToDetailUserFragment()
        action.name = data.login
        action.id = data.id
        view.findNavController().navigate(action)
    }

    private fun getData() {
        mainViewModel.getUserGithub().observe(viewLifecycleOwner, { listUsers ->
            if (listUsers != null) {
                showLoading(false)
                adapter.setData(listUsers)
                binding.swipeRefresh.isRefreshing = false
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}