package com.ngtu.WaybillGlonassComparison.controllers.domain;

import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.FuelWaybillService;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/fuel/waybill")
public class FuelWaybillController {
    private FuelWaybillService fuelWaybillService;

    @Autowired
    public FuelWaybillController(FuelWaybillService fuelWaybillService) {
        this.fuelWaybillService = fuelWaybillService;
    }

    @GetMapping()
    public String getPage(@RequestParam(value = "convoy", required = false) Integer convoy, Model model){
        model.addAttribute("reports",
                fuelWaybillService.findByConvoyNumber(Optional.ofNullable(convoy).orElse(1)));
        model.addAttribute("excel", new ExcelWrapper());
        return "/fuel/waybill";
    }


    @PostMapping()
    public String addData(@ModelAttribute("excel") ExcelWrapper excelFuelWaybill) throws IOException {
        fuelWaybillService.addData(excelFuelWaybill);
        return "redirect:waybill";
    }

    @PostMapping("/delete")
    public String deleteAll() throws IOException {
        fuelWaybillService.deleteAll();
        return "redirect:/fuel/waybill";
    }

    @PostMapping("/convoy/delete")
    public String delete(@RequestParam(value = "convoy", required = false) Integer convoy) throws IOException {
        fuelWaybillService.deleteByConvoyNumber(convoy);
        return "redirect:/fuel/waybill";
    }
}
