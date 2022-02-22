package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.model.ConfirmationToken;

public interface IConfirmationTokenService {
    void saveConfirmationToken(ConfirmationToken token);
    int setConfirmedAt(String token);
}
