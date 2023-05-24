package com.jorgesanaguaray.fakesocialnetwork.home.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.jorgesanaguaray.fakesocialnetwork.R
import com.jorgesanaguaray.fakesocialnetwork.core.domain.Post
import com.jorgesanaguaray.fakesocialnetwork.databinding.ItemHomeBinding
import org.ocpsoft.prettytime.PrettyTime
import java.text.NumberFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Created by Jorge Sanaguaray
 */

class HomeAdapter(

    private val homeViewModel: HomeViewModel,

) : RecyclerView.Adapter<HomeAdapter.MyHomeViewHolder>() {

    private var posts: List<Post> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHomeViewHolder {
        return MyHomeViewHolder(ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyHomeViewHolder, position: Int) {

        val post = posts[position]

        setUserInfo(post.userId, holder.binding)

        setPostInfo(post, holder.binding, holder.itemView.context)

    }

    override fun getItemCount(): Int {
        return posts.size
    }

    class MyHomeViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    fun setPosts(posts: List<Post>) {
        this.posts = posts
    }

    private fun setUserInfo(userId: Int, binding: ItemHomeBinding) {

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

    @SuppressLint("SetTextI18n")
    private fun setPostInfo(post: Post, binding: ItemHomeBinding, context: Context) {

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
            mLikes.text = numberFormat(post.likes)
            mCommentsAndShares.text = "${numberFormat(post.comments)} ${context.getString(R.string.comments)} • ${numberFormat(post.shares)} ${context.getString(R.string.shares)}"

        }

    }

    private fun numberFormat(likes: Long): String {

        val numberFormat = NumberFormat.getInstance()

        numberFormat.maximumFractionDigits = 1
        numberFormat.minimumFractionDigits = 0

        return when {

            likes < 1000 -> numberFormat.format(likes)
            likes < 1000000 -> numberFormat.format(likes / 1000.0) + "K"
            likes < 1000000000 -> numberFormat.format(likes / 1000000.0) + "M"
            else -> numberFormat.format(likes / 1000000000.0) + "B"

        }
    }

}