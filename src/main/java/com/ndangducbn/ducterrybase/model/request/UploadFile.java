package com.ndangducbn.ducterrybase.model.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFile {
    private MultipartFile fileData;
}
