package com.example.realmdatabasedemo.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.realmdatabasedemo.model.ArticleModel
import io.realm.Realm
import io.realm.kotlin.deleteFromRealm
import java.util.ArrayList
import java.util.UUID

class MainViewModel : ViewModel(){
    private var realm = Realm.getDefaultInstance()
    val allArticle by lazy { MutableLiveData<List<ArticleModel>>() }
   // var list : ArrayList<ArticleModel> ?= null
    fun insert(title:String,des : String){
        realm.executeTransaction {
            val article = it.createObject(ArticleModel::class.java,UUID.randomUUID().toString())
            article.title = title
            article.description = des
            realm.insertOrUpdate(article)
        }
    }
    fun getAllData(){
        val article = realm.where(ArticleModel::class.java).findAll()
        allArticle.value = realm.copyFromRealm(article)
       // list = realm.copyFromRealm(article) as ArrayList<ArticleModel>?
    }
    fun delete(id:String){
        val article =realm.where(ArticleModel::class.java).equalTo("id",id).findFirst()
        realm.executeTransaction {
            article!!.deleteFromRealm()
        }
    }
    fun update(id:String,title: String,des: String){
        val article = realm.where(ArticleModel::class.java).equalTo("id",id).findFirst()
        realm.executeTransaction {
            article?.title = title
            article?.description = des
            realm.insertOrUpdate(article!!)
        }
    }
}