package br.com.apprumo

import android.app.Application
import dev.hotwire.core.bridge.BridgeComponentFactory
import dev.hotwire.core.bridge.KotlinXJsonConverter
import dev.hotwire.core.config.Hotwire
import dev.hotwire.core.turbo.config.PathConfiguration
import dev.hotwire.navigation.config.defaultFragmentDestination
import dev.hotwire.navigation.config.registerBridgeComponents
import dev.hotwire.navigation.config.registerFragmentDestinations
import dev.hotwire.navigation.fragments.HotwireWebFragment
import kotlinx.serialization.json.Json

class HotwireApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        configureApp()
    }

    private fun configureApp() {
        // Configura o conversor JSON para o Hotwire
        Hotwire.config.jsonConverter = KotlinXJsonConverter(Json {
            ignoreUnknownKeys = true
            coerceInputValues = true
        })

        // Carrega o arquivo de configuração dos assets corretamente
        Hotwire.loadPathConfiguration(
            context = this,
            location = PathConfiguration.Location(
                assetFilePath = "configurations/android_v1.json"
            )
        )

        Hotwire.defaultFragmentDestination = WebFragment::class
        Hotwire.registerFragmentDestinations(
            WebFragment::class,
            HotwireWebFragment::class
        )

        // Registra o componente de bridge para o token de push
        Hotwire.registerBridgeComponents(
            BridgeComponentFactory("push-token", ::PushTokenBridgeComponent)
        )
    }
}
