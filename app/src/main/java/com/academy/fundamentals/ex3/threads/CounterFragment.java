package com.academy.fundamentals.ex3.threads;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.academy.fundamentals.ex3.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CounterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CounterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CounterFragment extends Fragment implements View.OnClickListener{
    public final static String FRAGMENT_TYPE = "fragment_type";

    private IAsyncTaskEvents mCallbackListener;

    private Button mBtnCreate;
    private Button mBtnStart;
    private Button mBtnCancel;
    private TextView mTxtValue;
    private TextView mTxtValue2;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CounterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CounterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CounterFragment newInstance(String param1, String param2) {
        CounterFragment fragment = new CounterFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_counter, container, false);

        mBtnCreate = rootView.findViewById(R.id.button_Create);
        this.mBtnStart = rootView.findViewById(R.id.button_Start);
        this.mBtnCancel = rootView.findViewById(R.id.button_Cancel);
        mTxtValue = rootView.findViewById(R.id.counter_status);
        mTxtValue2 = rootView.findViewById(R.id.counter_replacement);

        this.mBtnCreate.setOnClickListener(this);
        this.mBtnStart.setOnClickListener(this);
        this.mBtnCancel.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String fragmentText = this.getArguments().getString(FRAGMENT_TYPE).toString();
            // this.mTxtValue.setText(fragmentText);
        }


        return rootView ;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IAsyncTaskEvents) {
            this.mCallbackListener = (IAsyncTaskEvents) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if(!isAdded() || this.mCallbackListener == null) {
            return;
        }

        switch (v.getId()) {
            case R.id.button_Create:
                this.mCallbackListener.createAsyncTask();
                break;
            case R.id.button_Start:
                this.mCallbackListener.startAsyncTask();
                break;
            case R.id.button_Cancel:
                this.mCallbackListener.cancelAsyncTask();
                break;
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void updateFragmentText( String aCounterStatusText ){
        if (mTxtValue != null){
            mTxtValue.setText(aCounterStatusText);
            mTxtValue2.setText(aCounterStatusText);
        }
        Log.d("*****", "updateFragmentText: " + aCounterStatusText);

    }
}
