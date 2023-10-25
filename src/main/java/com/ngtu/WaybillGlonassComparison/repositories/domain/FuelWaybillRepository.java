package com.ngtu.WaybillGlonassComparison.repositories.domain;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelWaybill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelWaybillRepository extends JpaRepository<FuelWaybill, Long> {
    FuelWaybill findTop1ByWaybillNumberAndConsumptionRate(Long waybillNumber, Double consumptionRate);
    void deleteByWaybillNumber(Long number);
    void deleteByWaybillNumberAndConvoyNumber(Long number, int convoyNumber);
    List<FuelWaybill> findByConvoyNumber(int convoyNumber);
    void deleteByConvoyNumber(int convoyNumber);
}
