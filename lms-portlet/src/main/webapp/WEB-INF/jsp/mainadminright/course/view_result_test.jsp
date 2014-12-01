<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>

<%
    String userId = request.getRemoteUser();
%>
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
    <portlet:param name="<%= PARAM_ID_USER %>" value="<%= userId%>" />
</portlet:renderURL> 

<% List<String> answer = (List) request.getAttribute(ATTR_ANSWER); %>

<div class="admin-right"> 
    <h3><spting:message code="title-course-to-study" /></h3>
    <c:set var="num" value="${0}" />
    <form action="${actionUrl}" method="post" name="fm1" id="fm1">
        <c:forEach var="listV" items="${bankQPto}" >
            <label class="labelTes"><c:out value="${num + 1}" />.&nbsp;<spring:message code="guiz-question" />&nbsp;<c:out value="${listV.question}" /></label>
            <c:set var="number" value="${0}" />
            <c:forEach var="listVar" items="${listV.items}"  > 
                <% Boolean yes = false;
                    for (Iterator<String> i = answer.iterator(); i.hasNext();) {
                        String item = i.next();

                %>
                <c:if test="${item == listV.id}">
                    <div id="r${num}${number}"><c:out value="${number + 1}" />.&nbsp;<c:out value="${listVar.text}" />&nbsp;&nbsp;<input type="checkbox" checked="checked" name="${listVar.id}" value="true" style="background-color:#66FF33"></div>
                        <% yes = true; %>
                    <input type="hidden" value="off"><br/>
                </c:if> 
                <% }
                  if (yes == false) {
                %>
                <div id="r${num}${number}"><c:out value="${number + 1}" />.&nbsp;<c:out value="${listVar.text}" />&nbsp;&nbsp;<input type="checkbox" name="${listVar.id}" value="true"></div>
                <input type="hidden"value="off"><br/>
                <%
                    }%> 
                <c:set var="number" value="${number + 1}" /> 
            </c:forEach> 
            <c:set var="num" value="${num + 1}" />
        </c:forEach>
        <spring:message code="common-btn-save" var="submitText"/>
        <br/><br/>
        <input class="btnl"  type="submit" value="${submitText}" />
        <button class="btnl"  onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button>
    </form>  
</div>

<script>
    //script for Multiple choice question 

    function control() {
        var data = eval('(' + '${dataJsonM}' + ')');
        //  document.getElementById('button').setAttribute('disabled', 'disabled');
        var cboxes = document.getElementsByClassName('item');
        var len = cboxes.length;
        for (var i = 0; i < len; i++) {
            var imgY = document.createElement("img");
            imgY.src = "<%= request.getContextPath()%>/images/ok.png";
            var imgN = document.createElement("img");
            imgN.src = "<%= request.getContextPath()%>/images/no.png";

            var elem = document.getElementById("r" + i);

            if (data[i].answer == true) {
                elem.style.backgroundColor = "#66FF33";

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

    }
</script>

