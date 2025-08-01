package com.dangbun.domain.notificationreceiver.entity;

import com.dangbun.domain.member.entity.Member;
import com.dangbun.domain.notification.entity.Notification;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="notification_receiver")
public class NotificationReceiver {

    @EmbeddedId
    private NotificationReceiverId id;

    @MapsId("receiverId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private Member receiver;

    @MapsId("notificationId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notification_id")
    private Notification notification;

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;


    @Builder
    public NotificationReceiver(Member receiver, Notification notification, boolean isRead) {
        this.receiver = receiver;
        this.notification = notification;
        this.isRead = isRead;
    }
}
