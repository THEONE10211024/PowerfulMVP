package team.nobugs.powerfulmvp.core.remote.utils;

import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.apache.http.message.BasicNameValuePair;

import java.util.List;

import team.nobugs.powerfulmvp.core.remote.GsonGetRequest;
import team.nobugs.powerfulmvp.core.remote.GsonPostRequest;
import team.nobugs.powerfulmvp.core.remote.phraser.HttpObject;
import team.nobugs.powerfulmvp.core.remote.phraser.HttpObjectDeserializer;


/**
 * Created by xiayong on 2015/8/12.
 * 创建各种Request对象
 *
 */
public class RequestFactory {
    public static GsonGetRequest<HttpObject> createGetRequest(final String url, Response.Listener<HttpObject> listener,
                                                               Response.ErrorListener errorListener){
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(HttpObject.class, new HttpObjectDeserializer())
                .create();

        return new GsonGetRequest<>
                (
                        url,
                        new TypeToken<HttpObject>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }
    public static GsonGetRequest<HttpObject> createGetRequest(final String url, final List<BasicNameValuePair> params, Response.Listener<HttpObject> listener,
                                                               Response.ErrorListener errorListener){
        String formatUrl = Utils.attachHttpGetParams(url, params);
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(HttpObject.class, new HttpObjectDeserializer())
                .create();

        return new GsonGetRequest<>
                (
                        formatUrl,
                        new TypeToken<HttpObject>() {}.getType(),
                        gson,
                        listener,
                        errorListener
                );
    }

    public static GsonPostRequest<HttpObject> createPostRequest(final String url,final List<BasicNameValuePair> params,Response.Listener<HttpObject> listener,
                                                                Response.ErrorListener errorListener ){
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(HttpObject.class, new HttpObjectDeserializer())
                .create();
        final JsonObject jsonObject = new JsonObject();
        if(params != null){
            for(BasicNameValuePair pair : params ){
                jsonObject.addProperty(pair.getValue(),pair.getValue());
            }
        }

        final GsonPostRequest gsonPostRequest = new GsonPostRequest<>
                (
                        url,
                        jsonObject.toString(),
                        new TypeToken<HttpObject>()
                        {
                        }.getType(),
                        gson,
                        listener,
                        errorListener
                );

        gsonPostRequest.setShouldCache(false);

        return gsonPostRequest;
    }
}
