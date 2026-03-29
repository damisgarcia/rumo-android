package br.com.userumo

import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import dev.hotwire.core.bridge.BridgeComponent
import dev.hotwire.core.bridge.Message
import dev.hotwire.core.bridge.BridgeDelegate
import dev.hotwire.navigation.destinations.HotwireDestination
import kotlinx.serialization.Serializable

class PushTokenBridgeComponent(
    name: String,
    delegate: BridgeDelegate<HotwireDestination>
) : BridgeComponent<HotwireDestination>(name, delegate) {

    override fun onReceive(message: Message) {
        when (message.event) {
            "push-token" -> sendPushToken()
            else -> {}
        }
    }

    private fun sendPushToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val token = task.result
                val deviceName = "${Build.MANUFACTURER} ${Build.MODEL}"
                
                Log.d("PushTokenBridge", "Enviando token: $token para o dispositivo: $deviceName")
                
                replyTo("push-token", PushTokenData(
                    token = token,
                    name = deviceName,
                    platform = "google"
                ))
            } else {
                Log.e("PushTokenBridge", "Erro ao buscar token", task.exception)
            }
        }
    }

    @Serializable
    data class PushTokenData(
        val token: String,
        val name: String,
        val platform: String
    )
}
