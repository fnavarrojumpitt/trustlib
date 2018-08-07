package com.jumpitt.trustlib.utils;

import com.jumpitt.trustlib.network.TrifleResponse;

public interface TrustListener {
    interface OnCreateTrifle {
        void onSuccess(TrifleResponse.Audit audit);
        void onError(int code);

        void onFailure(Throwable t);
    }
}
