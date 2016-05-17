package gov.sequarius.app;

import okhttp3.OkHttpClient;

/**
 * Created by Sequarius on 2016/4/14.
 */
public class EPOkHttpClient extends OkHttpClient {
    private int resultCode = -1;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public EPOkHttpClient(Builder builder){

    }
}
