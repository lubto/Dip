<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:actionURL name="<%= ACTION_SAVE_Q_BANK_IN_BANK%>" var="actionUrl">
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />   
</portlet:actionURL>  
<portlet:renderURL var="backUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_Q_BANK%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
    <%--<portlet:param name="<%= PARAM_ID_BANK%>" value="${paramIdBank}" /> --%> 
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />  
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
</portlet:renderURL> 
<portlet:renderURL var="backToCourseUrl" >
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " /> 
</portlet:renderURL>

<div class="admin-right">  

    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}"><spring:message code="side-map-bank" /></a> > <c:out value="${shortcutName}" /> > <spring:message code="side-map-edit" /></span>  
 <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />
    <h3><spring:message code="title-edit-q-bank" arguments="${shortcutName}"/></h3> 
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />

    <form:form id="fm" modelAttribute="<%= ATTR_QUIZ_BANK_PTO%>" action="${actionUrl}" method="post" headingCode="${heading}"  name="fm">

        <form:hidden path="id" /> 
        <table width="100%"> <tr>
                <td  width="35%"  style="text-align: right;"><form:label  path="name"><spring:message code="map-name"/></form:label><!--dorobit sping message vsade--></td>
                <td><form:input  path="name" mandatory="true" /></td>
                <td><form:errors path="name" cssClass="error" /><br/></td>
            </tr>
            <tr>
                <td style="text-align: right;"><form:label  path="description"><spring:message code="description"/></form:label><!--dorobit sping message vsade--></td>
                <td><form:input  path="description" mandatory="true" /></td>
                <td><form:errors path="description" cssClass="error" /><br/></td>
            </tr>
            <tr>
                <td style="text-align: right;"><form:label  path="randomQ"><spring:message code="map-random-q"/></form:label></td>
                <td><form:checkbox  path="randomQ" value=""/></td>
                <td<form:errors path="randomQ" cssClass="error" /></td>
            </tr>
            <tr>
                <td style="text-align: right;"> <form:label  path="randomSubQ"><spring:message code="map-random-sub-q"/></form:label></td>
                <td><form:checkbox  path="randomSubQ" value=""/></td>
                <td><form:errors path="randomSubQ" cssClass="error" /></td>
            </tr>
            <tr>
                <td style="text-align: right;"><form:label  path="allQBank" ><spring:message code="map-all-bank"/></form:label></td>
                <td><form:checkbox  path="allQBank" id="allQBank" value="" onclick="funcVal();" /></td>
                <td><form:errors path="allQBank" cssClass="error" /></td>
            </tr>
            <tr>
                <td style="text-align: right;"><form:label  path="numberQForTest"><spring:message code="map-number-q-gor-test"/></form:label><!--dorobit sping message vsade--></td>
                <td><form:input  path="numberQForTest" />&nbsp;&nbsp;<c:out value="${quizBankPto.numberAllQ}" /> &nbsp;<spring:message code="map-in-test"/></td>
                <td><form:errors path="numberQForTest" cssClass="error" /></td>
            </tr>  
            <tr>
                <td style="text-align: right;"><form:label  path="inPercentDone" ><spring:message code="map-in-percent"/></form:label></td>
                <td><form:input  path="inPercentDone"  mandatory="true" />&nbsp;<c:out value="%" /></td>
                <td><form:errors path="inPercentDone" cssClass="error" /></td>
            </tr>
        </table> 
        <br/><br/>
        <spring:message code="common-btn-save" var="submitText"/> 
        <button class="btnl" onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-cancel" /></button> 
        <input class="btnl" type="submit" value="${submitText}" /> 

    </form:form>  
</div>
<style type="text/css">
    label.error { float: none; color: red; padding-left: .5em; vertical-align: top; }
</style> 

<script>
    if (document.getElementById('allQBank').checked == true)
    {
        document.getElementById('numberQForTest').setAttribute('disabled', 'disabled');

    } else {
        document.getElementById('randomQ1').setAttribute('disabled', 'disabled');
        document.getElementById('randomQ1').checked = true;
    }

    function funcVal()
    {
        if (document.getElementById('allQBank').checked == true)
        {
            document.getElementById('numberQForTest').setAttribute('disabled', 'disabled');
            document.getElementById('randomQ1').removeAttribute('disabled');
        }
        else
        {
            document.getElementById('numberQForTest').removeAttribute('disabled');
            document.getElementById('randomQ1').setAttribute('disabled', 'disabled');
            document.getElementById('randomQ1').checked = true;
        }
    }
</script>
<script type="text/javascript">
    <%
        int param = (Integer) request.getAttribute(ATTR_SIZE_ITEMS);
        int paramMin = 0;
        if (param > 0) {
            paramMin = 1;
        }

    %>
    $(document).ready(function() {
        $("#fm").validate({ 
            rules: {
                name: {
                    required: true
                },
                inPercentDone: {
                    required: true,
                    min: 10,
                    max: 100,
                    digits: true
                },
                numberQForTest: {
                    required: true,
                    min: <%=paramMin%>,
                    max: <%=param%>,
                    digits: true
                }
            }, messages: {
                name: '<spring:message code="NotEmpty" javaScriptEscape="true" />',
                inPercentDone: {
                    required: '<spring:message code="NotEmpty" javaScriptEscape="true" />',
                    min: '<spring:message code="Min" arguments="${10}" javaScriptEscape="true" />',
                    max: '<spring:message code="Max" arguments="${100}" javaScriptEscape="true" />',
                    digits: '<spring:message code="Digits" javaScriptEscape="true" />'
                },
                numberQForTest: {
                    required: '<spring:message code="NotEmpty" javaScriptEscape="true" />',
                    min: '<spring:message code="Min" arguments="<%= paramMin%>" javaScriptEscape="true" />',
                    max: '<spring:message code="Max" arguments="${quizBankPto.numberAllQ}" javaScriptEscape="true" />',
                    digits: '<spring:message code="Digits" javaScriptEscape="true" />'
                }
            }
        });
    });
</script> 