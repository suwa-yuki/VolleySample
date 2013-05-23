package jp.classmethod.android.sample.volley;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final MainActivity self = this;
    private RequestQueue mQueue;
    private long requestStartTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    
        mQueue = Volley.newRequestQueue(getApplicationContext());
    
        findViewById(R.id.volley).setOnClickListener(this);
        findViewById(R.id.http_client).setOnClickListener(this);
        findViewById(R.id.image_loading).setOnClickListener(this);
        findViewById(R.id.load_image).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.volley:
            requestVolley();
            break;
        case R.id.http_client:
            requestHttpClient();
            break;
        case R.id.image_loading:
            startActivity(new Intent(self, ImageLoadingActivity.class));
            break;
        case R.id.load_image:
            loadNetworkImageView();
            break;
        }
    }

    private void requestVolley() {
        // Volley でリクエスト
        String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        mQueue.add(new JsonObjectRequest(Method.GET, url, null,
                new Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        long time = System.currentTimeMillis() - requestStartTime;
                        Log.d(TAG, "Volley Request finished : " + time);
                    }
                }, null));
        requestStartTime = System.currentTimeMillis();
        mQueue.start();
    }

    private void requestHttpClient() {
        // HttpClient でリクエスト
        getSupportLoaderManager().initLoader(0, null, new LoaderCallbacks<JSONObject>() {
            @Override
            public Loader<JSONObject> onCreateLoader(int id, Bundle bundle) {
                requestStartTime = System.currentTimeMillis();
                return new JSONLoader(getApplicationContext());
            }
            @Override
            public void onLoadFinished(Loader<JSONObject> loader, JSONObject result) {
                long time = System.currentTimeMillis() - requestStartTime;
                Log.d(TAG, "HttpClient Request finished : " + time);
                getSupportLoaderManager().destroyLoader(0);
            }
            @Override
            public void onLoaderReset(Loader<JSONObject> loader) {
            }
        });
    }
    
    private void loadNetworkImageView() {
        String url = "http://dev.classmethod.jp/wp-content/uploads/2013/04/android_eyecatch.png";
        NetworkImageView view = (NetworkImageView) findViewById(R.id.network_image_view);
        view.setImageUrl(null, null);
        view.setImageUrl(url, new ImageLoader(mQueue, new BitmapCache()));
    }

}
