package co.id.bismalabs.githubusersearch.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import co.id.bismalabs.githubusersearch.adapter.UserFavoriteAdapter
import co.id.bismalabs.githubusersearch.databinding.FragmentFavoriteBinding
import co.id.bismalabs.githubusersearch.db.UserHelper
import co.id.bismalabs.githubusersearch.helper.MappingHelper
import co.id.bismalabs.githubusersearch.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private lateinit var userHelper: UserHelper
    private lateinit var userFavoriteAdapter: UserFavoriteAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userHelper = UserHelper.getInstance(requireContext())
        userHelper.open()

        binding.rvUser.layoutManager = LinearLayoutManager(requireContext())
        binding.rvUser.setHasFixedSize(true)
        userFavoriteAdapter = UserFavoriteAdapter(requireActivity())
        binding.rvUser.adapter = userFavoriteAdapter

        loadUserAsync()


    }

    private fun loadUserAsync() {
        GlobalScope.launch(Dispatchers.Main) {
            binding.progressbar.visibility = View.VISIBLE
            val deferredUsers = async(Dispatchers.IO) {
                val cursor = userHelper.queryAll()
                MappingHelper.mapCursorToArrayList(cursor)
            }
            binding.progressbar.visibility = View.INVISIBLE
            val users = deferredUsers.await()
            if (users.size > 0) {
                userFavoriteAdapter.user = users
                userFavoriteAdapter.setOnItemClickCallback(object :
                    UserFavoriteAdapter.OnItemClickCallback {
                    override fun onItemClicked(view: View, data: User) {
                        detailUser(view, data)
                    }
                })
            } else {
                userFavoriteAdapter.user = ArrayList()
                Toast.makeText(requireContext(), "Data Kosong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun detailUser(view: View, data: User) {
        val action = FavoriteFragmentDirections.actionNavigationFavoriteToDetailUserFragment()
        data.let {
            action.id = it.id!!
            action.name = it.username!!
        }
        view.findNavController().navigate(action)


    }
}