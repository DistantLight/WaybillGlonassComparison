package com.ngtu.WaybillGlonassComparison.repositories.domain;

import com.ngtu.WaybillGlonassComparison.entities.reports.ComparativeReport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComparativeReportRepository extends JpaRepository<ComparativeReport, Long> {
    List<ComparativeReport> findByConvoyNumber(int convoyNumber);

    void deleteByConvoyNumber(int convoyNumber);
}
