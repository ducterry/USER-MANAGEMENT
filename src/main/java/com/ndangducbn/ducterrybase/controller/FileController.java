package com.ndangducbn.ducterrybase.controller;

import com.ndangducbn.ducterrybase.common.constant.PathConstant;
import com.ndangducbn.ducterrybase.exception.NotFoundException;
import com.ndangducbn.ducterrybase.model.request.UploadFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/api/files/")
@Api(tags = "04. File Controller", description = "File API")
@Slf4j
public class FileController {
    private final String PREFIX = "UserServiceImpl_";


    @ApiOperation(value = "Upload file", response = String.class)
    @ApiResponses({
            @ApiResponse(code = 500, message="Internal Server Error"),
            @ApiResponse(code = 400, message="Bad request")
    })
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@ModelAttribute("uploadForm") UploadFile form) {
        /*
            - Create folder to save file if not exist
         */
        File uploadDir = new File(PathConstant.UPLOAD_FILE_PATH_SAVED);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        MultipartFile fileData = form.getFileData();
        String name = fileData.getOriginalFilename();
        if (name != null && name.length() > 0) {
            try {
                /*
                    - Create file
                 */
                File serverFile = new File(PathConstant.UPLOAD_FILE_PATH_SAVED + "/" + name);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(fileData.getBytes());
                stream.close();
                return ResponseEntity.ok("/file/"+name);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error when uploading");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request");
    }

    @ApiOperation(value = "Get file")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Bad request")
    })
    @GetMapping("/file/{filename}")
    public ResponseEntity<?> download(@PathVariable String filename) {
        File file = new File(PathConstant.UPLOAD_FILE_PATH_SAVED + "/" + filename);
        if (!file.exists()) {
            throw new NotFoundException("File not found");
        }

        UrlResource resource;
        try {
            resource = new UrlResource(file.toURI());
        } catch (MalformedURLException e) {
            throw new NotFoundException("File not found");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                .body(resource);
    }
}
