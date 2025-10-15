package com.puzzlix.solid_task.domain.notification.listener;

import com.puzzlix.solid_task.domain.issue.Issue;
import com.puzzlix.solid_task.domain.issue.event.IssueStatusChangedEvent;
import com.puzzlix.solid_task.domain.notification.NotificationSender;
import com.puzzlix.solid_task.domain.notification.NotificationSenderFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.AFTER_COMMIT;

@Component
@RequiredArgsConstructor
public class IssueNotificationEventListener {

    private final NotificationSenderFactory notificationSenderFactory;

    // 알림 정책 - yml 파일 -> EMAIL

    // 알림 정책 - yml 파일 -> EMAIL
    // 값을 읽어와서 변수에 주입
    @Value("${notification.policy.on-status-done}")
    private String onStatusDoneType;

    @TransactionalEventListener(phase = AFTER_COMMIT)
    @EventListener
    public void handleIssueStatusChangeEvent(IssueStatusChangedEvent event) {
        Issue issue = event.getIssue();
        String message = "이슈 #" +
                issue.getTittle() + " 의 상태가 " +
                issue.getIssueStatus() + "로 변경 되었습니다";

        if("DONE".equalsIgnoreCase(issue.getIssueStatus().name())) {
            // 팩토리 클래스를 활용해서 현재 알림 전략을 가지고 온다.
            //  yml --> EMAIL, SMS
            NotificationSender sender
            = notificationSenderFactory.findSender(onStatusDoneType);
            sender.send(message);
        }
    }

}
