package com.jorgesanaguaray.fakesocialnetwork.home.presentation.profile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.User
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemProfileBinding
import org.ocpsoft.prettytime.PrettyTime
import java.util.Calendar
import java.util.Date
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

    override fun onBindViewHolder(holder: MyProfileViewHolder, position: Int) {

        val post = posts[position]

        setUserInfo(holder.binding)
        setPostInfo(post, holder.binding)

        holder.binding.mEdit.setOnClickListener {
            editClick(post.id!!)
        }

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyProfileViewHolder(val binding: ItemProfileBinding) : RecyclerView.ViewHolder(binding.root)

    fun setUser(user: User?) {
        this.user = user
    }

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

    private fun setUserInfo(binding: ItemProfileBinding) {

        binding.apply {

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

        }

    }

    private fun setPostInfo(post: Post, binding: ItemProfileBinding) {

        binding.apply {

            val calendar = Calendar.getInstance(Locale.getDefault())
            calendar.timeInMillis = post.date.toLong()

            val publicationDate: Date = calendar.time
            val prettyTime = PrettyTime()
            val dateFormat: String = prettyTime.format(publicationDate)

            mImagePost.load(post.image) {
                crossfade(true)
                crossfade(400)
            }
            mDate.text = dateFormat
            mDescription.text = post.description

        }

    }

}