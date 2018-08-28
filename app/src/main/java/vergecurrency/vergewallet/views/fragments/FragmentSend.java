package vergecurrency.vergewallet.views.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vergecurrency.vergewallet.R;

public class FragmentSend extends Fragment {


    public FragmentSend() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        /*
        TODO: Get the balance before inflating. If the balance is > 0, inflate the fragment_send_balance view, otherwise inflate the fragment_send_nobalance view.
         */
        if (balance > 0){ //This is just for testing. Of course there will be something here.
            return inflater.inflate(R.layout.fragment_send_balance, container, false);
        } else return inflater.inflate(R.layout.fragment_send_nobalance, container, false);
    }

    double balance = 10d;
}
