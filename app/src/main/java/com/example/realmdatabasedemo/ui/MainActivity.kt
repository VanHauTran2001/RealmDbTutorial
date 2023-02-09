package com.example.realmdatabasedemo.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.realmdatabasedemo.R
import com.example.realmdatabasedemo.databinding.ActivityMainBinding
import com.example.realmdatabasedemo.model.ArticleModel
import com.example.realmdatabasedemo.ui.adapter.ArticleAdapter

class MainActivity : AppCompatActivity(){
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private var adapter : ArticleAdapter ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        binding.btnInsert.setOnClickListener {
            insertArticle()
        }
        onShowListDataRecylerView()
        adapter!!.setOnActionClickListener(object : ArticleAdapter.OnActionClick{
            override fun onCLickDelete(view: View, articleModel: ArticleModel) {
                deleteArticle(articleModel)
            }

        })
    }

    private fun deleteArticle(articleModel: ArticleModel) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Delete")
        builder.setMessage("Are you sure you want to delete article ?")
        builder.setPositiveButton("YES"){dialog ,_->
            viewModel.delete(articleModel.id.orEmpty())
            onShowListDataRecylerView()
            showToast("Delete successfully")
            dialog.dismiss()
        }
        builder.setNegativeButton("NO"){dialog,_ ->
            dialog.dismiss()
        }
        val alterDialog = builder.create()
        alterDialog.show()

    }

    private fun onShowListDataRecylerView() {
        binding.recylerView.layoutManager = LinearLayoutManager(this)
        adapter = ArticleAdapter()
        binding.recylerView.adapter = adapter
        viewModel.allArticle.observe(this){
            adapter!!.submitList(it) //list
        }
        viewModel.getAllData()
    }
    @SuppressLint("NotifyDataSetChanged")
    private fun insertArticle() {
        val title = binding.edtTitle.text.toString().trim()
        val des = binding.edtDes.text.toString().trim()
        viewModel.insert(title,des)
        onShowListDataRecylerView()
        showToast("Add successfully")
        binding.edtTitle.setText("")
        binding.edtDes.setText("")

    }
    private fun showToast(text : String){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show()
    }
}