package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemProfileBinding
import java.util.Calendar
import java.util.Locale

/**
 * Created by Jorge Sanaguaray
 */

class ProfileAdapter(

    private val profileViewModel: ProfileViewModel,
    private val editClick:(Int) -> Unit

) : RecyclerView.Adapter<ProfileAdapter.MyProfileViewHolder>() {

    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileViewHolder {
        return MyProfileViewHolder(ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyProfileViewHolder, position: Int) {

        val post = posts[position]

        holder.binding.apply {

            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.timeInMillis = post.date.toLong()
            val date = DateFormat.format("dd/MM/yyyy", calendar).toString()

            mDate.text = date
            mDescription.text = post.description

            mImagePost.load(post.image) {
                crossfade(true)
                crossfade(400)
            }

            mEdit.setOnClickListener {
                editClick(post.id!!)
            }

        }

        setUserOfPost(post.userId, holder.binding)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyProfileViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root)

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

    private fun setUserOfPost(userId: Int, binding: ItemProfileBinding) {

        profileViewModel.getUserById2(userId) {

            binding.mProfilePicture.load(it.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }
            binding.mUsername.text = it.username

            if (it.isVerified) binding.mVerified.visibility = View.VISIBLE
            else binding.mVerified.visibility = View.GONE

        }

    }

}