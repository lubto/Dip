package eu.lms.portlet.mainright;

import com.google.gson.Gson;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.service.RoleServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.sun.org.apache.bcel.internal.generic.ARRAYLENGTH;
import com.sun.org.omg.CORBA.AttributeMode;
import eu.lms.core.entity.BankQuestions;
import eu.lms.iface.dto.AccountUserDto;
import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.dto.CourseDto;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.ItemDocumentDto;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.LessonDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.dto.UserBankQDto;
import eu.lms.iface.dto.UserDto;
import eu.lms.iface.service.AccountUserService;
import eu.lms.iface.service.BankQuestionsService;
import eu.lms.iface.service.CategoryService;
import eu.lms.iface.service.CourseItemService;
import eu.lms.iface.service.CourseService;
import eu.lms.iface.service.GroupDocumentsService;
import eu.lms.iface.service.HelperService;
import eu.lms.iface.service.ItemDocumentService;
import eu.lms.iface.service.ItemMultipleChoiceService;
import eu.lms.iface.service.LessonService;
import eu.lms.iface.service.MultipleChoiceService;
import eu.lms.portlet.util.JodaDateEditor;
import static eu.lms.portlet.mainright.MainRightPortletConstants.*;
import eu.lms.portlet.mainright.pto.BankQuestionsPto;
import eu.lms.portlet.mainright.pto.CategoryPto;
import eu.lms.portlet.mainright.pto.CourseItemPto;
import eu.lms.portlet.mainright.pto.CoursePto;
import eu.lms.portlet.mainright.pto.DtoToPtoConverter;
import eu.lms.portlet.mainright.pto.GroupDocPto;
import eu.lms.portlet.mainright.pto.ItemDocumentPto;
import eu.lms.portlet.mainright.pto.ItemMatchingPto;
import eu.lms.portlet.mainright.pto.ItemMultiplechoicePto;
import eu.lms.portlet.mainright.pto.LessonPto;
import eu.lms.portlet.mainright.pto.MatchingPto;
import eu.lms.portlet.mainright.pto.MultipleChoicePto;
import static eu.lms.portlet.userright.MainCardRightPortletConstants.ACTION_UPLOAD_DMS;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.PortletException;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 *
 * @author Lubomir Lacika This class is base controller for VIEW mode.
 *
 */
@Controller
@RequestMapping("VIEW")
public class MainRightPortletViewController {

    protected final Logger LOG = Logger.getLogger(MainRightPortletConstants.class);

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(
                dateFormat, false));
    }

    @Autowired
    private CategoryService serviceCategory;

    @Autowired
    private CourseService serviceCourse;

    @Autowired
    private LessonService serviceLesson;

    @Autowired
    private CourseItemService serviceCourseItem;

    @Autowired
    private AccountUserService serviceAccountUser;

    @Autowired
    private ItemMultipleChoiceService serviceItemMCH;

    @Autowired
    private MultipleChoiceService serviceMultipleCH;

    @Autowired
    private BankQuestionsService serviceBankQuestions;

    @Autowired
    private GroupDocumentsService serviceGroupDoc;

    @Autowired
    private ItemDocumentService serviceItemDocuments;

    @Autowired
    private DtoToPtoConverter dtoToPtoConverter;

    @Autowired
    private HelperService serviceHelper;

    @RenderMapping
    public String doView(Model model) {
        if (!model.containsAttribute(ATTR_COURSE_PTO)) {
            model.addAttribute(ATTR_COURSE_PTO, new CoursePto());
            model.addAttribute(ATTR_CATEGORY_PTO, serviceCategory.getAllCategory());
        }
        return VIEW_CREATE_FORM_COURSE;
        //return MAIN_VIEW;
    }

    @EventMapping(value = "{http://liferay.com}admininfo")
    public void detailMyFinishCourses(
            EventRequest req,
            EventResponse res) throws PortalException, SystemException {

        Event event = req.getEvent();
        String value = (String) event.getValue();

        ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
        User currentUser = UserServiceUtil.getUserById(themeDisplay.getUserId());
        String id = String.valueOf(currentUser.getUserId());

        if (value.equals(SEND_ALL_CATEGORY)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_ALL_CATEGORY);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_CREATE_CATEGORY)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_CREATE_FORM_CATEGORY);
        } else if (value.equals(SEND_ALL_COURSE)) {
            res.setRenderParameter(PARAM_ID_USER, id);
            res.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_CREATE_COURSE)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_CREATE_FORM_COURSE);
        } else if (value.equals(SEND_SETUP_GROUP)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_SETUP_GROUP);
        } else if (value.equals(SEND_ALL_USERS)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_ALL_USERS);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_ALL_Q_BANK)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
            res.setRenderParameter(PARAM_ID_USER, id);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_ALL_Q)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q);
            res.setRenderParameter(PARAM_ID_USER, id);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_CREATE_Q)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_CREATE_Q);
        } else {
            LOG.info("Nothing choose from left bar");
            res.setRenderParameter(PARAM_PAGE, "");
        }
    }

    /* START CATEGORY */
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_CATEGORY)
    public String viewAllCategory(@RequestParam(ATTR_SEARCH) String searchAttr,
            Model model) {

        List<CategoryDto> list = serviceCategory.getAllCategory();
        if (list != null || !list.isEmpty()) {
            Collections.sort(list);
        }
        if (searchAttr
                == null || searchAttr.isEmpty()) {
            model.addAttribute(ATTR_ALL_ITEMS, list);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, serviceCategory.searchInList(searchAttr, list));
        }
        model.addAttribute(SEARCH_ATTR, searchAttr);

        return VIEW_ALL_CATEGORY;
    }

    @ActionMapping(ACTION_SEARCH_CATEGORY)
    public void searchAccount(
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_CATEGORY);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_COURSE_BY_CATEGORY)
    public String viewAllCourseByCategory(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        CategoryDto dto = serviceCategory.getCategoryById(id);
        model.addAttribute(ATTR_ALL_ITEMS, serviceCourse.getAllCourseByCategory(dto));
        model.addAttribute(SEARCH_ATTR, "");

        return VIEW_ALL_COURSE;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CREATE_FORM_CATEGORY)
    public String createFormCategory(Model model) {
        if (!model.containsAttribute(ATTR_CATEGORY_PTO)) {
            model.addAttribute(ATTR_CATEGORY_PTO, new CategoryPto());
        }
        return VIEW_CREATE_FORM_CATEGORY;
    }

    @ActionMapping(ACTION_CREATE_CATEGORY)
    public void createCategory(
            @Valid
            @ModelAttribute(ATTR_CATEGORY_PTO) CategoryPto pto,
            BindingResult result, ActionRequest request, ActionResponse response) {
        if (!result.hasErrors()) {
            CategoryDto dto = new CategoryDto(null, pto.getName(), pto.getDescription(), pto.getVisible(), 0, pto.getFreeCourse());
            serviceCategory.createCategory(dto);
            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(ATTR_SEARCH, "");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_CATEGORY);
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_FORM_CATEGORY);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_CATEGORY)
    public String detailCategory(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute("categoryPto", serviceCategory.getCategoryById(id));

        return VIEW_DETAIL_CATEGORY;
    }

    @ActionMapping(ACTION_SAVE_CATEGORY)
    public void saveCategory(
            @Valid @ModelAttribute(ATTR_CATEGORY_PTO) CategoryPto pto,
            BindingResult result, ActionRequest request,
            ActionResponse response) {
        if (!result.hasErrors()) {
            CategoryDto dto = serviceCategory.getCategoryById(pto.getId());
            dto.setName(pto.getName());
            dto.setDescription(pto.getDescription());
            dto.setVisible(pto.getVisible());
            dto.setFreeCourse(pto.getFreeCourse());
            serviceCategory.saveCategory(dto);
            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_DETAIL_CATEGORY);
            response.setRenderParameter(ATTR_SEARCH, "");
            response.setRenderParameter(PARAM_ID, pto.getId().toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_ID, pto.getId().toString());
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_FORM_CATEGORY);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DELETE_QUESTION_CATEGORY)
    public String deleteQuestion(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_CATEGORY_PTO, serviceCategory.getCategoryById(id));

        return VIEW_DELETE_QUESTION_CATEGORY;
    }

    @ActionMapping(ACTION_DELETE_CATEGORY)
    public void deleteCategory(
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response) {
        List<CourseDto> dto = serviceCourse.getAllCourseByCategory(serviceCategory.getCategoryById(id));
        if (dto.isEmpty() || dto == null) {
            serviceCategory.deleteCategoryById(id);
            SessionMessages.add(request, "SuccessKeyDeleted");
        } else {
            SessionErrors.add(request, "ErrorKeyFull");
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_CATEGORY);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_FORM_CATEGORY)
    public String editFormCategory(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        if (!model.containsAttribute(ATTR_CATEGORY_PTO)) {
            model.addAttribute(ATTR_CATEGORY_PTO, dtoToPtoConverter.convert(serviceCategory.getCategoryById(id)));
            CategoryDto dto = serviceCategory.getCategoryById(id);
            boolean permission = dto.isVisible();
            boolean permission1 = dto.isFreeCourse();
            model.addAttribute("checkbox", permission);
            model.addAttribute("checkFree", permission1);
        }
        return VIEW_EDIT_FORM_CATEGORY;
    }

    @ActionMapping(ACTION_DE_ACTIVATE_CATEGORY)
    public void actiDeactivateCategory(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM) int number,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        CategoryDto dto = new CategoryDto();
        try {
            if (number == 0) {
                serviceCategory.changeActivate(false, id);
                SessionMessages.add(request, "KeyDeactivated");
            } else {
                serviceCategory.changeActivate(true, id);
                SessionMessages.add(request, "KeyActivated");
            }
        } catch (Exception e) {
            SessionErrors.add(request, "ErrorKeyDeleted");
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_CATEGORY);
        response.setRenderParameter(ATTR_SEARCH, "");
    }
    /* END CATEGORY */

    /* START COURSE */
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_COURSE)
    public String showCourse(@RequestParam(ATTR_SEARCH) String searchAttr,
            @RequestParam(PARAM_ID_USER) Long userId,
            Model model) throws PortalException, SystemException {
        LOG.info("user id" + userId);
        User currentUser = UserServiceUtil.getUserById(Long.valueOf(userId));
        List<CourseDto> list = serviceCourse.getAllCourse();
        if (list != null || !list.isEmpty()) {
            Collections.sort(list);
        }
        if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_ADMINISTRATORL_LMS, true)) {
            if (searchAttr == null || searchAttr.isEmpty()) {
                model.addAttribute(ATTR_ALL_ITEMS, list);
            } else {
                model.addAttribute(ATTR_ALL_ITEMS, serviceCourse.searchInList(searchAttr, list));
            }
        } else if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_LEKTOR, true)) {
            List<CourseDto> listLektor = serviceCourse.getAllCourseByUserId(String.valueOf(currentUser.getUserId()));
            if (searchAttr == null || searchAttr.isEmpty()) {
                model.addAttribute(ATTR_ALL_ITEMS, listLektor);
            } else {
                model.addAttribute(ATTR_ALL_ITEMS, serviceCourse.searchInList(searchAttr, listLektor));
            }
        }
        model.addAttribute(SEARCH_ATTR, searchAttr);
        return VIEW_ALL_COURSE;
    }

    @ActionMapping(ACTION_SEARCH_COURSE)
    public void searchCourse(ActionRequest request, ActionResponse response, Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        String userId = request.getRemoteUser();
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_ID_USER, userId);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CREATE_FORM_COURSE)
    public String createFormCourse(Model model) {
        if (!model.containsAttribute(ATTR_COURSE_PTO)) {
            model.addAttribute(ATTR_COURSE_PTO, new CoursePto());
        }
        List<CategoryDto> list = new ArrayList<CategoryDto>();
        list = serviceCategory.getAllCategory();

        boolean isNull = false;
        if (list == null || list.size() == 0) {
            isNull = true;
        }
        model.addAttribute(ATTR_CATEGORY_PTO, list);
        model.addAttribute(ATTR_IS_NULL, isNull);

        return VIEW_CREATE_FORM_COURSE;
    }

    @ActionMapping(ACTION_CREATE_COURSE)
    public void createCourse(
            @Valid @ModelAttribute(ATTR_COURSE_PTO) CoursePto pto,
            BindingResult result, ActionRequest request,
            ActionResponse response) {

        if (!result.hasErrors()) {
            CategoryDto dtoC = new CategoryDto();
            dtoC = serviceCategory.getCategoryById(pto.getCategory());

            long timeNow = System.currentTimeMillis();
            Date date = new Date(timeNow);
            String creatorUserId = request.getRemoteUser();
            boolean visible = pto.isVisible();
            CourseDto dto = new CourseDto(null, dtoC, pto.getName(), pto.getShortName(), pto.getDescription(), creatorUserId, visible, null, date, null, null, false, false, false, false);
            dto.setVisibleDocuments((pto.getVisibleDocuments() == true) ? true : false);
            dto.setVisibleQuestions((pto.getVisibleQuestions() == true) ? true : false);
            dto.setVisibleLessons((pto.getVisibleLessons() == true) ? true : false);

            serviceCourse.createCourse(dto);

            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_FORM_COURSE);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_COURSE)
    public String detailCourse(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_COURSE_PTO, serviceCourse.getCourseById(id));
        return VIEW_DETAIL_COURSE;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DELETE_QUESTION_COURSE)
    public String deleteQuestionCourse(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_COURSE_PTO, serviceCourse.getCourseById(id));
        model.addAttribute(ATTR_SEARCH, "");
        return VIEW_DELETE_QUESTION_COURSE;
    }

    @ActionMapping(ACTION_DELETE_COURSE)
    public void deleteCourse(
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request, ActionResponse response) {
        CourseDto dto = serviceCourse.getCourseById(id);
        String creatorUserId = request.getRemoteUser();
        boolean isEmptyCourse = false;
        isEmptyCourse = serviceCourse.isEmptyCourse(dto);

        if (isEmptyCourse == true) {
            LOG.info("controller " + isEmptyCourse);
            List<CourseItemDto> listDto = new ArrayList<CourseItemDto>();
            listDto = serviceCourseItem.getAllCourseItemByCourse(id);
            if (listDto != null) {
                for (Iterator<CourseItemDto> it = listDto.iterator(); it.hasNext();) {
                    CourseItemDto courseItemDto = it.next();
                    serviceAccountUser.removeCourseItem(courseItemDto, courseItemDto.getAccountUser().getId());
                }
            }
            serviceCourse.deleteCourseById(id);

            SessionMessages.add(request, "SuccessKeyDeleted");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {

            LOG.info("controller " + isEmptyCourse);
            SessionErrors.add(request, "KeyFullCourse");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
        }

    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_FORM_COURSE)
    public String editFormCourse(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        if (!model.containsAttribute(ATTR_COURSE_PTO)) {
            model.addAttribute(ATTR_COURSE_PTO, dtoToPtoConverter.convert(serviceCourse.getCourseById(id)));
            CourseDto dto = serviceCourse.getCourseById(id);
            boolean permission = dto.isVisible();
            boolean permissionQ = dto.isVisibleQuestions();
            boolean permissionD = dto.isVisibleDocuments();
            boolean permissionL = dto.isVisibleLessons();
            model.addAttribute("checkboxQ", permissionQ);
            model.addAttribute("checkboxD", permissionD);
            model.addAttribute("checkboxL", permissionL);
            model.addAttribute("checkbox", permission);
            model.addAttribute(ATTR_CATEGORY_PTO, serviceCategory.getAllCategory());
        }
        return VIEW_EDIT_FORM_COURSE;
    }

    @ActionMapping(ACTION_SAVE_COURSE)
    public void saveCourse(
            @Valid
            @ModelAttribute(ATTR_COURSE_PTO) CoursePto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) {

        if (!result.hasErrors()) {
            CourseDto dto = serviceCourse.getCourseById(pto.getId());
            dto.setName(pto.getName());
            dto.setShortName(pto.getShortName());
            dto.setDescription(pto.getDescription());
            dto.setDateOfCreation(dto.getDateOfCreation());
            if (pto.isVisible() == true) {
                dto.setVisible(true);
            } else {
                dto.setVisible(false);
            }
            dto.setVisibleDocuments((pto.getVisibleDocuments() == true) ? true : false);
            dto.setVisibleQuestions((pto.getVisibleQuestions() == true) ? true : false);
            dto.setVisibleLessons((pto.getVisibleLessons() == true) ? true : false);
            CategoryDto dtoC = serviceCategory.getCategoryById(pto.getCategory());
            serviceCourse.changeStateCategory(pto, dto, dtoC);
            serviceCourse.saveCourse(dto);

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_DETAIL_COURSE);
            response.setRenderParameter(PARAM_ID, dto.getId().toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_ID, pto.getId().toString());
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_FORM_COURSE);
        }
    }

    @ActionMapping(ACTION_DE_ACTIVATE_COURSE)
    public void actiDeactivateCourse(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM) int number,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String creatorUserId = request.getRemoteUser();
        CourseDto dto = new CourseDto();
        try {
            if (number == 0) {
                serviceCourse.changeActivate(false, id);
                SessionMessages.add(request, "KeyDeactivated");
            } else {
                serviceCourse.changeActivate(true, id);
                SessionMessages.add(request, "KeyActivated");
            }
        } catch (Exception e) {
            SessionErrors.add(request, "ErrorKeyDeleted");
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_CATEGORY_IN_COURSE)
    public String detailCategoryInCourse(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute("categoryPto", serviceCategory.getCategoryById(id));

        return VIEW_DETAIL_CATEGORY_IN_COURSE;
    }

    //settings courses
