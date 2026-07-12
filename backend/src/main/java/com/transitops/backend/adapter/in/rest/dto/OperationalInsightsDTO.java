package com.transitops.backend.adapter.in.rest.dto;

import java.util.List;

public class OperationalInsightsDTO {
    private List<String> insights;

    public OperationalInsightsDTO(List<String> insights) {
        this.insights = insights;
    }

    public List<String> getInsights() { return insights; }
}
