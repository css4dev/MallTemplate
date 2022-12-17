package com.sawaaid.malltemplate.connection;

import androidx.annotation.NonNull;

import com.sawaaid.malltemplate.connection.response.RespAds;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.connection.response.RespSections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Request {
    private final API api = RestAdapter.createAPI();

    public void sections(RequestListener<RespSections> listener) {
        Call<RespSections> callbackCall = api.sections();
        callbackCall.enqueue(new Callback<RespSections>() {
            @Override
            public void onResponse(@NonNull Call<RespSections> call, @NonNull Response<RespSections> response) {
                RespSections resp = response.body();
                listener.onFinish();
                if (resp == null) {
                    listener.onFailed(null);
                } else {
                    if (resp.code != 200) {
                        listener.onFailed(resp.message);
                    } else {
                        listener.onSuccess(resp);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<RespSections> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void sectionProducts(RequestListener<RespProduct> listener) {
        Call<RespProduct> callbackCall = api.sectionProduct();
        callbackCall.enqueue(new Callback<RespProduct>() {
            @Override
            public void onResponse(@NonNull Call<RespProduct> call, @NonNull Response<RespProduct> response) {
                RespProduct resp = response.body();
                listener.onFinish();
                if (resp == null) {
                    listener.onFailed(null);
                } else {
                    if (resp.code != 200) {
                        listener.onFailed(resp.message);
                    } else {
                        listener.onSuccess(resp);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<RespProduct> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void newProducts(RequestListener<RespProduct> listener) {
        Call<RespProduct> callbackCall = api.newProducts();
        callbackCall.enqueue(new Callback<RespProduct>() {
            @Override
            public void onResponse(@NonNull Call<RespProduct> call, @NonNull Response<RespProduct> response) {
                RespProduct resp = response.body();
                listener.onFinish();
                if (resp == null) {
                    listener.onFailed(null);
                } else {
                    if (resp.code != 200) {
                        listener.onFailed(resp.message);
                    } else {
                        listener.onSuccess(resp);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<RespProduct> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void ads(RequestListener<RespAds> listener) {
        Call<RespAds> callbackCall = api.ads();
        callbackCall.enqueue(new Callback<RespAds>() {
            @Override
            public void onResponse(@NonNull Call<RespAds> call, @NonNull Response<RespAds> response) {
                RespAds resp = response.body();
                listener.onFinish();
                if (resp == null) {
                    listener.onFailed(null);
                } else {
                    if (resp.code != 200) {
                        listener.onFailed(resp.message);
                    } else {
                        listener.onSuccess(resp);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<RespAds> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }


}