//     @RenderMapping(params = PARAM_PAGE + "=" + PAGE_SETTINGS_COURSE)
//    public String editFormCourseSettings(
//            @RequestParam(PARAM_ID) Long id,
//            Model model) {
//        if (!model.containsAttribute(ATTR_COURSE_PTO)) {
//            model.addAttribute(ATTR_COURSE_PTO, dtoToPtoConverter.convert(serviceCourse.getCourseById(id)));
//            CourseDto dto = serviceCourse.getCourseById(id);
//            boolean permissionQ = dto.isVisibleQuestions();
//            boolean permissionD = dto.isVisibleDocuments();
//            boolean permissionL = dto.isVisibleLessons();
//            model.addAttribute("checkboxQ", permissionQ);
//            model.addAttribute("checkboxD", permissionD);
//            model.addAttribute("checkboxL", permissionL); 
//        }
//        return VIEW_EDIT_FORM_COURSE_SETTINGS;
//    }
//
//    @ActionMapping(ACTION_SETTINGS_COURSE)
//    public void saveCourseSettings(
//            @Valid
//            @ModelAttribute(ATTR_COURSE_PTO) CoursePto pto,
//            BindingResult result,
//            ActionRequest request,
//            ActionResponse response) {
//
//        if (!result.hasErrors()) {
//            CourseDto dto = serviceCourse.getCourseById(pto.getId());
//            dto.setVisibleDocuments(pto.getName());
//            dto.setShortName(pto.getShortName());
//            dto.setDescription(pto.getDescription());
//            dto.setDateOfCreation(dto.getDateOfCreation());
//            if (pto.isVisible() == true) {
//                dto.setVisible(true);
//            } else {
//                dto.setVisible(false);
//            }
//            CategoryDto dtoC = serviceCategory.getCategoryById(pto.getCategory());
//            serviceCourse.changeStateCategory(pto, dto, dtoC);
//            serviceCourse.saveCourse(dto);
//
//            SessionMessages.add(request, "SuccessKeySettings"); //dodat do course view 
//            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
//            response.setRenderParameter(PARAM_ID, dto.getId().toString());
//        } else {
//            SessionErrors.add(request, "ErrorKey");
//            response.setRenderParameter(PARAM_ID, pto.getId().toString());
//            response.setRenderParameter(PARAM_PAGE, PAGE_SETTINGS_COURSE);
//        }
//    }
    /* END course */

    /* START lesson */
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_LESSON)
    public String viewLesson(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        CourseDto courseDto = serviceCourse.getCourseById(id);
        boolean isNull = false;
        if (courseDto.getLessonDto() == null || courseDto.getLessonDto().size() == 0) {
            isNull = true;
        }
        List<LessonDto> list = new ArrayList<LessonDto>();
        list = courseDto.getLessonDto();
        if (list != null) {
            Collections.sort(list);
            model.addAttribute(ATTR_ALL_ITEMS, list);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, list);
        }
        model.addAttribute(PARAM_ID_COURSE, id);
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        model.addAttribute(ATTR_IS_NULL, isNull);

        return VIEW_ALL_LESSON;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CREATE_FORM_LESSON)
    public String createFormLesson(
            @RequestParam(PARAM_ID_COURSE) Long id,
            Model model) {
        if (!model.containsAttribute(ATTR_LESSON_PTO)) {
            CourseDto dtoC = serviceCourse.getCourseById(id);

            model.addAttribute(ATTR_LESSON_PTO, new LessonPto());
            model.addAttribute(PARAM_ID_COURSE, id);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        }
        return VIEW_CREATE_FORM_LESSON;
    }

    @ActionMapping(ACTION_CREATE_LESSON)
    public void createLesson(
            @RequestParam(PARAM_ID_COURSE) Long id,
            @Valid
            @ModelAttribute(ATTR_LESSON_PTO) LessonPto pto,
            BindingResult result,
            Model model,
            ActionRequest request,
            ActionResponse response) {
        if (!result.hasErrors()) {
            CourseDto dtoC = new CourseDto();
            dtoC = serviceCourse.getCourseById(id);

            List<LessonDto> listLesson = new ArrayList<LessonDto>();
            listLesson = dtoC.getLessonDto();

            int serialNumber = 1;
            if (listLesson != null) {
                if (listLesson.size() != 0) {
                    serialNumber = listLesson.size() + 1;
                }
            }
            String getValueFromEditor = ParamUtil.getString(request, "htmlCodeFromEditorPlacedHere");
            LessonDto lesson = new LessonDto(null, dtoC, pto.getName(), getValueFromEditor, pto.getStudyTime(), true, serialNumber);
            serviceCourse.addLesson(lesson, dtoC.getId());

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_LESSON);
            response.setRenderParameter(PARAM_ID, dtoC.getId().toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_FORM_LESSON);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_LESSON)
    public String detailLesson(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        CourseDto dtoC = serviceCourse.getCourseById(idCourse);

        model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        model.addAttribute(ATTR_LESSON_PTO, serviceLesson.getById(id));
        model.addAttribute(PARAM_ID_COURSE, idCourse);

        return VIEW_DETAIL_LESSON;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DELETE_QUESTION_LESSON)
    public String deleteQuestionLesson(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_LESSON_PTO, serviceLesson.getById(id));

        return VIEW_DELETE_QUESTION_LESSON;
    }

    @ActionMapping(ACTION_DELETE_LESSON)
    public void deleteLesson(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        LessonDto dto = serviceLesson.deleteById(id);
        SessionMessages.add(request, "SuccessKeyDeleted", new String[]{dto.getName()});
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_LESSON);
        response.setRenderParameter(PARAM_ID, idCourse.toString());
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_FORM_LESSON)
    public String editFormLesson(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        if (!model.containsAttribute(ATTR_LESSON_PTO)) {
            CourseDto dtoC = serviceCourse.getCourseById(idCourse);
            model.addAttribute(ATTR_LESSON_PTO, dtoToPtoConverter.convert(serviceLesson.getById(id)));
            LessonDto dto = serviceLesson.getById(id);
            boolean permission = dto.isVisible();
            LessonDto dtos = serviceLesson.getById(id);

            model.addAttribute("checkbox", permission);
            model.addAttribute(ATTR_FOR_EDITOR, dtos.getContent());
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        }
        return VIEW_EDIT_FORM_LESSON;
    }

    @ActionMapping(ACTION_SAVE_LESSON)
    public void saveLesson(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @Valid
            @ModelAttribute(ATTR_LESSON_PTO) LessonPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response,
            Model model) {

        if (!result.hasErrors()) {
            LessonDto dto = serviceLesson.getById(pto.getId());
            String getValueFromEditor = ParamUtil.getString(request, "htmlCodeFromEditorPlacedHere");
            LOG.info("info about serialNumber" + pto.getSerialNumber());
            dto.setName(pto.getName());
            dto.setStudyTime(pto.getStudyTime());
            //dto.setSerialNumber(pto.getSerialNumber());
            dto.setContent(getValueFromEditor);
            if (pto.isVisible() == true) {
                dto.setVisible(true);
            } else {
                dto.setVisible(false);
            }
            serviceLesson.save(dto);
            SessionMessages.add(request, "SuccessKeySaved");
            //response.setRenderParameter(PARAM_PAGE, PAGE_DETAIL_LESSON);
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_LESSON);
            response.setRenderParameter(PARAM_ID, idCourse.toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_ID, pto.getId().toString());
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_FORM_LESSON);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        }
    }

    // zobrazi  jednotlive lekcie za sebou cez next tlacidlo, prva strana
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_LESSONS)
    public String allLesson(Model model,
            @RequestParam(PARAM_ID_COURSE) Long idCourse) {
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<LessonDto> listLessonDto = serviceCourse.getAllLesson(courseDto);

        for (int i = 0; i < listLessonDto.size(); i++) {
            if (listLessonDto.get(i).getSerialNumber() == 1) {
                model.addAttribute(ATTR_LESSON_PTO, listLessonDto.get(i));
                model.addAttribute(ATTR_LESSON_SERIAL_NUMBER, 1);
                model.addAttribute(ATTR_LESSON_SIZE, listLessonDto.size());
            }
            model.addAttribute(PARAM_ID_COURSE, idCourse);
        }
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        return VIEW_ALL_LESSON_NEXT;
    }

