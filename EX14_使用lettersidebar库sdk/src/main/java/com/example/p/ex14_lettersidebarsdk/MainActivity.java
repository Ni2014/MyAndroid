package com.example.p.ex14_lettersidebarsdk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yunjiaedu.widget.LetterSideBar;

public class MainActivity extends AppCompatActivity {

    private BaseAdapter mAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tvOverLay = (TextView) findViewById(R.id.tv_overlay);
        LetterSideBar sideBar = (LetterSideBar) findViewById(R.id.letterSideBar);
        sideBar.setOnLetterChangedListener(new LetterSideBar.OnLetterChangedListener() {
            @Override
            public void onLetterChanged(String letter) {
                Log.e("sideBar", "letter: " + letter);
                tvOverLay.setVisibility(View.VISIBLE);
                tvOverLay.setText(letter);
                // 查找ListView中第一个出现当前首字母的行号
                for (int i = 0; i < Cheeses.sCheeseStrings.length; i++) {
                    String str = Cheeses.sCheeseStrings[i];
                    if (str.toUpperCase().startsWith(letter)) {
                        listView.setSelection(i);
                        break;
                    }
                }
            }

            @Override
            public void onActionUp() {
                tvOverLay.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tvOverLay.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);

    }

    class MyAdapter extends BaseAdapter {
        public int getCount() {
            return Cheeses.sCheeseStrings.length;
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
            View layout = getLayoutInflater().inflate(R.layout.list_item, null);
            TextView tvLetter = (TextView) layout.findViewById(R.id.tv_letter);
            TextView tvText = (TextView) layout.findViewById(R.id.tv_text);
            String text = Cheeses.sCheeseStrings[position];
            tvText.setText(text);
            String letter = text.substring(0, 1).toUpperCase();
            tvLetter.setText(letter);

            // 如果当前行和上一行的首字母相同，则首字母TextView设置为隐藏
            if (position > 0) {

                String lastText = Cheeses.sCheeseStrings[position - 1];
                String lastLetter = lastText.substring(0, 1).toUpperCase();
                if (lastLetter.equals(letter)) {
                    tvLetter.setVisibility(View.GONE);
                }
            }
            return layout;
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
