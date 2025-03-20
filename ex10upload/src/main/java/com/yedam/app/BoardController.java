package com.yedam.app;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class BoardController {

    private static final String UPLOAD_DIR = "c:/uploads/";
    
	@GetMapping("info")
	public String info(BoardVO vo) {
		return "board/info";
	}
	
    
	@GetMapping("register")
	public String registgerForm() {
		return "board/register";
	}
	
	
    @PostMapping("register")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "File is empty!";
        }
        try {
            File uploadDir = new File(UPLOAD_DIR);
            if (!uploadDir.exists()) {
                uploadDir.mkdirs();
            }

            String filePath = UPLOAD_DIR + file.getOriginalFilename();
            file.transferTo(new File(filePath));

            return "File uploaded successfully: " + file.getOriginalFilename();
        } catch (IOException e) {
            return "File upload failed!";
        }
    }
}
