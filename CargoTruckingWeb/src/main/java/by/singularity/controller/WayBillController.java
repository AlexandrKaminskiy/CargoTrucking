package by.singularity.controller;

import by.singularity.dto.CheckpointDto;
import by.singularity.dto.WayBillDto;
import by.singularity.entity.CarriageStatus;
import by.singularity.entity.WayBill;
import by.singularity.exception.CarException;
import by.singularity.exception.InvoiceException;
import by.singularity.exception.UserException;
import by.singularity.exception.WayBillException;
import by.singularity.service.WayBillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("api/waybills")
@RequiredArgsConstructor
public class WayBillController {

    private final WayBillService wayBillService;

    @GetMapping()
    public void getAll(HttpServletResponse response,
                       CarriageStatus carriageStatus,
                       Pageable pageable) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<WayBillDto> wayBills = wayBillService.getAllWayBills(carriageStatus, pageable);
        responseMap.put("content", wayBills);
        responseMap.put("totalElements", wayBills.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Set<CheckpointDto> getById(@PathVariable Long id) throws WayBillException {
        return wayBillService.getWayBill(id).getCheckpoints();
    }

    @PostMapping()
    public Long addWayBill(@RequestBody @Valid WayBillDto wayBillDto) throws InvoiceException, CarException, UserException {
        WayBill wayBill = wayBillService.createWayBill(wayBillDto);
        return wayBill.getId();
    }

    @PutMapping("/{id}")
    public void reachCheckpoint(@PathVariable Long id) throws WayBillException {
        wayBillService.reachCheckpoint(id);
    }
}