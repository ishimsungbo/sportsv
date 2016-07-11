package com.sportsv.test;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sportsv.R;
import com.sportsv.widget.VeteranToast;

import java.util.List;

public class GridViewTest extends AppCompatActivity {

    private static final String TAG = "GridViewTest";

    Activity act = this;
    GridView gridView;
    private List<ResolveInfo> apps;
    private PackageManager pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent mainIntent = new Intent(Intent.ACTION_MAIN,null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        pm = getPackageManager();
        apps = pm.queryIntentActivities(mainIntent,0);

        setContentView(R.layout.activity_grid_view);

        gridView = (GridView) findViewById(R.id.gridView1);
        gridView.setAdapter(new GridAdapter());

    }

    public class GridAdapter extends BaseAdapter{
        LayoutInflater inflater;

        public GridAdapter() {
            inflater = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return apps.size();
        }

        @Override
        public Object getItem(int position) {
            return apps.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            //어뎁터에 매핑할 뷰를 생성
            if(convertView == null){
                convertView = inflater.inflate(R.layout.griditem,parent,false);
            }

            //객체 생성 및 위젯에 정보를 담는다
            final ResolveInfo info = apps.get(position);

            ImageView imageView = (ImageView) convertView.findViewById(R.id.gridimageview);
            TextView textView = (TextView) convertView.findViewById(R.id.gridtextview);

            imageView.setImageDrawable(info.activityInfo.loadIcon(getPackageManager()));
            textView.setText(info.activityInfo.loadLabel(pm).toString());

            //Log.d(TAG,info.activityInfo.packageName+","+info.activityInfo.name);

            //클릭한 어플을 실행하는 이벤트를 작성한다
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_RUN);
                    intent.setComponent(new ComponentName(
                            info.activityInfo.packageName,
                            info.activityInfo.name));
                    act.startActivity(intent);

                    String msg = info.activityInfo.packageName + ","+info.activityInfo.name;
                    VeteranToast.makeToast(getApplicationContext(),"어플 실행 : "+msg, Toast.LENGTH_SHORT).show();


                }
            });

            return convertView;
        }
    }
}
