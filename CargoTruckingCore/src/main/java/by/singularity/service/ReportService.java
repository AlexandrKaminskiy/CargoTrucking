package by.singularity.service;

import by.singularity.entity.Payment;
import by.singularity.entity.QWayBill;
import by.singularity.entity.User;
import by.singularity.entity.WayBill;
import by.singularity.repository.WayBillRepository;
import by.singularity.repository.queryUtils.QPredicate;
import by.singularity.service.utils.ParseUtils;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportService {
    private final WayBillRepository wayBillRepository;
    
    public void createWaybillReport(String startWeekDate, String endWeekDate, HttpServletResponse response) throws IOException {
        Iterable<WayBill> wayBills = wayBillRepository.findAll(getFindingPredicate(startWeekDate,endWeekDate));
        try(XSSFWorkbook xssfWorkbook = new XSSFWorkbook()) {
            XSSFSheet xssfSheet = xssfWorkbook.createSheet("Waybill report");
            writeData(xssfSheet, wayBills, startWeekDate, endWeekDate);
            writeToResponse(xssfWorkbook, response);
        }
    }

    private void writeData(XSSFSheet xssfSheet, Iterable<WayBill> wayBills, String startWeekDate, String endWeekDate) {
        Row waybillColumnsRow = xssfSheet.createRow(0);
        createCells(waybillColumnsRow,0,"Waybill id", "Start week Date", "End week Date","Consumption","Income","Profit");
        Iterator<WayBill> iterator = wayBills.iterator();
        int rowNum = 1;
        int allIncome = 0;
        int allConsumptions = 0;
        int allProfit = 0;
        while (iterator.hasNext()) {
            WayBill wayBill = iterator.next();
            Row waybillRow = xssfSheet.createRow(rowNum);
            createCells(waybillRow,0,wayBill.getId(),
                    startWeekDate, endWeekDate,wayBill.getConsumption(),wayBill.getIncome(),wayBill.getProfit());
            allIncome += wayBill.getIncome();
            allConsumptions += wayBill.getConsumption();
            allProfit += wayBill.getProfit();
            rowNum++;
        }
        Row allRow = xssfSheet.createRow(rowNum);
        createCells(allRow,2,"Total:",allConsumptions,allIncome, allProfit);
        rowNum += 2;
        Row driverColumnsRow = xssfSheet.createRow(rowNum);
        createCells(driverColumnsRow,0,"Surname", "Name", "Patronymic","Profit");
        rowNum++;
        iterator = wayBills.iterator();
        while (iterator.hasNext()) {
            User driver = iterator.next().getInvoice().getDriver();
            Row waybillRow = xssfSheet.createRow(rowNum);
            double totalProfit = 0;
            for (Payment payment : driver.getPayment()) {
                totalProfit += payment.getPayment();
            }
            createCells(waybillRow,0,driver.getSurname(),
                    driver.getName(),driver.getPatronymic(), totalProfit);
            rowNum++;
        }

    }

    private void createCells(Row row, int startColumn, Object ... values) {
        for (int i = 0; i < values.length; i++) {
            Cell cell = row.createCell(startColumn + i);
            cell.setCellValue(values[i].toString());
        }
    }

    private void writeToResponse(XSSFWorkbook xssfWorkbook, HttpServletResponse response) throws IOException {
        xssfWorkbook.write(response.getOutputStream());
    }

    private Predicate getFindingPredicate(String startWeekDate, String endWeekDate) {
        return QPredicate.builder()
                .add(ParseUtils.parseDate(startWeekDate), QWayBill.wayBill.endDate::goe)
                .add(ParseUtils.parseDate(endWeekDate), QWayBill.wayBill.endDate::loe)
                .buildAnd();

    }
}
