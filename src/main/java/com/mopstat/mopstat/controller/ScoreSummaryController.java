package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.dto.ScoreSummaryDTO;
import com.mopstat.mopstat.service.ScoreSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/score")
public class ScoreSummaryController {

    @Autowired
    private ScoreSummaryService scoreSummaryService;

    @GetMapping("/summary")
    public ScoreSummaryDTO getSummary(Principal principal) {
        String username = principal.getName();
        return scoreSummaryService.getUserScoreSummary(username);
    }
}
