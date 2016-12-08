<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
    var type = navigator.userAgent.match(/.*Mobile.*/) ? "mobile" : "pc";
    alert(1);
    if (type == "mobile") {
        window.location.href = 'login/loginchoose';
    } else {
    }
</script>