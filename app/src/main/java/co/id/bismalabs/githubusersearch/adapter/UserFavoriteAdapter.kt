package co.id.bismalabs.githubusersearch.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import co.id.bismalabs.githubusersearch.databinding.ItemListUserFavoriteBinding
import co.id.bismalabs.githubusersearch.db.UserHelper
import co.id.bismalabs.githubusersearch.model.User
import com.bumptech.glide.Glide

class UserFavoriteAdapter(private val activity: Activity) :
    RecyclerView.Adapter<UserFavoriteAdapter.UserViewHolder>() {

    private var onItemClickCallBack: OnItemClickCallback? = null

    var user = ArrayList<User>()
        set(user) {
            if (user.size > 0) {
                this.user.clear()
            }
            this.user.addAll(user)
            notifyDataSetChanged()
        }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    fun removeItem(position: Int) {
        user.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, user.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding =
            ItemListUserFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        with(holder) {
            with(user[position]) {
                with(binding) {
                    Glide.with(holder.itemView)
                        .load(avatarUrl)
                        .into(imgUser)

                    tvName.text = username
                }
            }
            itemView.setOnClickListener {
                onItemClickCallBack?.onItemClicked(
                    holder.itemView,
                    user[position]
                )
            }
            binding.btnDelete.setOnClickListener {
                val userHelper: UserHelper = UserHelper.getInstance(activity)
                userHelper.open()
                userHelper.deleteById(user[position].id.toString())
                removeItem(position)
            }
        }
    }

    override fun getItemCount() = user.size

    class UserViewHolder(val binding: ItemListUserFavoriteBinding) :
        RecyclerView.ViewHolder(binding.root)

    interface OnItemClickCallback {
        fun onItemClicked(view: View, data: User)
    }
}

