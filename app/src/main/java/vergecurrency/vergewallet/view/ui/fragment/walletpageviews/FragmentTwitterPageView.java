package vergecurrency.vergewallet.view.ui.fragment.walletpageviews;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import vergecurrency.vergewallet.R;
import vergecurrency.vergewallet.viewmodel.TwitterViewModel;

public class FragmentTwitterPageView extends Fragment {

	private View rootView;
	private ListView tweetsView;
	private SwipeRefreshLayout pullRefreshView;
	private TwitterViewModel mViewModel;

	public FragmentTwitterPageView() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mViewModel = ViewModelProviders.of(this).get(TwitterViewModel.class);

		if (rootView == null) {

			// Inflate the layout for this fragment
			rootView = inflater.inflate(R.layout.fragment_pageview_twitter, container, false);

			//initComponents();
		}

		return rootView;
	}

	private void initComponents() {
		tweetsView = rootView.findViewById(R.id.twitter_listview);
		pullRefreshView = rootView.findViewById(R.id.tweets_refresh_pull);
		pullRefreshView.setOnRefreshListener(pullRefreshListener());
		getLastTweets();

	}


	private void getLastTweets() {
		tweetsView.setAdapter(mViewModel.getTwitterTimeline());
	}

	private SwipeRefreshLayout.OnRefreshListener pullRefreshListener() {
		return () -> {
			getLastTweets();
			pullRefreshView.setRefreshing(false);
		};
	}


}
