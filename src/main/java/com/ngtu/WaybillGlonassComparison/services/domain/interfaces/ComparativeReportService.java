package com.ngtu.WaybillGlonassComparison.services.domain.interfaces;

import com.ngtu.WaybillGlonassComparison.entities.reports.ComparativeReport;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ComparativeReportService {
    List<ComparativeReport> getData();

    ComparativeReport findById(Long id);

    void update(ComparativeReport report);

    List<ComparativeReport> findByConvoyNumber(int convoyNumber);

    List<ComparativeReport> addData(int convoyNumber);

    void deleteAll();

    @Transactional
    void deleteByConvoyNumber(int convoyNumber);
}
