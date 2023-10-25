package com.ngtu.WaybillGlonassComparison.util;

import org.springframework.web.multipart.MultipartFile;

public class ExcelWrapper {
    private MultipartFile excelFile;

    private int convoyNumber;

    public ExcelWrapper() {
    }

    public MultipartFile getExcelFile() {
        return excelFile;
    }

    public void setExcelFile(MultipartFile excelFile) {
        this.excelFile = excelFile;
    }

    public int getConvoyNumber() {
        return convoyNumber;
    }

    public void setConvoyNumber(int convoyNumber) {
        this.convoyNumber = convoyNumber;
    }
}
