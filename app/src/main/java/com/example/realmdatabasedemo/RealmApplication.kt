package com.example.realmdatabasedemo

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration


class RealmApplication : Application() {
    private var config : RealmConfiguration ?= null

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        config = RealmConfiguration.Builder().name("realm.db").deleteRealmIfMigrationNeeded()
            .schemaVersion(0).allowWritesOnUiThread(true).allowQueriesOnUiThread(true).build()
        config.let {
            Realm.setDefaultConfiguration(it)
        }
    }
}