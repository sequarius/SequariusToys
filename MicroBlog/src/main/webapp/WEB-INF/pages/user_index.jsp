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

    <div id="infocard">
        <p><img id="image_id" class="img-circle"
                src="${user.avatar==null?'/web_resource/image/male_180.png':user.avatar}"/>


        <p> <h4 id="blogowner">${user.username}<b>${user.gender=='男'?' ♂':' ♀'}</b></h4> </p>

        <p>  <h5 id="useradress">${user.address}</h5></p>
    </div>
    <div class="bg-info" id="content_bg">

            <%--<%@include file="info_card.jsp" %>--%>


        <c:if test="${cookie.logged_in.value==true&&cookie.user_name.value==username}">
            <%--<%page include file="publish.jsp"/>--%>
            <%@include file="publish.jsp" %>
        </c:if>


        <div id="post_container">
            <c:forEach var="post" items="${posts}" varStatus="st">
                <div id="post_item">
                    <div class="media-left">

                        <a href="${user.avatar==null?'/web_resource/image/male_180.png':user.avatar}">
                            <img id="image_user" class="media-object img-circle"
                                 src="${user.avatar==null?'/web_resource/image/male_180.png':user.avatar}"
                                 alt="...">
                        </a>
                    </div>
                    <div class="media-body">

                        <h4 class="media-heading" id="username">${post.user.username}说：</h4>
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

        #blogowner {
            text-align: center;
        }

        #useradress {
            text-align: center;
        }

        p {
            margin: 0 auto;
            text-align: center;
        }

        #infocard {
            padding-top: 50px;
            /*background-color: #b2dba1;*/
            background-image: url(/web_resource/image/bg-test.jpg);
            padding-bottom: 50px;
        }

        #image_id {
            height: 210px;
            width: 210px;
        }

        #image_user {
            margin: 16px;
            max-width: 90px;
            max-height: 90px;
        }

        #content_bg {
            margin-top: 20px;
            padding-bottom: 50px;
        }

    </style>
</rapid:override>
<!-- extends from base.jsp -->
<%@ include file="layout.jsp" %>
