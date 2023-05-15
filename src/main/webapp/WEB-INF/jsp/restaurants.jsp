<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<section>
    <h3><spring:message code="user.title"/></h3>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th><spring:message code="user.name"/></th>
        </tr>
        </thead>

        <form method="post" action="restaurants">
            <c:forEach items="${requestScope.restaurants}" var="restaurant">
                <thead>
                <tr>
                    <th>
                        <input type="radio" id="${restaurant.id}" name="restId" value="${restaurant.id}">
                        <label for="${restaurant.id}">${restaurant.name}</label><br>
                    </th>
                </tr>
                </thead>
                <tr>
                    <th>Name</th>
                    <th>Price</th>
                    <th>Created at</th>
                </tr>

                <c:forEach items="${restaurant.dishes}" var="dish">
                    <tr>
                        <td>${dish.name}</td>
                        <td>${dish.price}</td>
                        <td>${dish.createdAt}</td>
                    </tr>
                </c:forEach>

            </c:forEach>
            <button type="submit">Vote</button>
        </form>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>