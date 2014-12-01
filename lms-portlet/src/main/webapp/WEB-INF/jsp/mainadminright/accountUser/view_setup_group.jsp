<%@page import="eu.lms.iface.dto.UserDto"%> 
<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portal.model.UserGroup"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>

<portlet:actionURL name="<%= ACTION_SHOW_USERGROUP%>" var="showUserGroupUrl" />  
<portlet:actionURL name="<%= ACTION_CREATE_ACCOUNTITEM_USER%>" var="actionUrl" />  

<div class="admin-right"> 

    <span class="sideMape"><spring:message code="label-account" /> > <spring:message code="title-activate"/></span> 
    <liferay-ui:success key="SuccessKey" message="lms-msg-create-account-user" />
    <liferay-ui:success key="SuccessKeys" message="lms-msg-create-account-users" />

    <h3><spring:message code="title-activate-account" /></h3>  
    <c:set var="ns"><portlet:namespace/></c:set>
    <%
        List<UserGroup> usersGroup = (List) request.getAttribute(ATTR_GROUP_LIST);
        List<UserDto> usersDto = (List) request.getAttribute(ATTR_USERS_LIST);
        List<UserDto> usersSelected = (List) request.getAttribute(ATTR_USERS_LIST_SELECTED);
    %>

    <form name="fm1" method="post" action="${showUserGroupUrl}">  
        <table width="100%">
            <tr>
                <td></td>
                <td><br/></td>
            </tr>
            <tr>
                <td width="20%"></td>
                <td><select name="selectGroup" id="selectGroup"> 
                        <%for (Iterator<UserGroup> i = usersGroup.iterator(); i.hasNext();) {
                                UserGroup item = i.next();
                                String name = item.getName();
                                Long id = item.getPrimaryKey();
                        %><option value="<c:out value="<%= id%>" />"><c:out value="<%= name%>" /></option>
                        <%}%>  
                    </select>  
                    <spring:message code="mal-btn-load" var="submitGroup"/>
                    <input class="btnl"  type="submit" value="${submitGroup}" />  
                </td>
            </tr>
            <tr>
                <td><td>
                <td><br/></td>
            </tr>
    </table>
    </form> 
            <form name="fm2" method="post" action="${actionUrl}"> 
                <table width="100%">
                <tr>
                 <td width="20%"></td>
                 <td><label style="margin-left: 206px;" > <spring:message code="title-all-users-from-group-selected" /></label></td>
                </tr>
                <tr>
                    <td></td>
                    <td> <select multiple="true" class="searchable" name="searchable" id="searchable" >
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
                        </select> 
                        <a href='#' id='select-all'><spring:message code="btn-select-all" /></a> 
                        <a href='#' style="margin-left: 100px;" id='deselect-all'><spring:message code="btn-deselect-all" /></a>
                    </td>
                </tr>
        </table> 
    

    <br/>
    <spring:message code="common-btn-activate" var="submitText"/>
    <input class="btnl"  type="submit" value="${submitText}" onclick="return val();"/>  
</form>  
</div>  
<script type="text/javascript">
    //
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