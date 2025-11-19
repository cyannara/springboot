package com.example.demo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class IndustryService {

    private final ChatClient chatClient;
    //private final CompanyRepository companyRepository;
    
    public IndustryService( ChatClient.Builder builder) {
    	chatClient = builder.build();
    }
    
    public IndustrySummaryResponse process(String companyId, String industryRaw) {

        // 1. 업종 요약 생성
        String summaryPrompt = """
            다음 기업의 업종 설명을 영업 담당자도 이해하기 쉽게
            핵심만 3줄로 요약해줘.

            [업종 설명]
            %s
            """.formatted(industryRaw);

        String summary = chatClient.prompt()
                .user(summaryPrompt)
                .call()
                .content();

        // 2. 영업 스크립트 자동 생성
        String salesPrompt = """
            아래 요약된 업종 정보를 기반으로
            영업부가 실제 상담에서 사용할 수 있는
            맞춤형 영업 스크립트를 만들어줘.

            요약:
            %s
            """.formatted(summary);

        String script = chatClient.prompt()
                .user(salesPrompt)
                .call()
                .content();

        // 3. Oracle DB 저장
        //companyRepository.updateIndustryInfo(companyId, summary, script);
        
        System.out.println(summary);
        System.out.println(script);
        return new IndustrySummaryResponse(summary, script);
    }
}

