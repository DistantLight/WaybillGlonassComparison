package com.ngtu.WaybillGlonassComparison.services.domain.interfaces;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelWaybill;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.List;

public interface FuelWaybillService {
    List<FuelWaybill> getData();

    List<FuelWaybill> addData(ExcelWrapper excelFuelWaybill) throws IOException;

    FuelWaybill findByWaybillNumberAndConsumptionRate(Long waybillNumber, Double consumptionRate);

    void deleteAll();

    List<FuelWaybill> findByConvoyNumber(int convoyNumber);

    @Transactional
    void deleteByConvoyNumber(int convoyNumber);

    @Transactional
    void deleteByWaybillNumber(Long number);

    @Transactional
    void deleteByWaybillNumberAndConvoyNumber(Long number, int convoyNumber);

    FuelWaybill getEmptyObject(int convoyNumber);
}
