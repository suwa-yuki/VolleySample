package jp.classmethod.android.sample.volley;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.ParseException;
import android.support.v4.content.AsyncTaskLoader;

public class JSONLoader extends AsyncTaskLoader<JSONObject> {
    public JSONLoader(Context context) {
        super(context);
        forceLoad();
    }
    @Override
    public JSONObject loadInBackground() {
        String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        HttpResponse res = null;
        try {
            res = httpClient.execute(get);
            HttpEntity entity = res.getEntity();
            String body = EntityUtils.toString(entity);
            return new JSONObject(body);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}