package com.ngtu.WaybillGlonassComparison.controllers.domain;


import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelGlonassService;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/fuel/glonass")
public class FuelGlonassController {
    private FuelGlonassService fuelGlonassService;

    @Autowired
    public FuelGlonassController(FuelGlonassService fuelGlonassService) {
        this.fuelGlonassService = fuelGlonassService;
    }

    @GetMapping()
    public String getPage(@RequestParam(value = "convoy", required = false) Integer convoy, Model model){
        model.addAttribute("reports",
                fuelGlonassService.findByConvoyNumber(Optional.ofNullable(convoy).orElse(1)));
        model.addAttribute("excel", new ExcelWrapper());
        return "/fuel/glonass";
    }

    @PostMapping()
    public String addData(@ModelAttribute("excel") ExcelWrapper excelFuelGlonass ) throws IOException {
        fuelGlonassService.addData(excelFuelGlonass);
        return "redirect:glonass";
    }

    @PostMapping("/delete")
    public String deleteAll() throws IOException {
        fuelGlonassService.deleteAll();
        return "redirect:/fuel/glonass";
    }

    @PostMapping("/convoy/delete")
    public String delete(@RequestParam(value = "convoy", required = false) Integer convoy) throws IOException {
        fuelGlonassService.deleteByConvoyNumber(convoy);
        return "redirect:/fuel/glonass";
    }
}
