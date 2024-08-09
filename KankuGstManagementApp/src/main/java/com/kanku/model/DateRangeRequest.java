package com.kanku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DateRangeRequest {

    private LocalDate startDate;

    private LocalDate endDate;
}
