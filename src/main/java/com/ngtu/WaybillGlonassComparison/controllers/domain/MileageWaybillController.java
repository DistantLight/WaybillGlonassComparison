package com.ngtu.WaybillGlonassComparison.controllers.domain;

import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.MileageWaybillService;
import com.ngtu.WaybillGlonassComparison.util.ExcelWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping(value = "/mileage/waybill")
public class MileageWaybillController{
    private MileageWaybillService mileageWaybillService;

    @Autowired
    public MileageWaybillController(MileageWaybillService mileageWaybillService) {
        this.mileageWaybillService = mileageWaybillService;
    }

    @GetMapping()
    public String getPage(@RequestParam(value = "convoy", required = false) Integer convoy, Model model){
        model.addAttribute("reports",
                mileageWaybillService.findByConvoyNumber(Optional.ofNullable(convoy).orElse(1)));
        model.addAttribute("excel", new ExcelWrapper());
        return "/mileage/waybill";
    }

    @PostMapping()
    public String addData(@ModelAttribute("excel") ExcelWrapper excelMileageWaybill) throws IOException {
        mileageWaybillService.addData(excelMileageWaybill);
        return "redirect:waybill";
    }

    @PostMapping("/delete")
    public String deleteAll() throws IOException {
        mileageWaybillService.deleteAll();
        return "redirect:/mileage/waybill";
    }

    @PostMapping("/convoy/delete")
    public String delete(@RequestParam(value = "convoy", required = false) Integer convoy) throws IOException {
        mileageWaybillService.deleteByConvoyNumber(convoy);
        return "redirect:/mileage/waybill";
    }
}
