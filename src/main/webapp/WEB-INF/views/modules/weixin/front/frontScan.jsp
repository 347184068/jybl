
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>潍柴动力防伪查询微信助手</title>
<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1,user-scalable=no">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<link rel="stylesheet"  href="<%=request.getContextPath()%>/static/mui/css/mui.min.css"/>
<script type="text/javascript" src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/mui/mui.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/static/jquery/jquery-1.8.3.min.js">
</script>
	<style type="text/css">
		#loading {
			position: fixed;
			top: 0;
			right: 0;
			bottom: 0;
			left: 0;
			z-index: 998;
			background: rgba(0,0,0,.3) url(<%=request.getContextPath()%>/static/img/loading.gif) center center no-repeat;
		}
		.dn{display:none;}
	</style>
<script type="text/javascript">
	wx.config({
		debug : false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		appId : '${appId}', // 必填，公众号的唯一标识
		timestamp : ${timestamp}, // 必填，生成签名的时间戳
		nonceStr : '${nonceStr}', // 必填，生成签名的随机串
		signature : '${signature}',// 必填，签名，见附录1
		jsApiList : [ 'checkJsApi', 'scanQRCode', ]
	// 必填，需要使用的JS接口列表，所有JS接口列表见附录2
	});

	wx.ready(function() {
		// 9.1.2 扫描二维码并返回结果
		document.querySelector('#scanQRCode1').onclick = function() {

			wx.scanQRCode({
				needResult : 1,
				desc : 'scanQRCode desc',
				success : function(res) {
					getScanInfo(res.resultStr);
				}
			});
		};
	});
	wx.error(function(res) {
		alert(res.errMsg);
	});

	function getScanInfo(resultStr){

		var url = "<%=request.getContextPath()%>/validate/scanQrCode/validateScanCode";

		$("#loading").removeClass('dn');
		var openId = $("#openId").val();
		$.ajax({
			url : url,
			data : {
				qrCode:resultStr
				,openId:openId
			},
			dataType : 'json',
			type : 'post',
			async:false,
			success : function(result) {
				$("#loading").addClass('dn');
				var msg='<li class="mui-table-view-cell"><span style="font-weight: bold;">真伪鉴定报告</span>:'+result.msg+'</li>';
				/*$("#scanCode").val(result.scanCode);*/
				$("#list_content li").remove();
				$("#list_content").append(msg);
				if(result.flag){
					var productInfo ='<li class="mui-table-view-cell"><span style="font-weight: bold;">产品信息</span>：</li>'+
					'<li class="mui-table-view-cell" id="materialCode">'+
					'产品代码：'+result.materialCode+'</li>'+

					'<li class="mui-table-view-cell" id="materialName">产品名称：'+
					result.materialName+'</li>' ;
					$("#list_content").append(productInfo);
				}
				var footer = '<li class="mui-table-view-cell"><span style="color:#b52400;font-weight: bold;">'+
				'友情提示：如果产品信息与实物不符，请直接给客服留言，我们将跟踪处理。</span></li>';
				$("#list_content").append(footer);
				$("#list_content").css("display","block");
			},
			error : function(result) {
				alert('error---');
			}
		});
	}

	function getQueryInfo(){
		var scanCode = $("#scanCode").val();

		if(!scanCode){
			mui.toast("请输入有效的信息");
			return;
		}
		if(/^(W|N)[0-9a-zA-Z]{12}$/.test(scanCode) || /^\d{12}$/.test(scanCode) || /^\d{23}$/.test(scanCode)){
			getScanInfo(scanCode);
		}else{
			mui.toast("请输入有效的信息");
			return;
		}


	}
</script>

</head>
<body>
<input  type="hidden"  id="openId" value="${openId}"/>
<header class="mui-bar mui-bar-nav" style="border-bottom: solid 1px #000000;">
	<h1 id="title" class="mui-title" >
		<img src="<%=request.getContextPath()%>/static/img/logo.png" style="height: 40px;" />
	</h1>
</header>
<nav class="mui-bar mui-bar-tab">
	<a id="defaultTab" class="mui-tab-item mui-active" href="#">
		<span class="mui-tab-label">潍柴动力防伪查询</span>
	</a>
</nav>
<div class="mui-content">
	<div class="mui-content-padded" style="margin: 10px;">
		<table width="100%">
			<tr>
				<td width="70%">
					<input id="scanCode"  type="text" style="width: 90%;"  placeholder="">
				</td>
				<td width="15%">
					<a href="javascript:void(0);" id="scanQRCode1">
						<img src="<%=request.getContextPath()%>/static/img/btn_scan.png" style="width:38px;height:38px;margin-bottom:10px;"  />
					</a>
				</td>
				<td width="15%">
					<button type="button" class="mui-btn mui-btn-primary" style="width:60px;height:40px;margin-bottom:15px;" onclick="getQueryInfo()">查询</button>
				</td>
			</tr>
		</table>
	</div>
	<div style="border-bottom: solid 1px #000000;"></div>

	<ul id="list_content"  style="background-color: #92d14f;" class="mui-table-view">


	</ul>

</div>
<div id="loading" class="dn"></div>
</body>
</html>
