<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>

<html>
<head>
    <title>Meals edit</title>
</head>
<body>
<p>Изменение приема пищи</p>
<h3><a href="meals">Еда</a></h3>

<form method="post">
    <table width ="40%" border="2" style="left: 36px; position: relative">
        <tr align="center" style="color: darkblue">
            <td> <c:out value="№"/> </td>
            <td> <c:out value="Дата/время"/> </td>
            <td> <c:out value="Еда"/> </td>
            <td> <c:out value="Калории"/> <br/> </td>
        </tr>

        <tr align="center">
            <td> <c:out value="${requestScope.meal.getId()}"/> </td>
            <td> <input name="dateTime" title="DateTime" type="datetime-local" value="${requestScope.meal.getDateTime()}" /> </td>
            <td> <input name="description" title="Description" type="text" value="${requestScope.meal.getDescription()}" /> </td>
            <td> <input name="calories" title="Calories" type="number" value="${requestScope.meal.getCalories()}" /> </td>
            <td> <input hidden title="method" name="method" value="editComplete" />
                 <input hidden title="Id" name="id" value="${requestScope.meal.getId()}" />
                 <input type="submit" formaction="/meals" value="отправить" /> <br/>
            </td>
        </tr>

    </table>
</form>

</body>
</html>
