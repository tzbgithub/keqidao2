package com.qidao.application.entity.invite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvitedMember {

    private Long id;

    private String name;

    private String headImage;

    private LocalDateTime bindTime;

    private Long teacherId;

}
