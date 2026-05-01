package br.com.apprumo

import android.app.NotificationManager
import android.content.Context
import dev.hotwire.core.bridge.BridgeComponent
import dev.hotwire.core.bridge.Message
import dev.hotwire.core.bridge.BridgeDelegate
import dev.hotwire.navigation.destinations.HotwireDestination

class ClearNotificationBridgeComponent(
    name: String,
    private val bridgeDelegate: BridgeDelegate<HotwireDestination>
) : BridgeComponent<HotwireDestination>(name, bridgeDelegate) {

    override fun onReceive(message: Message) {
        when (message.event) {
            "clear-notification" -> clearNotifications()
            else -> {}
        }
    }

    private fun clearNotifications() {
        val context = bridgeDelegate.destination.fragment.context
        val notificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
        notificationManager?.cancelAll()
    }
}
