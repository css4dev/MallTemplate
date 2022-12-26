package com.sawaaid.malltemplate.connection;


import androidx.annotation.NonNull;

import com.sawaaid.malltemplate.connection.response.RespAddress;
import com.sawaaid.malltemplate.connection.response.RespAds;
import com.sawaaid.malltemplate.connection.response.Resp;
import com.sawaaid.malltemplate.connection.response.RespNotificationHistory;
import com.sawaaid.malltemplate.connection.response.RespOrder;
import com.sawaaid.malltemplate.connection.response.RespOrderDetails;
import com.sawaaid.malltemplate.connection.response.RespProduct;
import com.sawaaid.malltemplate.connection.response.RespSections;
import com.sawaaid.malltemplate.connection.response.RespSubSection;
import com.sawaaid.malltemplate.connection.response.RespUser;

import java.util.HashMap;

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

    public void sectionProducts(String page_no, String sectionId, String subSectionId, RequestListener<RespProduct> listener) {
        Call<RespProduct> callbackCall = api.sectionProduct(sectionId, subSectionId, page_no);
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

    public void cartProducts(String id, RequestListener<RespProduct> listener) {
        Call<RespProduct> callbackCall = api.cartProducts(id);
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

    public void search(String page_no, String word, RequestListener<RespProduct> listener) {
        Call<RespProduct> callbackCall = api.search(word, page_no);
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

    public void subSection(String sectionId, RequestListener<RespSubSection> listener) {
        Call<RespSubSection> callbackCall = api.subSections(sectionId);
        callbackCall.enqueue(new Callback<RespSubSection>() {
            @Override
            public void onResponse(@NonNull Call<RespSubSection> call, @NonNull Response<RespSubSection> response) {
                RespSubSection resp = response.body();
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
            public void onFailure(@NonNull Call<RespSubSection> call, @NonNull Throwable t) {
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

    public void specialProducts(RequestListener<RespProduct> listener) {
        Call<RespProduct> callbackCall = api.specialProducts();
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

    public void login(String userName, String password, RequestListener<RespUser> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", password);
        Call<RespUser> callbackCall = api.login(map);
        callbackCall.enqueue(new Callback<RespUser>() {
            @Override
            public void onResponse(@NonNull Call<RespUser> call, @NonNull Response<RespUser> response) {
                RespUser resp = response.body();
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
            public void onFailure(@NonNull Call<RespUser> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void addresses(int userId, RequestListener<RespAddress> listener) {
        Call<RespAddress> callbackCall = api.addresses(String.valueOf(userId));
        callbackCall.enqueue(new Callback<RespAddress>() {
            @Override
            public void onResponse(@NonNull Call<RespAddress> call, @NonNull Response<RespAddress> response) {
                RespAddress resp = response.body();
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
            public void onFailure(@NonNull Call<RespAddress> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void insertAddress(String locationString, String locationName, String userId, RequestListener<Resp> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("locationString", locationString);
        map.put("locationName", locationName);
        map.put("userId", userId);
        Call<Resp> callbackCall = api.insertAddress(map);
        callbackCall.enqueue(new Callback<Resp>() {
            @Override
            public void onResponse(@NonNull Call<Resp> call, @NonNull Response<Resp> response) {
                Resp resp = response.body();
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
            public void onFailure(@NonNull Call<Resp> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void deleteAddress(int id, RequestListener<Resp> listener) {
        Call<Resp> callbackCall = api.deleteAddress(String.valueOf(id));
        callbackCall.enqueue(new Callback<Resp>() {
            @Override
            public void onResponse(@NonNull Call<Resp> call, @NonNull Response<Resp> response) {
                Resp resp = response.body();
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
            public void onFailure(@NonNull Call<Resp> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void notificationHistory(String page, RequestListener<RespNotificationHistory> listener) {
        Call<RespNotificationHistory> callbackCall = api.notificationHistory(page);
        callbackCall.enqueue(new Callback<RespNotificationHistory>() {
            @Override
            public void onResponse(@NonNull Call<RespNotificationHistory> call, @NonNull Response<RespNotificationHistory> response) {
                RespNotificationHistory resp = response.body();
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
            public void onFailure(@NonNull Call<RespNotificationHistory> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void orders(String page, String userId, RequestListener<RespOrder> listener) {
        HashMap<String, String> map = new HashMap<>();
        map.put("page", page);
        map.put("userId", userId);
        Call<RespOrder> callbackCall = api.orders(map);
        callbackCall.enqueue(new Callback<RespOrder>() {
            @Override
            public void onResponse(@NonNull Call<RespOrder> call, @NonNull Response<RespOrder> response) {
                RespOrder resp = response.body();
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
            public void onFailure(@NonNull Call<RespOrder> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }

    public void orderDetails(int orderId, RequestListener<RespOrderDetails> listener) {
        Call<RespOrderDetails> callbackCall = api.orderDetails(String.valueOf(orderId));
        callbackCall.enqueue(new Callback<RespOrderDetails>() {
            @Override
            public void onResponse(@NonNull Call<RespOrderDetails> call, @NonNull Response<RespOrderDetails> response) {
                RespOrderDetails resp = response.body();
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
            public void onFailure(@NonNull Call<RespOrderDetails> call, @NonNull Throwable t) {
                listener.onFinish();
                listener.onFailed(t.getMessage());
            }
        });
    }


}
