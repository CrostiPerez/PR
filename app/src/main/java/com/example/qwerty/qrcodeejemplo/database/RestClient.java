package com.example.qwerty.qrcodeejemplo.database;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by qwerty on 19/05/18.
 */

public class RestClient {
    public static final String FILE_GET_MODELS = "get-models.php";
    public static final String FILE_EXIST_USER = "exist.php";
    public static final String FILE_GET_MODEL = "get-model.php";
    public static final String FILE_GET_PROCESSES = "get-processes.php";
    public static final String FILE_GET_PROJECTS = "get-projects.php";
    public static final String FILE_LOGIN = "login.php";
    public static final String FILE_SET_PIECE = "set-piece.php";
    public static final String FILE_SET_TIME = "set-time.php";

    private static final String BASE_URL = "http://www.prcalibradores.com/app/";
    private static AsyncHttpClient sClient = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        sClient.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
