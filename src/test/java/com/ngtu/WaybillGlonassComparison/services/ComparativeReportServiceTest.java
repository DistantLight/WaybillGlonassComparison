package com.ngtu.WaybillGlonassComparison.services;

import com.ngtu.WaybillGlonassComparison.entities.reports.*;
import com.ngtu.WaybillGlonassComparison.repositories.domain.ComparativeReportRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.implementations.*;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelGlonassService;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelWaybillService;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageGlonassService;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageWaybillService;
import com.ngtu.WaybillGlonassComparison.util.TestDataProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ComparativeReportServiceTest {
    private static final int CONVOY_NUMBER = 1;
    @Mock
    private ComparativeReportRepository comparativeReportRepository;
    @Mock
    private FuelGlonassService fuelGlonassService;
    @Mock
    private FuelWaybillService fuelWaybillService;
    @Mock
    private MileageWaybillService mileageWaybillService;
    @Mock
    private MileageGlonassService mileageGlonassService;
    @InjectMocks
    private ComparativeReportServiceImpl comparativeReportService;

    @Test
    void addData(){
        //given
        ArrayList<MileageWaybill> mileageWaybillList = TestDataProvider.getMileageWaybillList();
        when(mileageWaybillService.findByConvoyNumber(CONVOY_NUMBER))
                .thenReturn(mileageWaybillList);

        doNothing().when(fuelWaybillService).deleteByWaybillNumberAndConvoyNumber(0L, CONVOY_NUMBER);

        ArrayList<FuelWaybill> fuelWaybillList = TestDataProvider.getFuelWaybillList();
        when(fuelWaybillService.findByConvoyNumber(CONVOY_NUMBER))
                .thenReturn(fuelWaybillList);

        when(fuelWaybillService
                .findByWaybillNumberAndConsumptionRate(anyLong(), anyDouble()))
                .thenAnswer(invocationOnMock -> {
                    Long waybillNumber = invocationOnMock.getArgument(0);
                    Double consumptionRate = invocationOnMock.getArgument(1);

                   FuelWaybill fuelWaybill = fuelWaybillList.stream()
                           .filter(o -> Objects.equals(o.getWaybillNumber(), waybillNumber) && (o.getConsumptionRate() - consumptionRate) < 0.001)
                           .findFirst().get();
                   return fuelWaybill;
                });


        when(fuelWaybillService.getEmptyObject(anyInt())).thenReturn(new FuelWaybill(fuelWaybillList.stream().map(FuelWaybill::getId).mapToLong(o -> o).max().orElse(0),
                                   0L, "", 0.0, 0.0, 0.0,
                                   0.0, 0.0, 0.0, 0.0, 0.0,
                                   0.0, 0.0, CONVOY_NUMBER));

        ArrayList<MileageGlonass> mileageGlonassList = TestDataProvider.getMileageGlonassList();
        when(mileageGlonassService
                .findByVehicleNumberAndConvoyNumberAndTripDateBetween(anyString(), anyInt(), any(Date.class), any(Date.class)))
                .thenAnswer(invocationOnMock -> {
                    String vehicleNumber = invocationOnMock.getArgument(0);
                    int convoyNumber = invocationOnMock.getArgument(1);
                    Date departureDate = invocationOnMock.getArgument(2);
                    Date returnDate = invocationOnMock.getArgument(3);
                    LocalDate departureLocalDate = departureDate.toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate();
                    LocalDate returnLocalDate = returnDate.toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate();


                    List<MileageGlonass> resultList = mileageGlonassList.stream()
                            .filter(o -> o.getVehicleNumber().equals(vehicleNumber) &&
                                    o.getConvoyNumber() == convoyNumber &&
                                    o.getTripDate().toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate().isAfter(departureLocalDate) &&
                                    o.getTripDate().toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate().isBefore(returnLocalDate))
                            .collect(Collectors.toList());
                    return resultList;
                });

        ArrayList<FuelGlonass> fuelGlonassList = TestDataProvider.getFuelGlonassList();
        when(fuelGlonassService
                .findByVehicleNumberAndConvoyNumberAndTripDateBetween(anyString(), anyInt(), any(Date.class), any(Date.class)))
                .thenAnswer(invocationOnMock -> {
                    String vehicleNumber = invocationOnMock.getArgument(0);
                    int convoyNumber = invocationOnMock.getArgument(1);
                    Date departureDate = invocationOnMock.getArgument(2);
                    Date returnDate = invocationOnMock.getArgument(3);
                    LocalDate departureLocalDate = departureDate.toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate();
                    LocalDate returnLocalDate = returnDate.toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate();


                    List<FuelGlonass> resultList = fuelGlonassList.stream()
                            .filter(o -> o.getVehicleNumber().equals(vehicleNumber) &&
                                    o.getConvoyNumber() == convoyNumber &&
                                    o.getTripDate().toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate().isAfter(departureLocalDate) &&
                                    o.getTripDate().toInstant().atZone(ZoneId.of("Europe/Moscow")).toLocalDate().isBefore(returnLocalDate))
                            .collect(Collectors.toList());
                     return resultList;
                });

        when(comparativeReportRepository.saveAll(anyList()))
                .thenAnswer(invocation -> {
                    List<ComparativeReport> originalReports = invocation.getArgument(0);
                    List<ComparativeReport> copyReports = new ArrayList<>(originalReports);
                    return copyReports;
                });

        //when
        List<ComparativeReport> actualReports = comparativeReportService.addData(CONVOY_NUMBER);
        TestDataProvider.setIdsForList(actualReports);

        //then
        assertEquals(154, actualReports.size());
    }
}