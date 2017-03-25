package com.example.raamkumr.slider;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    private ViewPager viewPager;
    private viewpageradapter viewpageradapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip,btnNext;
    boolean mPROFILE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences=MainActivity.this.getPreferences(MODE_PRIVATE);
        mPROFILE=sharedPreferences.getBoolean("saveProfile",false);
        if(!mPROFILE)
        {
            saveProfile();
            viewPager = (ViewPager)findViewById(R.id.viewpager);
            dotsLayout=(LinearLayout)findViewById(R.id.layoutDots);
            btnSkip=(Button)findViewById(R.id.btn_skip);
            btnNext=(Button)findViewById(R.id.btn_next);
            layouts=new int[]{R.layout.screen1,R.layout.screen2,R.layout.screen3};
            addButtonDots(0);
            viewpageradapter=new viewpageradapter();
            viewPager.setAdapter(viewpageradapter);
            viewPager.addOnPageChangeListener(viewpagerPagerChangeListner);
        }else
        {
            launchHomeScreen();
        }
    }

    public void btnSkipClick(View v)
    {
        launchHomeScreen();
    }

    public void btnNextClick(View v)
    {
        int current=getItem(1);
        if(current<layouts.length)
        {
            viewPager.setCurrentItem(current);
        } else
        {
            launchHomeScreen();
        }
    }

    ViewPager.OnPageChangeListener viewpagerPagerChangeListner=new ViewPager.OnPageChangeListener()
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addButtonDots(position);
            if(position == layouts.length-1)
            {
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.GONE);
            }else{
                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


    private void addButtonDots(int CurrentPage)
    {
        dots=new TextView[layouts.length];
        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i]=new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            dotsLayout.addView(dots[i]);
        }
        if(dots.length>0)
        {
            dots[CurrentPage].setTextColor(getResources().getColor(R.color.dot_active));
        }
    }

    private int getItem(int i)
    {
        return  viewPager.getCurrentItem()+i;
    }

    private void launchHomeScreen()
    {
        startActivity(new Intent(this,MainActivity2.class));
        finish();
    }

    public class viewpageradapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        public viewpageradapter()
        {

        }
        @Override
        public Object instantiateItem(ViewGroup container,int position)
        {
            layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(layouts[position],container,false);
            container.addView(view);
            return view;
        }
        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container,int position,Object object)
        {
            View view=(View)object;
            container.removeView(view);
        }
    }

    public void saveProfile()
    {
        SharedPreferences sharedPreferences=MainActivity.this.getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("saveProfile",true);
        editor.apply();
    }
}
