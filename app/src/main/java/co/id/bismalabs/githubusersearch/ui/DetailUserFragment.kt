package co.id.bismalabs.githubusersearch.ui

import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import co.id.bismalabs.githubusersearch.R
import co.id.bismalabs.githubusersearch.adapter.SectionPagerAdapter
import co.id.bismalabs.githubusersearch.databinding.FragmentDetailUserBinding
import co.id.bismalabs.githubusersearch.db.DatabaseContract
import co.id.bismalabs.githubusersearch.db.UserHelper
import co.id.bismalabs.githubusersearch.viewmodel.MainViewModel
import com.bumptech.glide.Glide

class DetailUserFragment : Fragment() {
    private var _binding: FragmentDetailUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainViewModel: MainViewModel
    private lateinit var userHelper: UserHelper
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userHelper = UserHelper.getInstance(requireContext())
        userHelper.open()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        showLoading(true)

        val userName = DetailUserFragmentArgs.fromBundle(requireArguments()).name
        val id = DetailUserFragmentArgs.fromBundle(requireArguments()).id

        sectionPager(userName)
        checkIsFavorite(id)
        loadData(userName, id)

    }

    private fun loadData(userName: String, id: Int) {
        mainViewModel.loadDetail(userName)
        mainViewModel.getDetailUser().observe(viewLifecycleOwner, {
            showLoading(false)
            Glide.with(requireContext())
                .load(it.avatarUrl)
                .into(binding.imgUser)
            binding.tvName.text = it.name
            binding.tvFollowers.text = "Followers : ${it.followers}"
            binding.tvFollowing.text = "Following : ${it.following}"
            binding.tvRepositories.text = "Repository : ${it.repository}"

            val values = ContentValues()
            values.put(DatabaseContract.UserColumns._ID, it.id)
            values.put(DatabaseContract.UserColumns.USERNAME, it.username)
            values.put(DatabaseContract.UserColumns.NAME, it.name)
            values.put(DatabaseContract.UserColumns.AVATAR, it.avatarUrl)
            values.put(DatabaseContract.UserColumns.PUBLIC_REPOS, it.repository)
            values.put(DatabaseContract.UserColumns.FOLLOWERS, it.followers)
            values.put(DatabaseContract.UserColumns.FOLLOWING, it.following)

            binding.favorite.setOnClickListener {
                if (!isFavorite) {
                    val result = userHelper.insert(values)
                    if (result > 0) {
                        message("$userName add isFavorite")
                    }
                    isFavorite = true
                    stateFavorite(isFavorite)
                } else {
                    val result = userHelper.deleteById(id.toString())
                    if (result > 0) {
                        message("$userName Remove isFavorite")
                    }
                    isFavorite = false
                    stateFavorite(isFavorite)

                }

            }
        })

    }

    private fun checkIsFavorite(id: Int) {
        val cursor: Cursor = userHelper.queryById(id.toString())
        if (cursor.moveToNext()) {
            isFavorite = true
            Log.d("TAG", "isfavorite $isFavorite")
            stateFavorite(isFavorite)
        } else {
            Log.d("TAG", "isfavorite $isFavorite")
            stateFavorite(isFavorite)
        }
    }

    private fun message(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun stateFavorite(favorite: Boolean) {
        if (favorite) {
            binding.favorite.setImageResource(R.drawable.ic_filled_favorite)
        } else {
            binding.favorite.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun sectionPager(name: String) {
        val sectionPagerAdapter = SectionPagerAdapter(name, requireContext(), childFragmentManager)
        binding.viewPager.adapter = sectionPagerAdapter
        binding.tabMode.setupWithViewPager(binding.viewPager)
    }

    private fun showLoading(state: Boolean) {
        with(binding) {
            if (state) {
                lyLoading.shProfile.startShimmer()
                lyLoading.shProfile.visibility = View.VISIBLE
            } else {
                lyLoading.shProfile.stopShimmer()
                lyLoading.shProfile.visibility = View.GONE
            }
        }
    }
}