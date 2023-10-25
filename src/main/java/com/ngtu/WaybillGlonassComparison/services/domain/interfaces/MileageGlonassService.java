package com.ngtu.WaybillGlonassComparison.services.domain.interfaces;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageGlonass;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface MileageGlonassService {
    List<MileageGlonass> getData();

    List<MileageGlonass> addData(ExcelWrapper excelMileageGlonass) throws IOException;

    void deleteAll();

    List<MileageGlonass> findByVehicleNumberAndTripDateBetween(String number, Date date1, Date date2);

    List<MileageGlonass> findByVehicleNumberAndConvoyNumberAndTripDateBetween(String number, int convoy, Date date1, Date date2);

    List<MileageGlonass> findByConvoyNumber(int convoyNumber);

    @Transactional
    void deleteByConvoyNumber(int convoyNumber);
}
