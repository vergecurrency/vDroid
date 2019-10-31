package vergecurrency.vergewallet.helpers.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class NotificationCenter {

	public static void addObserver(Context context, NotificationType notification, BroadcastReceiver responseHandler) {
		LocalBroadcastManager.getInstance(context).registerReceiver(responseHandler, new IntentFilter(notification.name()));
	}

	public static void removeObserver(Context context, BroadcastReceiver responseHandler) {
		LocalBroadcastManager.getInstance(context).unregisterReceiver(responseHandler);
	}

	public static void postNotification(Context context, NotificationType notification, Bundle params) {
		Intent intent = new Intent(notification.name());
		// insert parameters if needed
		intent.putExtra("notification", params);
		LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
	}
}