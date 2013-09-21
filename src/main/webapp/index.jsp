<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0">
    <title>Hello Flume</title>
</head>
<body>
<h2>Hello Flume !</h2>
<a href="<c:url value="/log/debug?Hello+Debug"/>">Debug</a>
<a href="<c:url value="/log/info?Hello+Info"/>">Info</a>
<a href="<c:url value="/log/warn?Hello+Warn"/>">Warn</a>
<a href="<c:url value="/log/error?Hello+Error"/>">Error</a>
<br/>
<c:out value="${requestScope.qs}" default="No 'qs' value"/>
</body>
</html>
