package com.prototype.restaurantsreview.service;

public interface IEmailService {
    void send(String to,String email);
    String buildEmail(String name, String link);
    int enableUser(String email);
}
