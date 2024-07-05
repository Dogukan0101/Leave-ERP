package com.kafein.userERP.dtos;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class LeaveDTO {
    private Long id;
    private Date startDate;
    private Date endDate;
    private Timestamp createdAt;
    private Long userId;
    private String userName;

}
