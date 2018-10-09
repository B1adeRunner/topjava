<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Еда</h2>

<table width ="40%" border="2" style="left: 36px; position: relative">
    <tr style="color: darkblue">
        <td> <c:out value="№"/> </td>
        <td> <c:out value="Дата/время"/> </td>
        <td> <c:out value="Еда"/> </td>
        <td> <c:out value="Калории"/> <br/> </td>
    </tr>
<c:forEach var="meal" items="${requestScope.meals}" varStatus="rowCounter" >
    <tr style="color:${meal.isExceed() ? "red" : "darkgreen" }">
        <td> <c:out value="${rowCounter.count}"/> </td>
        <td> <javatime:parseLocalDateTime value="${meal.getDateTime()}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate"/>
             <javatime:format pattern="yyyy-MM-dd HH:mm" value="${parsedDate}" /> </td>
        <td> <c:out value="${meal.getDescription()}"/> </td>
        <td> <c:out value="${meal.getCalories()}"/> <br/> </td>
    </tr>
</c:forEach>
</table>

</body>
</html>
