<%@page import="com.liferay.portal.kernel.util.ListUtil"%>
<%@page import="eu.lms.iface.dto.GroupDocumentsDto"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="java.util.List"%>
<%@page import="javax.portlet.PortletContext"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:renderURL var="createUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_CREATE_GROUP_DOC%>" />
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:renderURL> 
<portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" />  
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" />
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<%
    String searchRequest = ParamUtil.getString(request, "searchRequest");
    PortletContext pc = renderRequest.getPortletSession().getPortletContext();

    List<GroupDocumentsDto> document = (List) request.getAttribute(ATTR_ALL_ITEMS);

    String searchAttr = "";
    searchAttr = (String) request.getAttribute(SEARCH_ATTR);
    Long userId = (Long) request.getAttribute(PARAM_ID_COURSE);
    PortletURL iteratorURL = renderResponse.createRenderURL();
    iteratorURL.setParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
    iteratorURL.setParameter(ATTR_SEARCH, searchAttr);
    iteratorURL.setParameter(PARAM_ID_COURSE, userId.toString());
%> 
<portlet:actionURL name="<%= ACTION_SEARCH_DMS_BANK%>" var="getSearchResultURL" >  
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:actionURL>

<div class="admin-right"> 
    <liferay-ui:success key="SuccessKey" message="lms-msg-dummy-created" />
    <liferay-ui:success key="SuccessKeySaved" message="lms-msg-dummy-saved" /> 
    <liferay-ui:success key="SuccessKeyDeleted" message="lms-msg-dummy-deleted" />
    <liferay-ui:error key="ErrorKeyFullBank" message="lms-msg-full-bank" /> 

    <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <spring:message code="mal-all-group-documents" /></span>  
    <h3><spring:message code="title-all-doc" /></h3> 
