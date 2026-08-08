package com.fintrack.fintrack.controller;

import com.fintrack.fintrack.dto.dashboard.DashboardResponse;
import com.fintrack.fintrack.service.DashboardService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
@SecurityRequirement(name = "Bearer Authentication")
public class DashboardController {
    private final DashboardService dashboardService;


    @GetMapping
    public DashboardResponse dashboard(@AuthenticationPrincipal UserDetails userDetails)
    {
        return dashboardService.getDashboard(userDetails);
    }

}
