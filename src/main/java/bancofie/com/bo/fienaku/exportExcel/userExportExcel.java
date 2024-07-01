package bancofie.com.bo.fienaku.exportExcel;

import bancofie.com.bo.fienaku.model.user;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class userExportExcel {

    public void export(List<user> users, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        createHeaderRow(sheet);
        writeDataRows(sheet, users);

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createHeaderRow(Sheet sheet) {
        Row row = sheet.createRow(0);

        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setBold(true);
        style.setFont(font);

        String[] headers = {"ID", "Nombre", "Apellido", "Trabajo", "Piso", "Teléfono", "Correo", "Cuenta", "Imagen", "Tipo de Usuario", "Nombre de Usuario", "Contraseña", "Fecha de Creación", "Fecha de Actualización"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(style);
        }
    }

    private void writeDataRows(Sheet sheet, List<user> users) {
        int rowNum = 1;
        for (user user : users) {
            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getName());
            row.createCell(2).setCellValue(user.getLastname());
            row.createCell(3).setCellValue(user.getJob());
            row.createCell(4).setCellValue(user.getFloor());
            row.createCell(5).setCellValue(user.getPhone());
            row.createCell(6).setCellValue(user.getMail());
            row.createCell(7).setCellValue(user.getAccount());
            row.createCell(8).setCellValue(user.getImage());
            row.createCell(9).setCellValue(user.getUsertype().toString());
            row.createCell(10).setCellValue(user.getUsername());
            row.createCell(11).setCellValue(user.getPassword());
            row.createCell(12).setCellValue(user.getCreate());
            row.createCell(13).setCellValue(user.getUpdate());
        }
    }
}
