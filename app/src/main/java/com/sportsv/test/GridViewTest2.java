package com.sportsv.test;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.sportsv.R;
import com.sportsv.widget.VeteranToast;

public class GridViewTest2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_test2);

        GridView gridView = (GridView) findViewById(R.id.gridView2);
        gridView.setAdapter(new ImageAdapter(this));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                VeteranToast.makeToast(getApplicationContext(),"클릭한 이미지의 배열 값은 : " + position+", id : "+ id, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public class ImageAdapter extends BaseAdapter{

        private Context mcontext;
        private Integer[] mThumbIds = {
          R.drawable.medal,R.drawable.medal,R.drawable.medal
                ,R.drawable.medal,R.drawable.medal,R.drawable.medal
                ,R.drawable.medal,R.drawable.medal,R.drawable.medal
        };

        public ImageAdapter(Context c) {
            mcontext = c;
        }

        @Override
        public int getCount() {
            return mThumbIds.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;

            if(convertView == null){
                imageView = new ImageView(mcontext);
                imageView.setLayoutParams(new GridView.LayoutParams(85,85));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(8,8,8,8);

            }else{
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mThumbIds[position]);


            return imageView;

            /*  레이아웃을 사용하지 않고 위젯을 붙히기 예제
            TextView textView;
            if(convertView == null){
                textView = new TextView(mcontext);
                textView.setLayoutParams(new GridView.LayoutParams(85,85));
                textView.setBackgroundColor(Color.parseColor("#00FFFFFF"));
                textView.setTextSize(5);
                textView.setPadding(8,8,8,8);
                textView.setText("텍스트");
            }else{
                textView = (TextView) convertView;
            }

            return textView;
            */

        }
    }

}
