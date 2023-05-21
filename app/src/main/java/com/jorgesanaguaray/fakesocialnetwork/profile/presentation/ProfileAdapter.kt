package com.jorgesanaguaray.fakesocialnetwork.profile.presentation

import android.annotation.SuppressLint
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemProfileBinding
import java.util.Calendar
import java.util.Locale

/**
 * Created by Jorge Sanaguaray
 */

class ProfileAdapter(

    private val editClick:(Int) -> Unit

) : RecyclerView.Adapter<ProfileAdapter.MyProfileViewHolder>() {

    private var user: User? = null
    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyProfileViewHolder {
        return MyProfileViewHolder(ItemProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyProfileViewHolder, position: Int) {

        val post = posts[position]

        holder.binding.apply {

            // Set user info in views
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

            // Set post info in views
            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.timeInMillis = post.date.toLong()
            val date = DateFormat.format("dd/MM/yyyy", calendar).toString()

            mDate.text = date
            mDescription.text = post.description
            mLikes.text = post.likes
            mCommentsAndShares.text = "${post.comments} ${holder.itemView.context.getString(R.string.comments)} • ${post.shares} ${holder.itemView.context.getString(R.string.shares)}"

            mImagePost.load(post.image) {
                crossfade(true)
                crossfade(400)
            }

            mEdit.setOnClickListener {
                editClick(post.id!!)
            }

        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyProfileViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root)

    fun setUser(user: User) {
        this.user = user
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

}