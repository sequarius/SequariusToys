/**
 * 图片裁切上传。基于jQuery库,Bootstrap框架,jquery.Jcrop.js插件
 */
;(function($, window) {
	$.fn.xmcrop = function(options) {
		var defaults = {
			'serverUrl': '',//后端接收url
			'uploadLabel': 'upfile',//上传图片时formData的key
			'imageType': ['jpeg', 'jpg', 'gif', 'png'],//允许的图片格式
			'imageSizeMax': 1*1024*1024,//最大图片尺寸
			'imageCropResolutionMin': [50, 50],//裁切出来的图片的最小宽高
			'imageCropResolutionMax': [2000, 2000],//裁切出来的图片的最大宽高
			'onSuccess': function(serverReturn) {},//上传成功回调，serverReturn，服务器返回的信息；this,触发的元素
			'onFailed': function(msg, data) {},//失败回调，msg，错误信息；data，相关数据；this，触发的元素
			'modalTitle': 'Modal Title',//模态框标题
			'modalCancleBtnText': 'Cancle',//模态框取消按钮文本
			'modalSubmitBtnText': 'OK',//模态框确定按钮文本
			'modalFade': true,//模态框是否淡入淡出
			'jcrop': {
				//Jcrop插件的相关参数
			},
		};
		var settings = $.extend({}, defaults, options);
		var HTML = '<div class="modal-dialog"><div class="modal-content"><div class="modal-header">';
		HTML += '<button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>';
		HTML += '<h4 class="modal-title">' + settings.modalTitle + '</h4></div><div class="modal-body"><div style="text-align: center"><img id="zncrop-img" style="max-width: 100%;max-height: 400px"/></div></div>';
		HTML += '<div class="modal-footer"><button type="button" class="btn btn-default cancle">' + settings.modalCancleBtnText + '</button>';
		HTML += '<button type="button" class="btn btn-primary submit">' + settings.modalSubmitBtnText + '</button></div></div></div>';
		
		var methods = {
			
		};

		var $modal = $("<div>");
		if (settings.modalFade) {
			$modal.addClass('modal fade');
		} else {
			$modal.addClass('modal');
		}
		$modal.html(HTML);
		$("body").append($modal);

		var $inputFile = $('<input type="file">');
		$inputFile.css({
			visibility: 'hidden',
			width: 0,
			height: 0,
			display: 'none',
		}).appendTo($('body'));

		//定义变量
		var $close = $modal.find('button.close');
		var $submit = $modal.find('button.submit');
		var $cancle = $modal.find('button.cancle');
		var $modalBody = $modal.find('.modal-body');
		var $img = $modal.find('#zncrop-img');
		var uploading = false;
		var file = null;
		var elem = null;
		var originalSrc = null;
		var jcrop_api = null;
		var naturalWidth = null;
		var naturalHeight = null;
		var scaleX = null;
		var scaleY = null;
		var errorMsg = null;
		var errorData = null;

		//绑定事件
		$inputFile.on('change', function(e) {
			file = e.originalEvent.target.files[0];
			// console.log(file);
			if (file.size > settings.imageSizeMax) {
				error('文件体积过大', settings.imageSizeMax);
				return;
			}
		
			if (file.type) {
				const type = file.type.split('/')[1];
				if (settings.imageType.indexOf(type) == -1) {
					error('不是被允许的图片格式', type);
					return;
				}
			} else {
				error('未知文件格式');
				return;
			}
			console.log(settings.imageType);
			console.log(type);
			var fr = new FileReader();
			fr.onload = function(e) {
				originalSrc = e.target.result;
				$img.attr("src", originalSrc);
				naturalWidth = $img.prop('naturalWidth');
				naturalHeight = $img.prop('naturalHeight');
				if (naturalWidth < settings.imageCropResolutionMin[0] || 
					naturalWidth < settings.imageCropResolutionMin[0]) 
				{
					error('图片分辨率过小', {naturalWidth: naturalWidth, naturalHeight: naturalHeight});
					return;
				}
				$modal.modal({
					backdrop: 'static',
					keyboard: false,
				})
			};
			fr.readAsDataURL(file);
		});
		
		$close.add($cancle).click(function() {
			if (!uploading) {
				$modal.modal('hide');
			}
		});

		$submit.click(function(e) {
			if (jcrop_api && file) {
				var jcropSelect = jcrop_api.tellSelect();
				var jcropScaled = jcrop_api.tellScaled();
//				console.log(jcropSelect);
//				console.log(jcropScaled);
				var dx = Math.floor(scaleX*jcropSelect.x);
				var dy = Math.floor(scaleY*jcropSelect.y);
				var width = Math.floor(scaleX*jcropSelect.w);
				var height = Math.floor(scaleY*jcropSelect.h);
				doUpload({
					dx: dx,
					dy: dy,
					width: width,
					height: height,
				});
			} else {
				errorMsg = 'jcrop_api没有获得或选择的文件没有获得';
				errorData = {jcrop_api: jcrop_api, file: file};
				$modal.modal('hide');
			}
		})

		$modal.on('shown.bs.modal', function(e) {
			var jcrop_settings = settings.jcrop;		
			var imgWidth = $img.width();
			var imgHeight = $img.height();
			jcrop_settings.boxWidth = imgWidth;//外盒宽高
			jcrop_settings.boxHeight = imgHeight;
			scaleX = naturalWidth/imgWidth;
			scaleY = naturalHeight/imgHeight;
			//规定了最终裁切出来的图片的最小宽高
			jcrop_settings.minSize = [Math.min(settings.imageCropResolutionMin[0]/scaleX, imgWidth), Math.min(settings.imageCropResolutionMin[1]/scaleY, imgHeight)];//这是针对选框来说的值
		
			//规定最终裁切出来的图片的最大宽高
			jcrop_settings.maxSize = [Math.min(settings.imageCropResolutionMax[0]/scaleX, imgWidth), Math.min(settings.imageCropResolutionMax[1]/scaleY, imgHeight)];//这是针对选框来说的值
			
			var x = (jcrop_settings.maxSize[0] + imgWidth)/2;
			var y = (jcrop_settings.maxSize[1] + imgHeight)/2;
			jcrop_settings.setSelect = [x, y, x - jcrop_settings.maxSize[0], y - jcrop_settings.maxSize[1]];
			// 初始化选框
			// jcrop_settings.setSelect = [Math.min(settings.imageCropResolutionMax[0], naturalWidth), Math.min(settings.imageCropResolutionMax[1], naturalHeight), 0, 0],//这是相对真实图片像素来说的
			// console.log(scaleX, scaleY);
			// console.log(x, y);
			// console.log(jcrop_settings);
			$img.Jcrop(jcrop_settings, function() {
				jcrop_api = this;
				
			});
		});
		
		$modal.on('hidden.bs.modal', function(e) {
			jcrop_api.destroy();
			if (errorMsg) {
				error(errorMsg, errorData);
			}
			reset();
		});

		function doUpload(cropData) {
			var url = settings.serverUrl;
			if (url.trim() === '') {
				errorMsg = '没有设置serverUrl';
				errorData = url;
				$modal.modal('hide');
				return;
			}
			var formData = new FormData();
			for (var i in cropData) {
				formData.append(i, cropData[i]);
			}
			formData.append(settings.uploadLabel, file);
			$.ajax({
				url: settings.serverUrl,
				type: 'POST',
				dataType: 'json',
				processData: false,
				contentType: false,
				data: formData,
				beforeSend: function() {
					$submit.text('上传中...').attr('disabled', true);
					uploading = true;
				}
			}).done(function(result) {
				if ($.isFunction(settings.onSuccess)) {
					settings.onSuccess.call(elem, result);
					$modal.modal('hide');
				}
			}).error(function(xhr, errorText, errorThrow) {
				errorMsg = 'ajax上传出错';
				errorData = errorText;
				$modal.modal('hide');
			})
		}

		function error(reason, data) {
			if ($.isFunction(settings.onFailed)) {
				settings.onFailed.call(elem, reason, data, settings);
			}
		}

		function reset() {
			file = null;
			uploading = false;
			elem = null;
			originalSrc = null;
			jcrop_api = null;
			naturalWidth = null;
			naturalHeight = null;
			errorMsg = null;
			errorData = null;
			scaleX = null;
			scaleY = null;
			$submit.text(settings.modalSubmitBtnText).removeAttr('disabled');
			$img.removeAttr('src').removeAttr('style').css({
				'max-width': '100%',
				'max-height': '400px',
			});
		}

		return this.each(function() {
			$(this).click(function(e) {
				elem = this;
				$inputFile.click();
			});					
		});
		
	}
})(jQuery, window);

