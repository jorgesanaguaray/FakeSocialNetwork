package com.jorgesanaguaray.fakesocialnetwork.home.presentation

import android.content.Context
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemHomeBinding
import java.util.Calendar
import java.util.Locale

/**
 * Created by Jorge Sanaguaray
 */

class HomeAdapter(

    private val homeViewModel: HomeViewModel,
    private val context: Context,
    private val editClick:(Int) -> Unit

) : RecyclerView.Adapter<HomeAdapter.MyHomeViewHolder>() {

    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHomeViewHolder {
        return MyHomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHomeViewHolder, position: Int) {

        // Get user id from SharedPreferences
        val sharedPreferences = context.getSharedPreferences(context.getString(R.string.user_id), Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt("id", 0)

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

            if (post.userId == userId) {
                holder.binding.mEdit.visibility = View.VISIBLE
                holder.binding.mFollow.visibility = View.GONE
            } else {
                holder.binding.mEdit.visibility = View.GONE
                holder.binding.mFollow.visibility = View.VISIBLE
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

    class MyHomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

    private fun setUserOfPost(userId: Int, binding: ItemHomeBinding) {

        homeViewModel.getUserById(userId) {

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