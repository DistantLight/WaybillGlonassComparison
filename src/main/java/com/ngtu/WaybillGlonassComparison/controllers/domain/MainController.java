package com.ngtu.WaybillGlonassComparison.controllers.domain;

import com.ngtu.WaybillGlonassComparison.entities.reports.ComparativeReport;
import com.ngtu.WaybillGlonassComparison.services.domain.interfaces.ComparativeReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@Controller
public class MainController {

    private final ComparativeReportService comparativeReportService;

    @Autowired
    public MainController(ComparativeReportService comparativeReportService) {
        this.comparativeReportService = comparativeReportService;
    }

    @GetMapping("/script")
    public ResponseEntity<Resource> getScript() throws IOException {

        Resource resource = new ClassPathResource("js/script.js");

        return ResponseEntity.ok()
                .contentLength(resource.contentLength())
                .contentType(MediaType.parseMediaType("text/javascript"))
                .body(resource);
    }

    @GetMapping("/index")
    public String getPage(@RequestParam(value = "convoy", required = false) Integer convoy, Model model){
        model.addAttribute("reports",
                comparativeReportService.findByConvoyNumber(Optional.ofNullable(convoy).orElse(1)));
        return "/main/index";
    }

    @PostMapping("/create")
    public String addData(@RequestParam(value = "convoy", required = false) Integer convoy){
        comparativeReportService.addData(convoy);
        return "redirect:index";
    }

    @PostMapping("/delete")
    public String deleteAll() throws IOException {
        comparativeReportService.deleteAll();
        return "redirect:index";
    }

    @PostMapping("/convoy/delete")
    public String delete(@RequestParam(value = "convoy", required = false) Integer convoy) throws IOException {
        comparativeReportService.deleteByConvoyNumber(convoy);
        return "redirect:/index";
    }

    @GetMapping("/trip/{id}")
    public String findById(@PathVariable("id") Long id,Model model){
        model.addAttribute("report", comparativeReportService.findById(id));
        return "/main/trip";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("report") ComparativeReport report){
        comparativeReportService.update(report);
        return "redirect:/index";
    }
}
