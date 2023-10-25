package com.ngtu.WaybillGlonassComparison.services.domain.implementations;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageGlonass;
import com.ngtu.WaybillGlonassComparison.repositories.domain.MileageGlonassRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageGlonassService;
import com.ngtu.WaybillGlonassComparison.util.DateConverter;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class MileageGlonassServiceImpl implements MileageGlonassService {
    private static final int FIRST_ROW = 3;
    private final MileageGlonassRepository mileageGlonassRepository;

    @Autowired
    public MileageGlonassServiceImpl(MileageGlonassRepository mileageGlonassRepository) {
        this.mileageGlonassRepository = mileageGlonassRepository;
    }

    @Override
    public List<MileageGlonass> getData(){
        return mileageGlonassRepository.findAll();
    }

    @Override
    public List<MileageGlonass> addData(ExcelWrapper excelMileageGlonass) throws IOException {
        var fileInputStream = excelMileageGlonass.getExcelFile().getInputStream();
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        var reports = new ArrayList<MileageGlonass>();
        int rowCount = FIRST_ROW;
        while (true){
            var report = new MileageGlonass();
            report.setTripDate(DateConverter.convertFromExcelToSql((workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(0)
                    .getStringCellValue())));
            report.setVehicleNumber(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(1)
                    .getStringCellValue());
            try {
                report.setMaxSpeed(((Double)(workbook.getSheetAt(0)
                        .getRow(rowCount)
                        .getCell(6)
                        .getNumericCellValue()))
                        .intValue());
            } catch (NullPointerException e){
                report.setMaxSpeed(0);
            }
            try {
                report.setAverageSpeed(((Double)(workbook.getSheetAt(0)
                        .getRow(rowCount)
                        .getCell(7)
                        .getNumericCellValue()))
                        .intValue());
            } catch (NullPointerException e){
                report.setAverageSpeed(0);
            }
            try {
                report.setMileage((workbook.getSheetAt(0)
                        .getRow(rowCount)
                        .getCell(8)
                        .getNumericCellValue()));
            } catch (NullPointerException e){
                report.setMileage(0.0);
            }

            report.setVehicleNumber(processVehicleNumber(report.getVehicleNumber()));
            report.setConvoyNumber(excelMileageGlonass.getConvoyNumber());
            reports.add(report);
            try {
                workbook.getSheetAt(0).getRow(rowCount+1).getCell(0).getStringCellValue();
            } catch (NullPointerException e){
                break;
            }
            rowCount++;
        }
        return mileageGlonassRepository.saveAll(reports);
    }

    @Override
    public void deleteAll() {
        mileageGlonassRepository.deleteAll();
    }

    @Override
    public List<MileageGlonass> findByVehicleNumberAndTripDateBetween(String number, Date date1, Date date2){
        return mileageGlonassRepository.findByVehicleNumberAndTripDateBetween(number, date1, date2);
    }

    @Override
    public List<MileageGlonass> findByVehicleNumberAndConvoyNumberAndTripDateBetween(String number, int convoy, Date date1, Date date2){
        return mileageGlonassRepository.findByVehicleNumberAndConvoyNumberAndTripDateBetween(number, convoy, date1, date2);
    }

    @Override
    public List<MileageGlonass> findByConvoyNumber(int convoyNumber){
        return mileageGlonassRepository.findByConvoyNumber(convoyNumber);
    }

    @Override
    @Transactional
    public void deleteByConvoyNumber(int convoyNumber){
        mileageGlonassRepository.deleteByConvoyNumber(convoyNumber);
    }

    private static String processVehicleNumber(String vehicleNumber){
        vehicleNumber = vehicleNumber.replaceAll(" ", "").toLowerCase(Locale.ROOT);
        while (vehicleNumber.contains("(")){
            vehicleNumber = vehicleNumber.substring(vehicleNumber.indexOf("(") + 1);
        }
        vehicleNumber = vehicleNumber.substring(0, vehicleNumber.length() - 1);
        int index = vehicleNumber.indexOf(")");
        if(index != -1) {
            vehicleNumber = vehicleNumber.substring(index + 1);
        }
        return vehicleNumber;
    }
}
