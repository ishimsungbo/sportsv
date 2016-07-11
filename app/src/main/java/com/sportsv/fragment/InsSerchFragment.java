package com.sportsv.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.sportsv.R;
import com.sportsv.adapter.InsAdapter;
import com.sportsv.dao.InstructorService;
import com.sportsv.retropit.ServiceGenerator;
import com.sportsv.vo.Instructor;
import com.sportsv.widget.VeteranToast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sungbo on 2016-07-11.
 */
public class InsSerchFragment extends Fragment {

    //유저정보를 넘겨서 크레덴셜을 생성해준다...

    private static final String TAG = "ListFragment";
    private static Context mContext;
    private GridView mGridView;
    private List<Instructor> instructorList;

    public InsSerchFragment() {
    }

    @SuppressLint("ValidFragment")
    public InsSerchFragment(Context context) {
        mContext = context;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach() ========================================");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() ========================================");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView() ========================================");

        View root = inflater.inflate(R.layout.ins_fragment,container,false);
        mGridView = (GridView) root.findViewById(R.id.grid_view);

        //액티비티의 값받기
        Bundle bundle = getArguments();
        if(bundle != null){
            String name = bundle.getString("username");
            Log.d(TAG,"넘어온 이름은 : " + name);
        }



        return root;
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() ========================================");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated() ========================================");

        Button button = (Button) getActivity().findViewById(R.id.btnSerchIns);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                VeteranToast.makeToast(mContext,"강사리스트를 가져옵니다", Toast.LENGTH_SHORT).show();

                InstructorService service = ServiceGenerator.createService(InstructorService.class);
                final Call<List<Instructor>> call = service.getInstructorList();

                call.enqueue(new Callback<List<Instructor>>() {
                    @Override
                    public void onResponse(Call<List<Instructor>> call, Response<List<Instructor>> response) {
                        if(response.isSuccessful()){
                            instructorList = response.body();
                            mGridView.setAdapter(new InsAdapter(instructorList,mContext));
                        }else{
                            Log.d(TAG,"서버 통신중 에러 발생");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Instructor>> call, Throwable t) {
                        Log.d(TAG,"서버 통신중 장애 발생");
                        t.printStackTrace();
                    }
                });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() ========================================");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() ========================================");
    }


    public static InsSerchFragment newInstance(String userName){
        InsSerchFragment ins = new InsSerchFragment();

        Bundle bundle = new Bundle();
        bundle.putString("username",userName);
        ins.setArguments(bundle);

        return ins;
    }

}
