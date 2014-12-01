<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>

<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL> 
<% List<String> answer = (List) request.getAttribute(ATTR_ANSWER); %>
<div class="user-right"> 

    <h3><spring:message code="title-result" arguments="${bankName}" /></h3>  
    <c:set var="num" value="${0}" />
    <form action="${actionUrl}" method="post" name="fm1" id="fm1"> 
        <c:forEach var="listV" items="${bankQPto}" > 
            <label class="labelTes"><strong><c:out value="${num + 1}" />.&nbsp;<spring:message code="guiz-question" />&nbsp;<c:out value="${listV.question}" /></strong></label> 
            <c:set var="number" value="${0}" />
            <c:forEach var="listVar" items="${listV.items}"  >
                <c:set value="${listVar.id}" var="listId" />
                <% Boolean yes = false;
                    Long test = (Long) pageContext.getAttribute("listId");
                    String test1 = String.valueOf(test);
                    for (Iterator<String> i = answer.iterator(); i.hasNext();) {
                        String item = i.next();
                        if (item.equals(test1)) {
                %> <div id="${listVar.id}" class="itemMultiple"> <input type="checkbox" checked="checked" name="${listVar.id}" value="true" style="background-color:#66FF33" class="check-box">&nbsp;<c:out value="${listVar.text}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                    <% yes = true; %>
                <input type="hidden" value="off"><br/><%
                            yes = true;
                        }
                    }
                    if (yes == false) {
                %>
                <div id="${listVar.id}" class="itemMultiple"><input type="checkbox" name="${listVar.id}" value="true" class="check-box" >&nbsp;<c:out value="${listVar.text}" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
                <input type="hidden"value="off"><br/>
                <% }%> 
                <c:set var="number" value="${number + 1}" /> 
            </c:forEach> 
            <c:set var="num" value="${num + 1}" />
        </c:forEach>
        <spring:message code="common-btn-save" var="submitText"/>
        <br/><br/>
        <hr>
        <p><spring:message code="p-your-total-points" /><c:out value="${points}" /> </p>

        <c:choose>
            <c:when test="${userBankQ.done == true}">
                <p><spring:message code="p-hiqht-points" arguments="${bankPercent}"  /></p>
            </c:when> 
            <c:otherwise> 
                <p><spring:message code="p-low-points" arguments="${bankPercent}"  /></p>
            </c:otherwise>
        </c:choose>

        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
    </form>  
</div>

<script>
    //script for Multiple choice question  
    var userAnswer = eval('(' + '${userAnswer}' + ')');
    var allAnswer = eval('(' + '${allAnswer}' + ')');
    var cboxes = document.getElementsByClassName('check-box');
    for (var k = 0; k < cboxes.length; k++) {
        cboxes[k].setAttribute('disabled', 'disabled');
    }
    for (var i = 0; i < allAnswer.length; i++) {
        var imgY = document.createElement("img");
        imgY.src = "<%= request.getContextPath()%>/images/ok.png";
        imgY.className = "testImg";
        var imgN = document.createElement("img");
        imgN.src = "<%= request.getContextPath()%>/images/no.png";
        imgN.className = "testImg";
        var elem = document.getElementById(allAnswer[i].id);

        if (allAnswer[i].answer == true) {
            elem.style.backgroundColor = "#66FF33";
        }
        for (var j = 0; j < userAnswer.length; j++) {

            if (allAnswer[i].id == userAnswer[j]) {
                if (allAnswer[i].answer == true) {
                    elem.appendChild(imgY);
                }
                if (allAnswer[i].answer == false) {
                    elem.style.backgroundColor = "#FFCEC2";
                    elem.appendChild(imgN);
                }
            }
        }
    }
</script>

