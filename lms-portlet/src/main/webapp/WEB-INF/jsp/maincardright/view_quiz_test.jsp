<%@page import="eu.lms.iface.dto.ItemMultipleChoiceDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@page import="eu.lms.iface.dto.MultipleChoiceDto"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %>   
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>
<%
    List<MultipleChoiceDto> i = (List<MultipleChoiceDto>) (request.getAttribute(ATTR_BANK_Q_PTO));
    List<Long> list = new ArrayList<Long>();
    List<Long> list2 = new ArrayList<Long>();
    for (Iterator<MultipleChoiceDto> it = i.iterator(); it.hasNext();) {
        MultipleChoiceDto m = it.next();
        list.add(m.getId());
        List<ItemMultipleChoiceDto> item = m.getItems();
        for (Iterator<ItemMultipleChoiceDto> itt = item.iterator(); itt.hasNext();) {
            ItemMultipleChoiceDto mm = itt.next();
            list2.add(mm.getId());
        }
        list2.add(0L);
    }
    pageContext.setAttribute("listM", list);
    pageContext.setAttribute("listM2", list2);

%>

<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_TESTS%>" />
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
    <portlet:param name="<%= ATTR_ID_COURSE_ITEM%>" value="${courseItem}" />
    <portlet:param name="<%= PARAM_ID%>" value="${idCourse}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<portlet:actionURL var="actionUrl" name="<%= ACTION_CHECK_TEST%>"  >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" /> 
    <portlet:param name="<%= PARAM_ID%>" value="${paramId}" /> 
    <portlet:param name="<%= PARAM_LIST%>" value="${listM}" />
    <portlet:param name="<%= PARAM_LIST_2%>" value="${listM2}" />
    <portlet:param name="<%= ATTR_USER_BANKQ%>" value="${userBankQ}" /> 
</portlet:actionURL>

<div class="user-right">  
    <h3><spring:message code="title-test" arguments="${bankName}" /></h3>
    <c:set var="num" value="${0}" />

    <form action="${actionUrl}" method="post" name="fm1" id="fm1"  >
        <c:forEach var="listV" items="${bankQPto}" >
            <label class="labelTes" ><strong><c:out value="${num + 1}" />.&nbsp;<spring:message code="guiz-question" />&nbsp;<c:out value="${listV.question}" /></strong> </label>
            <input type="hidden" name="${listV.id}" value="${listV.id}">

            <c:set var="number" value="${0}" />
            <c:forEach var="listVar" items="${listV.items}"  > 
                <div id="r${num}${number}"  class="itemMultiple">&nbsp;<input type="checkbox" name="${listVar.id}" value="true" class="check-box">&nbsp;&nbsp;<c:out value="${listVar.text}" /></div>
                <input type="hidden"   value="off"><br/>
                <c:set var="number" value="${number + 1}" /> 
            </c:forEach> 
            <c:set var="num" value="${num + 1}" />
        </c:forEach>
        <spring:message code="common-btn-save" var="submitText"/>
        <br/><br/>
        <input class="btnl" type="submit" value="${submitText}" />
        <%--<button onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-back" /></button> --%>
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

