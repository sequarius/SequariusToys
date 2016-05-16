<%@ page import="java.util.Date" %>
<%--
  Created by IntelliJ IDEA.
  User: Sequarius
  Date: 2015/6/1
  Time: 19:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<% //重定义父页面name=content的内容 %>

<%
    pageContext.setAttribute("date", new Date());
%>

<rapid:override name="content">

    <c:if test="${cookie.logged_in.value!=true}">
        <div class="jumbotron">
            <h1>欢迎来到MicroBlog</h1>

            <p>MicroBlog 是一个基于 Spring 的微博系统。</p>

            <p>
                <a class="btn btn-lg btn-success" href="/sign_in" role="button">登陆 &raquo;</a>
                <a class="btn btn-lg btn-primary" href="/sign_up" role="button">立即注册 &raquo;</a>
            </p>
        </div>
    </c:if>


    <div class="bg-info" id="content_bg">
        <c:if test="${cookie.logged_in.value==true}">
            <%@include file="publish.jsp" %>
        </c:if>
        <div id="post_container">
            <c:forEach var="post" items="${posts}" varStatus="st">
                <div id="post_item">
                    <div class="media-left">
                        <a href="/user_index/${post.user.username}">
                            <c:choose>
                                <c:when test="${post.user.avatar==null}">
                                    <img class="media-object img-circle" src="/web_resource/image/male_180.png"
                                         alt="...">
                                </c:when>
                                <c:otherwise>
                                    <img class="media-object img-circle" src="${post.user.avatar}" alt="...">
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </div>
                    <div class="media-body">
                        <a href="/user_index/${post.user.username}">
                            <h4 class="media-heading" id="username">${post.user.username}</h4>
                        </a>

                        <c:out value="${post.content}"/>
                        <br>
                        <c:out value="${post_times.get(st.index)}"/>
                    </div>

                </div>
            </c:forEach>
        </div>
    </div>
</rapid:override>

<rapid:override name="title">
    MicroBlog
</rapid:override>

<rapid:override name="head">
    <style type="text/css">
        #post_item {
            max-width: 70%;
            margin: 15px auto;
            background-color: #ffffff;
        }

        #username {
            margin-top: 10px;
        }

        img {
            margin: 16px;
            max-width: 90px;
            max-height: 90px;
        }

        #content_bg {
            padding-top: 20px;
            padding-bottom: 50px;
        }

    </style>
</rapid:override>
<!-- extends from base.jsp -->
<%@ include file="layout.jsp" %>
