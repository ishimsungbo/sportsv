package com.sportsv.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sportsv.R;
import com.sportsv.vo.Instructor;

import java.util.List;

/**
 * Created by sungbo on 2016-07-11.
 */
public class InsAdapter extends BaseAdapter {

    private static final String TAG = "InsAdapter";

    private List<Instructor> list;

    private Context mcontext;
    private LayoutInflater inflater = null;

    public InsAdapter(List<Instructor> list, Context mcontext) {
        this.list = list;
        this.mcontext = mcontext;
        this.inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        if(convertView==null){

            convertView =  inflater.inflate(R.layout.ingriditem, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.ins_name);
            TextView textView1 = (TextView) convertView.findViewById(R.id.ins_profile);
            textView.setText(list.get(position).getName());
            textView1.setText(list.get(position).getProfile());
        }

        return convertView;
    }
}
