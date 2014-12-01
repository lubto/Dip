<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %> 
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>  
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />  
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL> 
<script>
    $(document).ready(function() {
        $(document).ready(function() {
            $(".tips").tip_cards();
        });
    });

</script>
<div class="user-right"> 

    <span class="sideMape"><spring:message code="side-map-free-courses" /> > <spring:message code="side-map-category"/></span> 

    <h3><spring:message code="title-free-course-chose" /></h3> 
        <div class="page-container">
            <ul class="tips">
                <c:set value="${1}" var="number" />
                <c:forEach items="${categoryPto}" var="item" >     
                    <li>
                        <div class="tc_front">
                            <a href="#tip${number}"><c:out value="${item.name}" /></a>
                        </div> 
                        <div id="tip${number}" class="tip">
                            <div class="tc_front">  
                                <portlet:renderURL var="detailUrl">
                                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_FREE_COURSES%>" />
                                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />  
                                    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                                </portlet:renderURL>   
                                <h4><c:out value="${item.name}" /></h4>
                                <p><c:out value="${item.description}" /><br/><br/><button class="btnl" onclick="window.location = '${detailUrl}';"><spring:message code="label-view-courses" /></button></p> 
                            </div>
                        </div>
                    </li> <c:set var="number" value="${number+1}"/> 
                </c:forEach>
            </ul>
        </div> 
    <button class="btnl" onclick="window.location = '${backUrl}';"><spring:message code="lable-my-courses" /></button>  
</div>
