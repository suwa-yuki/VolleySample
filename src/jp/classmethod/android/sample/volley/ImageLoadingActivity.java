package jp.classmethod.android.sample.volley;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.GridView;

public class ImageLoadingActivity extends FragmentActivity {
    
    private GridView mGridView;
    
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mGridView = new GridView(this);
        mGridView.setNumColumns(3);
        setContentView(mGridView);
        
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < 200; i++) {
            String url = (i % 2 == 0) ? "http://dev.classmethod.jp/wp-content/uploads/2013/04/android_eyecatch.png" : "http://dev.classmethod.jp/wp-content/uploads/2013/05/ios_eyecatch.png";
            list.add(url);
        }
        ImageAdapter adapter = new ImageAdapter(this, list);
        mGridView.setAdapter(adapter);
    }

}
