package com.ngtu.WaybillGlonassComparison.services;

import com.ngtu.WaybillGlonassComparison.entities.reports.FuelGlonass;
import com.ngtu.WaybillGlonassComparison.repositories.domain.FuelGlonassRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.implementations.FuelGlonassServiceImpl;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelGlonassService;
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
class FuelGlonassServiceImplTest {

    private static final int CONVOY_NUMBER = 1;
    private static final int EXPECTED_NUMBER_OF_RECORDS = 46;
    @Mock
    private FuelGlonassRepository fuelGlonassRepository;
    @InjectMocks
    private FuelGlonassServiceImpl fuelGlonassService;
    private static ExcelWrapper excelWrapper;

    @BeforeAll
    static void setupExcelData() throws IOException {
        File excelFile = new File("src/test/ExcelData/fuelGlonass.xlsx");
        MockMultipartFile mockExcelFile = new MockMultipartFile(
                "fuelGlonass",
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
        when(fuelGlonassRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<FuelGlonass> fuelGlonassList = fuelGlonassService.addData(excelWrapper);

        //then
        assertEquals(EXPECTED_NUMBER_OF_RECORDS, fuelGlonassList.size());
    }

    @Test
    void checkThatAddedDataHasNoNull() throws IOException {
        //given
        when(fuelGlonassRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<FuelGlonass> fuelGlonassList = fuelGlonassService.addData(excelWrapper);

        //then
        fuelGlonassList.stream()
                .peek(object -> assertNotNull(object.getVehicleNumber()))
                .peek(object -> assertNotNull(object.getTripDate()))
                .peek(object -> assertNotNull(object.getFuelStart()))
                .peek(object -> assertNotNull(object.getFuelEnd()))
                .forEach(object -> assertNotNull(object.getRefillTotal()));
    }

    @Test
    void checkAddedData() throws IOException {
        //given
        ArrayList<FuelGlonass> expectedFuelGlonassList = TestDataProvider.getFuelGlonassList();
        List<FuelGlonass> actualFuelGlonassList;
        when(fuelGlonassRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        actualFuelGlonassList = fuelGlonassService.addData(excelWrapper);
        TestDataProvider.setIdsForList(actualFuelGlonassList);

        //then
        boolean equals =
                expectedFuelGlonassList.containsAll(actualFuelGlonassList) &&
                        actualFuelGlonassList.containsAll(expectedFuelGlonassList);
        assertTrue(equals);
    }
}