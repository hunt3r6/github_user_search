package co.id.bismalabs.githubusersearch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.bismalabs.githubusersearch.databinding.ListItemUserBinding
import co.id.bismalabs.githubusersearch.model.Item
import com.bumptech.glide.Glide

class UserAdapter(private val user: ArrayList<Item>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun setData(users: ArrayList<Item>) {
        user.clear()
        user.addAll(users)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ListItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = user.size

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

            itemView.setOnClickListener {
                onItemClickCallback?.onItemClicked(
                    holder.itemView,
                    user[position]
                )
            }
        }


    }

    class ViewHolder(val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(view: View, data: Item)
    }

}