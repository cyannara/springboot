```
[사용자]
   │  multipart/form-data (파일)
   ▼
[업로드 서버 :8081]                    [RAG 서버 :8080]
  FileUploadController                  RagWebController
       │                                     │
  FileUploadService                   DocumentIngestionService
   ① 공유 스토리지 저장                  ③ 공유 스토리지에서 파일 읽기
   ② POST /api/embed ──────────────▶  ④ Tika 파싱 + 청크 분할
       (fileName, category)            ⑤ OpenAI 임베딩
                                       ⑥ pgvector 저장
        [공유 스토리지: /shared/uploads]
         업로드 서버가 쓰고, RAG 서버가 읽음
```

POST /api/embed — 파일명 → 임베딩 등록

```bash
curl -X POST http://ip주소:86/api/embed \
  -H "Content-Type: application/json" \
  -d '{"fileName": "2024/12/15/report.pdf", "category": "기술문서"}'
```

POST /api/search — 질문 → RAG 답변

```bash
curl -X POST http://ip주소:86/api/search \
  -H "Content-Type: application/json" \
  -d '{"question": "IoC", "topK": 5}'
```
