package com.ngtu.WaybillGlonassComparison.services.domain.implementations;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelGlonass;
import com.ngtu.WaybillGlonassComparison.repositories.domain.FuelGlonassRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelGlonassService;
import com.ngtu.WaybillGlonassComparison.util.DateConverter;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
public class FuelGlonassServiceImpl implements FuelGlonassService {
    private static final int FIRST_ROW = 3;
    private final FuelGlonassRepository fuelGlonassRepository;

    @Autowired
    public FuelGlonassServiceImpl(FuelGlonassRepository fuelGlonassRepository) {
        this.fuelGlonassRepository = fuelGlonassRepository;
    }

    @Override
    public List<FuelGlonass> getData(){
        return fuelGlonassRepository.findAll();
    }

    @Override
    public List<FuelGlonass> addData(ExcelWrapper excelFuelGlonass) throws IOException {
        var fileInputStream = excelFuelGlonass.getExcelFile().getInputStream();
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        var reports = new ArrayList<FuelGlonass>();
        int rowCount = FIRST_ROW;
        while (true){
            var report = new FuelGlonass();
            report.setVehicleNumber(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(0)
                    .getStringCellValue());
            report.setTripDate(DateConverter.convertFromExcelToSql((workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(1)
                    .getStringCellValue())));
            report.setFuelStart((workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(9)
                    .getNumericCellValue()));
            report.setFuelEnd((workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(10)
                    .getNumericCellValue()));
            try {
                report.setRefillTotal((workbook.getSheetAt(0)
                        .getRow(rowCount)
                        .getCell(11)
                        .getNumericCellValue()));
            } catch (NullPointerException e){
                report.setRefillTotal(0.0);
            }

            report.setVehicleNumber(processVehicleNumber(report.getVehicleNumber()));
            report.setConvoyNumber(excelFuelGlonass.getConvoyNumber());
            reports.add(report);

            try {
                workbook.getSheetAt(0).getRow(rowCount+1).getCell(0).getStringCellValue();
            } catch (NullPointerException e){
                break;
            }
            rowCount++;
        }
        return fuelGlonassRepository.saveAll(reports);
    }


    @Override
    public List<FuelGlonass> findByVehicleNumberAndConvoyNumberAndTripDateBetween(String number, int convoy, Date date1, Date date2) {
        return fuelGlonassRepository.findByVehicleNumberAndConvoyNumberAndTripDateBetween(number, convoy, date1, date2);
    }

    @Override
    public void deleteAll() {
        fuelGlonassRepository.deleteAll();
    }

    @Override
    public List<FuelGlonass> findByConvoyNumber(int convoyNumber){
        return fuelGlonassRepository.findByConvoyNumber(convoyNumber);
    }

    @Override
    @Transactional
    public void deleteByConvoyNumber(int convoyNumber){
        fuelGlonassRepository.deleteByConvoyNumber(convoyNumber);
    }

    private static String processVehicleNumber(String vehicleNumber){
        return vehicleNumber.replaceAll(" ", "").toLowerCase(Locale.ROOT);
    }
}
