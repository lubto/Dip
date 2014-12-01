<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../init.jspf" %> 
<%@page import="static eu.lms.portlet.userright.MainCardRightPortletConstants.*" %>  

<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CATEGORY%>" />  
    <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
</portlet:renderURL>   
<script>
    $(document).ready(function() {
        $(document).ready(function() {
            $(".tips").tip_cards();
        });
    });

</script>
<div class="user-right"> 

    <span class="sideMape"><spring:message code="side-map-free-courses" /> > <a href="${backUrl}"><spring:message code="side-map-category"/></a> > <spring:message code="side-map-course"/> </span> 

    <h3><spring:message code="title-free-course-chose" /></h3> 
    <div class="page-container">
        <ul class="tips"> 
             <c:set value="${1}" var="number" />
            <c:forEach items="${coursePto}" var="coursePto" >   
                <li>
                    <div class="tc_front">
                        <a href="#tip${number}"><c:out value="${coursePto.name}" /></a>
                    </div> 
                    <div id="tip${number}" class="tip">
                        <div class="tc_front">  
                            <portlet:actionURL var="detailUrl" name="<%= ACTION_ADD_USERS_TO_COURSE%>"> 
                                <portlet:param name="<%= PARAM_ID_COURSE%>" value="${coursePto.id}" />  
                                <portlet:param name="<%= ATTR_CUR_ID_USER%>" value="${userId}" />
                            </portlet:actionURL>   
                            <h4><c:out value="${coursePto.name}" /></h4>
                            <p><c:out value="${coursePto.description}" /><br/><br/>
                                <c:choose>
                                    <c:when test="${coursePto.added == false}">
                                <button class="btnl" onclick="window.location = '${detailUrl}';"><spring:message code="label-add" /></button>
                                    </c:when>
                                    <c:otherwise>
                                        <spring:message code="p-added-courses" />
                                    </c:otherwise>
                            </c:choose>
                            </p> 
                        </div>
                    </div>
                </li>  <c:set value="${number+1}" var="number" />
            </c:forEach> 
        </ul>
    </div><br/><br/>
    <button class="btnl" onclick="window.location = '${backUrl}';"><spring:message code="back" /></button>
</div>
