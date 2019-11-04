package vergecurrency.vergewallet.wallet

import android.content.Context
import android.os.Bundle

import java.util.Timer

import vergecurrency.vergewallet.Constants
import vergecurrency.vergewallet.helpers.notification.NotificationCenter
import vergecurrency.vergewallet.helpers.notification.NotificationType
import vergecurrency.vergewallet.service.model.FiatRate
import vergecurrency.vergewallet.service.model.PreferencesManager
import vergecurrency.vergewallet.service.repository.RatesClient

class FiatRateTicker(private val context: Context) {

    var isStarted = false
    private val interval: Timer
    var rateInfo: FiatRate? = null

    init {
        interval = Timer()
    }

    fun start() {

        if (isStarted || PreferencesManager.isFirstLaunch) {
            return
        }

        isStarted = true
        fetch()
        interval.scheduleAtFixedRate(
                object : java.util.TimerTask() {
                    override fun run() {
                        fetch()
                    }
                },
                2000, Constants.FETCH_PRICE_TIMEOUT.toLong())

    }

    fun stop() {
        interval.cancel()
        isStarted = false
    }

    private fun fetch() {
        rateInfo = RatesClient.infoBy(PreferencesManager.preferredCurrency!!)
        val b = Bundle()
        b.putSerializable("rateInfo", rateInfo)
        NotificationCenter.postNotification(context, NotificationType.didReceiveFiatRatings, b)
    }
}
