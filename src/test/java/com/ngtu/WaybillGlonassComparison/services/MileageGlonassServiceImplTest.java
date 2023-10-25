package com.ngtu.WaybillGlonassComparison.services;

import com.ngtu.WaybillGlonassComparison.entities.reports.MileageGlonass;
import com.ngtu.WaybillGlonassComparison.repositories.domain.MileageGlonassRepository;
import com.ngtu.WaybillGlonassComparison.services.domain.implementations.MileageGlonassServiceImpl;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageGlonassService;
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
class MileageGlonassServiceImplTest {

    private static final int CONVOY_NUMBER = 1;
    private static final int EXPECTED_NUMBER_OF_RECORDS = 243;
    @Mock
    private MileageGlonassRepository mileageGlonassRepository;
    @InjectMocks
    private MileageGlonassServiceImpl mileageGlonassService;
    private static ExcelWrapper excelWrapper;

    @BeforeAll
    static void setupExcelData() throws IOException {
        File excelFile = new File("src/test/ExcelData/mileageGlonass.xlsx");
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
        when(mileageGlonassRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<MileageGlonass> mileageGlonassList = mileageGlonassService.addData(excelWrapper);

        //then
        assertEquals(EXPECTED_NUMBER_OF_RECORDS, mileageGlonassList.size());
    }

    @Test
    void checkThatAddedDataHasNoNull() throws IOException {
        //given
        when(mileageGlonassRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        List<MileageGlonass> mileageGlonassList = mileageGlonassService.addData(excelWrapper);

        //then
        mileageGlonassList.stream()
                .peek(object -> assertNotNull(object.getTripDate()))
                .peek(object -> assertNotNull(object.getVehicleNumber()))
                .forEach(object -> assertNotNull(object.getMileage()));
    }

    @Test
    void checkAddedData() throws IOException {
        //given
        ArrayList<MileageGlonass> expectedMileageGlonassList = TestDataProvider.getMileageGlonassList();
        List<MileageGlonass> actualMileageGlonass;
        when(mileageGlonassRepository.saveAll(anyList()))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        actualMileageGlonass = mileageGlonassService.addData(excelWrapper);
        TestDataProvider.setIdsForList(actualMileageGlonass);

        //then
        boolean equals =
                expectedMileageGlonassList.containsAll(actualMileageGlonass) &&
                        actualMileageGlonass.containsAll(expectedMileageGlonassList);
        assertTrue(equals);
    }
}