<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <title>信息完善</title>
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/mui.min.css">
    <link href="${ctxStatic}/jybl/css/mui.picker.css" rel="stylesheet"/>
    <link href="${ctxStatic}/jybl/css/mui.poppicker.css" rel="stylesheet"/>
    <link rel="stylesheet" href="${ctxStatic}/jybl/css/Information_perfect.css">
</head>

<body>
<div class="mui-card" style="margin:0;padding:0;box-shadow: none">
    <div class="mui-card-header mui-card-media"
         style="text-align: center;height:40vw;background-image:url(${ctxStatic}/images/cbd.jpg)">
    </div>
    <div class="mui-card-content">
        <div class="mui-card-content-inner">
            <form action="${ctp}/f/weixin/register" id="infoForm">
                <ul class="ul mui-input-row" style="width: 100%;float: left;padding-left: 10px;list-style: none">
                    <li>
                        <div class="left_div">
                            <span style="font-size: 15px;color: red;line-height: 40px"
                                  class="mui-icon mui-icon-star"></span>
                            <span class="label_span">真实姓名</span>
                        </div>
                        <input id="name" style="border: none;width: 60%;padding: 0;margin: 0;" type="text"
                               placeholder="真实姓名"
                               name="personName">
                    </li>
                    <li>
                        <div class="left_div">
                            <span style="font-size: 15px;color: red;line-height: 40px"
                                  class="mui-icon mui-icon-star"></span>
                            <span class="label_span">手机号</span>
                        </div>
                        <input id="pnumber" style="border: none;width: 60%;padding: 0;margin: 0;" type="number"
                               placeholder="手机号"
                               name="phoneNumber">
                    </li>
                    <li>
                        <div class="left_div">
                            <span style="font-size: 15px;color: red;line-height: 40px"
                                  class="mui-icon mui-icon-star"></span>
                            <span class="label_span">身份证</span>
                        </div>
                        <input id="idcard" style="border: none;width: 60%;padding: 0;margin: 0;font-size: 16px;"
                               type="text"
                               placeholder="身份证号"
                               name="idCard">
                    </li>
                    <li>
                        <div class="left_div">
                            <span style="font-size: 15px;color: red;line-height: 40px"
                                  class="mui-icon mui-icon-star"></span>
                            <span class="label_span">邮箱</span>
                        </div>
                        <input id="email" style="border: none;width: 60%;padding: 0;margin: 0;font-size: 16px"
                               type="text"
                               placeholder="邮箱"
                               name="email">
                    </li>
                </ul>
            </form>
        </div>
    </div>

</div>
<div style="margin-top: 20px;width: 100%;text-align: center">
    <button style="margin-left: 10%;border-radius: 15px;width: 80%;margin: 0 auto" type="submit"
            class="mui-btn mui-btn-danger" id="infouSub">提交
    </button>
</div>
<script src="${ctxStatic}/mui/mui.min.js"></script>
<script src="${ctp}/js/jybl/mui.picker.js"></script>
<script src="${ctp}/js/jybl/mui.poppicker.js"></script>
<script src="${ctxStatic}/jquery/jquery.min.js" type="text/javascript"></script>
<script type="application/javascript">
    (function ($, doc) {
        $.init();
        $.ready(function () {
            /**
             * 获取对象属性的值
             * 主要用于过滤三级联动中，可能出现的最低级的数据不存在的情况，实际开发中需要注意这一点；
             * @param {Object} obj 对象
             * @param {String} param 属性名
             */
            var _getParam = function (obj, param) {
                return obj[param] || '';
            };
            //普通示例
            var userPicker = new $.PopPicker();
            userPicker.setData([{
                value: 'nan',
                text: '男'
            }, {
                value: 'nv',
                text: '女'
            }]);
            var showUserPickerButton = doc.getElementById('showUserPicker');
            var userResult = doc.getElementById('userResult');
            showUserPickerButton.addEventListener('tap', function (event) {
                userPicker.show(function (items) {
                    userResult.innerText = items[0].text;
                    //返回 false 可以阻止选择框的关闭
                    //return false;
                });
            }, false);
        });
    })(mui, document);


    function JTrim(s) {
        return s.replace(/(^\s*)|(\s*$)/g, "");
    }
    function isEmail(str) {
        var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/;
        return reg.test(str);
    }

    $("#infouSub").click(function () {
        mui.init({
            swipeBack: false //启用右滑关闭功能
        });
        var personName = $("#name").val();
        var phoneNumber = $("#pnumber").val();
        var idCard = $("#idcard").val();
        var email = $("#email").val();

        if (JTrim(personName) == "") {
            mui.alert( '请填入姓名','注意');
            return;
        }
        if (JTrim(phoneNumber) == "") {
            mui.alert('请填入手机号', '注意');
            return;
        }
        if (JTrim(idCard) == "") {
            mui.alert('请填入身份证', '注意');
            return;
        }
        if (JTrim(email) == "" || !isEmail(email)) {
            mui.alert('请填入正确邮箱', '注意');
            return;
        }
        $("#infoForm").submit();
    });
</script>
</body>
</html>