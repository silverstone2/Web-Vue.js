<%@page import="test.write.dao.WriteDao"%>
<%@page import="test.write.dto.WriteDto"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//1. 폼 전송되는 수정할 글의 정보를 읽어온다.
	int num=Integer.parseInt(request.getParameter("num"));
	String title=request.getParameter("title");
	String content=request.getParameter("content");
	//2. DB 에 수정 반영
	WriteDto dto=new WriteDto();
	dto.setNum(num);
	dto.setTitle(title);
	dto.setContent(content);
	boolean isSuccess=WriteDao.getInstance().update(dto);
	//3. 응답
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>/board/private/update.jsp</title>
</head>
<body>
	<%if(isSuccess){ %>
		<script>
			alert("수정 했습니다.");
			location.href="${pageContext.request.contextPath }/board/detail.jsp?num=<%=num %>";
		</script>
	<%}else{ %>
		<script>
			alert("수정이 실패 되었습니다.");
			location.href="${pageContext.request.contextPath }/board/private/updateform.jsp?num=<%=num %>";
		</script>
	<%} %>
</body>
</html>













