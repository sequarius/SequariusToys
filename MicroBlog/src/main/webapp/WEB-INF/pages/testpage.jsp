<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE>
<html>
<head>
    <meta charset="utf-8">
    <title>图片弹出层裁切上传|xiaomlove.com|小喵爱你</title>
    <style type="text/css">
        .test{margin-top: 100px;}
        .test img{max-width: 100%;max-height: 100%}
        .test p{width: 120px;height: 120px;text-align: center}
    </style>
    <style>#BAIDU_DSPUI_FLOWBAR,.ad,div[class^="ad-widsget"],div.ad960-90,div.ad-side,table.adtable,div.a_mu,div.a_h,ins.adsbygoogle,div.ad_pic,div#ggPar,div.wp.a_f,div.wp.a_t,div.a_p,div.a_pb,div.a_pt,div.a_pr,#preAdContent,div.a_cn,div.bm.a_c,div[id^="div-gpt-ad-"]{display:none !important;}</style></head>

<body>
<div class="container">
    <div class="col-md-2 test">
        <p><img src="/web_resource/image/male_180.png"/></p>
        <button class="btn btn-info change-btn" data-toggle="popover" data-placement="bottom" data-trigger="manual" data-content="保存成功">修改头像</button>
    </div>
</div>
<link rel="stylesheet" href="/web_resource/css/bootstrap.min.css">
<link rel="stylesheet" href="/web_resource/css/jquery.Jcrop.css"/>
<script src="/web_resource/js/jquery.min.js"></script>
<script src="/web_resource/js/bootstrap.min.js"></script>
<script src="/web_resource/js/jquery.Jcrop.min.js"></script>
<script src="/web_resource/js/xmcrop.js"></script>
<script>
    var $btn = $('.change-btn');
    $btn.on('shown.bs.popover', function() {
        var _this = this;
        setTimeout(function() {
            $(_this).popover('hide');
        }, 1500);
    });

    $('.change-btn').xmcrop({
        onFailed: function(msg, data) {
            $(this).attr('data-content', msg).popover('show');
        },
        onSuccess: function(result) {
            if (result.code == 1) {
                $(this).parent().find('img:first').attr('src', result.src);
            }
            $(this).attr('data-content', result.msg).popover('show');
        },
        serverUrl: '/upload_avatar',
        uploadLabel: 'file',
        modalTitle: '先裁切，确定后上传',
        modalSubmitBtnText: '确定',
        modalCancleBtnText: '取消',
        imageType: ['jpeg', 'png'],
        imageSizeMax: 0.5*1024*1024,
        imageCropResolutionMin: [50, 50],
        imageCropResolutionMax: [300, 300],
        jcrop: {
            aspectRatio: 1,//宽高比
            allowSelect: false,//不允许重新建立选择框
        }
    })
</script>
</body>