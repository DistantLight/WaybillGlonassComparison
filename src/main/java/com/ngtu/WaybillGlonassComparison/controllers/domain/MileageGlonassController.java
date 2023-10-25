package com.ngtu.WaybillGlonassComparison.controllers.domain;

import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageGlonassService;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/mileage/glonass")
public class MileageGlonassController{
    private MileageGlonassService mileageGlonassService;

    @Autowired
    public MileageGlonassController(MileageGlonassService mileageGlonassService) {
        this.mileageGlonassService = mileageGlonassService;
    }

    @GetMapping()
    public String getPage(@RequestParam(value = "convoy", required = false) Integer convoy, Model model){
        model.addAttribute("reports",
                mileageGlonassService.findByConvoyNumber(Optional.ofNullable(convoy).orElse(1)));
        model.addAttribute("excel", new ExcelWrapper());
        return "/mileage/glonass";
    }

    @PostMapping()
    public String addData(@ModelAttribute("excel") ExcelWrapper excelMileageGlonass) throws IOException {
        mileageGlonassService.addData(excelMileageGlonass);
        return "redirect:glonass";
    }

    @PostMapping("/delete")
    public String deleteAll() throws IOException {
        mileageGlonassService.deleteAll();
        return "redirect:/mileage/glonass";
    }

    @PostMapping("/convoy/delete")
    public String delete(@RequestParam(value = "convoy", required = false) Integer convoy) throws IOException {
        mileageGlonassService.deleteByConvoyNumber(convoy);
        return "redirect:/mileage/glonass";
    }
}
