<%--
  Created by IntelliJ IDEA.
  User: Sequarius
  Date: 2015/6/7
  Time: 18:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<spring:hasBindErrors name="post">
  <div class="alert alert-danger" role="alert">

    <c:if test="${errors.fieldErrorCount > 0}">
      <c:forEach items="${errors.fieldErrors}" var="error">
        <spring:message var="message" code="${error.code}" arguments="${error.arguments}"
                        text="${error.defaultMessage}"/>
        ${message}<br/>
      </c:forEach>
    </c:if>

    <c:if test="${errors.globalErrorCount > 0}">
      Making making making making global error: <br/>
      <c:forEach items="${errors.globalErrors}" var="error">
        <spring:message var="message" code="${error.code}" arguments="${error.arguments}"
                        text="${error.defaultMessage}"/>
        <c:if test="${not empty message}">
          ${message}<br/>
        </c:if>
      </c:forEach>
    </c:if>
  </div>
</spring:hasBindErrors>
