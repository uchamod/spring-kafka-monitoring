package com.uchamod.notification.Server;



import com.uchamod.commonmodules.DTO.EmailDTO;
import com.uchamod.commonmodules.DTO.UserWrapper;
import com.uchamod.commonmodules.Models.Order;
import com.uchamod.commonmodules.Models.OrderProductModel;


import com.uchamod.notification.Model.User;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;


@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final JavaMailSender javaMailSender;


    @Value("${app.email.from}")
    private String fromEmail;

    //sending simple email
    @Async
    public void sendSimpleEmail(String to,String subject,String body){
        try{
            SimpleMailMessage message=new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);

            javaMailSender.send(message);
            log.info("Email sent successfully to: {}", to);
        }catch (Exception e){
            log.error("Failed to send email to: {}. Error: {}", to, e.getMessage());
        }
    }
    //text email templete
    @Async
    public void sendOrderNotificationToSeller(UserWrapper seller, Order order, UserWrapper customer,
                                              EmailDTO emailDTO, StringBuilder body, String subjectPrefix) {
        try {

            body.setLength(0);
            body.append("Dear ").append(seller.getUserName()).append(",\n\n");
            body.append("You have received a new order!\n\n");
            body.append("Order Details:\n");
            body.append("===================\n");
            body.append("Order ID: ").append(order.getOrderId()).append("\n");
            body.append("Order Status: ").append(order.getOrderStatus()).append("\n");
            body.append("Order Date: ").append(new SimpleDateFormat("dd-MM-yyyy").format(order.getLastUpdate())).append("\n\n");

            body.append("Customer Information:\n");
            body.append("-------------------\n");
            body.append("Name: ").append(customer.getUserName()).append("\n");
            body.append("Email: ").append(customer.getUserEmail()).append("\n");
            body.append("Phone: ").append(customer.getUserPhone() != null ? customer.getUserPhone() : "N/A").append("\n");
            body.append("Address: ").append(customer.getUserAddress() != null ? customer.getUserAddress() : "N/A").append("\n\n");

            body.append("Your Products in This Order:\n");
            body.append("----------------------------\n");

            for(OrderProductModel orderProductModel : emailDTO.getOrderProductModelList()){
                body.append(String.format("Product ID: %s\n", orderProductModel.getProductId()));
                body.append(String.format("Quantity: %d\n", orderProductModel.getProductCount()));
                body.append(String.format("Unit Price: $%.2f\n", orderProductModel.getProductItemPrice()));
                body.append(String.format("Subtotal: $%.2f\n\n", orderProductModel.getProductCount()*orderProductModel.getProductItemPrice()));
            }


            body.append(String.format("Your Total: $%.2f\n\n", emailDTO.getTotalAmount()));
            body.append("Please prepare these items for shipping.\n\n");
            body.append("Thank you for your business!\n\n");
            body.append("Best regards,\n");
            body.append("Your Marketplace Team");

            sendSimpleEmail(seller.getUserEmail(), subjectPrefix + order.getOrderId(), body.toString());

        } catch (Exception e) {
            log.error("Failed to send order notification to seller: {}. Error: {}",
                    seller.getUserEmail(), e.getMessage());
        }
    }

    @Async
    public void sendUserRegisterEmail(User user,StringBuilder body,String subjectPrefix){
        try{
            //set email body
            body.setLength(0);
            body.append("Dear ").append(user.getUserName()).append(",\n\n");

            body.append("Welcome to E-Store! 🎉\n\n");

            body.append("Your account has been successfully created. We're excited to have you join our community!\n\n");

            body.append("Here are your account details:\n");
            body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            body.append("User ID: ").append(user.getUserId()).append("\n");
            body.append("Username: ").append(user.getUserName()).append("\n");
            body.append("Email: ").append(user.getUserEmail()).append("\n");
            body.append("Phone: ").append(user.getUserPhone() != null ? user.getUserPhone() : "Not provided").append("\n");
            body.append("Registration Date: ").append(user.getUserRegisterDate()).append("\n");
            body.append("Account Status: ").append(user.getUserStatus()).append("\n");
            body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n\n");

            body.append("What you can do now:\n");
            body.append("✓ Browse our wide range of products\n");
            body.append("✓ Add items to your cart\n");
            body.append("✓ Place orders with secure checkout\n");
            body.append("✓ Track your order status in real-time\n");
            body.append("✓ Manage your profile and preferences\n\n");

            body.append("Need help getting started?\n");
            body.append("Visit our Help Center or contact our support team anytime.\n\n");

            body.append("Thank you for choosing E-Store!\n\n");

            body.append("Best regards,\n");
            body.append("The E-Store Team\n\n");

            body.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
            body.append("This is an automated message. Please do not reply to this email.\n");
            body.append("If you did not create this account, please contact us immediately.\n");


            //send email
            sendSimpleEmail(user.getUserEmail(), subjectPrefix + user.getUserName(), body.toString());
        } catch (Exception e) {
            log.error("Failed to send order notification to seller: {}. Error: {}",
                    user.getUserEmail());
            throw new RuntimeException(e);
        }
    }
}
