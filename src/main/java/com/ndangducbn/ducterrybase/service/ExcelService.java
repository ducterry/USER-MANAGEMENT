package com.ndangducbn.ducterrybase.service;

import org.springframework.stereotype.Component;

@Component
public interface ExcelService {
    String exportUserToExcel();

    String downloadTemplates();
}
