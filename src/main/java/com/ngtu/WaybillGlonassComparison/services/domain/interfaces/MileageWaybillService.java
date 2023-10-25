package com.ngtu.WaybillGlonassComparison.services.domain.interfaces;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageWaybill;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

public interface MileageWaybillService {
    List<MileageWaybill> getData();

    List<MileageWaybill> addData(ExcelWrapper excelMileageWaybill) throws IOException;

    void deleteAll();

    List<MileageWaybill> findByConvoyNumber(int convoyNumber);

    @Transactional
    void deleteByConvoyNumber(int convoyNumber);
}
