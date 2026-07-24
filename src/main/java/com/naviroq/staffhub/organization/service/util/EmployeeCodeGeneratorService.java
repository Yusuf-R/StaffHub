package com.naviroq.staffhub.organization.service.util;

import com.naviroq.staffhub.organization.domain.entity.Department;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeCodeGeneratorService {

    private static final int MAX_RETRIES = 5;

    public String generateEmployeeCode(Department department) {
        // 1. Get department code
        String deptCode = department.getCode();

        // 2. Get current date in YYMMDD format
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

        // 3. Generate 6-digit random hexadecimal
        String randomPart = generateRandomHex(6);

        // 4. Combine: SE-250724-A7B3C9
        return String.format("%s-%s-%s", deptCode, datePart, randomPart);
    }

    private String generateRandomHex(int length) {
        // Generate a random hex string of given length
        return Long.toHexString(System.nanoTime() + (long) (Math.random() * Long.MAX_VALUE))
                .substring(0, length)
                .toUpperCase();
    }
}