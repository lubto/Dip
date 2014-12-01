<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="eu.lms.iface.dto.UserDto"%>
<%@page import="java.util.Iterator"%> 
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %> 
<%
        ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
        String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:actionURL name="<%= ACTION_ADD_USERS_TO_COURSE%>" var="actionUrl" >
    <portlet:param name="<%= PARAM_ID_COURSE%>" value="${idCourse}" />
</portlet:actionURL>
 <portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_VIEW_USERS_IN_COURSE%>" /> 
     <portlet:param name="<%= PARAM_ID%>" value="${idCourse}" /> 
     <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
 <portlet:renderURL var="backToCourseUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_COURSE%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
 
<div class="admin-right"> 
        <liferay-ui:success key="SuccessKeys" message="lms-msg-users_add_to-course" />
        <liferay-ui:success key="SuccessKey" message="lms-msg-user_add_to-course" />
        <span class="sideMape"><a href="${backToCourseUrl}"><spring:message code="label-course" /></a> > <c:out value="${shortcutC}" /> > <a href="${backUrl}"><spring:message code="label-user" /></a> > <spring:message code="mal-add-user-to-course"/></span> 
   
    <h3><spring:message code="title-register-user" /></h3>  
    <c:set var="ns"><portlet:namespace/></c:set>
    <%
        List<UserDto> usersDto = (List) request.getAttribute(ATTR_USERS_LIST);
        List<UserDto> usersSelected = (List) request.getAttribute(ATTR_USERS_LIST_SELECTED); 
    %>
   <br/> 
    <form name="fm2" method="post" action="${actionUrl}">
        <table width="100%">
            <tr>
                <td width="20%"></td>
                <td><label style="margin-left: 206px;"><spring:message code="title-all-users-from-group-selected" /></label></td>
            </tr>
             <tr>
                <td></td>
                 <td><select multiple="true" class="searchable" name="searchable" id="searchable" > 
           <% if (usersDto != null) {
                    for (Iterator<UserDto> i = usersDto.iterator(); i.hasNext();) {
                        UserDto item = i.next();
                        String name = item.getFullName();
                        Long id = item.getId();
            %><option value="<c:out value="<%= id%>" />"> <c:out value="<%= name%>" /></option> 
            <%}
            } else {%> <%}%>
            <% if (usersSelected != null) {
                    for (Iterator<UserDto> it = usersSelected.iterator(); it.hasNext();) {
                        UserDto item = it.next();
                        String name = item.getFullName();
                        Long id = item.getId();
            %><option value="<c:out value="<%= id%>" />" disabled > <c:out value="<%= name%>" /></option> 
            <%}
            } else {%> <%}%>
        </select> </td>
            </tr>
        </table> 
        <br/><br/> 
        <button class="btnl"  onclick="window.location = '${backUrl}';
                return false;"><spring:message code="common-btn-cancel" /></button>
        <spring:message code="mal-add-user-to-course" var="submitText"/>
        <input class="btnl"  type="submit" value="${submitText}" onclick="return val();" /> 
    </form>
</div> 
<script type="text/javascript">
    function val() {
     var x = document.getElementById("searchable").selectedIndex;
        if (fm2.searchable.selectedIndex == -1) {
            alert('<spring:message code='alert-select-option' javaScriptEscape='true' />');
            fm2.searchable.focus();
            return false;
        }
        return true;
    }
</script> 
<script>
    $('.searchable').multiSelect({
        selectableHeader: "<input type='text' style='height: 25px;' class='search-input' autocomplete='off' placeholder='<spring:message code="title-searche" javaScriptEscape="true" />' >",
        selectionHeader: "<input type='text' style='height: 25px;' class='search-input' autocomplete='off' placeholder='<spring:message code="title-searche" javaScriptEscape="true" />'>",
        afterInit: function(ms) {
            var that = this,
                    $selectableSearch = that.$selectableUl.prev(),
                    $selectionSearch = that.$selectionUl.prev(),
                    selectableSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selectable:not(.ms-selected)',
                    selectionSearchString = '#' + that.$container.attr('id') + ' .ms-elem-selection.ms-selected';

            that.qs1 = $selectableSearch.quicksearch(selectableSearchString)
                    .on('keydown', function(e) {
                        if (e.which === 40) {
                            that.$selectableUl.focus();
                            return false;
                        }
                    });

            that.qs2 = $selectionSearch.quicksearch(selectionSearchString)
                    .on('keydown', function(e) {
                        if (e.which == 40) {
                            that.$selectionUl.focus();
                            return false;
                        }
                    });
        },
        afterSelect: function() {
            this.qs1.cache();
            this.qs2.cache();
        },
        afterDeselect: function() {
            this.qs1.cache();
            this.qs2.cache();
        }
    });
    
    $('#searchable').multiSelect();
    $('#select-all').click(function() {
        $('#searchable').multiSelect('select_all');
        return false;
    });
    $('#deselect-all').click(function() {
        $('#searchable').multiSelect('deselect_all');
        return false;
    });
</script>
 