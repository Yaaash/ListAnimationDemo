package com.learning.katyal.yashika.listanimationdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListActivity extends Activity {

    public final static Integer LIST_LENGTH = 10;
    public boolean FLAG_NEW_ELEMENT = false;
    @Bind(R.id.namesList)
    ListView nameList;
    @Bind(R.id.addNames)
    Button addButton;
    private DisplayMetrics metrics;
    private int mode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        ButterKnife.bind(this);

        nameList.setFadingEdgeLength(0);
        final ArrayList<String> strings = new ArrayList<String>();
        for (int i = 0; i < LIST_LENGTH; i++) {
            strings.add("Item:#" + (i + 1));
        }

        final MainAdapter mAdapter = new MainAdapter(this, strings, metrics);
        nameList.setAdapter(mAdapter);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strings.add(0, "new");
                FLAG_NEW_ELEMENT = true;
                mAdapter.notifyDataSetChanged();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);
        boolean result = super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, 1, 0, "TranslateAnimation1");
        menu.add(Menu.NONE, 2, 0, "TranslateAnimation2");
        menu.add(Menu.NONE, 3, 0, "ScaleAnimation");
        menu.add(Menu.NONE, 4, 0, "Fade in");
        menu.add(Menu.NONE, 5, 0, "Hyper Space in");
        menu.add(Menu.NONE, 6, 0, "hyper_space_out");
        menu.add(Menu.NONE, 7, 0, "wave_scale");
        menu.add(Menu.NONE, 8, 0, "push_left_in");
        menu.add(Menu.NONE, 9, 0, "push_left_out");
        menu.add(Menu.NONE, 10, 0, "push_up_in");
        menu.add(Menu.NONE, 11, 0, "push_up_out");
        menu.add(Menu.NONE, 12, 0, "shake");
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mode = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    public class MainAdapter extends ArrayAdapter<String> {
        private Context context;
        private LayoutInflater mInflater;
        private ArrayList<String> strings;
        private DisplayMetrics metrics_;

        public MainAdapter(Context context, ArrayList<String> strings,
                           DisplayMetrics metrics) {
            super(context, 0, strings);
            this.context = context;
            this.mInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.strings = strings;
            this.metrics_ = metrics;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            final String str = this.strings.get(position);
            final Holder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(
                        android.R.layout.simple_list_item_1, null);
                convertView.setBackgroundColor(getResources().getColor(R.color.list_bg));

                holder = new Holder();
                holder.textview = (TextView) convertView
                        .findViewById(android.R.id.text1);
                holder.textview.setTextColor(getResources().getColor(R.color.textColor));
                holder.textview.setBackgroundResource(R.drawable.background);

                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }

            holder.textview.setText(str);

            Animation animation = null;

            if (position == 0) {
                switch (mode) {
                    case 1:
                        animation = new TranslateAnimation(metrics_.widthPixels / 2, 0,
                                0, 0);
                        break;

                    case 2:
                        animation = new TranslateAnimation(0, 0, metrics_.heightPixels,
                                0);
                        break;

                    case 3:
                        animation = new ScaleAnimation((float) 1.0, (float) 1.0,
                                (float) 0, (float) 1.0);
                        break;

                    case 4:
                        animation = AnimationUtils.loadAnimation(context, R.anim.fade_in);
                        break;
                    case 5:
                        animation = AnimationUtils.loadAnimation(context, R.anim.hyperspace_in);
                        break;
                    case 6:
                        animation = AnimationUtils.loadAnimation(context, R.anim.hyperspace_out);
                        break;
                    case 7:
                        animation = AnimationUtils.loadAnimation(context, R.anim.wave_scale);
                        break;
                    case 8:
                        animation = AnimationUtils.loadAnimation(context, R.anim.push_left_in);
                        break;
                    case 9:
                        animation = AnimationUtils.loadAnimation(context, R.anim.push_left_out);
                        break;
                    case 10:
                        animation = AnimationUtils.loadAnimation(context, R.anim.push_up_in);
                        break;
                    case 11:
                        animation = AnimationUtils.loadAnimation(context, R.anim.push_up_out);
                        break;
                    case 12:
                        animation = AnimationUtils.loadAnimation(context, R.anim.shake);
                        break;
                }

                animation.setDuration(500);
                convertView.startAnimation(animation);
                FLAG_NEW_ELEMENT = false;
            }
            return convertView;
        }

        private class Holder {
            public TextView textview;
        }

    }
}
