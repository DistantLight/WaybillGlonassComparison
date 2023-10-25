package com.ngtu.WaybillGlonassComparison.services.domain.implementations;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelWaybill;
import com.ngtu.WaybillGlonassComparison.repositories.domain.FuelWaybillRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelWaybillService;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class FuelWaybillServiceImpl implements FuelWaybillService {
    private static final int FIRST_ROW = 1;

    private final FuelWaybillRepository fuelWaybillRepository;

    @Autowired
    public FuelWaybillServiceImpl(FuelWaybillRepository fuelWaybillRepository) {
        this.fuelWaybillRepository = fuelWaybillRepository;
    }

    @Override
    public List<FuelWaybill> getData(){
        return fuelWaybillRepository.findAll();
    }

    @Override
    public List<FuelWaybill> addData(ExcelWrapper excelFuelWaybill) throws IOException {
        var fileInputStream = excelFuelWaybill.getExcelFile().getInputStream();
        Workbook workbook = new XSSFWorkbook(fileInputStream);

        var reports = new ArrayList<FuelWaybill>();
        int rowCount = FIRST_ROW;
        String checkEmptyString;
        Double checkEmptyNumeric;
        while (true){
            var report = new FuelWaybill();

            try {
                checkEmptyString = (workbook
                        .getSheetAt(0)
                        .getRow(rowCount)
                        .getCell(0)
                        .getStringCellValue());
                if(Objects.equals(checkEmptyString, "")){
                    report.setWaybillNumber(0L);
                } else {
                    report.setWaybillNumber(Long.parseLong(checkEmptyString));
                }
            } catch (IllegalStateException e){
                checkEmptyNumeric = (workbook
                        .getSheetAt(0)
                        .getRow(rowCount)
                        .getCell(0)
                        .getNumericCellValue());
                report.setWaybillNumber(checkEmptyNumeric.longValue());
            }

            report.setFuelGrade(Optional.ofNullable(workbook
                    .getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(3)
                    .getStringCellValue())
                    .orElse(""));
            report.setFuelStart(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(4)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelEnd(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(7)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setConsumptionRate(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(10)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelingOwnWeight(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(11)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelingBranchWeight(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(12)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelingOwnVolume(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(13)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelingBranchVolume(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(14)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelingOutsideVolume(Optional.of(workbook.getSheetAt(0)
                    .getRow(rowCount)
                    .getCell(15)
                    .getNumericCellValue())
                    .orElse(0.0));
            report.setFuelTotal(report.getFuelingOwnWeight() +
                    report.getFuelingBranchWeight() +
                    report.getFuelingOwnVolume() +
                    report.getFuelingBranchVolume() +
                    report.getFuelingOutsideVolume());
            report.setFuelConsumption(report.getFuelStart() +
                    report.getFuelTotal() -
                    report.getFuelEnd());
            report.setConvoyNumber(excelFuelWaybill.getConvoyNumber());
            reports.add(report);

            try {
                workbook.getSheetAt(0).getRow(rowCount+1).getCell(3).getStringCellValue();
            } catch (NullPointerException e){
                break;
            }
            rowCount++;
        }
        return fuelWaybillRepository.saveAll(reports);
    }

    @Override
    public FuelWaybill findByWaybillNumberAndConsumptionRate(Long waybillNumber, Double consumptionRate){
        return fuelWaybillRepository.findTop1ByWaybillNumberAndConsumptionRate(waybillNumber, consumptionRate);
    }

    @Override
    public void deleteAll() {
        fuelWaybillRepository.deleteAll();
    }

    @Override
    public List<FuelWaybill> findByConvoyNumber(int convoyNumber){
        return fuelWaybillRepository.findByConvoyNumber(convoyNumber);
    }

    @Override
    @Transactional
    public void deleteByConvoyNumber(int convoyNumber){
        fuelWaybillRepository.deleteByConvoyNumber(convoyNumber);
    }

    @Override
    @Transactional
    public void deleteByWaybillNumber(Long number){
        fuelWaybillRepository.deleteByWaybillNumber(number);
    }

    @Override
    @Transactional
    public void deleteByWaybillNumberAndConvoyNumber(Long number, int convoyNumber){
        fuelWaybillRepository.deleteByWaybillNumberAndConvoyNumber(number, convoyNumber);
    }

    @Override
    public FuelWaybill getEmptyObject(int convoyNumber){
        FuelWaybill fuelWaybill = new FuelWaybill();
        fuelWaybill.setWaybillNumber(0L);
        fuelWaybill.setFuelGrade("");
        fuelWaybill.setFuelStart(0.0);
        fuelWaybill.setFuelEnd(0.0);
        fuelWaybill.setConsumptionRate(0.0);
        fuelWaybill.setFuelingOwnWeight(0.0);
        fuelWaybill.setFuelingBranchWeight(0.0);
        fuelWaybill.setFuelingOwnVolume(0.0);
        fuelWaybill.setFuelingBranchVolume(0.0);
        fuelWaybill.setFuelingOutsideVolume(0.0);
        fuelWaybill.setFuelConsumption(0.0);
        fuelWaybill.setFuelTotal(0.0);
        fuelWaybill.setConvoyNumber(convoyNumber);
        return fuelWaybillRepository.save(fuelWaybill);
    }
}
