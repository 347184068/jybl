
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8" />
  <title>防伪码管理系统</title>
  <meta http-equiv="pragma" content="no-cache" />
  <meta http-equiv="cache-control" content="no-cache" />
  <meta http-equiv="expires" content="0" />
  <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.5/themes/default/easyui.css">
  <link rel="stylesheet" type="text/css" href="${ctxStatic}/jquery-easyui-1.5/themes/icon.css">
  <script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.5/jquery.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.5/jquery.easyui.min.js"></script>
  <script type="text/javascript" src="${ctxStatic}/jquery-easyui-1.5/locale/easyui-lang-zh_CN.js"></script>
  <style type="text/css">
    .bordertable{border-color:#99BBE8;border-style:dashed;border-width:0px;margin:0 auto;border-right-width:1px;border-bottom-width:1px;border-collapse:collapse;}
    .bordertable tr{height: 38px;}
    .bordertable td,.bordertable th{padding:3px;border-color:#99BBE8;border-style:dashed;border-width:0px;border-top-width:1px;border-left-width:1px;border-right-width:1px;border-bottom-width:1px;}
    .bordertable th{text-align: right;}
  </style>
</head>
<body>
<div id="jbxx">
  <fieldset style="border:solid 1px #95B8E7;">
    <legend style="font-size:14px;font-weight:bold;">获取防伪码</legend>
    <form:form id="getCode"  action="/file/fileDeal/grantInOutCode?loginName=sys_Wc_01&pwd=wc_bc1@2017&type=01" method="post" class="form-horizontal">
    <table cellpadding="0" cellspacing="0" width="100%" class="bordertable" style="font-size: 12px;">
      <tr>
        <td width="25%" align="right">点击获取防伪码:</td>
        <td width="75%" colspan="3" align="left">
          <a href="javascript:void(0)" id="btn_file" onclick="getFile();" class="easyui-linkbutton" iconCls="icon-add">获取文件</a>
        </td>
      </tr>
      <tr>
        <td width="25%" align="right">防伪码下载地址:</td>
        <td width="75%" colspan="3" align="left">
          <span id="filePath"></span>
        </td>
      </tr>
    </table>
    </form:form>
  </fieldset>
</div>
<div style="height:10px;"></div>
<div id="jbxx1">
  <fieldset style="border:solid 1px #95B8E7;">
    <legend style="font-size:14px;font-weight:bold;">上传已使用防伪码</legend>
    <form id="inputForm"  enctype="multipart/form-data"  action="<%=request.getContextPath()%>/file/fileDeal/uploadFile?loginName=sys_Wc_01&pwd=wc_bc1@2017&type=01" method="post" class="form-horizontal">
    <table cellpadding="0" cellspacing="0" width="100%" class="bordertable" style="font-size: 12px;">
      <tr>

        <td width="25%" align="right">上传已使用防伪码:</td>
        <td width="75%" align="left">
          <input name="file" class="easyui-filebox" style="width:300px;" data-options="required:true, buttonText:'选择已使用防伪码文件',accept: 'txt/*'" />
          <span id="upLoadMsg"></span>
        </td>
      </tr>
      <tr>
        <td width="25%" align="right"></td>
        <td width="75%" align="left">
          <a href="javascript:void(0)" id="btn_submit" onclick="uploadFile();" class="easyui-linkbutton" iconCls="icon-save">提交上传</a>
        </td>
      </tr>
    </table>
    </form>
  </fieldset>
</div>
<script type="text/javascript">

  function getFile(){
    $("#btn_file").linkbutton({disabled:true});

     var url = "<%=request.getContextPath()%>/file/fileDeal/grantInOutCode?loginName=sys_Wc_01&pwd=wc_bc1@2017&type=01";

     $.post(url,{},function(data){
       if(data.code==1){
         $("#filePath").html(data.msg);
       }else{
         $("#filePath").html(data.message);
       }

     $("#btn_file").linkbutton({disabled:false});
     },'json');


  }
  function uploadFile(){

    var r=$("#inputForm").form('validate');
    if(!r){
      return;
    }
    $("#btn_submit").linkbutton({disabled:true});

    $("#inputForm").submit();

  }

</script>
</body>
</html>