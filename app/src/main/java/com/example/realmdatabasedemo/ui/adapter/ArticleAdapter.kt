package com.example.realmdatabasedemo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.realmdatabasedemo.databinding.ItemArticleBinding
import com.example.realmdatabasedemo.model.ArticleModel

class ArticleAdapter : ListAdapter<ArticleModel, ArticleAdapter.ArticleViewHolder>(MyDiffUtil){
    private var onActionClick : OnActionClick ?= null

    object MyDiffUtil : DiffUtil.ItemCallback<ArticleModel>(){
        override fun areItemsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
           return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ArticleModel, newItem: ArticleModel): Boolean {
            return oldItem.id == newItem.id
        }

    }

        class ArticleViewHolder(private val binding:ItemArticleBinding) : RecyclerView.ViewHolder(binding.root){
            fun bindView(articleModel: ArticleModel,onActionClick:OnActionClick?){
                binding.txtTitle.text = articleModel.title
                binding.txtDes.text = articleModel.description
                binding.imgDelete.setOnClickListener {
                    onActionClick?.onCLickDelete(it,articleModel)
                }
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ArticleViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bindView(article,onActionClick)
    }
    fun setOnActionClickListener(onActionClick: OnActionClick){
        this.onActionClick = onActionClick
    }
    interface OnActionClick{
        fun onCLickDelete(view:View,articleModel: ArticleModel)
    }
}