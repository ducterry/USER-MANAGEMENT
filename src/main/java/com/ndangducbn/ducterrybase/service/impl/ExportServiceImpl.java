package com.ndangducbn.ducterrybase.service.impl;

import com.ndangducbn.ducterrybase.Repository.UserRepository;
import com.ndangducbn.ducterrybase.common.constant.PathConstant;
import com.ndangducbn.ducterrybase.entity.User;
import com.ndangducbn.ducterrybase.enums.ETypeCellFormat;
import com.ndangducbn.ducterrybase.enums.ETypeDateFormat;
import com.ndangducbn.ducterrybase.helper.Convert2DTO;
import com.ndangducbn.ducterrybase.model.response.ExcelUsersDTO;
import com.ndangducbn.ducterrybase.model.response.UserResDTO;
import com.ndangducbn.ducterrybase.service.ExportService;
import com.ndangducbn.ducterrybase.utils.DateUtil;
import com.ndangducbn.ducterrybase.utils.FileUtil;
import com.ndangducbn.ducterrybase.utils.JSONFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class ExportServiceImpl implements ExportService {
    private final String PREFIX = "ExcelController_";

    private final Convert2DTO convert2DTO;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public ExportServiceImpl(Convert2DTO convert2DTO, UserRepository userRepository, ModelMapper modelMapper) {
        this.convert2DTO = convert2DTO;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String exportUserToExcel() {
        try {
            log.debug(PREFIX + "exportUserToExcel =>");

            /*
                01. Tạo thư mục Export File
             */
            String path = PathConstant.EXCEL_PATH_SAVED;
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            /*
                02. Xử lí Workbook
             */
            XSSFWorkbook workbook = null;
            InputStream excelFile;
            try {
                path = path +"/"+ DateUtil.dateToString(ETypeDateFormat.DATE_FORMAT_FULL, new Date()) + ".xlsx";
                excelFile = new ClassPathResource(PathConstant.EXCEL_EXPORT).getInputStream();
                workbook = new XSSFWorkbook(excelFile);
            } catch (Exception e) {
                log.error(PREFIX + "Export Excel Exception => {}", JSONFactory.toString(e.getMessage()));
            }

            /*
                03. Mapping Excel DTO
             */
            List<ExcelUsersDTO> excelUsersDTOS = new ArrayList<>();
            List<User> userList = this.userRepository.findAll();
            List<UserResDTO> resDTOS = this.convert2DTO.convertLstEntityToLstDTO(userList);
            resDTOS.forEach(item -> {
                ExcelUsersDTO excelUsersDTO = this.modelMapper.map(item, ExcelUsersDTO.class);
                excelUsersDTOS.add(excelUsersDTO);
            });


            /*
                04. Fill Data
             */
            int index = 1;
            int rowNum = 2;
            Sheet sheet = workbook.getSheetAt(0);
            CellStyle cellStyle = FileUtil.cellStyle(workbook, ETypeCellFormat.TYPE_TEXT);
            if (!excelUsersDTOS.isEmpty()) {
                for (ExcelUsersDTO item : excelUsersDTOS) {
                    int i = 1;
                    Row row = sheet.createRow(++rowNum);

                    //STT
                    FileUtil.createCell(row, i++, "" + index++, cellStyle);
                    //Họ và tên
                    FileUtil.createCell(row, i++, item.getName() != null ? item.getName() + "" : "", cellStyle);
                    //Ngày sinh
                    FileUtil.createCell(row, i++, item.getBirthday() != null ? item.getBirthday() + "" : "", cellStyle);
                    //Phone
                    FileUtil.createCell(row, i++, item.getPhone() != null ? item.getPhone() + "" : "", cellStyle);
                    //Email
                    FileUtil.createCell(row, i++, item.getEmail() != null ? item.getEmail() + "" : "", cellStyle);
                    //Vai trò
                    FileUtil.createCell(row, i++, item.getRole() != null ? item.getRole() + "" : "", cellStyle);
                    //Avatar
                    FileUtil.createCell(row, i, item.getAvatar() != null ? item.getAvatar() + "" : "", cellStyle);

                }
            }

            /*
                05. Export File
             */
            try (FileOutputStream outputStream = new FileOutputStream(path)) {
                workbook.write(outputStream);
                outputStream.flush();
            } catch (FileNotFoundException e) {
                log.error(e.getMessage(), e);
            } catch (IOException e) {
                log.error(e.getMessage(), e);
                return "";
            }
            return path;
        } catch (Exception e) {
            return null;
        }
    }
}
