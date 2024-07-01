package bancofie.com.bo.fienaku.exportExcel;

import bancofie.com.bo.fienaku.model.fienaku;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class fienakuExportExcel {

    public void export(List<fienaku> fienakus, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Fienakus");

        createFienakuHeaderRow(sheet);
        writeFienakuDataRows(sheet, fienakus);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=fienakus.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createFienakuHeaderRow(Sheet sheet) {
        Row row = sheet.createRow(0);

        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        style.setFont(font);

        String[] headers = {"ID", "Nombre", "Imagen", "Código", "Monto de Pago", "Penitencia", "Periodo de Pago", "Fecha de Creación", "Fecha de Actualización"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void writeFienakuDataRows(Sheet sheet, List<fienaku> fienakus) {
        int rowNum = 1;
        for (fienaku fienaku : fienakus) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(fienaku.getId());
            row.createCell(1).setCellValue(fienaku.getName());
            row.createCell(2).setCellValue(fienaku.getImage());
            row.createCell(3).setCellValue(fienaku.getCode());
            row.createCell(4).setCellValue(fienaku.getMount());
            row.createCell(5).setCellValue(fienaku.getPenitence());
            row.createCell(6).setCellValue(fienaku.getTimespan());
            row.createCell(7).setCellValue(fienaku.getCreate());
            row.createCell(8).setCellValue(fienaku.getUpdate());
        }
    }
}
