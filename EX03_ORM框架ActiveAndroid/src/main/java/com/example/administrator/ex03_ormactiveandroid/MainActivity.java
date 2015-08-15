package com.example.administrator.ex03_ormactiveandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.util.Log;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {


    @InjectView(R.id.textView)
    TextView mTextView;

    @OnClick({R.id.btn_insert, R.id.btn_delete, R.id.btn_update, R.id.btn_query, R.id.btn_batch})
    public void click(View view){
        switch (view.getId()){
            case R.id.btn_insert:
                Student s1 = new Student(23, "Tom");
                s1.save();

                insertNews();
                break;
            case R.id.btn_delete:
                new Delete().from(Student.class).execute();
                break;
            case R.id.btn_update:
                // 留为作业课后练习
                // 基于先查找，修改属性后再保存
                break;
            case R.id.btn_query:
                List<Student> list = new Select().from(Student.class).execute();
                Student student = list.get(0);
                mTextView.setText(student.name);

                queryNews();
                break;
            case R.id.btn_batch:
                long begin = System.currentTimeMillis();
                Log.e("batch", "begin: " + begin);
                ActiveAndroid.beginTransaction();
                try {
                    for (int i = 0; i < 1000; i++) {
                        Student s = new Student(23 , "Tom" + i);
                        s.save();
                    }
                    ActiveAndroid.setTransactionSuccessful();
                }
                finally {
                    ActiveAndroid.endTransaction();
                }
                long end = System.currentTimeMillis();
                Log.e("batch", "begin: " + end);
                Log.e("batch", "共耗时: " + (end - begin));
                System.out.println(begin);
                break;

        }
    }

    private void queryNews() {
        List<News> list = new Select().from(News.class).execute();
        News news = list.get(1);
        List<PicInfo> pics = news.getPics();
        PicInfo picInfo = pics.get(1);
        mTextView.setText(picInfo.picUrl);
    }

    private void insertNews() {
        News news = new News("title", "subtitle", "from_src");
        news.save();

        for (int i = 0; i < 3; i++) {
            List<PicInfo> pics = news.getPics();
            PicInfo info = new PicInfo("url" + i, news);
            info.save();
            pics.add(info);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
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
