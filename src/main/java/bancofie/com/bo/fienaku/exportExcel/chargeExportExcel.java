package bancofie.com.bo.fienaku.exportExcel;

import bancofie.com.bo.fienaku.model.charge;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class chargeExportExcel {

    public void export(List<charge> charges, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Charges");

        createChargeHeaderRow(sheet);
        writeChargeDataRows(sheet, charges);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=charges.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createChargeHeaderRow(Sheet sheet) {
        Row row = sheet.createRow(0);

        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        style.setFont(font);

        String[] headers = {"ID", "Fienaku ID", "Usuario ID", "Cuenta", "Monto", "Fecha", "Imagen", "Estado"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void writeChargeDataRows(Sheet sheet, List<charge> charges) {
        int rowNum = 1;
        for (charge charge : charges) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(charge.getId());
            row.createCell(1).setCellValue(charge.getFienaku().getId());
            row.createCell(2).setCellValue(charge.getUser().getId());
            row.createCell(3).setCellValue(charge.getAccount());
            row.createCell(4).setCellValue(charge.getMount());
            row.createCell(5).setCellValue(charge.getDate());
            row.createCell(6).setCellValue(charge.getImage());
            row.createCell(7).setCellValue(charge.isStatus());
        }
    }
}

