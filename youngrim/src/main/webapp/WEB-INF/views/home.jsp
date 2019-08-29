<%@ page language="java" contentType="text/html; charset=EUC-KR"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach items="${list}" var="item">
<p>${ item.id } / ${ item.title } / ${ item.content }
/ ${ item.hit }/ ${ item.writer }/ ${ item.write_date }
/ ${ item.grp }/ ${ item.ord }/ ${ item.level }</p>
</c:forEach>

​