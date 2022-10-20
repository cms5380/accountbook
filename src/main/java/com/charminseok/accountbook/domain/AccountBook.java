package com.charminseok.accountbook.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccountBook {
    private int id;
    private int userId;
    private int money;
    private String memo;
    private String deleteYn;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
