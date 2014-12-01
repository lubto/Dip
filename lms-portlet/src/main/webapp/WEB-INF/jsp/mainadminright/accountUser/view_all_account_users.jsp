<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@page import="eu.lms.iface.dto.AccountUserDto"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.kernel.util.ListUtil" %>
<%@page import="javax.portlet.PortletContext"%> 
<%@page import="com.liferay.portal.model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>  
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<%@include file="../../init.jspf" %>

<div class="admin-right">
    
    <span class="sideMape"><spring:message code="label-account" /> > <spring:message code="label-view"/></span> 
     
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" /> 
    <liferay-ui:error key="ErrorKeyDeleted" message="msg-error-deleted" />
    <liferay-ui:success key="KeyDeactivated" message="msg-deactivate" />  
    <liferay-ui:success key="KeyActivated" message="msg-activate" />
     <liferay-ui:success key="SuccessKey" message="lms-msg-create-account-user" />
    <liferay-ui:success key="SuccessKeys" message="lms-msg-create-account-users" />
    
    <h3><spring:message code="title-all-account-users" /></h3>
    <%
        String searchRequest = ParamUtil.getString(request, "searchRequest");
        PortletContext pc = renderRequest.getPortletSession().getPortletContext();
        
        List<AccountUserDto> usersGroup = (List) request.getAttribute(ATTR_ALL_ITEMS);
        
        String searchAttr = "";
        searchAttr = (String) request.getAttribute(SEARCH_ATTR); 
       
        PortletURL iteratorURL = renderResponse.createRenderURL();  
        iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_USERS); 
        iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    %> 
    <portlet:actionURL name="<%= ACTION_SEARCH_ACCOUNTS%>" var="getSearchResultURL" >  
    </portlet:actionURL>
    
 <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
     <input type="text" name="ASearchRequest" value='<%= (searchAttr == "" ) ? "" : searchAttr%>' class="in-put" >
      <spring:message code="btn-search" var="var_search"/>
      <input class="btnl" type="submit" value="${var_search}">
 </form>
    <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items"  iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= usersGroup.size()%>" >
          
        <liferay-ui:search-container-results>
            <%                
                results = ListUtil.subList(usersGroup, searchContainer.getStart(), searchContainer.getEnd());
                total = usersGroup.size();

                pageContext.setAttribute("results", results);
                pageContext.setAttribute("total", total);
            %>
        </liferay-ui:search-container-results> 
        <liferay-ui:search-container-row className="eu.lms.iface.dto.AccountUserDto" modelVar="item" keyProperty="id">
            <%--  <liferay-ui:search-container-column-text name="lable-name">
                  <c:set var="idUser" value="${item.userId}"/>
                  <%/*
                      String id = String.valueOf(pageContext.getAttribute("idUser"));
                      user = UserLocalServiceUtil.getUserById(Long.valueOf(id));*/
                  %>
                  <c:out value="<%= user.getFullName()%>" /> 

            </liferay-ui:search-container-column-text> --%>
            <liferay-ui:search-container-column-text name="lable-name">
                <c:out value="${item.userName}" />
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lable-dateOfCreation">
                <c:out value="${item.dateCreation}" />
            </liferay-ui:search-container-column-text> 
            <liferay-ui:search-container-column-text name="lable-disable-enable"> 
                <c:choose>
                    <c:when test="${item.activate == true}">
                        <spring:message code="lms-msg-visible" htmlEscape="true" />
                    </c:when>
                    <c:otherwise>
                        <spring:message code="lms-msg-unvisible" htmlEscape="true" />
                    </c:otherwise> 
                </c:choose> 
            </liferay-ui:search-container-column-text>   
            <liferay-ui:search-container-column-text name="lms-label-actions">
                <portlet:renderURL var="viewAllAccountUserUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ITEMS_USER%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
                </portlet:renderURL>
                <portlet:actionURL name="<%=  ACTION_DE_ACTIVATE_ACCOUN%>" var="deactivateUrl"> 
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM%>" value="1" />
                </portlet:actionURL>
                <portlet:actionURL name="<%=  ACTION_DE_ACTIVATE_ACCOUN%>" var="activateUrl">  
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                    <portlet:param name="<%= PARAM%>" value="0" />
                </portlet:actionURL>
                <portlet:actionURL name="<%= ACTION_DELETE_ACCOUNTUSER%>" var="deleteUrl">
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:actionURL>
                <portlet:renderURL var="deleteQuestionUrl">
                    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DELETE_QUESTION_ACCOUNTUSER%>" />
                    <portlet:param name="<%= PARAM_ID%>" value="${item.id}" />
                </portlet:renderURL>  
                <spring:message code="mal-added-course" var="var1" /> 
                <spring:message code="mal-deactivate-account-user" var="var2" />
                <spring:message code="mal-activate-account-user" var="var3" />
                <liferay-ui:icon-menu>
                    <liferay-ui:icon image="all_pages" url="${viewAllAccountUserUrl}" message="${var1}" />
                    <c:choose>
                        <c:when test="${item.activate == true}">
                            <liferay-ui:icon image="activate" url="${activateUrl}" message="${var2}" /> 
                        </c:when>
                        <c:otherwise>
                            <liferay-ui:icon image="deactivate" url="${deactivateUrl}" message="${var3}" /> 
                        </c:otherwise> 
                    </c:choose>  
                    <li class role="presentation" >
                        <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.userName}" htmlEscape="true" />')) {
                                    window.location = '${deleteUrl}';
                                }
                                return false;">
                            <liferay-ui:icon image="delete" />
                        </a> 
                    </li> 
                </liferay-ui:icon-menu>
            </liferay-ui:search-container-column-text>
        </liferay-ui:search-container-row> 
        <liferay-ui:search-iterator />
    </liferay-ui:search-container > 
    
</div>