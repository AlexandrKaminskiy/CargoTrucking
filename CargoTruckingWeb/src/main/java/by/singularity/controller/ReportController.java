package by.singularity.controller;

import by.singularity.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;

    @GetMapping("/waybills")
    public void waybillReport(@RequestParam String startWeekDate,
                             @RequestParam String endWeekDate,
                             HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachement; filename=waybill_report.xlsx");
        reportService.createWaybillReport(startWeekDate, endWeekDate, response);
    }

    @GetMapping("/clients")
    public void clientReport(@RequestParam String startWeekDate,
                              @RequestParam String endWeekDate,
                              HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachement; filename=client_report.xlsx");
        reportService.createClientReport(startWeekDate, endWeekDate, response);
    }
}