// zobrazi  jednotlive lekcie za sebou cez next tlacidlo 
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_LESSON_NEXT)
    public String allLessonNext(Model model,
            @RequestParam(PARAM_ID) int number,
            @RequestParam(PARAM_ID_COURSE) Long idCourse) {

        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<LessonDto> listLessonDto = serviceCourse.getAllLesson(courseDto);
        LOG.info("shotcut" + courseDto.getShortName());
        for (int i = 0; i < listLessonDto.size(); i++) {
            if (listLessonDto.get(i).getSerialNumber() == number) {
                model.addAttribute(ATTR_LESSON_PTO, listLessonDto.get(i));
                model.addAttribute(ATTR_LESSON_SERIAL_NUMBER, listLessonDto.get(i).getSerialNumber());
                model.addAttribute(ATTR_LESSON_SIZE, listLessonDto.size());
            }
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        }
        return VIEW_ALL_LESSON_NEXT;
    }

    @ActionMapping(ACTION_UP_LESSON)
    public void upLesson(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        LessonDto dto = serviceLesson.getById(id);
        serviceLesson.changePosition(true, dto);

        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_LESSON);
        response.setRenderParameter(PARAM_ID, idCourse.toString());
    }

    @ActionMapping(ACTION_DOWN_LESSON)
    public void downLesson(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        LessonDto dto = serviceLesson.getById(id);
        serviceLesson.changePosition(false, dto);

        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_LESSON);
        response.setRenderParameter(PARAM_ID, idCourse.toString());
    }
    /* END lesson */

    /* START AccountUser */
    @ActionMapping(ACTION_POKUS)
    public void saveCourseItems(
            @RequestParam("searchable") List<String> stringg,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        LOG.info("LOG ");
        Date date = new Date(System.currentTimeMillis());
        for (Iterator<String> i = stringg.iterator(); i.hasNext();) {
            String itemUserId = i.next();
            LOG.info("Log M : " + itemUserId);
            AccountUserDto dto = serviceAccountUser.getAccountUserByUserId(itemUserId);
            if (dto == null) {
                AccountUserDto accontU = new AccountUserDto(null, itemUserId, date, null, true, "");
                serviceAccountUser.createAccounUserDto(accontU);
            }
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
    }

    @ActionMapping(ACTION_SHOW_USERGROUP)
    public void showUserGroup(
            @RequestParam(ATTR_SELECT_GROUP) Long selectGroup,
            ActionRequest request,
            ActionResponse response,
            Model model) throws PortalException, SystemException {
        Long companyId = CompanyThreadLocal.getCompanyId();
        if (selectGroup != null) {
            response.setRenderParameter("idCompany", companyId.toString());
            response.setRenderParameter("idGroup", selectGroup.toString());
            response.setRenderParameter(PARAM_PAGE, PAEGE_SHOW_USERGROUP);
        } else {
            response.setRenderParameter(PARAM_PAGE, MAIN_VIEW);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAEGE_SHOW_USERGROUP)
    public String setupGroup1(
            @RequestParam("idCompany") Long idCompany,
            @RequestParam("idGroup") Long idGroup,
            Model model) throws SystemException, PortalException {

        List<User> users = UserLocalServiceUtil.getUserGroupUsers(idGroup);
        List<UserDto> listAllUser = new ArrayList<UserDto>();

        for (Iterator<User> i = users.iterator(); i.hasNext();) {
            UserDto userDto = new UserDto();
            User item = i.next();
            LOG.info("name " + item.getFullName());
            LOG.info("name id" + item.getUserId());
            LOG.info("groupIds" + item.getGroupIds());
            LOG.info("groupId " + item.getGroupId());
            userDto.setId(item.getUserId());
            userDto.setFullName(item.getFullName());
            listAllUser.add(userDto);
        }
        List<UserGroup> userGroup = UserGroupLocalServiceUtil.getUserGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);

        //List users kt. maju vytvoreny uz AccountUser
        List<String> userIdFromAccountUser = new ArrayList<String>();
        List<UserDto> listUserAccount = new ArrayList<UserDto>();

        userIdFromAccountUser = serviceAccountUser.getAllUserIdFromAccounUser();
        if (!userIdFromAccountUser.isEmpty()) {
            for (String item : userIdFromAccountUser) {
                for (Iterator<UserDto> it = listAllUser.iterator(); it.hasNext();) {
                    UserDto u = it.next();
                    int isNull = (u.getId().compareTo(Long.valueOf(item)));
                    if (isNull == 0) {
                        listUserAccount.add(u);
                        it.remove();
                    }
                }
            }
        }
        model.addAttribute(ATTR_GROUP_LIST, userGroup);
        model.addAttribute(ATTR_USERS_LIST, listAllUser);  //List all user from group what dont have AccountUser
        model.addAttribute(ATTR_USERS_LIST_SELECTED, listUserAccount);  //List all user what have created AccountUser

        return VIEW_SETUP_GROUP;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_SETUP_GROUP)
    public String setupGroup(Model model) throws SystemException {
        List<UserGroup> userGroup = UserGroupLocalServiceUtil.getUserGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
        model.addAttribute(ATTR_GROUP_LIST, userGroup);

        return VIEW_SETUP_GROUP;
    }

    @ActionMapping(ACTION_CREATE_ACCOUNTITEM_USER)
    public void createAccountItemUser(
            @RequestParam("searchable") List<String> idUser,
            ActionRequest request,
            ActionResponse response,
            Model model) throws SystemException {
        int size = idUser.size();
        for (Iterator<String> i = idUser.iterator(); i.hasNext();) {
            String itemUserId = i.next();
            Date date = new Date(System.currentTimeMillis());
            AccountUserDto account = new AccountUserDto(null, itemUserId, date, null, true, "");
            serviceAccountUser.createAccounUserDto(account);
        }
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_USERS);
        if (size == 1) {
            SessionMessages.add(request, "SuccessKey");
        } else {
            SessionMessages.add(request, "SuccessKeys");
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_USERS)
    public String allAccountUsers(@RequestParam(ATTR_SEARCH) String searchAttr,
            Model model) {

        List<AccountUserDto> list = serviceAccountUser.getAllAccountUser();
        if (searchAttr == null || searchAttr.isEmpty()) {
            model.addAttribute(ATTR_ALL_ITEMS, list);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, serviceAccountUser.searchInList(searchAttr, list));
        }
        model.addAttribute(SEARCH_ATTR, searchAttr);

        return VIEW_ALL_ACCOUNT_USER;
    }

    @ActionMapping(ACTION_SEARCH_ACCOUNTS)
    public void searchUser(
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        LOG.info("ok " + searchRequest);
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_USERS);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ITEMS_USER)
    public String allItemsUsers(@RequestParam(ATTR_SEARCH) String searchAttr,
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        AccountUserDto dto = serviceAccountUser.getAccountUserId(id);

        List<CourseItemDto> list = new ArrayList<CourseItemDto>();
        list = dto.getListCourses();
        if (list != null) {
            for (CourseItemDto course : list) {
                course.setNameCourse(serviceCourse.getCourseById(Long.valueOf(course.getCourseId())).getName());
            }
        }
        if (searchAttr == null || searchAttr.isEmpty()) {
            model.addAttribute(ATTR_ALL_ITEMS, list);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, serviceCourseItem.searchInListByCourse(searchAttr, list));
        }
        boolean isNull = false;
        if (list == null || list.isEmpty()) {
            isNull = true;
        }

        model.addAttribute(ATTR_IS_NULL, isNull);
        model.addAttribute(SEARCH_ATTR, searchAttr);
        model.addAttribute(PARAM_ID_ACCOUNT, id);
        model.addAttribute(PARAM_ID, id);
        model.addAttribute(ATTR_SHORTCUT_NAME, dto.getUserName());

        return VIEW_COURSE_ITEMS;
    }

    @ActionMapping(ACTION_SEARCH_COURSE_ITEM_ACCOUNT)
    public void searchCourseItemAccount(@RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        LOG.info("OK");
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_ITEMS_USER);
        response.setRenderParameter(PARAM_ID, id.toString());
    }

    @ActionMapping(ACTION_DE_ACTIVATE_ITEM_COURSE)
    public void actiDeactivateCourseItem(
            @RequestParam(PARAM_ID_ACCOUNT) String idAccount,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM) int number,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        CourseItemDto dto = new CourseItemDto();
        try {
            if (number == 0) {
                serviceCourseItem.changeActivate(false, id);
                SessionMessages.add(request, "KeyDeactivated");
            } else {
                serviceCourseItem.changeActivate(true, id);
                SessionMessages.add(request, "KeyActivated");
            }
        } catch (Exception e) {
            SessionErrors.add(request, "ErrorKeyDeleted", new String[]{dto.getUserId()});
        }
        response.setRenderParameter(PARAM_ID, idAccount);
        response.setRenderParameter(PARAM_PAGE, PAGE_ITEMS_USER);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_FORM_COURSE_ITEM_I)
    public String editFormCourseItemAcount(
            @RequestParam(PARAM_ID_ACCOUNT) Long idAccount,
            @RequestParam(PARAM_ID) Long id,
            Model model) throws PortalException, SystemException {
        if (!model.containsAttribute(ATTR_COURSE_ITEM_PTO)) {
            LOG.info("LOG start");
            CourseItemDto dto = serviceCourseItem.getCourseItemById(id);
            CourseDto dtoC = serviceCourse.getCourseById(Long.valueOf(dto.getCourseId()));
            dto.setNameCourse(dtoC.getName());
            User user = UserLocalServiceUtil.getUser(Long.valueOf(dto.getUserId()));
            dto.setFullName(user.getFullName());
            model.addAttribute(ATTR_COURSE_ITEM_PTO, dtoToPtoConverter.convert(dto));
            boolean permission = dto.getVisible();
            boolean permission1 = dto.isFinishedCourse();
            boolean permission2 = dto.isVisitedCourse();
            AccountUserDto dtoA = serviceAccountUser.getAccountUserId(idAccount);

            List<CourseItemDto> list = new ArrayList<CourseItemDto>();
            list = dtoA.getListCourses();
            if (list != null) {
                for (CourseItemDto course : list) {
                    course.setNameCourse(serviceCourse.getCourseById(Long.valueOf(course.getCourseId())).getName());
                }
            }
            LOG.info("LOG end");
            model.addAttribute(ATTR_SHORTCUT_NAME, dto.getFullName());
            model.addAttribute("checkbox", permission);
            model.addAttribute("chVisitedCourse", permission2);
            model.addAttribute("chFinishedCourse", permission1);
            model.addAttribute(PARAM_ID_ACCOUNT, idAccount);
        }
        return VIEW_EDIT_COURSE_ITEM_I;
    }

    @ActionMapping(ACTION_SAVE_COURSE_ITEM_I)
    public void saveCourseItemI(
            @RequestParam(PARAM_ID_ACCOUNT) Long idAccount,
            @Valid
            @ModelAttribute(ATTR_COURSE_ITEM_PTO) CourseItemPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response,
            Model model) {

        if (!result.hasErrors()) {
            CourseItemDto dto = serviceCourseItem.getCourseItemById(pto.getId());

            dto.setFinishedCourse(pto.getFinishedCourse());
            dto.setVisible(pto.getVisible());
            dto.setVisitedCourse(pto.getVisitedCourse());
            dto.setEndCourse(pto.getEndCourse());

            serviceCourseItem.saveCourseItem(dto);

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_ITEMS_USER);
            response.setRenderParameter(PARAM_ID, idAccount.toString());
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_FORM_COURSE_ITEM_I);
            response.setRenderParameter(PARAM_ID, idAccount.toString());
        }
    }

    @ActionMapping(ACTION_REMOVE_COURSE_ITEM_I)
    public void removeCourseItemI(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_ACCOUNT) Long idAccount,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        CourseItemDto dto = serviceCourseItem.getCourseItemById(id);
        serviceAccountUser.removeCourseItem(dto, dto.getAccountUser().getId());

        SessionMessages.add(request, "KeyRemoved");
        response.setRenderParameter(PARAM_PAGE, PAGE_ITEMS_USER);
        response.setRenderParameter(PARAM_ID, idAccount.toString());
        response.setRenderParameter(ATTR_SEARCH, "");
    }
    /* END AccountUser */

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DELETE_QUESTION_ACCOUNTUSER)
    public String deleteQuestionAccountUser(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_ACCOUNT_USER_PTO, serviceAccountUser.getAccountUserId(id));

        return VIEW_DELETE_QUESTION_LESSON;
    }

    @ActionMapping(ACTION_DELETE_ACCOUNTUSER)
    public void deleteAccountUser(
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        AccountUserDto dto = new AccountUserDto();
        try {
            serviceAccountUser.deleteAccountUserById(id);
            SessionMessages.add(request, "SuccessKeyDeleted", new String[]{dto.getUserId()});
        } catch (Exception e) {
            SessionErrors.add(request, "ErrorKeyDeleted", new String[]{dto.getUserId()});
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_USERS);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @ActionMapping(ACTION_DE_ACTIVATE_ACCOUN)
    public void actiDeactivateAccountUser(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM) int number,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        AccountUserDto dto = new AccountUserDto();
        try {
            if (number == 0) {
                serviceAccountUser.changeActivateAccountUser(false, id);
                SessionMessages.add(request, "KeyDeactivated");
            } else {
                serviceAccountUser.changeActivateAccountUser(true, id);
                SessionMessages.add(request, "KeyActivated");
            }
            //
        } catch (Exception e) {
            SessionMessages.add(request, "ErrorKeyDeleted", new String[]{dto.getUserId()});
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_USERS);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    /* Users in course */
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_VIEW_USERS_IN_COURSE)
    public String viewUsersInCourse(
            @RequestParam(ATTR_SEARCH) String searchAttr,
            @RequestParam(PARAM_ID) Long idCourse,
            Model model) throws PortalException, SystemException {
        LOG.info("search attr: " + searchAttr);
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<CourseItemDto> itemsCourse = new ArrayList<CourseItemDto>();
        itemsCourse = serviceCourseItem.getAllCourseItemByCourse(idCourse);
        itemsCourse = serviceCourseItem.checkTimeIsUpList(itemsCourse);
//        if (itemsCourse != null || !itemsCourse.isEmpty()) {
//            Collections.sort(itemsCourse);
//        }
        if (searchAttr == null || searchAttr.isEmpty()) {
            model.addAttribute(ATTR_ALL_ITEMS, itemsCourse);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, serviceCourseItem.searchInListByFullName(searchAttr, itemsCourse));
        }
        model.addAttribute(ATTR_SEARCH, searchAttr);
        model.addAttribute(PARAM_ID, idCourse);
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());

        return VIEW_USERS_IN_COURSE;
    }

    @ActionMapping(ACTION_SEARCH_COURSE_ITEM)
    public void searchCourseItem(
            @RequestParam(PARAM_ID) String courseId,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        LOG.info("ok 2" + searchRequest);
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_ID, courseId);
        response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_USERS_IN_COURSE);
    }

