package by.singularity.service;

import by.singularity.entity.*;
import by.singularity.repository.ClientRepository;
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
    private final ClientRepository clientRepository;

    public void createWaybillReport(String startWeekDate, String endWeekDate, HttpServletResponse response) throws IOException {
        Iterable<WayBill> wayBills = wayBillRepository.findAll(getFindingWaybillPredicate(startWeekDate,endWeekDate));
        try(XSSFWorkbook xssfWorkbook = new XSSFWorkbook()) {
            XSSFSheet xssfSheet = xssfWorkbook.createSheet("Waybill report");
            writeWaybillData(xssfSheet, wayBills, startWeekDate, endWeekDate);
            writeToResponse(xssfWorkbook, response);
        }
    }

    private void writeWaybillData(XSSFSheet xssfSheet, Iterable<WayBill> wayBills, String startWeekDate, String endWeekDate) {
        Row waybillColumnsRow = xssfSheet.createRow(0);
        createCells(waybillColumnsRow,0,"Waybill id", "Start week Date",
                "End week Date","Consumption","Income","Profit");
        Iterator<WayBill> iterator = wayBills.iterator();
        int rowNum = 1;
        while (iterator.hasNext()) {
            WayBill wayBill = iterator.next();
            Row waybillRow = xssfSheet.createRow(rowNum);
            createCells(waybillRow,0,wayBill.getId(),
                    startWeekDate, endWeekDate,wayBill.getConsumption(),wayBill.getIncome(),wayBill.getProfit());
            rowNum++;
        }
        Row allRow = xssfSheet.createRow(rowNum);
        int[] allIncomeInfo = getAllIncomeInfo(wayBills);
        createCells(allRow,2,"Total:",allIncomeInfo[0],allIncomeInfo[1], allIncomeInfo[2]);
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

    public void createClientReport(String startWeekDate, String endWeekDate, HttpServletResponse response) throws IOException {
        try(XSSFWorkbook xssfWorkbook = new XSSFWorkbook()) {
            XSSFSheet xssfSheet = xssfWorkbook.createSheet("Client report");
            writeClientData(xssfSheet, startWeekDate, endWeekDate);
            writeToResponse(xssfWorkbook, response);
        }
    }

    private void writeClientData(XSSFSheet xssfSheet, String startWeekDate, String endWeekDate) {
        Iterable<WayBill> wayBills = wayBillRepository.findAll(getFindingWaybillPredicate(startWeekDate,endWeekDate));
        long lostClients = clientRepository.count(getFindingClientPredicate(startWeekDate,endWeekDate, false));
        long newClients = clientRepository.count(getFindingClientPredicate(startWeekDate,endWeekDate, true));
        long clientsAmount = clientRepository.count();
        int[] allIncomeInfo = getAllIncomeInfo(wayBills);
        Row clientsColumnsRow = xssfSheet.createRow(0);
        createCells(clientsColumnsRow,0,"Start week Date", "End week Date","Total clients amount",
                "Lost clients in period","New clients in period","Consumption","Income","Profit");
        Row clientsInfoRow = xssfSheet.createRow(1);
        createCells(clientsInfoRow,0,startWeekDate, endWeekDate,clientsAmount,
                lostClients,newClients ,allIncomeInfo[0],allIncomeInfo[1],allIncomeInfo[2]);
    }

    private int[] getAllIncomeInfo(Iterable<WayBill> wayBills) {
        Iterator<WayBill> iterator = wayBills.iterator();
        int allConsumptions = 0;
        int allIncome = 0;
        int allProfit = 0;
        while (iterator.hasNext()) {
            WayBill wayBill = iterator.next();
            allConsumptions += wayBill.getConsumption();
            allIncome += wayBill.getIncome();
            allProfit += wayBill.getProfit();
        }
        return new int[] {allConsumptions, allIncome, allProfit};
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


    private Predicate getFindingWaybillPredicate(String startWeekDate, String endWeekDate) {
        return QPredicate.builder()
                .add(ParseUtils.parseDate(startWeekDate), QWayBill.wayBill.endDate::goe)
                .add(ParseUtils.parseDate(endWeekDate), QWayBill.wayBill.endDate::loe)
                .buildAnd();

    }

    private Predicate getFindingClientPredicate(String startWeekDate, String endWeekDate, boolean isActive) {
        return QPredicate.builder()
                .add(ParseUtils.parseDate(startWeekDate), QClient.client.activeDate::goe)
                .add(ParseUtils.parseDate(endWeekDate), QClient.client.activeDate::loe)
                .add(isActive, QClient.client.isActive::eq)
                .buildAnd();
    }
}
