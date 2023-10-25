package com.ngtu.WaybillGlonassComparison.repositories.domain;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageWaybill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MileageWaybillRepository extends JpaRepository<MileageWaybill, Long> {
    List<MileageWaybill> findByConvoyNumber(int convoyNumber);

    void deleteByConvoyNumber(int convoyNumber);
}
