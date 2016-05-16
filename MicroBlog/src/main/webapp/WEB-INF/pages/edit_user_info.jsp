<%--
  Created by IntelliJ IDEA.
  User: Sequarius
  Date: 2015/6/9
  Time: 12:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.rapid-framework.org.cn/rapid" prefix="rapid" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<rapid:override name="content">
    <div class="container">

        <h2 class="form-register-heading">我的资料</h2>

        <div class="container test" id="avatar_div">
            <c:choose>
                <c:when test="${user.avatar==null}">
                    <p id="image"><img class="img-circle" src="/web_resource/image/male_180.png"/></p>
                </c:when>
                <c:otherwise>
                    <p id="image"><img id="image_id" class="img-rounded" src="${user.avatar}"/></p>
                </c:otherwise>
            </c:choose>

            <div id="btn_choose_avatar">
                <button class="btn btn-info change-btn" data-toggle="popover" data-placement="bottom"
                        data-trigger="manual"
                        data-content="保存成功">更换头像
                </button>
            </div>
        </div>


        <form class="form-register" id="form-register" action="/editinfo" method="post">
            <c:if test="${edit_success!=null}">
                <div class="alert alert-success" role="alert">${edit_success}</div>
            </c:if>
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


            <label for="input_nickname" class="sr-only">Username</label>
            <input type="text" value="${user.username}" id="input_nickname" required name="username"
                   class="form-control" placeholder="用户名" maxlength="20">
            <label for="input_address" class="sr-only">住址</label>
            <input type="text" value="${user.address}" id="input_address" name="address" class="form-control"
                   placeholder="住址"
                   autofocus
                   maxlength="70">
            <label for="input_gender" class="sr-only">性别</label>
            <input type="text" id="input_gender" value="${user.gender}" name="gender" class="form-control"
                   placeholder="性别" autofocus
                   maxlength="1" required>
            <label for="input_phone" class="sr-only">电话</label>
            <input type="text" id="input_phone" name="phoneNum" value="${user.phoneNum}" class="form-control"
                   placeholder="电话" autofocus
                   maxlength="13">
            <button class="btn btn-lg btn-danger btn-block" type="submit">更新</button>
        </form>

    </div>
</rapid:override>

<rapid:override name="title">
    编辑资料
</rapid:override>
<rapid:override name="js">
    <script src="/web_resource/js/jquery.Jcrop.min.js"></script>
    <script src="/web_resource/js/xmcrop.js"></script>
    <script src="/web_resource/js/bootstrap-select.min.js"></script>

    <script>
        $('.selectpicker').selectpicker({
            size: 2
        });
        var $btn = $('.change-btn');
        $btn.on('shown.bs.popover', function () {
            var _this = this;
            setTimeout(function () {
                $(_this).popover('hide');
            }, 1500);
        });

        $('.change-btn').xmcrop({
            onFailed: function (msg, data) {
                $(this).attr('data-content', msg).popover('show');
            },
            onSuccess: function (result) {
                if (result.code == 1) {
                    $(this).parent().parent().find('img:first').attr('src', result.src);
                }
                $(this).attr('data-content', result.msg).popover('show');
            },
            serverUrl: '/upload_avatar',
            uploadLabel: 'file',
            modalTitle: '先裁切，确定后上传',
            modalSubmitBtnText: '确定',
            modalCancleBtnText: '取消',
            imageType: ['jpeg', 'png'],
            imageSizeMax: 0.5 * 1024 * 1024,
            imageCropResolutionMin: [50, 50],
            imageCropResolutionMax: [300, 300],
            jcrop: {
                aspectRatio: 1,//宽高比
                allowSelect: false,//不允许重新建立选择框
            }
        })
    </script>

</rapid:override>
<rapid:override name="head">
    <link href="/web_resource/css/sign_up.css" rel="stylesheet">
    <link rel="stylesheet" href="/web_resource/css/jquery.Jcrop.css"/>
    <style type="text/css">
        /*.test {*/
        /*/!*margin-top: 100px;*!/*/
        /*margin: 0 auto;*/
        /*}*/

        /*.test img {*/
        /*max-width: 100%;*/
        /*max-height: 100%;*/
        /*}*/

        /*.test p {*/
        /*width: 120px;*/
        /*height: 120px;*/

        /*text-align: center*/
        /*}*/

        #image_id{
            max-width: 270px;
            max-height: 270px;
        }

        #btn_choose_avatar {
            text-align: center;
            margin: 15px auto;
        }

        #image {
            text-align: center;
            margin: 0 auto;
        }

        #avatar_div {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
    </style>


</rapid:override>
<!-- extends from base.jsp -->
<%@ include file="layout.jsp" %>

