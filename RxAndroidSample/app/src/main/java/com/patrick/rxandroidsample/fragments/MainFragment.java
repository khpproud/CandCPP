package com.patrick.rxandroidsample.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.patrick.rxandroidsample.R;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class MainFragment extends Fragment {
    public static final String TAG = MainFragment.class.getSimpleName();

    private Unbinder mUnbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
    }

    @OnClick(R.id.btn_click_observer)
    void demoHello() {
        startDemo(new OnClickFragment());
    }

    @OnClick(R.id.btn_debounce)
    void demoDebounce() {
        startDemo(new DebounceFragment());
    }

    @OnClick(R.id.btn_debounce_search)
    void demoDebounceSearch() {
        startDemo(new DebounceSearchFragment());
    }

    private void startDemo(Fragment fragment) {
        final String TAG = fragment.getClass().getSimpleName();
        getActivity().getSupportFragmentManager().beginTransaction()
                .addToBackStack(TAG)
                .replace(android.R.id.content, fragment, TAG)
                .commit();
    }
}
