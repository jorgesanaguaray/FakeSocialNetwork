package com.jorgesanaguaray.fakesocialnetwork.search.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemSearchBinding

/**
 * Created by Jorge Sanaguaray
 */

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MySearchViewHolder>() {

    private var users: List<User> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MySearchViewHolder {
        return MySearchViewHolder(ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MySearchViewHolder, position: Int) {

        val user = users[position]

        holder.binding.apply {

            mProfilePicture.load(user.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }
            mUsername.text = user.username

            if (user.isVerified) mVerified.visibility = View.VISIBLE
            else mVerified.visibility = View.GONE

        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    class MySearchViewHolder(val binding: ItemSearchBinding) : RecyclerView.ViewHolder(binding.root)

    fun setUsers(users: List<User>) {
        this.users = users
    }

}