package com.example.demo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/industry")
public class IndustryController {

    private final IndustryService industryService;

    // 폼 페이지
    @GetMapping("/form")
    public  ModelAndView form(Model model) {
        // 기본값(예: companyId 1) 지정 가능
        model.addAttribute("request", new IndustrySummaryRequest());
        return new ModelAndView("industry/form");
    }
    
    @PostMapping("/analyze")
    @ResponseBody
    public IndustrySummaryResponse analyze(@RequestBody IndustrySummaryRequest request) {
        return industryService.process(
                request.getCompanyId(),
                request.getIndustryRaw()
        );
    }
}

