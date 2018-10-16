<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .exceeded {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meals</h2>
    <form method="post" >
        <h2>Профиль</h2>
        <select name="userId" title="profile" required>
            <option disabled>Выберите профиль</option>
            <option value="1">userNumberOne</option>
            <option value="2">userNumberTwo</option>
        </select>
        <input title="authorised" name="authorised" value="true" hidden>
        <input type="submit" formaction="/meals" value="Выбрать">
    </form>

    <form method="post" action="meals">
        <dl>
            <dt>Время с:</dt>
            <dd><input type="time" name="activeFromTime" ></dd>
            <dt>Время по:</dt>
            <dd><input type="time" name="activeToTime" ></dd>
        </dl>

        <dl>
            <dt>Дата с:</dt>
            <dd><input type="date" name="activeFromDate" ></dd>
            <dt>Дата по:</dt>
            <dd><input type="date" name="activeToDate" ></dd>
        </dl>
        <input title="filter" name="filter" value="filter" hidden>
        <button type="submit">Отфильтровать</button>
    </form>



    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>