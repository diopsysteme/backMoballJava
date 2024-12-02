package org.example.backmobile.Services.Interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExcelIService {
    public List<Map<String, String>> importExcelData(MultipartFile file) throws IOException;

}
