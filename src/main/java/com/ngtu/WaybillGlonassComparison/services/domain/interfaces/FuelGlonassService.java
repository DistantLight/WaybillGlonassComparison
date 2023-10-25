package com.ngtu.WaybillGlonassComparison.services.domain.interfaces;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelGlonass;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface FuelGlonassService {
    List<FuelGlonass> getData();

    List<FuelGlonass> addData(ExcelWrapper excelFuelGlonass) throws IOException;

    List<FuelGlonass> findByVehicleNumberAndConvoyNumberAndTripDateBetween(String number, int convoy, Date date1, Date date2);

    void deleteAll();

    List<FuelGlonass> findByConvoyNumber(int convoyNumber);

    @Transactional
    void deleteByConvoyNumber(int convoyNumber);
}
