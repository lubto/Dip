<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="javax.portlet.PortletURL"%>
<%@ page import="com.liferay.portal.kernel.util.OrderByComparator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@include file="../init.jspf" %>  

<div class="admin-right"> 
    <h3>LMS FOR LIFERAY</h3>

<%-- <!--
    <portlet:renderURL var="searchURL">
        <portlet:param name="jspPage" value="/html/search-users.jsp" />
    </portlet:renderURL>

    <aui:form name="searchForm" action="<%=searchURL%>" method="post">
        <aui:input  name="keywords" label="Enter any keyword related to user you wish to search"/>
        <aui:button type="submit" value="Search" />
    </aui:form>
    
    <%       
        String keywords = ParamUtil.getString(request, "keywords");
        PortletURL renderURL = renderResponse.createRenderURL();
        renderURL.setParameter("jspPage", "/html/search-users.jsp");
        // here we have created renderURL just to remain on the current jsp page after the pagination.
%>
   
<liferay-ui:search-container  iteratorURL="<%= renderURL %>" emptyResultsMessage="there-are-no-records" delta="5">
   
    <liferay-ui:search-container-results>
       
        <%   
                LinkedHashMap<String, Object> userParams = new LinkedHashMap<String, Object>();
                       
                List<User> list = UserLocalServiceUtil.search(
                company.getCompanyId(), keywords, true, userParams,
                searchContainer.getStart(), searchContainer.getEnd(),
                (OrderByComparator) null);
       
                total = UserLocalServiceUtil.searchCount(
                company.getCompanyId(), keywords, true,  userParams);
                pageContext.setAttribute("results", list);
                pageContext.setAttribute("total", total);
        %>
         
    </liferay-ui:search-container-results>
   
    <liferay-ui:search-container-row className="com.liferay.portal.model.User" modelVar="record">
           
            <liferay-ui:search-container-column-text name="User Id" property="userId" />
            <liferay-ui:search-container-column-text name="First Name" property="firstName" />
            <liferay-ui:search-container-column-text name="LastName" property="lastName" />
            // any other information you want to display
    </liferay-ui:search-container-row>
   
    <liferay-ui:search-iterator />
   
</liferay-ui:search-container> --> --%>
</div>