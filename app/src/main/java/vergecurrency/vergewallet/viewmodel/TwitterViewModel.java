package vergecurrency.vergewallet.viewmodel;


import android.app.Application;

import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class TwitterViewModel extends AndroidViewModel {

	public TwitterViewModel(@NonNull Application application) {
		super(application);
	}

	public TweetTimelineListAdapter getTwitterTimeline() {

		UserTimeline vergeTimeline = new UserTimeline.Builder().screenName("vergecurrency").includeReplies(false).includeRetweets(false).maxItemsPerRequest(10).build();

		return new TweetTimelineListAdapter.Builder(getApplication().getApplicationContext()).setTimeline(vergeTimeline).build();
	}

}