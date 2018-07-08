package com.example.qwerty.qrcodeejemplo.database;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by qwerty on 19/05/18.
 */

public class RestClient {
    public static final class GetModelsScript {
        public static final String FILE_NAME = "get-models.php";

        public static final class Params {
            public static final String $1 = "project_id";
            public static final String $2 = "user_id";
        }
    }

    public static final class ExistUserScript {
        public static final String FILE_NAME = "exist.php";

        public static final class Params {
            public static final String $1 = "login_id";
            public static final String $2 = "login_password";
        }
    }

    public static final class GetModelScript {
        public static final String FILE_NAME = "get-model.php";

        public static final class Params {
            public static final String $1 = "project_id";
            public static final String $2 = "model_id";
            public static final String $3 = "user_id";
        }
    }

    public static final class GetProcessesScript {
        public static final String FILE_NAME = "get-processes.php";

        public static final class Params {

        }
    }

    public static final class GetProjectsScript {
        public static final String FILE_NAME = "get-projects.php";

        public static final class Params {
        }
    }

    public static final class LoginScript {
        public static final String FILE_NAME = "login.php";

        public static final class Params {
            public static final String $1 = "username";
            public static final String $2 = "password";
        }
    }

    public static final class SetPieceScript {
        public static final String FILE_NAME = "set-piece.php";

        public static final class Params {
            public static final String $1 = "model_id";
            public static final String $2 = "user_id";
        }
    }

    public static final class SetTimeScript {
        public static final String FILE_NAME = "set-time.php";

        public static final class Params {
            public static final String $1 = "muertes";
            public static final String $2 = "id";
            public static final String $3 = "json";
        }
    }

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
