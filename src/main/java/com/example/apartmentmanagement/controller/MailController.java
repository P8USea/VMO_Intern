package com.example.apartmentmanagement.controller;

import com.example.apartmentmanagement.dto.response.APIResponse;
import com.example.apartmentmanagement.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    private final MailService mailService;

    @Autowired
    public MailController(MailService emailService) {
        this.mailService = emailService;
    }

    @Operation(summary = "Mail sender")
    @GetMapping("/send-email")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String sendEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) {
        mailService.sendSimpleEmail(to, subject, text);
        return "Email sent successfully!";
    }
    @GetMapping("/send-html-email")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public String sendHtmlEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String text) throws MessagingException {
        mailService.sendHtmlEmail(to, subject, text);
        return "Email sent successfully!";
    }
    @PostMapping("/send-billing-email")
    public APIResponse<String> sendBillingEmail() {
        mailService.sendBillingEmailsToResidents();
        return APIResponse.<String>builder()
                .result("Emails have been sent to all residents.")
                .build();
    }
}
