package com.ngtu.WaybillGlonassComparison.services;


import com.ngtu.WaybillGlonassComparison.entities.reports.MileageWaybill;
import com.ngtu.WaybillGlonassComparison.repositories.domain.MileageWaybillRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.implementations.MileageWaybillServiceImpl;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageWaybillService;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import com.ngtu.WaybillGlonassComparison.util.TestDataProvider;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MileageWaybillServiceImplTest {

    private static final int CONVOY_NUMBER = 1;
    private static final int EXPECTED_NUMBER_OF_RECORDS = 154;
    @Mock
    private MileageWaybillRepository mileageWaybillRepository;
    @InjectMocks
    private MileageWaybillServiceImpl mileageWaybillService;
    private static ExcelWrapper excelWrapper;

    @BeforeAll
    static void setupExcelData() throws IOException {
        File excelFile = new File("src/test/ExcelData/mileageWaybills.xlsx");
        MockMultipartFile mockExcelFile = new MockMultipartFile(
                "fuelWaybill",
                excelFile.getName(),
                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
                Files.readAllBytes(excelFile.toPath())
        );
        excelWrapper = new ExcelWrapper();
        excelWrapper.setExcelFile(mockExcelFile);
        excelWrapper.setConvoyNumber(CONVOY_NUMBER);
    }

    @Test
    void checkThatAddedDataHasExpectedNumberOfObjects() throws IOException {
        //given
        when(mileageWaybillRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<MileageWaybill> mileageWaybillList = mileageWaybillService.addData(excelWrapper);

        //then
        assertEquals(EXPECTED_NUMBER_OF_RECORDS, mileageWaybillList.size());
    }

    @Test
    void checkThatAddedDataHasNoNull() throws IOException {
        //given
        when(mileageWaybillRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<MileageWaybill> mileageGlonassList = mileageWaybillService.addData(excelWrapper);

        //then
        mileageGlonassList.stream()
                .peek(object -> assertNotNull(object.getVehicleId()))
                .peek(object -> assertNotNull(object.getColumn()))
                .peek(object -> assertNotNull(object.getVehicleModification()))
                .peek(object -> assertNotNull(object.getVehicleNumber()))
                .peek(object -> assertNotNull(object.getDriverName()))
                .peek(object -> assertNotNull(object.getWaybillNumber()))
                .peek(object -> assertNotNull(object.getDepartureDate()))
                .peek(object -> assertNotNull(object.getReturnDate()))
                .peek(object -> assertNotNull(object.getMileage()))
                .peek(object -> assertNotNull(object.getPlannedRoute()))
                .forEach(object -> assertNotNull(object.getActualRoute()));
    }

    @Test
    void checkAddedData() throws IOException {
        //given
        ArrayList<MileageWaybill> expectedMileageWaybillList = TestDataProvider.getMileageWaybillList();
        List<MileageWaybill> actualMileageWaybill;
        when(mileageWaybillRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        actualMileageWaybill = mileageWaybillService.addData(excelWrapper);
        TestDataProvider.setIdsForList(actualMileageWaybill);

        //then
        boolean equals =
                expectedMileageWaybillList.containsAll(actualMileageWaybill) &&
                        actualMileageWaybill.containsAll(expectedMileageWaybillList);
        assertTrue(equals);
    }
}