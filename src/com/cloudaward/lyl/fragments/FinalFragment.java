package com.cloudaward.lyl.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cloudaward.lyl.R;

public class FinalFragment extends BaseFragment {

  private static final class Holder {
    private static final FinalFragment INSTANCE = new FinalFragment();
  }

  private FinalFragment() {
    super();
  }

  public static FinalFragment getInstance() {
    return Holder.INSTANCE;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_latest, container, false);
    return view;
  }

}
