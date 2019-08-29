<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<ol type="A">
<c:forEach items="${list}" var="item">
	<li>
		<c:forEach begin="2" end="${item.LEVEL}">
			&nbsp;&nbsp;&nbsp;		
		</c:forEach>
	${item.ID} / ${item.TITLE} / ${item.WRITE_DATE}
	</li>
</c:forEach>
</ol>