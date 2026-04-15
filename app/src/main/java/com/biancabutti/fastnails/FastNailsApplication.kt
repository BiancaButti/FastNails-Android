package com.biancabutti.fastnails

import android.app.Application
import com.biancabutti.fastnails.core.AppContainer
import com.biancabutti.fastnails.core.SupabaseClientProvider

class FastNailsApplication : Application() {

    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        val supabaseClient = SupabaseClientProvider.create()
        appContainer = AppContainer(supabaseClient)
    }
}
