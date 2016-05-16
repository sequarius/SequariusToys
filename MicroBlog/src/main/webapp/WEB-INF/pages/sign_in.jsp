<%--
  Created by IntelliJ IDEA.
  User: Sequarius
  Date: 2015/6/1
  Time: 21:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<% //重定义父页面name=content的内容 %>


<rapid:override name="content">
    <div class="container">

        <form class="form-register" id="form-register" action="/login" method="post">
            <h2 class="form-register-heading">登陆MicroBlog</h2>
            <h3 class="form-register-heading">分享精彩生活</h3>
                <%--表单錯誤回显--%>

            <spring:hasBindErrors name="user">
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

            <label for="input_email" class="sr-only">Email address</label>
            <input type="email" id="input_email" name="email" class="form-control" placeholder="邮箱" required autofocus
                   maxlength="40">
            <label for="input_password" class="sr-only">Password</label>
            <input type="password" id="input_password" name="password" class="form-control"
                   placeholder="密码" required
                   maxlength="16">
            <button class="btn btn-lg btn-success btn-block" type="submit">登陆</button>
        </form>

    </div>
</rapid:override>

<rapid:override name="title">
    加入MicroBlog
</rapid:override>
<rapid:override name="head">
    <link href="/web_resource/css/sign_up.css" rel="stylesheet">
</rapid:override>
<!-- extends from base.jsp -->
<%@ include file="layout.jsp" %>
