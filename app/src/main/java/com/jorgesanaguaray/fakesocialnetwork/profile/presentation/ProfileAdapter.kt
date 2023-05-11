package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.data.local.PostEntity
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemProfileBinding

/**
 * Created by Jorge Sanaguaray
 */

class ProfileAdapter : RecyclerView.Adapter<ProfileAdapter.MyProfileViewHolder>() {

    private var user: User? = null
    private var posts: List<PostEntity> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileViewHolder {
        return MyProfileViewHolder(ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyProfileViewHolder, position: Int) {

        val post = posts[position]

        holder.binging.apply {

            mProfilePicture.load(user!!.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }
            mUsername.text = user!!.username

            if (user!!.isVerified) mVerified.visibility = View.VISIBLE
            else mVerified.visibility = View.GONE

            mDescription.text = post.description

        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyProfileViewHolder(val binging: ItemProfileBinding) : RecyclerView.ViewHolder(binging.root)

    fun setUser(user: User) {
        this.user = user
    }

    fun setPosts(posts: List<PostEntity>) {
        this.posts = posts
    }

}