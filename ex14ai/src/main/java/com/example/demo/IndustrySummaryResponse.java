package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndustrySummaryResponse {
    private String summary;        // 업종 요약
    private String salesScript;    // 영업 스크립트
}

