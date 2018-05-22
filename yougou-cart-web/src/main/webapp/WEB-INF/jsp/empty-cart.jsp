<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="/css/base.css">
    <link href="/css/purchase.2012.css?v=201410141639" rel="stylesheet" type="text/css">
    <title>我的购物车 - 优购商城</title>
    <style type="text/css">
        .cart-empty {
            height: 98px;
            padding: 80px 0 120px;
            color: #333;
            border: none;
        }
        .cart-empty .message {
            height: 98px;
            padding-left: 401px;
            background: url(../../images/shortcut/no-login-icon.png) 290px 22px no-repeat;
        }
        a, address, b, big, blockquote, body, center, cite, code, dd, del, div, dl, dt, em, fieldset, font, form, h1, h2, h3, h4, h5, h6, html, i, iframe, img, ins, label, legend, li, ol, p, pre, small, span, strong, u, ul, var {
            margin: 0;
            padding: 0;
        }

        /*.cart-empty ul, menu, dir {
            display: block;
            list-style-type: disc;
            -webkit-margin-before: 1em;
            -webkit-margin-after: 1em;
            -webkit-margin-start: 0px;
            -webkit-margin-end: 0px;
            -webkit-padding-start: 40px;
        }*/
        li {
            display: list-item;
            text-align: -webkit-match-parent;
        }
        ol, ul {
            list-style: none;
        }s
        .w {
            width: 990px;
            margin: 0 auto;
        }
    </style>
    <script>
        var pageConfig = {};
    </script>
<body>
<!--shortcut start-->
<jsp:include page="commons/shortcut.jsp"/>
<!--shortcut end-->
<div class="w w1 header clearfix">
    <div id="logo"><a href="//localhost:8082/"><img clstag="clickcart|keycount|xincart|logo"
                                                         src="/images/yougou-logo.png" width="220" height="60" title="返回优购商城首页" alt="返回优购商城首页"></a>
    </div>
    <div class="language"><a href="javascript:void(0);" onclick="toEnCart()"></a></div>
    <div class="progress clearfix">
        <ul class="progress-1">
            <li class="step-1"><b></b>1.我的购物车</li>
            <li class="step-2"><b></b>2.填写核对订单信息</li>
            <li class="step-3">3.成功提交订单</li>
        </ul>
    </div>
</div>
<!--显示空的购物车-->
<div id="container" class="cart" ecarddg="0" cartalwaysdg="0" t="0">
    <div class="w">
        <div class="cart-empty">
            <div class="message">
                <ul>
                    <li class="txt">
                        购物车空空的哦~，去看看心仪的商品吧~
                    </li>
                    <li class="mt10">
                        <a href="//localhost:8082/" class="ftx-05">
                            去购物&gt;
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</div>
<!--推荐位html修改处-->


<script type="text/javascript" src="/js/base-v1.js"></script>
<!-- footer start -->
<jsp:include page="commons/footer.jsp"/>
<!-- footer end -->

<!-- 购物车相关业务 -->
<script type="text/javascript" src="/js/cart.js"></script>
<script type="text/javascript" src="/js/jquery.price_format.2.0.min.js"></script>

</html>