# 파일 업로드

### 업로드 설정

application.properties

```yml
# 단일 파일 최대 크기
spring.servlet.multipart.max-file-size=10MB

# 요청 전체 크기 (여러 파일 포함)
spring.servlet.multipart.max-request-size=20MB
```

spring에서는 multipartResplver 빈 등록해야함.
xml 기반 설정

```xml
<bean id="multipartResolver"
      class="org.springframework.web.multipart.commons.CommonsMultipartResolver">
    <property name="maxUploadSize" value="20971520"/> <!-- 20MB -->
    <property name="maxUploadSizePerFile" value="10485760"/> <!-- 10MB -->
    <property name="defaultEncoding" value="UTF-8"/>
</bean>
```

```java
java 기반 설정
@Configuration
public class FileUploadConfig {

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSize(20 * 1024 * 1024);      // 요청 전체 20MB
        resolver.setMaxUploadSizePerFile(10 * 1024 * 1024); // 개별 파일 10MB
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }
}
```

### 업로드 폼

```java
	<form name="frm" th:action="@{/register}"
	      method="post" enctype="multipart/form-data">
	   <input type="text" name="title">
	   <input type="file" name="file">
	   <button>등록</button>
	</form>
```

### 컨트롤러 업로드 처리

```java
@Controller
public class BoardController {
  private static final String UPLOAD_DIR = "c:/upload/";

	@GetMapping("info")
	public String info(BoardVO vo) {
		return "board/info";
	}


	@GetMapping("register")
	public String registgerForm() {
		return "board/register";
	}

	@PostMapping("/register")
	public String uploadFile(@RequestPart("file") MultipartFile file) {
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
			// FileUtils.uuidFilename(file.getOriginalFilename());

			// 업로드할 pathname(path+파일명) 지정하고 저장
			String filePath = UPLOAD_DIR + file.getOriginalFilename();
			file.transferTo(new File(filePath));

			return "redirect:/info";
		} catch (IOException e) {
			e.printStackTrace();
			return "/register";
		}
	}
}
```

### 파일 다운로드

ResponseEntity를 이용한 방식

- 코드는 간결하지만 아주 큰 파일은 메모리 부담이 갈 수 있음.

```java
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
```

response 이용한 방식

- 스트리밍 방식으로 대용량 파일도 처리 가능함.

```java
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
```

### 업로드 경로 설정

application.properties

```yml
file.upload.dir=c:/upload/
```

리눅스인 경우는 "/home/ec2-user/app/upload" 나 "/var/www/upload" 경로로 지정

BoardContrller

```java
  //private static final String UPLOAD_DIR = "c:/upload/";

	@Value("${file.upload.dir}")
	String UPLOAD_DIR;
```

### 외부 경로를 정적 리소스처럼 접근

MvcConfigurer 인터페이스의 addResourceHandlers 메서드 구현.

```java
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.dir}")
    private String uploadDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
      //문자열을 Path 객체로 변환 -> 절대경로로 변환 -> 경로에서 불필요한 요소를 제거
      Path path = Paths.get(uploadDir).toAbsolutePath().normalize();

      // OS에 맞게 file:///c:/upload/ 또는 file:/upload/
      String resourcePath = path.toUri().toString();

      // 외부 경로에 접근할 resource 경로 ->  http://upload/파일명
      registry.addResourceHandler("/upload/**")
              .addResourceLocations(resourcePath);
    }
}
```

### ajax 업로드

업로드 폼

```html
<button type="button" onclick="apiregister()">ajax 등록</button>

<script>
  async function apiregister() {
    const formData = new FormData();
    formData.append("file", frm.file.files[0]);

    const url = "/api/register";
    const response = await fetch(url, {
      method: "post",
      body: formData,
    });

    const result = await response.json();
    console.log(result);
  }
</script>
```

업로드 컨트롤러

```java
	@PostMapping("/api/register")
	@ResponseBody
	public Map apiuploadFile(@RequestPart("file") MultipartFile file) {
    ...
    return Collections.singletonMap("result", "success");
  }
```

### 파일명 중복 제거

파일명 중복만 피하려면 UUID+확장자 방식 사용.  
많은 파일을 장기간 관리한다면 날짜별 폴더 + UUID 조합 사용.

> 원본: photo.png  
> 저장: 550e8400-e29b-41d4-a716-446655440000.png

```java
    // 확장자 추출
    String originalFilename = file.getOriginalFilename()
    if (originalFilename != null && originalFilename.contains(".")) {
        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    // UUID로 새로운 파일명 생성
    String savedFilename = UUID.randomUUID().toString() + extension;
    String filePath = UPLOAD_DIR + savedFilename;
```

공통으로 사용될 클래스 작성

```java
public final class FileUtils {
      public static String uuidFilename(String originalFilename) {
      // 확장자 추출
      String originalFilename = file.getOriginalFilename()
      if (originalFilename != null && originalFilename.contains(".")) {
        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
      }

      // UUID로 새로운 파일명 생성
      String savedFilename = UUID.randomUUID().toString() + extension;
        return savedFilename;
      }
}
```

