package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.report.CategoryReportResponse;
import com.fintrack.fintrack.dto.report.MonthlyReportResponse;
import com.fintrack.fintrack.dto.report.YearlyReportResponse;
import com.fintrack.fintrack.service.CategoryReportService;
import com.fintrack.fintrack.service.MonthlyReportService;
import com.fintrack.fintrack.service.YearlyReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {
    private final MonthlyReportService monthlyReportService;
    private final YearlyReportService yearlyReportService;
    private final CategoryReportService categoryReportService;

    @GetMapping("/montly")
    public MonthlyReportResponse geyMonthlyReport(@AuthenticationPrincipal UserDetails userDetails
    , @RequestParam int month,@RequestParam int year)
    {
        return monthlyReportService.getMonthlyReport(userDetails,month,year);
    }

    @GetMapping("/yearly")
    public YearlyReportResponse getYearlyReport(@AuthenticationPrincipal UserDetails userDetails,@RequestParam int year)
    {
        return yearlyReportService.getYearlyReport(userDetails,year);
    }

    @GetMapping("/category")
    public List<CategoryReportResponse> getAllCategory(@AuthenticationPrincipal UserDetails userDetails)
    {
        return categoryReportService.getByCategory(userDetails);
    }

}
