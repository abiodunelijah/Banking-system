package com.abbey2u.bankingsystem.service;

import com.abbey2u.bankingsystem.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
    void sentEmailWithAttachment(EmailDetails emailDetails);
}
