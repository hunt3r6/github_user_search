package co.id.bismalabs.githubusersearch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.bismalabs.githubusersearch.databinding.ListItemUserBinding
import co.id.bismalabs.githubusersearch.model.DetailFollowingItem
import com.bumptech.glide.Glide

class FollowingAdapter(private val user: ArrayList<DetailFollowingItem>) :
    RecyclerView.Adapter<FollowingAdapter.ViewHolder>() {


    class ViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(user[position]) {
                with(binding) {

                    Glide.with(holder.itemView)
                        .load(avatarUrl)
                        .into(imgUser)

                    tvName.text = login
                }
            }
        }
    }

    override fun getItemCount(): Int = user.size

}