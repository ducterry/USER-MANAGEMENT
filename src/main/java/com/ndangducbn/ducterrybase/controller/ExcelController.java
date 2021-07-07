package com.ndangducbn.ducterrybase.controller;

import com.ndangducbn.ducterrybase.service.ExportService;
import com.ndangducbn.ducterrybase.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/export/")
@Api(tags = "05. Export Controller", description = "Export API")
@Slf4j
public class ExcelController {
    private final String PREFIX = "ExcelController_";

    private final ExportService exportService;
    private final ServletContext servletContext;

    public ExcelController(ExportService exportService, ServletContext servletContext) {
        this.exportService = exportService;
        this.servletContext = servletContext;
    }

    @ApiOperation(value = "Export List User -> Excel", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 404, message = "No user found"),
            @ApiResponse(code = 500, message = "Server Error")
    })
    @GetMapping("/excel/users")
    public ResponseEntity<?> exportUserToExcel() throws FileNotFoundException {
        log.debug(PREFIX + "exportUserToExcel =>");


        String path = this.exportService.exportUserToExcel();
        File file = FileUtils.getFile(path);
        MediaType mediaType = FileUtil.getMediaTypeForFileName(this.servletContext, file.getName());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                // Content-Type
                .contentType(mediaType)
                // Contet-Length
                .contentLength(file.length()) //
                .body(resource);
    }
}
