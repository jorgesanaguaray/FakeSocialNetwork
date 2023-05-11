package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostEntity
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemProfileBinding

/**
 * Created by Jorge Sanaguaray
 */

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.MyProfileViewHolder>() {

    private var posts: List<PostEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileViewHolder {
        return MyProfileViewHolder(ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyProfileViewHolder, position: Int) {

        val post = posts[position]

        holder.binging.apply {

            mDescription.text = post.description

        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyProfileViewHolder(val binging: ItemProfileBinding) : RecyclerView.ViewHolder(binging.root)

    fun setPosts(posts: List<PostEntity>) {
        this.posts = posts
    }

}