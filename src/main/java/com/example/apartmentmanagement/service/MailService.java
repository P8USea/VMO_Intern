package com.example.apartmentmanagement.service;

import com.example.apartmentmanagement.entity.Apartment;
import com.example.apartmentmanagement.entity.Resident;
import com.example.apartmentmanagement.entity.ServiceUsage;
import com.example.apartmentmanagement.repository.ApartmentRepository;
import com.example.apartmentmanagement.repository.ResidentRepository;
import com.example.apartmentmanagement.repository.ServiceUsageRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.List;

@Service
@Slf4j
public class MailService {
    @Autowired
    private final JavaMailSender mailSender;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private ResidentRepository residentRepository;
    private final ServiceUsageRepository serviceUsageRepository;
    @Value("${mail.subject}")
    String subject;
    @Value("${spring.mail.username}")
    String from;
    @Autowired
    public MailService(JavaMailSender mailSender, ServiceUsageRepository serviceUsageRepository) {
        this.mailSender = mailSender;
        this.serviceUsageRepository = serviceUsageRepository;
    }

    public void sendSimpleEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom(from);

        mailSender.send(message);
    }
    public void autoEmailSender() {
        SimpleMailMessage message = new SimpleMailMessage();
        for (Apartment apartment : apartmentRepository.findAll()) {
            String email = residentRepository.findById(apartment.getProxyId()).get().getUser().getEmail();
            message.setTo(email);
            message.setSubject(subject);
            message.setFrom(from);
            message.setText("Phí dịch vụ của căn hộ {}: \n" + apartment.getId() +
                    apartment.getServiceUsages());
        }
    }
    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        helper.setText(htmlBody, true); // Đặt true để gửi email dạng HTML
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setFrom(from);

        mailSender.send(mimeMessage);
    }

    public void sendBillingEmailsToResidents() {
        List<Resident> residents = residentRepository.findAll();
        YearMonth currentMonth = YearMonth.now();

        for (Resident resident : residents) {
            List<ServiceUsage> serviceUsages = serviceUsageRepository.findByApartmentIdAndMonth(
                    resident.getApartment().getId(), currentMonth
            );
            sendEmail(resident, serviceUsages);
        }
    }

    private void sendEmail(Resident resident, List<ServiceUsage> serviceUsages) {
        String emailContent = buildEmailContent(resident, serviceUsages);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(resident.getUser().getEmail());
        message.setSubject("Your Monthly Service Usage Report");
        message.setText(emailContent);

        try {
            mailSender.send(message);
            log.info("Email sent to: {}", resident.getUser().getEmail());
        } catch (Exception e) {
            log.error("Failed to send email to: {}", resident.getUser().getEmail(), e);
        }
    }

    private String buildEmailContent(Resident resident, List<ServiceUsage> serviceUsages) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(resident.getUser().getFirstName()).append(",\n\n");
        sb.append("Here is your service usage report for the month:\n\n");

        for (ServiceUsage usage : serviceUsages) {
            sb.append("Service Type: ").append(usage.getServiceType().getName()).append("\n");
            sb.append("Usage Quantity: ").append(usage.getQuantity()).append("\n");
            sb.append("Total Cost: ").append(usage.getTotal()).append("\n\n");
        }

        sb.append("Thank you for your attention.\n");
        sb.append("Management");

        return sb.toString();
    }
}


