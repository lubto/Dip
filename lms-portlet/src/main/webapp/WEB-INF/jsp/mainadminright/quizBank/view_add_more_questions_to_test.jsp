<%@page import="java.util.Iterator"%>
<%@page import="com.liferay.portal.model.User"%>
<%@page import="java.util.List"%>
<%@page import="eu.lms.iface.dto.MultipleChoiceDto"%>
<%@page import="com.liferay.portal.theme.ThemeDisplay"%>
<%@page import="com.liferay.portal.service.UserServiceUtil"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%> 
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../init.jspf" %>
<%@page import="static eu.lms.portlet.mainright.MainRightPortletConstants.*" %>
<%
    ThemeDisplay tDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
    User currentUser = UserServiceUtil.getUserById(tDisplay.getUserId());
    String idCurrentUser = String.valueOf(currentUser.getUserId());
%>
<portlet:actionURL var="addUrl" name="<%= ACTION_SAVE_ADD_Q_TO_TEST%>"> 
    <portlet:param name="<%= PARAM_ID%>" value="${entityId}" /> 
</portlet:actionURL>
<portlet:renderURL var="backUrl">
    <portlet:param name="<%= PARAM_PAGE%>" value="<%= PAGE_ALL_Q_BANK%>" /> 
    <portlet:param name="<%= PARAM_ID_USER%>" value="<%= idCurrentUser%>" /> 
    <portlet:param name="<%= ATTR_SEARCH%>" value=" " />
</portlet:renderURL>
<%
    List<MultipleChoiceDto> list = (List) request.getAttribute(ATTR_LIST_BANKS_Q);

%>
<div class="admin-right"> 

    <span class="sideMape"><spring:message code="side-map-queston" /> ><a href="${backUrl}" ><spring:message code="side-map-view-bank-q" /></a> > <spring:message code="common-btn-add" /></span>  
 

    <h3><spring:message code="map-addtobank" /></h3>
    <c:set var="ns"><portlet:namespace/></c:set>
    <liferay-ui:error key="ErrorKey" message="common-msg-err-form-contains-errors" />

    <form name="fm2" method="post" action="${addUrl}"> 
        <table width="100%">
            <tr>
                <td width="20%"></td>
                <td><spring:message code="choose-questions" /></td>
            </tr>
            <tr>
                <td></td>
                <td> <select multiple="true" class="searchable" name="searchable" id="searchable" >
                        <% if (list != null) {
                                for (Iterator<MultipleChoiceDto> i = list.iterator(); i.hasNext();) {
                                    MultipleChoiceDto item = i.next();
                                    String name = item.getQuestionShort();
                                    Long id = item.getId();
                        %><option value="<c:out value="<%= id%>" />"> <c:out value="<%= name%>" /></option> 
                        <%}
                        } else {%> <%}%> 
                    </select> 
                    <a href='#' id='select-all'><spring:message code="btn-select-all" /></a>
                    <a href='#' id='deselect-all' style="margin-left: 100px;"><spring:message code="btn-deselect-all" /></a>
                </td>
            </tr>
        </table>  
        <br/>
       <button class="btnl" onclick="window.location = '${backUrl}';
            return false;"><spring:message code="common-btn-cancel" /></button>  
         <spring:message code="common-btn-add" var="submitText"/>
        <c:if test="${isNull == false}">
            <input class="btnl" type="submit" value="${submitText}" onclick="return val();"/>  
        </c:if>
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