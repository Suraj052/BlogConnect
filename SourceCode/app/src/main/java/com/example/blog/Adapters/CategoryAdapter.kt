package com.example.blog.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blog.HomeFragmentDirections
import com.example.blog.Models.Post
import com.example.blog.R
import com.example.blog.databinding.CategoryBlogItemBinding
import java.text.SimpleDateFormat
import java.util.*



class CategoryAdapter(var PostList: List<Post>, var CategoryName :String, var flag :Int) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>()
{

    inner class ViewHolder(val binding : CategoryBlogItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val BlogCategory: TextView = itemView.findViewById(R.id.Category_word)
        val BlogTitle: TextView = itemView.findViewById(R.id.Title_word)
        val BlogImage: ImageView = itemView.findViewById(R.id.Image_word)
        val BlogDate: TextView = itemView.findViewById(R.id.Date_word)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CategoryBlogItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            var current = PostList[position]
            holder.BlogCategory.text = current.category
            holder.BlogTitle.text =  current.title
            holder.BlogDate.text = convertDateFormat(current.published)
            Glide.with(holder.itemView.context).load(current.imageUrl).into(holder.BlogImage)

            if(flag==1){
            holder.itemView.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToBlogDetailFragment(current.title,current.content,current.author,current.category,current.imageUrl,current.published)
                Navigation.createNavigateOnClickListener(action).onClick(holder.itemView)
            }}
        }
    }

    override fun getItemCount(): Int {
        return PostList.size
    }
}

fun convertDateFormat(dateString: String): String {
    val inputFormatWithMillis = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"
    val inputFormatWithoutMillis = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val format = if (dateString.contains(".")) {
        inputFormatWithMillis
    } else {
        inputFormatWithoutMillis
    }

    val inputFormat = SimpleDateFormat(format, Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd-MMMM-yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateString)
    return outputFormat.format(date!!)
}