package vergecurrency.vergewallet.wallet;

import android.content.Context;
import android.os.Bundle;

import java.util.Timer;

import vergecurrency.vergewallet.Constants;
import vergecurrency.vergewallet.helpers.Notification.NotificationCenter;
import vergecurrency.vergewallet.helpers.Notification.NotificationType;
import vergecurrency.vergewallet.service.model.FiatRate;
import vergecurrency.vergewallet.service.model.PreferencesManager;
import vergecurrency.vergewallet.service.repository.RatesClient;

public class FiatRateTicker {

	private boolean started = false;
	private Timer interval;
	private FiatRate rateInfo;
	private Context context;

	public FiatRateTicker(Context c) {
		context = c;
		interval = new Timer();
	}

	public void start() {

		if (started || PreferencesManager.isFirstLaunch()) {
			return;
		}

		started = true;
		fetch();
		interval.scheduleAtFixedRate(
				new java.util.TimerTask() {
					@Override
					public void run() {
						fetch();
					}
				},
				2000, Constants.FETCH_PRICE_TIMEOUT);

	}

	public void stop() {
		interval.cancel();
		started = false;
	}

	private void fetch() {
		rateInfo = RatesClient.infoBy(PreferencesManager.getPreferredCurrency());
		Bundle b = new Bundle();
		b.putSerializable("rateInfo", rateInfo);
		NotificationCenter.postNotification(context, NotificationType.didReceiveFiatRatings, b);
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}

	public FiatRate getRateInfo() {
		return rateInfo;
	}

	public void setRateInfo(FiatRate rateInfo) {
		this.rateInfo = rateInfo;
	}
}
