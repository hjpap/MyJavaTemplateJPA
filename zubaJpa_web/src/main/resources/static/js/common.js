define(function(require, exports, module) {
	var $ = require('jquery');
	
	require('layer'); //弹窗库
	require('layerui'); //弹窗样式库
	// 验证库
	require('validate');
	// 验证拓展库
	require('validation');
	
	var _csrf_token = $("meta[name='_csrf']").attr("content");
	var _csrf_header = $("meta[name='_csrf_header']").attr("content");
	var _http_header = {};
	_http_header[_csrf_header] = _csrf_token;
	//_http_header['Authorization'] = 'Basic ' + window.btoa(unescape(encodeURIComponent('user:secret')));
	$.ajaxSetup({
		headers: _http_header,
		cache: false,
//		type: "POST",
		statusCode: {
			403: function(xhr) {
				window.location.href = "/login";
			}
		}
	});
	$("form").each(function() {
		$("<input>", {
			name: '_csrf',
			value: _csrf_token,
			type: 'hidden'
		}).appendTo($(this));
	});
	
	exports.getTokens = function() {
		return _csrf_token;
	};

	var public_action = {
		init: function() {
			//所有页面顶部工具条
			this.dropdownmenu();//下拉菜单
			this.gototop();//返回顶部
			this.tabEffect(".tab_menu_box", ".tab_content_box", "current", "click");//TAB切换
		},
		//按钮的下拉
		dropdownmenu: function() {
			$(document).on({
				mouseenter: function() {
					if ($(this).find("div").hasClass("popmain")) {
						$(this)
							.addClass("current")
							.find("div").show();
					}
				},
				mouseleave: function() {
					$(this).removeClass("current").find("div").hide();
				}
			}, '.dropdownmenuUl > li');
		},
		//返回顶部
		gototop: function() {
			$('#gototop').click(function() {
				$('body,html').animate({
					scrollTop: 0
				}, 800);
			});
			$('.toolbars li').hover(function(){
				$(this).addClass('cur');
			},function(){
				$(this).removeClass('cur');	
			});
		},
		//tab 全局
		tabEffect: function(t_name, c_name, hover, state) {
			var move = $(t_name).attr('moving'),
				arrow = $('.arrow'),
				time = null;
			if (state == "hover") {
				$(t_name).find("li").mouseover(function() {
					$(this).addClass(hover).siblings().removeClass(hover);
					$(c_name).find("> div").eq($(this).index()).show().siblings().hide();
				});
			} else {
				$(t_name).find("li").click(function() {
					//对详情页tab特殊处理
					if ($(this).parent().parent().hasClass('tab_menu_box_fixed')) {
						var i = $(".tl_detail_wrap").offset().top;
						getScrollTop();
						$("html,body").scrollTop(i);
					}
					//end!
					$(this).addClass(hover).siblings().removeClass(hover);
					$(c_name).find("> div").eq($(this).index()).show().siblings().hide();
				});
			}
			//获取滚动条高度
			function getScrollTop() {
				var scrollTop = 0;
				if (document.documentElement && document.documentElement.scrollTop) {
					scrollTop = document.documentElement.scrollTop;
				} else if (document.body) {
					scrollTop = document.body.scrollTop;
				}
				return scrollTop;
			}
		}

	}
	$(function() {
		public_action.init();
	});
	
	//意见反馈
	$('#FeedbackBtn').on('click', function(e){
		$.layer({
			type: 1,
			shade: [0.3, '#000'],
			title: '善林宝意见反馈',
			area: ['auto','auto'],
			shadeClose: false,
			border: [0],
			closeBtn: [0, true], //去掉默认关闭按钮
			moveType: 1,
			page : {
				dom : '#FeedbackForm'
			}
		});
		$('.pagebtn').on('click', function(e){
			$("#suggestionContent").val("");
	   		layer.closeAll();
		});
	});
	
	//意见反馈表单提交
	$('#UserFeedbackForm').validate({
		errorPlacement : function(error, element) {
			$(element).parent('div').append(error);
		},
		onkeyup : false,
		rules : {
			suggestionContent : {
				required : true
			}
		},
		messages : {
			suggestionContent : {
				required : '请输入您的意见'
			}
		},
		submitHandler : function(form) {
			suggestionValidateOK();
		}
	});
	function suggestionValidateOK(){
		$.ajax({
			url : "/suggest/feedback",
			type : "post",
			dataType: "text",
			data : $('#UserFeedbackForm').serialize(),
			success : function(result){
				layer.closeAll();
				if(result =="true"){
					$("#suggestionContent").val("");
					$.layer({
						shade: [0.3, '#000'],
						area: ['auto','auto'],
						border: [0],
						closeBtn: [1, false],
						dialog: {
							msg: '<div class="tips right"><h2>操作成功</h2><p>点击确定，返回当前页面</p></div>',
							btns: 1,                    
							type: -1,
							btn: ['确定'],
							yes: function(){
								layer.closeAll();
								//window.location.href=window.location.href;
							}
						}
					});
				} else if (result == "false"){
					$.layer({
						shade: [0.3, '#000'],
						area: ['auto','auto'],
						border: [0],
						closeBtn: [1, false],
						dialog: {
							msg: '<div class="tips error"><h2>操作失败</h2><p style="color:red;">'+result+'</p></div>',
							btns: 1,                    
							type: -1,
							btn: ['确定'],
							yes: function(){
								layer.closeAll();
								//window.location.href=window.location.href;
							}
						}
					});
				} else {
					window.location.href='/login';
				}
			}
		});
	}
	
	$.ajax({
		url : "/findMsgCount",
		type : "GET",
		dataType : "json",
		success : function(data) {
			if(data != null){
				$("#message_div").empty().html('<a href="/account/message">消息<span class="UnreadMessage">(' + data + ')</span></a>');
			}
		}
	});
	
	var wordLimit = function() {
		$(".text_overflow").each(function() {
			var copyThis = $(this.cloneNode(true)).hide().css({
				'position': 'absolute',
				'width': 'auto',
				'overflow': 'visible'
			});
			$(this).after(copyThis);
			if (copyThis.width() > $(this).width()) {
				$(this).text($(this).text().substring(0, $(this).html().length - 4));
				$(this).html($(this).html() + '...');
				copyThis.remove();
				wordLimit();
			} else {
				copyThis.remove(); //清除复制 return; 
			}
		});
	}
	wordLimit();
});