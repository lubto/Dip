<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_Q%>" />
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>

<div class="admin-right">  

    <span class="sideMape"><spring:message code="side-map-queston" /> > <a href="${backUrl}"><spring:message code="side-map-all-question" /></a> > <spring:message code="side-map-detail" /></span>  

    <h3><spring:message code="title-detail-question" /></h3>  
    <c:set var="number" value="${0}" />
    <form action="#" method="post" name="fm1" id="fm1">
        <label><spring:message code="guiz-question" />&nbsp;<c:out value="${questionPto.question}" /></label>
        <div id="addRight"><c:forEach var="listVar" items="${itemMPto}"  > 
            <div id="r${number}"><input type="checkbox" name="item${number}" value="true" id="item${number}" class="check-box">&nbsp;<c:out value="${listVar.text}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <input type="hidden" name="item${number}" value="off"><br/>
            <c:set var="number" value="${number + 1}" /> 
        </c:forEach> </div>
    </form> 
    <div class="buttons">
           <button class="btnl" onclick="window.location = '${backUrl}';
                        return false;"><spring:message code="common-btn-back" /></button>
        <button class="btnl" id="butt" onclick="control()" ><spring:message code="test" /></button>
     
    </div>
</div> 
<script>
    //script for Multiple choice question 
    function control() {
        var data = eval('(' + '${dataJsonM}' + ')');
        var cboxes = document.getElementsByClassName('check-box');
        var len = cboxes.length;
        for (var i = 0; i < len; i++) {
            var imgY = document.createElement("img");
            imgY.src = "<%= request.getContextPath()%>/images/ok.png";
            var imgN = document.createElement("img");
            imgN.src = "<%= request.getContextPath()%>/images/no.png";

            var elem = document.getElementById("r" + i);

            if (data[i].answer == true) {
                elem.style.backgroundColor = "#c3ffaf";

                if (cboxes[i].checked == true) {
                    elem.appendChild(imgY);
                }
                if (cboxes[i].checked == false) {
                    //elem.appendChild(imgN); 
                }

            } else if (data[i].answer == false) {
                if (cboxes[i].checked == true) {
                    elem.style.backgroundColor = "#FFCEC2";
                    elem.appendChild(imgN);
                } else if (cboxes[i].checked == false) {
                    // elem.appendChild(imgY);
                }
            }
        }
        document.getElementById('butt').setAttribute('disabled', 'disabled');

    }
</script>