///////////////do service
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_COURSE_TO_USER)
    public String addCourseToUser(
            @RequestParam(PARAM_ID_COURSE) Long id,
            Model model) throws SystemException, PortalException {
        LOG.info("Log message: settings Add user to course ");

        List<String> userIdFromAccountUser = new ArrayList<String>();
        List<String> userIdFromCourseItem = new ArrayList<String>();

        List<UserDto> listAllUser = new ArrayList<UserDto>();
        List<UserDto> listUserCourseI = new ArrayList<UserDto>();

        userIdFromCourseItem = serviceCourseItem.getAllCourseItemByCourse(id.toString(), true); //ochranit ked nie je ziadny zaznam v tabulke aby nevyhodilao vynimku
        userIdFromAccountUser = serviceAccountUser.getAllUserIdFromAccounUser();

        if (!userIdFromAccountUser.isEmpty()) {
            for (Iterator<String> i = userIdFromAccountUser.iterator(); i.hasNext();) {
                String userId = i.next();
                User user = UserLocalServiceUtil.getUser(Long.valueOf(userId));
                LOG.info("log user: " + user.getFullName());
                UserDto dto = new UserDto();
                dto.setFullName(user.getFullName());
                dto.setId(user.getPrimaryKey());

                listAllUser.add(dto);
            }
        }
        for (String item : userIdFromCourseItem) {
            for (Iterator<UserDto> it = listAllUser.iterator(); it.hasNext();) {
                UserDto u = it.next();
                int isNull = (u.getId().compareTo(Long.valueOf(item)));
                if (isNull == 0) {
                    listUserCourseI.add(u);
                    it.remove();
                }
            }
        }
        model.addAttribute(PARAM_SHORTCUT_COURSE, serviceCourse.getCourseById(id).getShortName());
        model.addAttribute(PARAM_ID_COURSE, id);
        model.addAttribute(ATTR_USERS_LIST_SELECTED, listUserCourseI);
        model.addAttribute(ATTR_USERS_LIST, listAllUser);
        model.addAttribute(ATTR_SEARCH, "");

        return VIEW_ADD_COURSE_TO_USER;
    }
