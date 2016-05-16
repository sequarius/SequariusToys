<%--
  Created by IntelliJ IDEA.
  User: Sequarius
  Date: 2015/6/7
  Time: 0:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style type="text/css">
    #input_publish {
        max-width: 70%;
        margin: 0px auto;
        padding: 20px 0px;
    }

    #btn_submit {
        /*position: absolute;*/
        /*right: 15px;*/
        margin-left: 90%;
        /*margin-right: 15px;*/
        margin-right: 30%;
        margin-top: 15px;

    }
</style>

<div id="input_publish">
    <form method="post"  action="/post/publishPost">
        <%@ include file="error_message.jsp" %>
        <textarea name="content" class="form-control" rows="3" maxlength="140"></textarea>


        <button id="btn_submit" type="submit" class="btn btn-success">发布
        </button>

    </form>
</div>
