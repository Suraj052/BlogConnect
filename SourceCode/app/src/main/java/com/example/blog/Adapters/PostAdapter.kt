package com.example.blog.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blog.HomeFragmentDirections
import com.example.blog.Models.PostGetModel
import com.example.blog.R
import com.example.blog.databinding.AllBlogItemBinding
import java.util.*

class PostAdapter (var PostList : List<PostGetModel>) : RecyclerView.Adapter<PostAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(AllBlogItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            var current = PostList[position]

            holder.Title.text = current.title
            holder.Category.text = current.category
            Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.Image)

            holder.Image.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToBlogDetailFragment(current.title,current.content,current.author,current.category,current.imageUrl,current.published)
                Navigation.createNavigateOnClickListener(action).onClick(holder.Image)
            }
        }
    }

    override fun getItemCount(): Int {
        return PostList.size
    }

    inner class ViewHolder(val binding : AllBlogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val Title: TextView = itemView.findViewById(R.id.Title_text)
        val Category: TextView = itemView.findViewById(R.id.Category_text)
        val Image: ImageView = itemView.findViewById(R.id.Image_url)
    }



}


