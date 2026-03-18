합계
<span th:text="${#aggregates.sum(list.![salary + 1])}">35.23</span>

프로퍼티

```java
  @Value("${file.upload}")
```

```html
#{프로퍼티키}
```

페이징 프레그먼트

<div th:insert="~{common/paging::paging(${1},${5})}"></div>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
 <div th:fragment="paging(start,end)">
 <nav aria-label="Page navigation example">
  <ul class="pagination">
    <li class="page-item"><a class="page-link" href="#">Previous</a></li>
    
    <li class="page-item"
        th:each="i : ${#numbers.sequence(start, end)}">
        <a class="page-link" href="#">[[${i}]]</a>
    </li>
    
    <li class="page-item"><a class="page-link" href="#">Next</a></li>
  </ul>
</nav>
 
 </div>
</body>
</html>
