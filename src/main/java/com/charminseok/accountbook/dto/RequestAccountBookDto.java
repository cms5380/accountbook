package com.charminseok.accountbook.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestAccountBookDto {
    private int userId;
    private int accountBookId;
    private Long money;
    private String memo;
}
