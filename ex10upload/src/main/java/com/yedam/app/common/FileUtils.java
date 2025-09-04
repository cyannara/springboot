package com.yedam.app.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.UUID;

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
