package com.prototype.restaurantsreview.service;

import com.prototype.restaurantsreview.model.ConfirmationToken;
import com.prototype.restaurantsreview.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService implements IConfirmationTokenService {
    @Autowired
    ConfirmationTokenRepository confirmationTokenRepository;
    @Autowired
    EmailService emailService;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }


    public int setConfirmedAt(String token) {
        return confirmationTokenRepository.updateConfirmedAt(
                token, LocalDateTime.now());
    }

    @Transactional
    public String confirmToken(String token) {

        ConfirmationToken confirmationToken = getToken(token)
                .orElseThrow(() -> new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }


        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        setConfirmedAt(token);
        emailService.enableUser(
                confirmationToken.getUser().getUserEmail());
        return "confirmed";
    }
}