////////// do servicee

    @ActionMapping(ACTION_ADD_USERS_TO_COURSE)
    public void addUsersToCourse(
            @RequestParam("searchable") List<String> listUsers,
            @RequestParam(PARAM_ID_COURSE) String idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) throws PortalException, SystemException {
        if (!listUsers.isEmpty()) {

            for (Iterator<String> i = listUsers.iterator(); i.hasNext();) {
                String itemUserId = i.next();
                CourseDto dto = serviceCourse.getCourseById(Long.valueOf(idCourse));
                List<BankQuestionsDto> listBankQ = dto.getBankQuestionsDto();
                List<UserBankQDto> listUserBankQ = new ArrayList<UserBankQDto>();
                if (listBankQ != null) {
                    for (Iterator<BankQuestionsDto> it = listBankQ.iterator(); it.hasNext();) {
                        BankQuestionsDto bankQuestionsDto = it.next();
                        UserBankQDto userBankQ = new UserBankQDto(null, false, 0, null, String.valueOf(bankQuestionsDto.getId()), null, bankQuestionsDto.getSerialNumber());
                        listUserBankQ.add(userBankQ);
                    }
                }
                Date date = new Date(System.currentTimeMillis());
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);
                cal.add(Calendar.DATE, DAY_FOR_STUDING);
                Date endDate = cal.getTime();
                AccountUserDto accountDto = serviceAccountUser.getAccountUserByUserId(itemUserId);
                CourseItemDto courseI = new CourseItemDto(null, itemUserId, idCourse, accountDto, false, false, date, endDate, true, " ", false, " ", null, dto.isVisibleDocuments(), dto.isVisibleQuestions(), dto.isVisibleLessons());

                serviceAccountUser.addCourseItemToAccountUser(courseI, accountDto.getId(), listUserBankQ);
                if (listUsers.size() == 1) {
                    SessionMessages.add(request, "SuccessKey");
                } else {
                    SessionMessages.add(request, "SuccessKeys");
                }
            }
            response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_USERS_IN_COURSE);

        } else {
            response.setRenderParameter(PARAM_PAGE, PAGE_ADD_COURSE_TO_USER);
            SessionErrors.add(request, "ErrorKey");
        }
        response.setRenderParameter(PARAM_ID, idCourse);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @ActionMapping(ACTION_DE_ACTIVATE_COURSE_ITEM)
    public void actiDeactivateCourseItem(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM) int number,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        CourseItemDto dto = new CourseItemDto();
        try {
            if (number == 0) {
                serviceCourseItem.changeActivate(false, id);
                SessionMessages.add(request, "KeyDeactivated");
            } else {
                serviceCourseItem.changeActivate(true, id);
                SessionMessages.add(request, "KeyActivated");
            }
        } catch (Exception e) {
            SessionMessages.add(request, "ErrorKeyDeleted", new String[]{dto.getUserId()});
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_USERS_IN_COURSE);
        response.setRenderParameter(PARAM_ID, idCourse.toString());
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_FORM_COURSE_ITEM)
    public String editFormCourseItem(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            Model model) throws PortalException, SystemException {
        if (!model.containsAttribute(ATTR_COURSE_ITEM_PTO)) {
            CourseDto dtoC = serviceCourse.getCourseById(idCourse);
            CourseItemDto dto = serviceCourseItem.getCourseItemById(id);
            dto.setNameCourse(dtoC.getName());
            User user = UserLocalServiceUtil.getUser(Long.valueOf(dto.getUserId()));
            dto.setFullName(user.getFullName());
            model.addAttribute(ATTR_COURSE_ITEM_PTO, dtoToPtoConverter.convert(dto));

            boolean permission = dto.getVisible();
            boolean permission1 = dto.isFinishedCourse();
            boolean permission2 = dto.isVisitedCourse();

            model.addAttribute("checkbox", permission);
            model.addAttribute("chVisitedCourse", permission2);
            model.addAttribute("chFinishedCourse", permission1);
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
            model.addAttribute(ATTR_SEARCH, "");
        }
        return VIEW_EDIT_COURSE_ITEM;
    }

    @ActionMapping(ACTION_SAVE_COURSE_ITEM)
    public void saveCourseItem(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @Valid
            @ModelAttribute(ATTR_COURSE_ITEM_PTO) CourseItemPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        if (!result.hasErrors()) {
            CourseItemDto dto = serviceCourseItem.getCourseItemById(pto.getId());
            dto.setFinishedCourse(pto.getFinishedCourse());
            dto.setVisible(pto.getVisible());
            dto.setVisitedCourse(pto.getVisitedCourse());
            dto.setEndCourse(pto.getEndCourse());

            serviceCourseItem.saveCourseItem(dto);

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_USERS_IN_COURSE);
            response.setRenderParameter(PARAM_ID, idCourse.toString());
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_FORM_COURSE_ITEM);
            response.setRenderParameter(PARAM_ID, idCourse.toString());
        }
    }

    @ActionMapping(ACTION_REMOVE_COURSE_ITEM)
    public void removeCourseItem(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        CourseItemDto dto = serviceCourseItem.getCourseItemById(id);
        serviceAccountUser.removeCourseItem(dto, dto.getAccountUser().getId());
        response.setRenderParameter(PARAM_ID, idCourse.toString());
        SessionMessages.add(request, "KeyRemoved");
        response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_USERS_IN_COURSE);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_USER_COURSES)
    public String viewAllUserCourses(@RequestParam(ATTR_SEARCH) String searchAttr,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {

        CourseDto dtoC = serviceCourse.getCourseById(idCourse);
        AccountUserDto dto = serviceAccountUser.getAccountUserId(serviceCourseItem.getCourseItemById(id).getAccountUser().getId());
        List<CourseItemDto> list = new ArrayList<CourseItemDto>();
        list = dto.getListCourses();
        if (list != null) {
            for (Iterator<CourseItemDto> it = list.iterator(); it.hasNext();) {
                CourseItemDto item = it.next();
                Long idC = Long.valueOf(item.getCourseId());
                CourseDto courseDto = serviceCourse.getCourseById(idC);
                item.setNameCourse(courseDto.getName());
            }
        }
        if (list != null || !list.isEmpty()) {
            Collections.sort(list);
        }
        if (searchAttr == null || searchAttr.isEmpty()) {
            model.addAttribute(ATTR_ALL_ITEMS, list);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, serviceCourseItem.searchInListByCourse(searchAttr, list));
        }
        model.addAttribute(ATTR_SEARCH, searchAttr);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(PARAM_ID, id);
        model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        model.addAttribute(ATTR_SHORTCUT_NAME, dto.getUserName());

        return VIEW_ITEM_COURSE_BY_USER;
    }

    @ActionMapping(ACTION_SEARCH_USER_IN_COURSE)
    public void searchUserInCourse(@RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_USER_COURSES);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        response.setRenderParameter(PARAM_ID, id.toString());
    }

    // QUIZ //
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_VIEW_ALL_Q_IN_BANK)
    public String viewAllQuestionsInBank(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        BankQuestionsDto dto = serviceBankQuestions.getById(id);
        List<MultipleChoiceDto> list = dto.getMultipleChoiceDto();
        boolean isNull = false;
        if (list == null || list.isEmpty()) {
            isNull = true;
        }

        model.addAttribute(ATTR_IS_NULL, isNull);
        model.addAttribute(ATTR_SHORTCUT_NAME, dto.getName());
        model.addAttribute(ATTR_ALL_MULTIPLE, dto.getMultipleChoiceDto());
        model.addAttribute(ATTR_QUIZ_BANK_PTO, id);

        return VIEW_ALL_Q_IN_BANK;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_REMOVE_Q_FROM_BANK)
    public String removeQFromBank(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_QUIZ_BANK_PTO) Long quizBankPto,
            Model model) {
        model.addAttribute(ATTR_QUESTION_PTO, serviceLesson.getById(id));
        model.addAttribute(ATTR_QUIZ_BANK_PTO, quizBankPto);

        return VIEW_REMOVE_QUESTION_FROM_BANK;
    }
    /* quiz bank */

    @ActionMapping(ACTION_REMOVE_Q_FROM_BANK)
    public void removeQFBank(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_QUIZ_BANK_PTO) Long quizBankPto,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceBankQuestions.removeQ(id, quizBankPto);

        response.setRenderParameter(PARAM_ID, quizBankPto.toString());
        SessionMessages.add(request, "SuccessKeyremoved");
        response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_ALL_Q_IN_BANK);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_Q_BANK)
    public String allQuizBank(@RequestParam(PARAM_ID_USER) Long userId,
            @RequestParam(ATTR_SEARCH) String searchAttr,
            Model model) throws PortalException, SystemException {

        User currentUser = UserServiceUtil.getUserById(userId);
        List<BankQuestionsDto> list = serviceBankQuestions.getAll();
        for (Iterator<BankQuestionsDto> it = list.iterator(); it.hasNext();) {
            BankQuestionsDto bankQuestionsDto = it.next();
            if (bankQuestionsDto.getCourse() != null) {
                it.remove();
            }
        }
        if (list != null || !list.isEmpty()) {
            Collections.sort(list);
        }
        if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_ADMINISTRATORL_LMS, true)) {
            if (searchAttr == null || searchAttr.isEmpty()) {
                model.addAttribute(ATTR_ALL_ITEMS, list);
            } else {
                model.addAttribute(ATTR_ALL_ITEMS, serviceBankQuestions.searchInList(searchAttr, list));
            }
        } else if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_LEKTOR, true)) {
            List<BankQuestionsDto> listLektor = serviceBankQuestions.getAllBankQuestionsByUserId(String.valueOf(userId));
            if (searchAttr == null || searchAttr.isEmpty()) {
                model.addAttribute(ATTR_ALL_ITEMS, listLektor);
            } else {
                model.addAttribute(ATTR_ALL_ITEMS, serviceBankQuestions.searchInList(searchAttr, listLektor));
            }
        }
        model.addAttribute(SEARCH_ATTR, searchAttr);
        return VIEW_ALL_Q_BANK;
    }

    @ActionMapping(ACTION_SEARCH_QUIZ_BANK)
    public void searchQuizBank(
            @RequestParam(PARAM_ID_USER) Long userId,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_ID_USER, userId.toString());
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DELETE_Q_BANK)
    public String deleteQuestionBank(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_QUIZ_BANK_PTO, serviceBankQuestions.getById(id));

        return VIEW_DELETE_QUESTION_COURSE;
    }

    @ActionMapping(ACTION_DELETE_Q_BANK)
    public void deleteQBank(
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response) {
        BankQuestionsDto dto = serviceBankQuestions.getById(id);
        String creatorUserId = request.getRemoteUser();
        if (dto.getMultipleChoiceDto() == null) {
            serviceBankQuestions.deleteById(id);

            SessionMessages.add(request, "SuccessKeyDeleted");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionErrors.add(request, "ErrorKeyFullBank");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_TO_BANK)
    public String addToBankQuestion(
            @RequestParam(PARAM_ID_USER) Long idUser,
            @RequestParam(PARAM_ID) Long id,
            Model model) throws PortalException, SystemException {

        User currentUser = UserServiceUtil.getUserById(idUser);
        List<BankQuestionsDto> dto = serviceBankQuestions.getAll();
        if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_ADMINISTRATORL_LMS, true)) {
            model.addAttribute(ATTR_LIST_BANKS_Q, dto);
        } else if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_LEKTOR, true)) {
            dto = serviceBankQuestions.getAllBankQuestionsByUserId(String.valueOf(idUser));
            model.addAttribute(ATTR_LIST_BANKS_Q, dto);
        }
        boolean isNull = false;
        if (dto == null || dto.isEmpty()) {
            isNull = true;
        }
        model.addAttribute(PARAM_ID, id);
        model.addAttribute(ATTR_IS_NULL, isNull);

        return VIEW_ADD_TO_BANK;
    }

    @ActionMapping(ACTION_SAVE_ADD_TO_BANK)
    public void saveAddToBank(
            @RequestParam(PARAM_ID) Long idQuestion,
            @RequestParam(ATTR_LIST_BANKS_Q) Long idBank,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceBankQuestions.addQ(idQuestion, idBank);
        String creatorUserId = request.getRemoteUser();

        SessionMessages.add(request, "SuccessKeyAdd");
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q);

    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_Q_BANK)
    public String editQuizBank(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        if (!model.containsAttribute(ATTR_QUIZ_BANK_PTO)) {
            model.addAttribute(ATTR_SHORTCUT_NAME, serviceBankQuestions.getById(id).getName());
            model.addAttribute(ATTR_QUIZ_BANK_PTO, dtoToPtoConverter.convert(serviceBankQuestions.getById(id)));
            model.addAttribute(ATTR_SIZE_ITEMS, serviceBankQuestions.getById(id).getNumberAllQ());
        }
        return VIEW_EDIT_Q_BANK;
    }

    @ActionMapping(ACTION_SAVE_Q_BANK)
    public void saveQuizBank(
            @Valid
            @ModelAttribute(ATTR_CATEGORY_PTO) BankQuestionsPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) {
        BankQuestionsDto dto = serviceBankQuestions.getById(pto.getId());
        String creatorUserId = request.getRemoteUser();
        boolean dummy = true;
        if (pto.getAllQBank() != true) {
            if (pto.getInPercentDone() > 100 || pto.getInPercentDone() < 9) {
                dummy = false;
            }
            if (dto.getNumberAllQ() == 0 && pto.getNumberQForTest() != 0) {
                dummy = false;
            }
            if (dto.getNumberAllQ() > 0 && pto.getNumberQForTest() == 0) {
                dummy = false;
            }
            if (dto.getNumberAllQ() < pto.getNumberQForTest()) {
                dummy = false;
            }
        }
        if (!result.hasErrors() && dummy == true) {
            dto.setName(pto.getName());
            dto.setRandomSubQ(pto.getRandomSubQ());
            dto.setRandomQ(pto.getRandomQ());
            dto.setAllQBank(pto.getAllQBank());
            dto.setNumberQForTest(pto.getNumberQForTest());
            dto.setInPercentDone(pto.getInPercentDone());
            dto.setDescription(pto.getDescription());
            serviceBankQuestions.save(dto);

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);

        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_Q_BANK);
        }
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(PARAM_ID, pto.getId().toString());
    }

    @ActionMapping(ACTION_CREATE_Q_BANK)
    public void createQBank(
            ActionRequest request,
            ActionResponse response) {
        List<BankQuestionsDto> allDto = new ArrayList<BankQuestionsDto>();
        allDto = serviceBankQuestions.getAll();

        int sizeBank;
        BankQuestionsDto dto;
        String creatorUserId = request.getRemoteUser();
        if (!allDto.isEmpty()) {
            sizeBank = allDto.size();
            sizeBank++;
        } else {
            sizeBank = 1;
        }

        dto = new BankQuestionsDto(null, "box " + sizeBank, null, null, 0, 0, false, false, true, null, false, null, 80, null, creatorUserId, 0);
        serviceBankQuestions.create(dto);

        SessionMessages.add(request, "SuccessKey");
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
        response.setRenderParameter(ATTR_SEARCH, "");
    }
    /* START QUESTIONS */

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_FORM_MULTIPLECHOICE)
    public String editQuestion(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        // if (!model.containsAttribute(ATTR_MULTIPLE_CHOICE_PTO)) {
        MultipleChoicePto pto = dtoToPtoConverter.convert(serviceMultipleCH.getById(id));

        model.addAttribute(ATTR_MULTIPLE_CHOICE_PTO, dtoToPtoConverter.convert(serviceMultipleCH.getById(id)));
        List<ItemMultiplechoicePto> listPto = new ArrayList<ItemMultiplechoicePto>();
        List<ItemMultipleChoiceDto> listDto = new ArrayList<ItemMultipleChoiceDto>();
        listDto = serviceMultipleCH.getById(id).getItems();
        for (Iterator<ItemMultipleChoiceDto> it = listDto.iterator(); it.hasNext();) {
            ItemMultipleChoiceDto itemMultipleChoiceDto = it.next();
            listPto.add(dtoToPtoConverter.convert(itemMultipleChoiceDto));
        }
        model.addAttribute(ATTR_ITEM_MCH_PTO, listPto);
        model.addAttribute(PARAM_ID, id);
        model.addAttribute("dataJson", new Gson().toJson(listPto.size()));
        //   }
        return VIEW_EDIT_FORM_MULTIPLECHOICE;
    }

    @ActionMapping(ACTION_SAVE_Q)
    public void saveQuestion(
            @RequestParam(PARAM_ID) Long id,
            @Valid
            @ModelAttribute(ATTR_MULTIPLE_CHOICE_PTO) MultipleChoicePto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) {
        LOG.info("som tu");

        boolean ok = true;
        String creatorUserId = request.getRemoteUser();
        for (Iterator<ItemMultiplechoicePto> i = pto.getItemsM().iterator(); i.hasNext();) {
            ItemMultiplechoicePto item = i.next();
            if (item.getText().isEmpty() || ok != true) {
                ok = false;
                break;
            }
        }
        if (!result.hasErrors() && ok == true) {
            List<ItemMultipleChoiceDto> listItemM = new ArrayList<ItemMultipleChoiceDto>();
            for (ItemMultiplechoicePto item : pto.getItemsM()) {
                ItemMultipleChoiceDto itemDto = new ItemMultipleChoiceDto(null, item.getText(), item.getAnswer(), 0, null);
                listItemM.add(itemDto);
            }
            MultipleChoiceDto multipleDto = serviceMultipleCH.getById(pto.getId());
            serviceMultipleCH.removeItemM(listItemM, multipleDto);
            multipleDto.setPoints(pto.getPoints());
            multipleDto.setQuestion(pto.getQuestion());
            multipleDto.setQuestionShort(serviceHelper.cutString(STRING_CUT_SIZE, pto.getQuestion()));
            serviceMultipleCH.save(multipleDto);
            serviceMultipleCH.addItemToMultipleChoice(listItemM, multipleDto);

            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
            response.setRenderParameter(PARAM_ID, id.toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_FORM_MULTIPLECHOICE);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(PARAM_ID, id.toString());
            response.setRenderParameter(ATTR_SEARCH, "");
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CREATE_Q)
    public String createQuestion(Model model) {
        if (!model.containsAttribute(ATTR_MULTIPLE_CHOICE_PTO)) {
            model.addAttribute(ATTR_MULTIPLE_CHOICE_PTO, new MultipleChoicePto());
        }
        return VIEW_CREATE_Q;
    }

    @ActionMapping(ACTION_CREATE_Q_MCH)
    public void createMultipleCH(
            @Valid
            @ModelAttribute(ATTR_MULTIPLE_CHOICE_PTO) MultipleChoicePto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) {
        boolean ok = true;
        for (Iterator<ItemMultiplechoicePto> i = pto.getItemsM().iterator(); i.hasNext();) {
            ItemMultiplechoicePto item = i.next();
            if (item.getText().isEmpty() || ok != true) {
                ok = false;
                SessionErrors.add(request, "ErrorKey");
                response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_Q);
                break;
            }
        }
        if (!result.hasErrors() && ok == true) {
            List<ItemMultipleChoiceDto> listItemM = new ArrayList<ItemMultipleChoiceDto>();
            for (ItemMultiplechoicePto item : pto.getItemsM()) {
                ItemMultipleChoiceDto itemDto = new ItemMultipleChoiceDto(null, item.getText(), item.getAnswer(), 0, null);
                listItemM.add(itemDto);
            }
            String creatorUserId = request.getRemoteUser();

            MultipleChoiceDto multipleDto = new MultipleChoiceDto(null, null, pto.getQuestion(), pto.getPoints(), null, creatorUserId, serviceHelper.cutString(STRING_CUT_SIZE, pto.getQuestion()));
            MultipleChoiceDto savedMultiple = serviceMultipleCH.createO(multipleDto);
            serviceMultipleCH.addItemToMultipleChoice(listItemM, savedMultiple);
            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_Q);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_Q)
    public String allQuestions(@RequestParam(PARAM_ID_USER) Long userId,
            @RequestParam(ATTR_SEARCH) String searchAttr,
            Model model) throws PortalException, SystemException {

        List<MultipleChoiceDto> listMultiple = serviceMultipleCH.notSetOurseList(serviceMultipleCH.getAllMultipleChoice());

        User currentUser = UserServiceUtil.getUserById(Long.valueOf(userId));
        if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_ADMINISTRATORL_LMS, true)) {
            if (searchAttr == null || searchAttr.isEmpty()) {
                model.addAttribute(ATTR_ALL_MULTIPLE, listMultiple);
            } else {
                model.addAttribute(ATTR_ALL_MULTIPLE, serviceMultipleCH.searchInList(searchAttr, listMultiple));
            }
        } else if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_LEKTOR, true)) {
            listMultiple = serviceMultipleCH.getAllQuestionsByUser(listMultiple, String.valueOf(currentUser.getUserId()));
            if (searchAttr == null || searchAttr.isEmpty()) {
                model.addAttribute(ATTR_ALL_MULTIPLE, listMultiple);
            } else {
                model.addAttribute(ATTR_ALL_MULTIPLE, serviceMultipleCH.searchInList(searchAttr, listMultiple));
            }
        }
        model.addAttribute(SEARCH_ATTR, searchAttr);
        LOG.info("info 2");
        return VIEW_ALL_Q;
        //skontroluj ci nova metoda getAllQUestionsByUser funguje
    }

    @ActionMapping(ACTION_SEARCH_QUESTION)
    public void searchQuestion(
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String userId = request.getRemoteUser();
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        LOG.info("ok " + searchRequest);
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q);
        response.setRenderParameter(PARAM_ID_USER, userId);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DELETE_Q_TRUE_FALSE)
    public String deleteQTrueFalse(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        model.addAttribute(ATTR_QUESTION_PTO, serviceLesson.getById(id));

        return VIEW_DELETE_QUESTION_LESSON;
    }

    @ActionMapping(ACTION_DELETE_Q_T_F)
    public void deleteQTF(
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceMultipleCH.deleteAllItemsBy(serviceMultipleCH.getById(id));
        serviceMultipleCH.deleteById(id);
        String creatorUserId = request.getRemoteUser();
        SessionMessages.add(request, "SuccessKeyDeleted");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q);
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_BANK_TO_COURSE)
    public String addBankToCourse(Model model) {
        return VIEW_ADD_BANK_TO_COURSE;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_Q_TO_BANK)
    public String addQuestionToCourse(Model model) {
        return VIEW_ADD_Q_TO_BANK;
    }

    //add to course
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_TO_COURSE)
    public String addToCourse(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_USER) Long idUser,
            Model model) throws PortalException, SystemException {
        List<CourseDto> dto = serviceCourse.getAllCourse();
        User currentUser = UserServiceUtil.getUserById(idUser);

        if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_ADMINISTRATORL_LMS, true)) {
            model.addAttribute(ATTR_COURSE_PTO, dto);
        } else if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_LEKTOR, true)) {
            dto = serviceCourse.getAllCourseByUserId(String.valueOf(idUser));
            model.addAttribute(ATTR_COURSE_PTO, dto);
        }
        boolean isNull = false;
        if (dto == null || dto.isEmpty()) {
            isNull = true;
        }
        model.addAttribute(ATTR_IS_NULL, isNull);
        model.addAttribute(PARAM_ID, id);

        return VIEW_ADD_TO_COURSE;
    }

    @ActionMapping(ACTION_SAVE_ADD_TO_COURSE)
    public void saveAddToCourse(
            @RequestParam(PARAM_ID) Long idBank,
            @RequestParam(ATTR_COURSE_PTO) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceCourse.addBankQuestions(idCourse, idBank);
        String creatorUserId = request.getRemoteUser();

        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        SessionMessages.add(request, "SuccessKeyAdd");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_REMOVE_Q_FROM_COURSE)
    public String removeQuestionFromCourse(
            @RequestParam(ATTR_QUIZ_BANK_PTO) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        model.addAttribute(ATTR_QUIZ_BANK_PTO, serviceBankQuestions.getById(id));
        model.addAttribute(PARAM_ID_COURSE, idCourse);

        return VIEW_REMOVE_QUESTION_FROM_COURSE;
    }

    @ActionMapping(ACTION_REMOVE_Q_FROM_COURSE)
    public void removeQFromCourse(
            @RequestParam(ATTR_QUIZ_BANK_PTO) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceCourse.removeBankQuestions(idCourse, id);
        String creatorUserId = request.getRemoteUser();

        SessionMessages.add(request, "SuccessKeyremoved");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    //add more questions to test
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_MORE_QUESTIONS)
    public String addMoreQuestionsToTest(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_USER) Long idUser,
            Model model) throws PortalException, SystemException {
        List<MultipleChoiceDto> list = serviceMultipleCH.notSetOurseList(serviceMultipleCH.getAllMultipleChoice());
        User currentUser = UserServiceUtil.getUserById(idUser);
        if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_ADMINISTRATORL_LMS, true)) {
            model.addAttribute(ATTR_LIST_BANKS_Q, list);
        } else if (RoleServiceUtil.hasUserRole(currentUser.getUserId(), currentUser.getCompanyId(), ROLE_LEKTOR, true)) {
            list = serviceMultipleCH.getAllQuestionsByUser(list, String.valueOf(currentUser.getUserId()));
            model.addAttribute(ATTR_LIST_BANKS_Q, list);
        }
        boolean isNull = false;
        if (list == null || list.isEmpty()) {
            isNull = true;
        }
        model.addAttribute(PARAM_ID, id);
        model.addAttribute(PARAM_ID_USER, idUser.toString());
        model.addAttribute(ATTR_IS_NULL, isNull);

        return VIEW_ADD_MORE_QUESTIONST_TO_TEST;
    }

    @ActionMapping(ACTION_SAVE_ADD_Q_TO_TEST)
    public void saveMoreQuestionsToCourse(
            @RequestParam("searchable") List<String> isMultipleQuestions,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String creatorUserId = request.getRemoteUser();

        LOG.info("start");
        for (Iterator<String> it = isMultipleQuestions.iterator(); it.hasNext();) {
            String string = it.next();
            LOG.info("cyklus: ");
            serviceBankQuestions.addQ(Long.valueOf(string), id);
        }

        LOG.info("end");
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        SessionMessages.add(request, "SuccessKeyAdd");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_Q_BANK);
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_ID, id.toString());
    }

    //detail quesion
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_QUESTION)
    public String detailQuestion(
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        MultipleChoiceDto dto = serviceMultipleCH.getById(id);
        model.addAttribute(ATTR_ITEM_MCH_PTO, dto.getItems());
        model.addAttribute(ATTR_QUESTION_PTO, dto);
        model.addAttribute("dataJsonM", new Gson().toJson(dto.getItems()));

        return VIEW_DETAIL_QUESTION;
    }

    //START DMS //
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_GROUP_DOC)
    public String viewAllGroupDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(ATTR_SEARCH) String searchAttr,
            Model model) {

        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<GroupDocumentsDto> groupDto = new ArrayList<GroupDocumentsDto>();
        groupDto = serviceCourse.getAllGroupDoc(courseDto);

        if (groupDto != null) {
            groupDto = serviceGroupDoc.setSizeItemDocuments(groupDto);
            // List<AccountUserDto> list = serviceAccountUser.getAllAccountUser();
            //if (searchAttr == null || searchAttr.isEmpty()) {
            if (groupDto != null || !groupDto.isEmpty()) {
                Collections.sort(groupDto);
            }
            model.addAttribute(ATTR_ALL_ITEMS, groupDto);
            // } else {
            //    model.addAttribute(ATTR_ALL_ITEMS, serviceGroupDoc.searchInList(searchAttr, groupDto));
            // }
        }
        boolean isNull = false;
        if (groupDto == null || groupDto.size() == 0) {
            isNull = true;
        }
        model.addAttribute(ATTR_IS_NULL, isNull);
        model.addAttribute(SEARCH_ATTR, searchAttr);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        return VIEW_ALL_GROUP_DOC;
    }

