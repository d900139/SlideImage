package yzuic.kevinchang.slideimage;

import android.content.res.Configuration;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout llMain;
    private LinearLayout llVertical;
    private LinearLayout llHorizontal;
    private TextView tv;
    private ViewPager vp;
    private ArrayList<ImageView> ivs = new ArrayList<ImageView>();
    int[] ids = {R.drawable.s1, R.drawable.s2, R.drawable.s3}; // for imageView
    String[] str = {"so easy", "very easy", "super easy"}; // for textView
    boolean flag = false; // false -> vertical, true -> horizontal

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = (LinearLayout) findViewById(R.id.mainLayout);
        llVertical = new LinearLayout(this);
        llVertical.setOrientation(LinearLayout.VERTICAL);
        llHorizontal = new LinearLayout(this);
        llHorizontal.setOrientation(LinearLayout.VERTICAL);

        tv = new TextView(this);
        tv.setTextSize(40);

        vp = new ViewPager(this);
        for (int i = 0; i < ids.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setBackgroundResource(ids[i]);
            ivs.add(iv);
        }

        vp.setAdapter(new myViewPagerAdapter(ivs));
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tv.setText(str[position]);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (!flag) {
            llHorizontal.removeView(tv);
            llHorizontal.removeView(vp);
            llMain.removeView(llHorizontal);
            llVertical.addView(tv);
            llVertical.addView(vp);
            llMain.addView(llVertical);
        } else {
            llVertical.removeView(tv);
            llVertical.removeView(vp);
            llMain.removeView(llVertical);
            llHorizontal.addView(tv);
            llHorizontal.addView(vp);
            llMain.addView(llHorizontal);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        switch (newConfig.orientation) {
            case Configuration.ORIENTATION_LANDSCAPE: //橫
                // llVertical.setOrientation(LinearLayout.HORIZONTAL); //轉換時圖會不見,因重繪時CONTEXT無東西造成
                // setContentView(R.layout.activity_main); 拉兩隻XML,轉橫時換CONTEXT
                flag = true;
                Toast.makeText(MainActivity.this, "horizontal", Toast.LENGTH_LONG).show();
                break;
            case Configuration.ORIENTATION_PORTRAIT: //直
                // llVertical.setOrientation(LinearLayout.VERTICAL);
                flag = false;
                Toast.makeText(MainActivity.this, "vertical", Toast.LENGTH_LONG).show();
                break;
        }
    }

    // ---------------巢狀類別  extends :繼承------------------------ //
    public class myViewPagerAdapter extends PagerAdapter {
        private List<ImageView> list;

        public myViewPagerAdapter(ArrayList<ImageView> ivs) {
            this.list = ivs;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = list.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}

