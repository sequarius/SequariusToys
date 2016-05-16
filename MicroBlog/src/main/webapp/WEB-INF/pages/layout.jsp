<%@ page import="java.net.URLDecoder" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String name="~!";
    Cookie[] cookies=request.getCookies();
    if (cookies!=null) {
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("user_name")){
                name=URLDecoder.decode(cookie.getValue(),"UTF-8");
            }
        }
    }
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <rapid:block name="head">
    </rapid:block>

    <title><rapid:block name="title">
        base_head_content
    </rapid:block></title>

    <!-- Bootstrap core CSS -->
    <link href="/web_resource/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <%--<link href="navbar.css" rel="stylesheet">--%>

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <!--[if lt IE 9]>
    <script src="/web_resource/js/ie-emulation-modes-warning.js"></script><![endif]-->
    <script src="/web_resource/js/ie-emulation-modes-warning.js"></script>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="/web_resource/js/html5shiv.min.js"></script>
    <script src="/web_resource/js/respond.min.js"></script>
    <![endif]-->
    <style>#BAIDU_DSPUI_FLOWBAR, .ad, div[class^="ad-widsget"], div.ad960-90, div.ad-side, table.adtable, div.a_mu, div.a_h, ins.adsbygoogle, div.ad_pic, div#ggPar, div.wp.a_f, div.wp.a_t, div.a_p, div.a_pb, div.a_pt, div.a_pr, #preAdContent, div.a_cn, div.bm.a_c, div[id^="div-gpt-ad-"] {
        display: none !important;
    }</style>
</head>

<body>
<div class="container">

    <!-- Static navbar -->
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar"
                        aria-expanded="false" aria-controls="navbar">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="/">MicroBlog</a>
            </div>
            <div id="navbar" class="navbar-collapse collapse">
                <ul class="nav navbar-nav">
                    <li class="active"><a href="/">首页</a></li>
                    <c:if test="${cookie.logged_in.value==true}">
                        <li><a href="/user_index/${cookie.user_name.value}">我的微博</a></li>
                    </c:if>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <c:choose>
                        <c:when test="${cookie.logged_in.value==true}">

                            <li><a href="/edit_user_info"><%=name %></a></li>
                            <li><a href="/sign_out">退出</a></li>
                        </c:when>
                        <c:otherwise>
                            <li class="active"><a href="/sign_in">登陆 <span class="sr-only">(current)</span></a></li>
                            <li><a href="/sign_up">注册</a></li>
                        </c:otherwise>
                    </c:choose>

                </ul>
            </div>
            <!--/.nav-collapse -->
        </div>
        <!--/.container-fluid -->
    </nav>

    <!-- Main component for a primary marketing message or call to action -->
    <rapid:block name="content">
        base_body_content
    </rapid:block>


</div>
<!-- /container -->
<script src="/web_resource/js/jquery.min.js"></script>
<script src="/web_resource/js/bootstrap.min.js"></script>
<rapid:block name="js">
</rapid:block>
<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->

<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
</body>
</html>