//    @ActionMapping(ACTION_SEARCH_DMS_BANK)
//    public void searchDmsGroup(
//            @RequestParam(PARAM_ID_COURSE) String idCourse,
//            ActionRequest request,
//            ActionResponse response,
//            Model model) {
//        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
//        response.setRenderParameter(ATTR_SEARCH, searchRequest);
//        response.setRenderParameter(PARAM_ID_COURSE, idCourse);
//        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
//    }
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CREATE_GROUP_DOC)
    public String createGroupDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        if (!model.containsAttribute(ATTR_GROUP_DOC_PTO)) {
            CourseDto dto = serviceCourse.getCourseById(idCourse);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dto.getShortName());
            model.addAttribute(ATTR_GROUP_DOC_PTO, new GroupDocPto());
            model.addAttribute(PARAM_ID_COURSE, idCourse);
        }
        return VIEW_CREATE_GROUP_DOC;
    }

    @ActionMapping(ACTION_CREATE_GROUP_DOC)
    public void createGroupDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @Valid
            @ModelAttribute(ATTR_GROUP_DOC_PTO) GroupDocPto pto,
            BindingResult result, Model model, ActionRequest request,
            ActionResponse response) {
        CourseDto dto = serviceCourse.getCourseById(idCourse);
        if (!result.hasErrors()) {
            CourseDto dtoC = new CourseDto();
            dtoC = serviceCourse.getCourseById(idCourse);
            GroupDocumentsDto groupDoc = new GroupDocumentsDto(null, pto.getName(), pto.getDescription(), null, dtoC, pto.getVisible(), 0, 0);
            serviceCourse.addGroupDoc(groupDoc, idCourse);

            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_GROUP_DOC);
            response.setRenderParameter(PARAM_SHORTCUT_COURSE, dto.getShortName());
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_Q_ADD_ITEM_DOC)
    public String qAddItemDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            Model model) {
        CourseDto dtoC = serviceCourse.getCourseById(idCourse);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(PARAM_ID_GROUP, idGroup);
        model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());

        return VIEW_Q_CREATE_ITEM;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ADD_ITEM_DOC)
    public String addItemDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            Model model) {
        if (!model.containsAttribute(ATTR_ITEM_DOC_PTO)) {
            CourseDto dtoC = serviceCourse.getCourseById(idCourse);
            model.addAttribute(ATTR_ITEM_DOC_PTO, new ItemDocumentPto());
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_ID_GROUP, idGroup);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        }
        return VIEW_CREATE_ITEM_DOC;
    }

    @ActionMapping(ACTION_ADD_ITEM_DOC)
    public void addItemDocument(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @Valid
            @ModelAttribute(ATTR_ITEM_DOC_PTO) ItemDocumentPto pto,
            BindingResult result,
            Model model,
            ActionRequest request,
            ActionResponse response) {
        if (!result.hasErrors()) {
            GroupDocumentsDto dto = new GroupDocumentsDto();
            dto = serviceGroupDoc.getById(idGroup);
            ItemDocumentDto itemDto = new ItemDocumentDto(null, pto.getLink(), pto.getName(), true, null, "none", true);
            serviceGroupDoc.addItemDoc(itemDto, idGroup);

            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_DETAIL_ITEM_DOC);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(PARAM_ID_GROUP, idGroup.toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(PARAM_PAGE, PAGE_CREATE_GROUP_DOC);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_UPLOAD_ITEM_DOC)
    public String uploadItemDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            Model model) {
        if (!model.containsAttribute(ATTR_ITEM_DOC_PTO)) {
            CourseDto dtoC = serviceCourse.getCourseById(idCourse);
            model.addAttribute(ATTR_ITEM_DOC_PTO, new ItemDocumentPto());
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_ID_GROUP, idGroup);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        }
        return VIEW_UPLOAD_ITEM;
    }

    @ActionMapping(ACTION_UPLOAD_DMS)
    public void uploadDms(
            ActionRequest request,
            ActionResponse response,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID_GROUP) Long idGroup) throws PortletException, SystemException, IOException, PortalException {

        ThemeDisplay themeDisplay = null;
        themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        Long companyId = CompanyThreadLocal.getCompanyId();
        long userId = Long.valueOf(request.getRemoteUser());
        long repoId = themeDisplay.getScopeGroupId();
        // DLFolderLocalServiceUtil.deleteAll(repoId);

        List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getDLFolders(0, DLFolderLocalServiceUtil.getDLFoldersCount());
        DLFolder courseFolder = null;
        DLFolder parent = null;
        DLFolder categoryFolder = null;

        for (DLFolder folder1 : dlFolders) {
            LOG.info("Folder Name >>" + folder1.getName());
            if (folder1.getName().equals(NAME_OF_REPOSITARE)) {
                LOG.info("yes equals");
                parent = folder1;
                break;
            }
        }
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        //create main folder
        if (parent == null) {
            parent = serviceItemDocuments.createFolder(NAME_OF_REPOSITARE, parent, 0, themeDisplay, userId, companyId);
            LOG.info("vytvoreny folder parent ");
        }

        //create category
        String category = courseDto.getCategory().getName();
        for (DLFolder folder1 : dlFolders) {
            LOG.info("Folder Name >>" + folder1.getName());
            LOG.info("Folder id>>" + folder1.getPrimaryKey());
            if (folder1.getName().equals(category)) {
                LOG.info("yes equals");
                categoryFolder = folder1;
                break;
            }
        }
        if (categoryFolder == null) {
            categoryFolder = serviceItemDocuments.createFolder(category, categoryFolder, parent.getFolderId(), themeDisplay, userId, companyId);
            LOG.info("vytvoreny folder category");
        }
        //crate folder with name Course
        for (DLFolder folder1 : dlFolders) {
            LOG.info("Folder Name >>" + folder1.getName()); //if(folder1.getFolderId() == 12692){ 

            if (folder1.getName().equals(courseDto.getName())) {
                LOG.info("yes equals");
                courseFolder = folder1;
                break;
            }
        }
        if (courseFolder == null) {
            courseFolder = serviceItemDocuments.createFolder(courseDto.getName(), courseFolder, categoryFolder.getFolderId(), themeDisplay, userId, companyId);
        }
        try {
            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
            if (uploadRequest.getSize("fileName") == 0) {
                LOG.info("EROR pri nacitavani ");
            }
            String sourceFileName = uploadRequest.getFileName("fileName");
            sourceFileName = sourceFileName.substring(0, sourceFileName.lastIndexOf('.'));
            LOG.info("source file name:  " + sourceFileName);

            List<DLFileEntry> listFiles = new ArrayList<DLFileEntry>(DLFileEntryLocalServiceUtil.getFileEntries(repoId, courseFolder.getFolderId()));
            boolean duplicate = false;
            for (DLFileEntry dLFileEntry : listFiles) {
                LOG.info("log uploadRequest: " + sourceFileName);
                LOG.info("log dLFileEntry: " + dLFileEntry.getTitle());
                if (dLFileEntry.getTitle().equals(sourceFileName)) {
                    SessionErrors.add(request, "ErrorKey");
                    response.setRenderParameter(PARAM_PAGE, PAGE_UPLOAD_ITEM_DOC);
                    response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
                    response.setRenderParameter(PARAM_ID_GROUP, idGroup.toString());
                    duplicate = true;
                    //LOG.info("Duplicate");
                    break;
                }
            }
            if (!duplicate) {
                // LOG.info("No Duplicate");
                File file = uploadRequest.getFile("fileName");
                ServiceContext serviceContext = ServiceContextFactory.getInstance(request);
                DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(userId, repoId, repoId, courseFolder.getFolderId(), file.getName(), MimeTypesUtil.getContentType(file), sourceFileName, "description", "sss",
                        0, null, file, null, file.length(), serviceContext);

                DLFileEntry newFileEntry = DLFileEntryLocalServiceUtil.updateFileEntry(userId, dlFileEntry.getFileEntryId(), file.getName(), MimeTypesUtil.getContentType(file), sourceFileName, "descriptionn", "comment", true, dlFileEntry.getFileEntryTypeId(),
                        null, file, null, file.length(), serviceContext);

                newFileEntry = newFileEntry.toEscapedModel();
                long fileEntryId = newFileEntry.getFileEntryId();
                long folderId = newFileEntry.getFolderId();
                String title = newFileEntry.getTitle();
                if (request != null) {
                    themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
                }
                String fileUrl = themeDisplay.getPortalURL() + themeDisplay.getPathContext() + "/documents/" + themeDisplay.getScopeGroupId() + "//" + folderId + "//" + HttpUtil.encodeURL(HtmlUtil.unescape(title));

                //add link
                GroupDocumentsDto dto = new GroupDocumentsDto();
                dto = serviceGroupDoc.getById(idGroup);
                ItemDocumentDto itemDto = new ItemDocumentDto(null, fileUrl, sourceFileName, true, null, "none", false);
                serviceGroupDoc.addItemDoc(itemDto, idGroup);

                SessionMessages.add(request, "SuccessKey");
                response.setRenderParameter(PARAM_PAGE, PAGE_DETAIL_ITEM_DOC);
                response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
                response.setRenderParameter(PARAM_ID_GROUP, idGroup.toString());
                response.setRenderParameter(PARAM_ID_ITEM_DOC, fileUrl);
            }

        } catch (NullPointerException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
            //SessionMessages.add(actionRequest, "error");
        }

    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_ITEM_DOC)
    public String detailItemsDoc(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        CourseDto dtoC = serviceCourse.getCourseById(idCourse);
        GroupDocumentsDto dto = serviceGroupDoc.getById(idGroup);
        List<ItemDocumentDto> listItemDoc = serviceGroupDoc.addIconToItems(dto.getItemDocumentDto());
        model.addAttribute(PARAM_ID_GROUP, idGroup.toString());
        model.addAttribute(ATTR_GROUP_DOC_PTO, dto);
        model.addAttribute(ATTR_ITEM_DOC_PTO, listItemDoc);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(ATTR_SIZE_ITEMS, dto.getItemDocumentDto().size());
        model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());

        return VIEW_DETAIL_ITEMS_DOC;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_REMOVE_ITEM_DOC)
    public String removeItemDoc(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        CourseDto dtoC = serviceCourse.getCourseById(idCourse);
        GroupDocumentsDto dto = serviceGroupDoc.getById(idGroup);
        List<ItemDocumentDto> listItemDoc = serviceGroupDoc.addIconToItems(dto.getItemDocumentDto());
        model.addAttribute(ATTR_GROUP_DOC_PTO, dto);
        model.addAttribute(ATTR_ITEM_DOC_PTO, listItemDoc);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(PARAM_ID_GROUP, idGroup);
        model.addAttribute(ATTR_SIZE_ITEMS, dto.getItemDocumentDto().size());
        model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());

        return VIEW_REMOVE_ITEMS_DOC;
    }

    @ActionMapping(ACTION_DELETE_ITEM_DOC)
    public void deleteItemDoc(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_ITEM_DOC) Long idItem,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        ItemDocumentDto itemDto = serviceItemDocuments.getById(idItem);
        serviceGroupDoc.removeItemDoc(itemDto, idGroup);
        List<ItemDocumentDto> list = new ArrayList<ItemDocumentDto>();
        list = serviceGroupDoc.getById(idGroup).getItemDocumentDto();
        if (list == null) {
            SessionMessages.add(request, "SuccessKeyDeleted");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionMessages.add(request, "SuccessKeyDeleted", new String[]{itemDto.getName()});
            response.setRenderParameter(PARAM_PAGE, PAGE_REMOVE_ITEM_DOC);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(PARAM_ID_GROUP, idGroup.toString());
        }
    }

    @ActionMapping(ACTION_DELETE_ITEM_DOC_FROM_REPO)
    public void deleteItemDocWithRepository(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_ITEM_DOC) Long idItem,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) throws SystemException, PortalException {
        ItemDocumentDto itemDto = serviceItemDocuments.getById(idItem);
        serviceGroupDoc.removeItemDoc(itemDto, idGroup);
        List<ItemDocumentDto> list = new ArrayList<ItemDocumentDto>();
        list = serviceGroupDoc.getById(idGroup).getItemDocumentDto();

        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getDLFolders(0, DLFolderLocalServiceUtil.getDLFoldersCount());
        DLFolder folder = null;
        for (DLFolder folder1 : dlFolders) {
            LOG.info("Folder Name >>" + folder1.getName());
            if (folder1.getName().equals(courseDto.getName())) {
                LOG.info("yes equals");
                folder = folder1;
                // break;
            }
        }
        ThemeDisplay themeDisplay = null;
        themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        long repositoryId = themeDisplay.getScopeGroupId();
        if (folder != null) {
            List<DLFileEntry> listFiles = DLFileEntryLocalServiceUtil.getFileEntries(repositoryId, folder.getFolderId());
            for (Iterator<DLFileEntry> it = listFiles.iterator(); it.hasNext();) {
                DLFileEntry dLFileEntry = it.next();
                LOG.info("File name :" + dLFileEntry.getTitle());
                if (dLFileEntry.getTitle().equals(itemDto.getName())) {
                    LOG.info("primary :" + dLFileEntry.getPrimaryKey());
                    LOG.info("primary :" + dLFileEntry.getFileEntryId());
                    DLFileEntryLocalServiceUtil.deleteDLFileEntry(dLFileEntry.getPrimaryKey());
                    LOG.info("ok :");
                }
            }
        }
        //delete empty folder name   
        long repoId = themeDisplay.getScopeGroupId();
        List<DLFileEntry> listFiles = new ArrayList<DLFileEntry>(DLFileEntryLocalServiceUtil.getFileEntries(repoId, folder.getFolderId()));
        if (listFiles == null || listFiles.isEmpty()) {
            DLFolderLocalServiceUtil.deleteDLFolder(folder.getFolderId());
            LOG.info("vymazany folder " + folder.getName());
        } else {
            LOG.info("plny folder " + folder.getName());
        }
        //delete empty folder category 
//        dlFolders = DLFolderLocalServiceUtil.getDLFolders(0, DLFolderLocalServiceUtil.getDLFoldersCount());
//        DLFolder folderCategory = null; 
//        for (DLFolder folder1 : dlFolders) {
//            if (folder1.getName().equals(courseDto.getCategory().getName())) { 
//                folderCategory = folder1;
//                break;
//            }
//        } 
//        List<DLFolder> listFolders = new ArrayList<DLFolder>(DLFolderLocalServiceUtil.getDLFileEntryTypeDLFolders(folderCategory.getPrimaryKey()));
//        
//        if (listFolders == null || listFolders.isEmpty()) {
//            LOG.info("size listcategory " + listFolders.size());
//            DLFolderLocalServiceUtil.deleteDLFolder(folderCategory.getFolderId());
//            LOG.info("vymazany category folder " + folderCategory.getName());
//        } else {
//            LOG.info("plny category folder " + folderCategory.getName());
//        }
        //DLFolderLocalServiceUtil.deleteDLFolder(14175);
        if (list == null) {
            SessionMessages.add(request, "SuccessKeyDeleted");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(ATTR_SEARCH, "");
        } else {
            SessionMessages.add(request, "SuccessKeyDeleted", new String[]{itemDto.getName()});
            response.setRenderParameter(PARAM_PAGE, PAGE_REMOVE_ITEM_DOC);
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(PARAM_ID_GROUP, idGroup.toString());
        }
    }

    //Group
    @ActionMapping(ACTION_DELETE_GROUP_DOC)
    public void deleteGroupDoc(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request, ActionResponse response,
            Model model) {
        GroupDocumentsDto groupDocDto = serviceGroupDoc.getById(idGroup);
        if (groupDocDto.getItemDocumentDto() == null) {
            serviceCourse.removeGroupDoc(groupDocDto, idCourse);
            SessionMessages.add(request, "SuccessKeyDeleted", new String[]{groupDocDto.getName()});
        } else {
            SessionErrors.add(request, "ErrorKeyFullBank");
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_GROUP_DOC)
    public String detailGroupDoc(
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        CourseDto dtoC = serviceCourse.getCourseById(idCourse);
        GroupDocumentsDto dto = serviceGroupDoc.getById(idGroup);
        List<ItemDocumentDto> list = new ArrayList<ItemDocumentDto>();
        list = dto.getItemDocumentDto();
        if (list == null) {
            model.addAttribute(ATTR_GROUP_DOC_PTO, dto);
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());

            return VIEW_DETAIL_GROUP_DOC;
        } else {
            List<ItemDocumentDto> listItemDoc = serviceGroupDoc.addIconToItems(dto.getItemDocumentDto());
            model.addAttribute(ATTR_GROUP_DOC_PTO, dto);
            model.addAttribute(ATTR_ITEM_DOC_PTO, listItemDoc);
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(ATTR_SIZE_ITEMS, dto.getItemDocumentDto().size());
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());

            return VIEW_DETAIL_ITEMS_DOC;
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_GROUP_DOC)
    public String editFormGroupDOc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID_GROUP) Long idGroup,
            Model model) {
        if (!model.containsAttribute(ATTR_GROUP_DOC_PTO)) {
            CourseDto dtoC = serviceCourse.getCourseById(idCourse);
            model.addAttribute(ATTR_GROUP_DOC_PTO, dtoToPtoConverter.convert(serviceGroupDoc.getById(idGroup)));
            GroupDocumentsDto dto = serviceGroupDoc.getById(idGroup);
            boolean permission = dto.isVisible();
            model.addAttribute("checkbox", permission);
            model.addAttribute(ATTR_FOR_EDITOR, dto.getDescription());
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
        }
        return VIEW_EDIT_FROM_GROUP_DOC;
    }

    @ActionMapping(ACTION_EDIT_GROUP_DOC)
    public void saveGroupDoc(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @Valid
            @ModelAttribute(ATTR_GROUP_DOC_PTO) GroupDocPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        CourseDto dtoC = serviceCourse.getCourseById(idCourse);
        LOG.info("LOG short name " + dtoC.getShortName());
        if (!result.hasErrors()) {
            GroupDocumentsDto dto = serviceGroupDoc.getById(pto.getId());
            String getValueFromEditor = ParamUtil.getString(request, "htmlCodeFromEditorPlacedHere");
            dto.setName(pto.getName());
            dto.setVisible(pto.getVisible());
            dto.setDescription(getValueFromEditor);
            serviceGroupDoc.save(dto);

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_ID_GROUP, pto.getId().toString());
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_GROUP_DOC);
        }
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_ID_COURSE, dtoC.getId().toString());
        response.setRenderParameter(PARAM_SHORTCUT_COURSE, dtoC.getShortName());
    }

    @ActionMapping(ACTION_DE_ACTIVATE_GROUP_DOC)
    public void actiDeactivateGroup(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM) int number,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        GroupDocumentsDto dto = new GroupDocumentsDto();
        try {
            if (number == 0) {
                serviceGroupDoc.changeActivate(false, id);
                SessionMessages.add(request, "KeyDeactivated");
            } else {
                serviceGroupDoc.changeActivate(true, id);
                SessionMessages.add(request, "KeyActivated");
            }
        } catch (Exception e) {
            SessionErrors.add(request, "ErrorKeyDeleted");
        }
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_ID_GROUP, id.toString());
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
    }

    @ActionMapping(ACTION_UP_GDOCUMENTS)
    public void upGDocuments(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        GroupDocumentsDto dto = serviceGroupDoc.getById(id);
        serviceGroupDoc.changePosition(true, dto);

        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @ActionMapping(ACTION_DOWN_GDOCUMENTS)
    public void downGDocuments(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        GroupDocumentsDto dto = serviceGroupDoc.getById(id);
        serviceGroupDoc.changePosition(false, dto);

        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_GROUP_DOC);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        response.setRenderParameter(ATTR_SEARCH, "");
    }
    /*END DMS*/

    /*START QUESTION BANK IN COURSE*/
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_Q_BANK)
    public String viewQuizBankCourse(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        CourseDto dto = serviceCourse.getCourseById(idCourse);
        List<BankQuestionsDto> list = new ArrayList<BankQuestionsDto>();
        list = dto.getBankQuestionsDto();
        boolean isNull = false;
        if (list == null || list.size() == 0) {
            isNull = true;
        }
        if (list != null) {
            Collections.sort(list);
            LOG.info("log pocet " + list.size());
        }
        model.addAttribute(ATTR_ALL_ITEMS, list);
        model.addAttribute(ATTR_IS_NULL, isNull);
        model.addAttribute(PARAM_ID_COURSE, idCourse.toString());
        model.addAttribute(PARAM_SHORTCUT_COURSE, dto.getShortName());

        return VIEW_BANK_OF_QUESTIONS;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_Q_BANK_IN_COURSE)
    public String editQuizBankInCourse(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        if (!model.containsAttribute(ATTR_QUIZ_BANK_PTO)) {
            CourseDto dto = serviceCourse.getCourseById(idCourse);
            model.addAttribute(ATTR_SHORTCUT_NAME, serviceBankQuestions.getById(id).getName());
            model.addAttribute(ATTR_QUIZ_BANK_PTO, dtoToPtoConverter.convert(serviceBankQuestions.getById(id)));
            model.addAttribute(PARAM_SHORTCUT_COURSE, dto.getShortName());
            model.addAttribute(PARAM_ID_COURSE, idCourse);
            model.addAttribute(ATTR_SIZE_ITEMS, serviceBankQuestions.getById(id).getNumberAllQ());
        }
        return VIEW_EDIT_Q_BANK_IN_COURSE;
    }

    @ActionMapping(ACTION_SAVE_Q_BANK_IN_BANK)
    public void saveQBankInBANK(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @Valid
            @ModelAttribute(ATTR_CATEGORY_PTO) BankQuestionsPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) {
        BankQuestionsDto dto = serviceBankQuestions.getById(pto.getId());
        String creatorUserId = request.getRemoteUser();
        boolean dummy = true;
        if (pto.getAllQBank() != true) {
            if (pto.getInPercentDone() > 100 || pto.getInPercentDone() < 9) {
                dummy = false;
            }
            if (dto.getNumberAllQ() == 0 && pto.getNumberQForTest() != 0) {
                dummy = false;
            }
            if (dto.getNumberAllQ() > 0 && pto.getNumberQForTest() == 0) {
                dummy = false;
            }
            if (dto.getNumberAllQ() < pto.getNumberQForTest()) {
                dummy = false;
            }
        }
        if (!result.hasErrors() && dummy == true) {
            dto.setName(pto.getName());
            dto.setRandomSubQ(pto.getRandomSubQ());
            dto.setRandomQ(pto.getRandomQ());
            dto.setAllQBank(pto.getAllQBank());
            dto.setNumberQForTest(pto.getNumberQForTest());
            dto.setInPercentDone(pto.getInPercentDone());
            dto.setDescription(pto.getDescription());
            serviceBankQuestions.save(dto);

            SessionMessages.add(request, "SuccessKeySaved");
            response.setRenderParameter(PARAM_PAGE, PAGE_Q_BANK);
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_Q_BANK_IN_COURSE);
        }
        response.setRenderParameter(PARAM_ID, pto.getId().toString());
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
    }

    @ActionMapping(ACTION_REMOVE_Q_BANK_FROM_COURSE)
    public void removeQBankFromCourse(
            @RequestParam(ATTR_QUIZ_BANK_PTO) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceCourse.removeBankQuestions(idCourse, id);
        String creatorUserId = request.getRemoteUser();

        SessionMessages.add(request, "SuccessKeyremoved");
        response.setRenderParameter(PARAM_PAGE, PAGE_Q_BANK);
        response.setRenderParameter(PARAM_ID_USER, creatorUserId);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_VIEW_Q_IN_BANK)
    public String viewQuestionsInBank(@RequestParam(ATTR_SEARCH) String searchAttr,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        BankQuestionsDto dto = dto = serviceBankQuestions.getById(id);
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<MultipleChoiceDto> list = new ArrayList<MultipleChoiceDto>();
        list = dto.getMultipleChoiceDto();
        if (searchAttr == null || searchAttr.isEmpty()) {
            model.addAttribute(ATTR_ALL_ITEMS, list);
        } else {
            model.addAttribute(ATTR_ALL_ITEMS, serviceMultipleCH.searchInList(searchAttr, list));
        }
        boolean isNull = false;
        if (list == null || list.size() == 0) {
            isNull = true;
        }

        model.addAttribute(ATTR_IS_NULL, isNull);
        model.addAttribute(SEARCH_ATTR, searchAttr);
        model.addAttribute(ATTR_SHORTCUT_NAME, dto.getName());
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        model.addAttribute(ATTR_QUIZ_BANK_PTO, id);
        model.addAttribute(PARAM_ID_COURSE, idCourse);

        return VIEW_Q_BANK_IN_COURSE;
    }

    @ActionMapping(ACTION_SEARCH_QUESTIONS_IN_BANK)
    public void searchQInBank(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        LOG.info("ok " + searchRequest);
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_Q_IN_BANK);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        response.setRenderParameter(PARAM_ID, id.toString());
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DETAIL_QUESTION_IN_COURSE)
    public String detailQuestionInCourse(
            @RequestParam(PARAM_ID_BANK) Long idBank,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        LOG.info("LOG ID " + id);
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        MultipleChoiceDto dto = serviceMultipleCH.getById(id);
        BankQuestionsDto bankDto = serviceBankQuestions.getById(idBank);
        model.addAttribute(ATTR_ITEM_MCH_PTO, dto.getItems());
        model.addAttribute(ATTR_QUESTION_PTO, dto);
        model.addAttribute(PARAM_ID, id.toString());
        model.addAttribute(PARAM_ID_BANK, idBank.toString());
        model.addAttribute(PARAM_ID_COURSE, idCourse.toString());
        model.addAttribute("dataJsonM", new Gson().toJson(dto.getItems()));
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        model.addAttribute(ATTR_SHORTCUT_NAME, bankDto.getName());

        return VIEW_DETAIL_QUESTION_IN_COURSE;
    }

    @ActionMapping(ACTION_REMOVE_Q_FROM_BANK_IN_COURSE)
    public void removeQFBankInCourse(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_QUIZ_BANK_PTO) Long quizBankPto,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        serviceBankQuestions.removeQ(id, quizBankPto);

        response.setRenderParameter(PARAM_ID, quizBankPto.toString());
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
        SessionMessages.add(request, "SuccessKeyremoved");
        response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_Q_IN_BANK);
        response.setRenderParameter(ATTR_SEARCH, "");
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_EDIT_Q_IN_COURSE)
    public String editQuestionInCourse(
            @RequestParam(PARAM_ID_BANK) Long idBank,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            Model model) {
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        //  if (!model.containsAttribute(ATTR_MULTIPLE_CHOICE_PTO)) {
        MultipleChoicePto pto = dtoToPtoConverter.convert(serviceMultipleCH.getById(id));

        model.addAttribute(ATTR_MULTIPLE_CHOICE_PTO, dtoToPtoConverter.convert(serviceMultipleCH.getById(id)));
        List<ItemMultiplechoicePto> listPto = new ArrayList<ItemMultiplechoicePto>();
        List<ItemMultipleChoiceDto> listDto = new ArrayList<ItemMultipleChoiceDto>();
        listDto = serviceMultipleCH.getById(id).getItems();
        for (Iterator<ItemMultipleChoiceDto> it = listDto.iterator(); it.hasNext();) {
            ItemMultipleChoiceDto itemMultipleChoiceDto = it.next();
            listPto.add(dtoToPtoConverter.convert(itemMultipleChoiceDto));
        }
        model.addAttribute(PARAM_SHORTCUT_COURSE, courseDto.getShortName());
        model.addAttribute(PARAM_ID, id);
        model.addAttribute(PARAM_ID_BANK, idBank);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(ATTR_ITEM_MCH_PTO, listPto);
        model.addAttribute("dataJson", new Gson().toJson(listPto.size()));
        //  }
        return VIEW_EDIT_Q_IN_COURSE;
    }

    @ActionMapping(ACTION_SAVE_Q_IN_COURSE)
    public void saveQuestionInCourse(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID_BANK) Long paramIdBank,
            @Valid
            @ModelAttribute(ATTR_MULTIPLE_CHOICE_PTO) MultipleChoicePto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) {
        LOG.info("som tu");
        boolean ok = true;
        String creatorUserId = request.getRemoteUser();
        for (Iterator<ItemMultiplechoicePto> i = pto.getItemsM().iterator(); i.hasNext();) {
            ItemMultiplechoicePto item = i.next();
            if (item.getText().isEmpty() || ok != true) {
                ok = false;
                break;
            }
        }
        if (!result.hasErrors() && ok == true) {
            LOG.info("dostal sa do ukladania");
            List<ItemMultipleChoiceDto> listItemM = new ArrayList<ItemMultipleChoiceDto>();
            for (ItemMultiplechoicePto item : pto.getItemsM()) {
                ItemMultipleChoiceDto itemDto = new ItemMultipleChoiceDto(null, item.getText(), item.getAnswer(), 0, null);
                listItemM.add(itemDto);
            }
            MultipleChoiceDto multipleDto = serviceMultipleCH.getById(pto.getId());
            serviceMultipleCH.removeItemM(listItemM, multipleDto);
            multipleDto.setPoints(pto.getPoints());
            multipleDto.setQuestion(pto.getQuestion());
            multipleDto.setQuestionShort(serviceHelper.cutString(STRING_CUT_SIZE, pto.getQuestion()));
            serviceMultipleCH.save(multipleDto);
            serviceMultipleCH.addItemToMultipleChoice(listItemM, multipleDto);

            SessionMessages.add(request, "SuccessKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_VIEW_Q_IN_BANK);
            response.setRenderParameter(PARAM_ID_USER, creatorUserId);
            response.setRenderParameter(ATTR_SEARCH, "");
            response.setRenderParameter(PARAM_ID, paramIdBank.toString());
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
           // response.setRenderParameter(PARAM_ID_BANK, paramIdBank.toString());
        } else {
            SessionErrors.add(request, "ErrorKey");
            response.setRenderParameter(PARAM_PAGE, PAGE_EDIT_Q_IN_COURSE);
            response.setRenderParameter(PARAM_ID, id.toString());
            response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
            response.setRenderParameter(PARAM_ID_BANK, paramIdBank.toString());
        }
    }

    @ActionMapping(ACTION_UP_QBANK)
    public void upQBank(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        BankQuestionsDto dto = serviceBankQuestions.getById(id);
        serviceBankQuestions.changePosition(true, dto);

        response.setRenderParameter(PARAM_PAGE, PAGE_Q_BANK);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
    }

    @ActionMapping(ACTION_DOWN_QBANK)
    public void downQBank(
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(PARAM_ID) Long id,
            ActionRequest request,
            ActionResponse response,
            Model model) {
        BankQuestionsDto dto = serviceBankQuestions.getById(id);
        serviceBankQuestions.changePosition(false, dto);

        response.setRenderParameter(PARAM_PAGE, PAGE_Q_BANK);
        response.setRenderParameter(PARAM_ID_COURSE, idCourse.toString());
    }
    /*END QUESTION BANK IN COURSE*/
}
