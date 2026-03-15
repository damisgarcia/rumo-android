package br.com.userumo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import dev.hotwire.core.turbo.config.PathConfigurationProperties
import dev.hotwire.core.turbo.visit.VisitAction
import dev.hotwire.navigation.destinations.HotwireDestinationDeepLink
import dev.hotwire.navigation.fragments.HotwireWebFragment

@HotwireDestinationDeepLink(uri = "hotwire://fragment/web")
class WebFragment : HotwireWebFragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbarForNavigation()?.visibility = View.GONE
    }

    @SuppressLint("InflateParams")
    override fun createProgressView(location: String): View {
        return layoutInflater.inflate(R.layout.hotwire_progress, null)
    }

    override fun customNavigationOptions(
        newLocation: String,
        newPathProperties: PathConfigurationProperties,
        action: VisitAction
    ): NavOptions? {
        return NavOptions.Builder()
            .setEnterAnim(0)
            .setExitAnim(0)
            .setPopEnterAnim(0)
            .setPopExitAnim(0)
            .build()
    }
}