메서드 사용

```java
  String filePath = UPLOAD_DIR + FileUtils.uuidFilename(file.getOriginalFilename());
```

```java
public final class FileUtils {

    // 결과 묶음 타입
    public static final class FilePlan {
        private final Path directory;  // 실제 저장할 디렉토리
        private final String filename; // 저장 파일명
        private final Path fullPath;   // directory.resolve(filename)

        public FilePlan(Path directory, String filename) {
            this.directory = directory.toAbsolutePath().normalize();
            this.filename = filename;
            this.fullPath = this.directory.resolve(this.filename).normalize();
        }
        public Path getDirectory() { return directory; }
        public String getFilename() { return filename; }
        public Path getFullPath() { return fullPath; }
        @Override public String toString() { return fullPath.toString(); }
    }

    /* =========================
       0) 공통 헬퍼
       ========================= */

    // 폴더 생성(이미 있으면 통과)
    private static Path ensureDir(Path dir) throws IOException {
        Files.createDirectories(dir);
        return dir;
    }

    // 파일명에서 확장자 추출 (.포함, 없으면 빈 문자열)
    private static String ext(String originalName) {
        if (originalName == null) return "";
        int dot = originalName.lastIndexOf('.');
        if (dot <= 0 || dot == originalName.length() - 1) return "";
        return originalName.substring(dot); // 예: ".png"
    }

    // 파일명에서 베이스네임만 추출(확장자 제외)
    private static String base(String originalName) {
        if (originalName == null) return "file";
        int slash = Math.max(originalName.lastIndexOf('/'), originalName.lastIndexOf('\\'));
        String justName = (slash >= 0) ? originalName.substring(slash + 1) : originalName;
        int dot = justName.lastIndexOf('.');
        return (dot > 0) ? justName.substring(0, dot) : justName;
    }

    // 안전한 파일명으로 정규화(디렉토리 분리자 제거, 허용 문자만)
    private static String sanitize(String name) {
        if (name == null || name.isBlank()) return "file";
        String justName = name.replace("\\", "/");
        int slash = justName.lastIndexOf('/');
        if (slash >= 0) justName = justName.substring(slash + 1);
        // 한글/영문/숫자/._- 만 허용, 나머지는 _
        justName = justName.replaceAll("[^\\p{IsAlphabetic}\\p{IsDigit}\\._-]", "_");
        // 너무 길면 자르기
        return justName.length() > 200 ? justName.substring(0, 200) : justName;
    }

    /* =========================
       1) UUID 파일명 만들기
       ========================= */
    public static String uuidFilename(String originalFilename) {
        String extension = ext(originalFilename);
        return UUID.randomUUID().toString() + extension;
    }

    /* =========================
       2) 지정 폴더 + 지정 파일명 (그대로 저장할 때)
       - folder: 절대/상대 상관없음
       - filename: 원하면 sanitize해서 사용 권장
       ========================= */
    public static FilePlan inFolderWithName(String folder, String filename) throws IOException {
        Path dir = ensureDir(Paths.get(folder).toAbsolutePath().normalize());
        String safe = sanitize(filename);
        return new FilePlan(dir, safe);
    }

    /* =========================
       3) 날짜별 폴더 + 원본 파일명
       - baseDir 아래에 yyyy/MM/dd 구조로 생성
       ========================= */
    public static FilePlan dateFolderWithOriginalName(String baseDir, String originalFilename) throws IOException {
        LocalDate now = LocalDate.now();
        Path dir = ensureDir(
                Paths.get(baseDir, String.valueOf(now.getYear()),
                                   String.format("%02d", now.getMonthValue()),
                                   String.format("%02d", now.getDayOfMonth()))
                     .toAbsolutePath().normalize()
        );
        String safe = sanitize(base(originalFilename)) + ext(originalFilename);
        return new FilePlan(dir, safe);
    }

    /* =========================
       4) 날짜별 폴더 + UUID 파일명
       ========================= */
    public static FilePlan dateFolderWithUuidName(String baseDir, String originalFilename) throws IOException {
        LocalDate now = LocalDate.now();
        Path dir = ensureDir(
                Paths.get(baseDir, String.valueOf(now.getYear()),
                                   String.format("%02d", now.getMonthValue()),
                                   String.format("%02d", now.getDayOfMonth()))
                     .toAbsolutePath().normalize()
        );
        String saved = uuidFilename(originalFilename);
        return new FilePlan(dir, saved);
    }

    /* =========================
       5) 지정 폴더 + (원본/UUID) 자동 선택
       - useUuid=true 면 UUID, 아니면 원본(정규화)
       ========================= */
    public static FilePlan inFolderAuto(String folder, String originalFilename, boolean useUuid) throws IOException {
        Path dir = ensureDir(Paths.get(folder).toAbsolutePath().normalize());
        String name = useUuid
                ? uuidFilename(originalFilename)
                : sanitize(base(originalFilename)) + ext(originalFilename);
        return new FilePlan(dir, name);
    }
}
```