<%--
    <form action="${getSearchResultURL.toString()}" method="post"  class="tab-le">
        <input type="text" name="ASearchRequest" value='<%= (searchAttr == "") ? "" : searchAttr%>' class="in-put" >
        <spring:message code="btn-search" var="var_search"/>
        <input class="btnl" type="submit" value="${var_search}">
    </form> --%>
    <c:choose> 
        <c:when test="${isNull == false}">
            <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" iteratorURL="<%= iteratorURL%>" delta="5" deltaConfigurable="true" total="<%= document.size()%>">
                <liferay-ui:search-container-results>
                    <%
                        results = ListUtil.subList(document, searchContainer.getStart(), searchContainer.getEnd());
                        total = document.size();

                        pageContext.setAttribute("results", results);
                        pageContext.setAttribute("total", total);
                    %>  
                </liferay-ui:search-container-results>    
                <liferay-ui:search-container-row className="eu.lms.iface.dto.GroupDocumentsDto" modelVar="item" keyProperty="id">
                    <liferay-ui:search-container-column-text name="lable-name">
                        <c:out value="${item.name}" />
                    </liferay-ui:search-container-column-text> 
                    <liferay-ui:search-container-column-text name="lable-number-itemsDoc">
                        <c:out value="${item.sizeItemDocument}" />
                    </liferay-ui:search-container-column-text>
                    <liferay-ui:search-container-column-text name="label-position"> 
                        <portlet:actionURL name="<%= ACTION_UP_GDOCUMENTS%>" var="upUrl">
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" /> 
                        </portlet:actionURL>
                        <portlet:actionURL name="<%= ACTION_DOWN_GDOCUMENTS%>" var="downUrl">                        
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:actionURL>
                          <c:out value="${item.serialNumber}" />
                        <c:if test="${item.serialNumber != 1}">
                            <a href="${upUrl}"><img src="<%= request.getContextPath()%>/images/up.png"></a>
                            </c:if>
                            <c:if test="${item.serialNumber != fn:length(allItems)}">
                            <a href="${downUrl}"><img src="<%= request.getContextPath()%>/images/down.png"></a>
                            </c:if>
                        </liferay-ui:search-container-column-text> 
                    <liferay-ui:search-container-column-text name="lable-visible">
                        <c:choose>
                            <c:when test="${item.visible == true}">
                                <spring:message code="lms-msg-visible" htmlEscape="true" />
                            </c:when>
                            <c:otherwise>
                                <spring:message code="lms-msg-unvisible" htmlEscape="true" />
                            </c:otherwise> 
                        </c:choose>  
                    </liferay-ui:search-container-column-text> 
                    <liferay-ui:search-container-column-text name="lms-label-actions">
                        <portlet:renderURL var="addDocUrl">
                            <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_Q_ADD_ITEM_DOC%>" />
                            <portlet:param name="<%= PARAM_ID_GROUP%>" value="${item.id}" />
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:renderURL>
                        <portlet:renderURL var="removeItemsUrl">
                            <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_REMOVE_ITEM_DOC%>" />
                            <portlet:param name="<%= PARAM_ID_GROUP%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:renderURL>
                        <portlet:renderURL var="detailItemsUrl">
                            <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_ITEM_DOC%>" />
                            <portlet:param name="<%= PARAM_ID_GROUP%>" value="${item.id}" />
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:renderURL>
                        <portlet:renderURL var="detailUrl">
                            <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_DETAIL_GROUP_DOC%>" />
                            <portlet:param name="<%= PARAM_ID_GROUP%>" value="${item.id}" />
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:renderURL>
                        <portlet:renderURL var="editUrl">
                            <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_EDIT_GROUP_DOC%>" />
                            <portlet:param name="<%= PARAM_ID_GROUP%>" value="${item.id}" />
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:renderURL>
                        <portlet:actionURL name="<%= ACTION_DELETE_GROUP_DOC%>" var="deleteUrl">
                            <portlet:param name="<%= PARAM_ID_GROUP%>" value="${item.id}" />
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:actionURL> 
                        <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_GROUP_DOC%>" var="deactivateUrl"> 
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM%>" value="1" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:actionURL>
                        <portlet:actionURL name="<%= ACTION_DE_ACTIVATE_GROUP_DOC%>" var="activateUrl">  
                            <portlet:param name="<%= PARAM_ID%>" value="${item.id}" /> 
                            <portlet:param name="<%= PARAM%>" value="0" /> 
                            <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
                        </portlet:actionURL> 
                        <spring:message code="btn-add-item-doc" var="var1" />
                        <spring:message code="btn-view-item-doc" var="var2" />
                        <spring:message code="btn-remove-file" var="var3" /> 
                        <spring:message code="btn-view-group" var="var4" /> 
                        <spring:message code="btn-edit-group" var="var5" />
                        <spring:message code="mal-deactivate-account-user" var="var6" />
                        <spring:message code="mal-activate-account-user" var="var7" />
                        <liferay-ui:icon-menu> 
                            <liferay-ui:icon image="add_article" url="${addDocUrl}" message="${var1}"/>
                            <c:if test="${item.sizeItemDocument != 0}" >  
                                <liferay-ui:icon image="delete_attachment" url="${removeItemsUrl}" message="${var3}"/>
                                <liferay-ui:icon image="all_pages" url="${detailItemsUrl}" message="${var2}"/>
                            </c:if>
                            <c:if test="${item.sizeItemDocument == 0}" > 
                                <liferay-ui:icon image="view" url="${detailUrl}" message="${var4}" />
                            </c:if>
                            <liferay-ui:icon image="edit" url="${editUrl}"  message="${var5}"/>
                            <c:choose>
                                <c:when test="${item.visible == true}">
                                    <liferay-ui:icon image="activate" url="${activateUrl}" message="${var6}" /> 
                                </c:when>
                                <c:otherwise>
                                    <liferay-ui:icon image="deactivate" url="${deactivateUrl}" message="${var7}" /> 
                                </c:otherwise> 
                            </c:choose> 
                            <li class role="presentation">
                                <a class="taglib-icon" href="${deleteQuestionUrl}" onclick="if (confirm('<spring:message code="lms-msg-dele-question" arguments="${item.name}" htmlEscape="true" />')) {
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
            </liferay-ui:search-container>
        </c:when>
        <c:otherwise>
            <liferay-ui:search-container emptyResultsMessage="lms-msg-no-items" >
                <liferay-ui:search-container-results results="${allItems}" total="${fn:length(allItems)}" /> 
                <liferay-ui:search-container-row className="eu.lms.iface.dto.GroupDocumentsDto" modelVar="item" > 
                </liferay-ui:search-container-row> 
                <liferay-ui:search-iterator  />
            </liferay-ui:search-container>
        </c:otherwise>
    </c:choose> 
    <br/>
    <div class="buttons">
        &nbsp; <button class="btnl"  onclick="window.location = '${createUrl}';"><spring:message code="btn-create-group-doc" /></button>&nbsp;
        <button class="btnl"  style="float: left;" onclick="window.location = '${backToCourseUrl}';"><spring:message code="mal-btn-back-course" /></button>
    </div>
</div>