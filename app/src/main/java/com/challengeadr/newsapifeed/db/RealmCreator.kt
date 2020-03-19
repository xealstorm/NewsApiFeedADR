package com.challengeadr.newsapifeed.db

import android.content.Context
import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import io.realm.Realm
import io.realm.RealmConfiguration

object RealmCreator {
    val REALM_FILE_NAME = "db.realm"
    fun create(context: Context): Realm {
        Stetho.initialize(
            Stetho.newInitializerBuilder(context)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(context))
                .enableWebKitInspector(
                    RealmInspectorModulesProvider.builder(context)
                        .withMetaTables()
                        .build()
                )
                .build()
        )
        Realm.init(context)
        val configuration = RealmConfiguration.Builder()
            .name(REALM_FILE_NAME)
            .modules(RealmModule())
            .schemaVersion(1)
            .build()
        return Realm.getInstance(configuration)
    }
}
