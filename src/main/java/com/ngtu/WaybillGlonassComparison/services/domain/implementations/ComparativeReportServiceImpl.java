package com.ngtu.WaybillGlonassComparison.services.domain.implementations;

import com.ngtu.WaybillGlonassComparison.entities.reports.*;
import com.ngtu.WaybillGlonassComparison.repositories.domain.ComparativeReportRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class ComparativeReportServiceImpl implements ComparativeReportService {
    private final ComparativeReportRepository comparativeReportRepository;
    private final FuelGlonassService fuelGlonassService;
    private final FuelWaybillService fuelWaybillService;
    private final MileageWaybillService mileageWaybillService;
    private final MileageGlonassService mileageGlonassService;
    private final ArrayList<ComparativeReport> reports;

    @Autowired
    public ComparativeReportServiceImpl(ComparativeReportRepository comparativeReportRepository,
                                        FuelGlonassService fuelGlonassService,
                                        FuelWaybillService fuelWaybillService,
                                        MileageWaybillService mileageWaybillService,
                                        MileageGlonassService mileageGlonassService) {
        this.comparativeReportRepository = comparativeReportRepository;
        this.fuelGlonassService = fuelGlonassService;
        this.fuelWaybillService = fuelWaybillService;
        this.mileageWaybillService = mileageWaybillService;
        this.mileageGlonassService = mileageGlonassService;
        this.reports = new ArrayList<>();
    }

    @Override
    public List<ComparativeReport> getData(){
        return comparativeReportRepository.findAll();
    }

    @Override
    public ComparativeReport findById(Long id){
        return comparativeReportRepository.findById(id).get();
    }

    @Override
    public void update(ComparativeReport report){
        comparativeReportRepository.save(report);
    }

    @Override
    public List<ComparativeReport> findByConvoyNumber(int convoyNumber){
        var mileageWaybills = mileageWaybillService.findByConvoyNumber(convoyNumber);
        var ids = mileageWaybills.stream().map(MileageWaybill::getId).toList();
        var comparativeReports = comparativeReportRepository.findByConvoyNumber(convoyNumber);
        var sortedComparativeReports = new ArrayList<ComparativeReport>();
        for (Long id : ids){
            for (ComparativeReport comparativeReport: comparativeReports){
                if(Objects.equals(id, comparativeReport.getMileageWaybill().getId())){
                    sortedComparativeReports.add(comparativeReport);
                }
            }
        }
        return sortedComparativeReports;
    }

    @Override
    public List<ComparativeReport> addData(int convoyNumber) {
        setMileageWaybillData(convoyNumber);
        setFuelWaybillData(convoyNumber);
        setMileageGlonassData();
        setFuelGlonassData();

        for (ComparativeReport report: reports) {
            report.setMileageDifference(report.getMileageWaybill().getMileage() - report.getMileage());
            report.setFuelDifferenceStart(report.getFuelWaybill().getFuelStart() - report.getFuelStart());
            report.setFuelDifferenceEnd(report.getFuelWaybill().getFuelEnd() - report.getFuelEnd());
            report.setRefillDifference(report.getFuelWaybill().getFuelTotal() - report.getRefillTotal());
            report.setConsumptionTotal(report.getFuelStart() + report.getRefillTotal() - report.getFuelEnd());
            report.setConsumptionDifference(report.getFuelWaybill().getFuelConsumption() - report.getConsumptionTotal());
            report.setConvoyNumber(convoyNumber);
        }

        List<ComparativeReport> savedReports = comparativeReportRepository.saveAll(reports);
        reports.clear();
        return savedReports;
    }

    @Override
    public void deleteAll() {
        comparativeReportRepository.deleteAll();
        fuelWaybillService.deleteByWaybillNumber(0L);
    }

    @Override
    @Transactional
    public void deleteByConvoyNumber(int convoyNumber){
        comparativeReportRepository.deleteByConvoyNumber(convoyNumber);
    }

    private void setMileageWaybillData(int convoyNumber){
        var list = mileageWaybillService.findByConvoyNumber(convoyNumber);
        for (MileageWaybill mileageWaybill: list) {
            var report = new ComparativeReport();
            report.setMileageWaybill(mileageWaybill);
            reports.add(report);
        }
    }

    private void setFuelWaybillData(int convoyNumber){
        fuelWaybillService.deleteByWaybillNumberAndConvoyNumber(0L, convoyNumber);
        Map<Long, Double> maxValues = new HashMap<>();
        var set = new HashSet(Arrays.asList("101592НД", "1015ДТГН", "1015ДТНД", "101595ГН","1015ГЗДВ", "1015ГЗМК"));
        var list = fuelWaybillService.findByConvoyNumber(convoyNumber);
        for (FuelWaybill fuelWaybill: list) {
            if((maxValues.get(fuelWaybill.getWaybillNumber()) == null)){
                maxValues.put(fuelWaybill.getWaybillNumber(), fuelWaybill.getConsumptionRate());
                continue;
            }

            if(set.contains(fuelWaybill.getFuelGrade())
                    && (maxValues.get(fuelWaybill.getWaybillNumber()) != null)
                    && fuelWaybill.getConsumptionRate() > maxValues.get(fuelWaybill.getWaybillNumber())){
                maxValues.remove(fuelWaybill.getWaybillNumber());
                maxValues.put(fuelWaybill.getWaybillNumber(), fuelWaybill.getConsumptionRate());
            }
        }

        for (ComparativeReport report: reports) {
            Long waybillNumber = report.getMileageWaybill().getWaybillNumber();
            Double consumptionRate = maxValues.get(report.getMileageWaybill().getWaybillNumber());

            report.setFuelWaybill(Optional.ofNullable(fuelWaybillService
                            .findByWaybillNumberAndConsumptionRate(waybillNumber, consumptionRate))
                    .orElse(fuelWaybillService.getEmptyObject(report.getConvoyNumber())));
        }
    }

    private void setMileageGlonassData(){
        for (ComparativeReport report: reports) {
            var mileageGlonassList = mileageGlonassService.findByVehicleNumberAndConvoyNumberAndTripDateBetween(
                    report.getMileageWaybill().getVehicleNumber(),
                    report.getMileageWaybill().getConvoyNumber(),
                    report.getMileageWaybill().getDepartureDate(),
                    report.getMileageWaybill().getReturnDate());

            report.setMileage(0.0);
            report.setMaxSpeed(0);
            report.setAverageSpeed(0);
            for (MileageGlonass mileageGlonass : mileageGlonassList){
                report.setMileage(report.getMileage() + mileageGlonass.getMileage());

                if (mileageGlonass.getMaxSpeed() > report.getMaxSpeed()){
                    report.setMaxSpeed(mileageGlonass.getMaxSpeed());
                }

                if (mileageGlonass.getAverageSpeed() > report.getAverageSpeed()){
                    report.setAverageSpeed(mileageGlonass.getAverageSpeed());
                }
            }
        }
    }

    private void setFuelGlonassData(){
        for (ComparativeReport report: reports) {
            var fuelGlonassList = fuelGlonassService.findByVehicleNumberAndConvoyNumberAndTripDateBetween(
                    report.getMileageWaybill().getVehicleNumber(),
                    report.getMileageWaybill().getConvoyNumber(),
                    report.getMileageWaybill().getDepartureDate(),
                    report.getMileageWaybill().getReturnDate());

            if(!fuelGlonassList.isEmpty()){
                report.setFuelStart(fuelGlonassList.get(0).getFuelStart());
                report.setFuelEnd(fuelGlonassList.get(fuelGlonassList.size() - 1).getFuelEnd());
            } else {
                report.setFuelStart(0.0);
                report.setFuelEnd(0.0);
            }

            report.setRefillTotal(0.0);
            for (FuelGlonass fuelGlonass : fuelGlonassList){
                report.setRefillTotal(report.getRefillTotal() + fuelGlonass.getRefillTotal());
            }
        }
    }
}
