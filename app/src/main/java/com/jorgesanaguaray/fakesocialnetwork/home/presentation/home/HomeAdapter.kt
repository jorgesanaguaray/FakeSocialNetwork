package com.jorgesanaguaray.fakesocialnetwork.home.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.models.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.ocpsoft.prettytime.PrettyTime
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Jorge Sanaguaray
 */

class HomeAdapter(
    private val viewModel: HomeViewModel
) : RecyclerView.Adapter<HomeAdapter.MyHomeViewHolder>() {

    private val scope = CoroutineScope(Dispatchers.Main)

    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHomeViewHolder {
        return MyHomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHomeViewHolder, position: Int) {

        val post = posts[position]

        setUserInfo(post.userId, holder.binding)
        setPostInfo(post, holder.binding)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyHomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

    private fun setUserInfo(userId: Int, binding: ItemHomeBinding) {

        scope.launch {
            val user = viewModel.getUserById(userId)

            binding.mProfilePicture.load(user.profilePicture) {
                transformations(CircleCropTransformation())
                placeholder(R.drawable.ic_profile)
                error(R.drawable.ic_profile)
                crossfade(true)
                crossfade(400)
            }
            binding.mUsername.text = user.username

            if (user.isVerified) binding.mVerified.visibility = View.VISIBLE
            else binding.mVerified.visibility = View.GONE
        }

    }

    private fun setPostInfo(post: Post, binding: ItemHomeBinding) {

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