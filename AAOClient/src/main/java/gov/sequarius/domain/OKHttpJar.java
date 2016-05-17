package gov.sequarius.domain;

import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;

/**
 * Created by Sequarius on 2016/4/14.
 */
public class OKHttpJar {
    private OkHttpClient client;
    private int resultCode;
    private JSONObject jsonObject;

    public JSONObject getJsonObject() {
        return jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public OKHttpJar() {
    }

    public OKHttpJar(OkHttpClient client, int resultCode) {
        this.client = client;
        this.resultCode = resultCode;
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }
}