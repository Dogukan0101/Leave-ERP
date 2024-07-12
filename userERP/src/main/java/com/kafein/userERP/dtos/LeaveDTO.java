package com.kafein.userERP.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

@Data
public class LeaveDTO {
    private Long id;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private LocalDateTime endDate;

    private Long days;
    private LocalDateTime createdAt;
    private Long userId;
    private String userName;

}
