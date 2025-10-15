package com.puzzlix.solid_task.domain.notification;

import com.solapi.sdk.SolapiClient;
import com.solapi.sdk.message.exception.SolapiMessageNotReceivedException;
import com.solapi.sdk.message.model.Message;
import com.solapi.sdk.message.service.DefaultMessageService;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationSender implements NotificationSender {
    @Override
    public void send(String message) {
        // 외부 API 연동 문자 발송 처리 (오늘에 도전)
        smsHelper();
    }

    @Override
    public boolean supports(String type) {
        return "SMS".equalsIgnoreCase(type);
    }


public void smsHelper() {

    DefaultMessageService messageService =  SolapiClient.INSTANCE.createInstance("시크릿키", "시크릿코드");

    Message message = new Message();
    message.setFrom("01098562028");
    message.setTo("01098562028");
    message.setText("트랜잭션 에프터커밋 해야 문자가 갑니다.");

    try {

        messageService.send(message);
    } catch (SolapiMessageNotReceivedException exception) {
        System.out.println(exception.getFailedMessageList());
        System.out.println(exception.getMessage());
    } catch (Exception exception) {
        System.out.println(exception.getMessage());
    }

}


}
