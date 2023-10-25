package com.ngtu.WaybillGlonassComparison.services.domain.implementations;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageWaybill;
import com.ngtu.WaybillGlonassComparison.repositories.domain.MileageWaybillRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageWaybillService;
import com.ngtu.WaybillGlonassComparison.util.DateConverter;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class MileageWaybillServiceImpl implements MileageWaybillService {
    private static final int FIRST_ROW = 1;
    private final MileageWaybillRepository mileageWaybillRepository;

    @Autowired
    public MileageWaybillServiceImpl(MileageWaybillRepository mileageWaybillRepository) {
        this.mileageWaybillRepository = mileageWaybillRepository;
    }


    @Override
    public List<MileageWaybill> getData(){
        return mileageWaybillRepository.findAll();
    }

    @Override
    public List<MileageWaybill> addData(ExcelWrapper excelMileageWaybill) throws IOException{
        var fileInputStream = excelMileageWaybill.getExcelFile().getInputStream();
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        var reports = new ArrayList<MileageWaybill>();
        int rowCount = FIRST_ROW;
        while (true){
            var report = new MileageWaybill();
            report.setVehicleId((long)(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(0)
                    .getNumericCellValue()));
            report.setColumn(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(2)
                    .getStringCellValue());
            report.setVehicleModification(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(3)
                    .getStringCellValue());
            report.setVehicleNumber(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(5)
                    .getStringCellValue());
            report.setDriverName(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(10)
                    .getStringCellValue());
            report.setWaybillNumber((long)(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(6)
                    .getNumericCellValue()));
            report.setDepartureDate(DateConverter.convertFromJavaToSql(String.valueOf(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(8)
                    .getDateCellValue())));
            report.setReturnDate(DateConverter.convertFromJavaToSql(String.valueOf(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(9)
                    .getDateCellValue())));
            report.setMileage((workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(29)
                    .getNumericCellValue()));
            report.setPlannedRoute(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(19)
                    .getStringCellValue());
            report.setActualRoute(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(20)
                    .getStringCellValue());

            report.setVehicleNumber(processVehicleNumber(report.getVehicleNumber()));
            report.setConvoyNumber(excelMileageWaybill.getConvoyNumber());
            reports.add(report);
            try {
                workbook.getSheetAt(0).getRow(rowCount+1).getCell(2).getStringCellValue();
            } catch (NullPointerException e){
                break;
            }
            rowCount++;
        }
        return mileageWaybillRepository.saveAll(reports);
    }

    @Override
    public void deleteAll() {
        mileageWaybillRepository.deleteAll();
    }

    @Override
    public List<MileageWaybill> findByConvoyNumber(int convoyNumber){
        return mileageWaybillRepository.findByConvoyNumber(convoyNumber);
    }

    @Override
    @Transactional
    public void deleteByConvoyNumber(int convoyNumber){
        mileageWaybillRepository.deleteByConvoyNumber(convoyNumber);
    }

    private static String processVehicleNumber(String vehicleNumber){
        return vehicleNumber.replaceAll(" ", "").toLowerCase(Locale.ROOT);
    }
}
