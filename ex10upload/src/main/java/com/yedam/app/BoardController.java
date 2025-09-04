package com.yedam.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class BoardController {

	// private static final String UPLOAD_DIR = "c:/uploads/";
	@Value("${file.upload.dir}")
	String UPLOAD_DIR;

	@GetMapping("info")
	public String info(BoardVO vo) {
		return "board/info";
	}

	@GetMapping("register")
	public String registgerForm() {
		return "board/register";
	}

	@PostMapping("/api/register")
	@ResponseBody
	public Map apiuploadFile(@RequestPart("file") MultipartFile file) {
		if (file.isEmpty()) {
			return Collections.singletonMap("result", "error");
		}
		try {
			// 폴더가 없으면 생성(일별, 모듈별)
			Path uploadPath = Paths.get(UPLOAD_DIR);
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			// 파일명 중복을 피하기 위해서 rename 필요함.
			// FileUtils.uuidFilename(file.getOriginalFilename());
			String filePath = UPLOAD_DIR + file.getOriginalFilename();
			file.transferTo(new File(filePath));

			return Collections.singletonMap("result", "success");
		} catch (IOException e) {
			e.printStackTrace();
			return Collections.singletonMap("result", "error");
		}
	}

	@PostMapping("/register")
	public String register(@RequestPart("file") MultipartFile file) {
		if (file.isEmpty()) {
			return "File is empty!";
		}
		try {
			// 문자열을 Path 객체로 변환
			Path uploadPath = Paths.get(UPLOAD_DIR);

			// 폴더가 없으면 생성
			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			// 파일명 중복을 피하기 위해서 rename 필요함.
			// String filePath = UPLOAD_DIR + FileUtils.uuidFilename(file.getOriginalFilename());

			// 업로드할 pathname(path+파일명) 지정하고 저장
			String filePath = UPLOAD_DIR + file.getOriginalFilename();
			file.transferTo(new File(filePath));

			return "redirect:/info";
		} catch (IOException e) {
			e.printStackTrace();
			return "/register";
		}
	}

	@GetMapping("/download/{filename}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) throws MalformedURLException {
		// 파일 경로 설정
		Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();

		// 파일을 Resource 로 로드
		Resource resource = new UrlResource(filePath.toUri());
		if (!resource.exists()) {
			return ResponseEntity.notFound().build();
		}

		// Content-Disposition 헤더에 다운로드용 파일명 지정
		String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM) // 이진 파일
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
	}

	@GetMapping("/download/resp/{filename}")
	public void downloadFile(@PathVariable String filename, HttpServletResponse response) throws IOException {
		Path filePath = Paths.get(UPLOAD_DIR).resolve(filename).normalize();

		if (!Files.exists(filePath) || !Files.isRegularFile(filePath)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "파일이 존재하지 않습니다.");
			return;
		}

		// 파일명 한글/특수문자 처리
		String encodedFilename = URLEncoder.encode(filePath.getFileName().toString(), "UTF-8").replaceAll("\\+", "%20");

		// 응답 헤더 설정
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFilename + "\"");
		response.setContentLengthLong(Files.size(filePath));

		// 파일 스트림 전송
		try (InputStream is = Files.newInputStream(filePath); OutputStream os = response.getOutputStream()) {

			byte[] buffer = new byte[8192]; // 8KB 버퍼
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.flush();
		}
	}
}
