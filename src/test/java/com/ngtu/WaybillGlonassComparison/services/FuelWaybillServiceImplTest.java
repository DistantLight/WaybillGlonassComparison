package com.ngtu.WaybillGlonassComparison.services;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelWaybill;
import com.ngtu.WaybillGlonassComparison.repositories.domain.FuelWaybillRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.implementations.FuelWaybillServiceImpl;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelWaybillService;
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
class FuelWaybillServiceImplTest {
    private static final int CONVOY_NUMBER = 1;
    private static final int EXPECTED_NUMBER_OF_RECORDS = 283;
    @Mock
    private FuelWaybillRepository fuelWaybillRepository;
    @InjectMocks
    private FuelWaybillServiceImpl fuelWaybillService;
    private static ExcelWrapper excelWrapper;

    @BeforeAll
    static void setupExcelData() throws IOException {
        File excelFile = new File("src/test/ExcelData/fuelWaybills.xlsx");
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
        when(fuelWaybillRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<FuelWaybill> fuelWaybillListList = fuelWaybillService.addData(excelWrapper);

        //then
        assertEquals(EXPECTED_NUMBER_OF_RECORDS, fuelWaybillListList.size());
    }

    @Test
    void checkThatAddedDataHasNoNull() throws IOException {
        //given
        when(fuelWaybillRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<FuelWaybill> fuelWaybillListList = fuelWaybillService.addData(excelWrapper);

        //then
        fuelWaybillListList.stream()
                .peek(object -> assertNotNull(object.getWaybillNumber()))
                .peek(object -> assertNotNull(object.getFuelGrade()))
                .peek(object -> assertNotNull(object.getConsumptionRate()))
                .peek(object -> assertNotNull(object.getFuelingOwnWeight()))
                .peek(object -> assertNotNull(object.getFuelingBranchWeight()))
                .peek(object -> assertNotNull(object.getFuelingOwnVolume()))
                .peek(object -> assertNotNull(object.getFuelingBranchVolume()))
                .peek(object -> assertNotNull(object.getFuelingOutsideVolume()))
                .peek(object -> assertNotNull(object.getFuelConsumption()))
                .forEach(object -> assertNotNull(object.getFuelTotal()));
    }

    @Test
    void checkAddedData() throws IOException {
        //given
        ArrayList<FuelWaybill> expectedFuelWaybillList = TestDataProvider.getFuelWaybillList();
        List<FuelWaybill> actualFuelWaybillList;
        when(fuelWaybillRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        actualFuelWaybillList = fuelWaybillService.addData(excelWrapper);
        TestDataProvider.setIdsForList(actualFuelWaybillList);

        //then
        boolean equals =
                expectedFuelWaybillList.containsAll(actualFuelWaybillList) &&
                        actualFuelWaybillList.containsAll(expectedFuelWaybillList);
        assertTrue(equals);
    }
}