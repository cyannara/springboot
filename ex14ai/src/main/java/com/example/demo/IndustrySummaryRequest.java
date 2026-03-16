package com.example.demo;

import lombok.Data;

@Data
public class IndustrySummaryRequest {
    private String companyId;
    private String industryRaw;   // 업종 원문
}
