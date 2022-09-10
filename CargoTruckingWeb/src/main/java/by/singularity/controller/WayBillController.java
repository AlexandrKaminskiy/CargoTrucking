package by.singularity.controller;

import by.singularity.dto.ClientDto;
import by.singularity.dto.WayBillDto;
import by.singularity.entity.Checkpoint;
import by.singularity.entity.Client;
import by.singularity.entity.WayBill;
import by.singularity.exception.CarException;
import by.singularity.exception.InvoiceException;
import by.singularity.exception.WayBillException;
import by.singularity.service.WayBillService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
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

    //todo add pagination
    @GetMapping()
    public void getAll(HttpServletResponse response) throws IOException {
        Map<String, Object> responseMap = new HashMap<>();
        List<WayBill> wayBills = wayBillService.getAllWayBills();
        responseMap.put("content", wayBills);
        responseMap.put("totalElements", wayBills.size());
        new ObjectMapper().writeValue(response.getOutputStream(), responseMap);
    }

    @GetMapping("/{id}")
    public Set<Checkpoint> getById(@PathVariable Long id) throws WayBillException {
        return wayBillService.getWayBill(id).getCheckpoints();
    }

    @PostMapping()
    public Long addWayBill(@RequestBody @Valid WayBillDto wayBillDto) throws InvoiceException, CarException {
        WayBill wayBill = wayBillService.createWayBill(wayBillDto);
        return wayBill.getId();
    }

    @PutMapping("/{id}")
    public void reachCheckpoint(@PathVariable Long id) throws WayBillException {
        wayBillService.reachCheckpoint(id);
    }
}