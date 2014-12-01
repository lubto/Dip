package eu.lms.portlet.userright;

import com.google.gson.Gson;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.DiffHtmlUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserGroupGroupRole;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupGroupRoleLocalServiceUtil;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;
import com.sun.org.apache.xpath.internal.operations.Mult;
import eu.lms.iface.dto.AccountUserDto;
import eu.lms.iface.dto.BankQuestionsDto;
import eu.lms.iface.dto.CategoryDto;
import eu.lms.iface.dto.CourseDto;
import eu.lms.iface.dto.CourseItemDto;
import eu.lms.iface.dto.GroupDocumentsDto;
import eu.lms.iface.dto.HelperUserBankDto;
import eu.lms.iface.dto.ItemAttemptsDto;
import eu.lms.iface.dto.ItemMultipleChoiceDto;
import eu.lms.iface.dto.LessonDto;
import eu.lms.iface.dto.MultipleChoiceDto;
import eu.lms.iface.dto.UserBankQDto;
import eu.lms.iface.service.AccountUserService;
import eu.lms.iface.service.BankQuestionsService;
import eu.lms.iface.service.CategoryService;
import eu.lms.iface.service.CourseItemService;
import eu.lms.iface.service.CourseService;
import eu.lms.iface.service.GroupDocumentsService;
import eu.lms.iface.service.HelperService;
import eu.lms.iface.service.ItemMultipleChoiceService;
import eu.lms.iface.service.MultipleChoiceService;
import eu.lms.iface.service.UserBankQService;
import static eu.lms.portlet.mainright.MainRightPortletConstants.DATE_TIME_PATTERN;
import eu.lms.portlet.mainright.pto.EmailPto;
import static eu.lms.portlet.userright.MainCardRightPortletConstants.*;
import eu.lms.portlet.util.JodaDateEditor;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
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
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
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
 */
@Controller
@RequestMapping("VIEW")
public class MainCardRightPortletViewController {

    protected final Logger LOG = Logger.getLogger(MainCardRightPortletViewController.class);

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(DateTime.class, new JodaDateEditor(DATE_TIME_PATTERN));
    }

    @Autowired
    private AccountUserService serviceAccountUser;

    @Autowired
    private CourseService serviceCourse;

    @Autowired
    private CourseItemService serviceCourseItem;

    @Autowired
    private BankQuestionsService serviceBankQuestions;

    @Autowired
    private ItemMultipleChoiceService serviceItemMultipleChoice;

    @Autowired
    private MultipleChoiceService serviceMultipleChoice;

    @Autowired
    private GroupDocumentsService serviceGroupDoc;

    @Autowired
    private HelperService serviceHelper;

    @Autowired
    private UserBankQService serviceUserBankQ;

    @Autowired
    private CategoryService serviceCategory;

    @RenderMapping
    public String doView(
            Model model) {

        return MAIN_VIEW;
    }

    @EventMapping(value = "{http://liferay.com}basketinfo")
    public void detailMyFinishCourses(
            EventRequest req,
            EventResponse res) throws javax.portlet.PortletException, java.io.IOException {
        Event event = req.getEvent();
        String value = (String) event.getValue();
        String userId = req.getRemoteUser();

        ThemeDisplay themeDisplay = null;
        themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
        long repositoryId = themeDisplay.getScopeGroupId();
        
        if (value.equals(SEND_COURSE_FOR_STUDY)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
            res.setRenderParameter(ATTR_CUR_ID_USER, userId);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_FINISH_COURSE)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_FINISH_COURSE);
            res.setRenderParameter(ATTR_CUR_ID_USER, userId);
            res.setRenderParameter(ATTR_SEARCH, "");
        } else if (value.equals(SEND_QUESTION)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_SEND_EMAIL);
            res.setRenderParameter(ATTR_CUR_ID_USER, userId);
            res.setRenderParameter(PARAM_ID_REPOZ, String.valueOf(repositoryId));
        } else if (value.equals(SEND_SEACHR_COURSE)) {
            res.setRenderParameter(PARAM_PAGE, PAGE_CATEGORY); //PAGE_ALL_FINISH_COURSE
            res.setRenderParameter(ATTR_CUR_ID_USER, userId);
        } else {
            res.setRenderParameter(PARAM_PAGE, "");
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_COURSE)
    public String viewAllCourse(@RequestParam(ATTR_SEARCH) String searchAttr,
            Model model,
            @RequestParam(ATTR_CUR_ID_USER) String userId) {

        try {
            AccountUserDto dto = serviceAccountUser.getAccountUserByUserId(userId);
            if (dto.getActivate() == true) {
                //check if is finished course, if is visible and if time is up.
                List<CourseItemDto> itemDto = dto.getListCourses();
                for (Iterator<CourseItemDto> it = itemDto.iterator(); it.hasNext();) {
                    CourseItemDto courseItemDto = it.next();
                    CourseDto courseDto = serviceCourse.getCourseById(Long.valueOf(courseItemDto.getCourseId()));
                    courseItemDto.setNameCourse(courseDto.getName());
                    if (courseItemDto.isFinishedCourse() == true) {
                        it.remove();
                    } else if (serviceCourse.getCourseById(Long.valueOf(courseItemDto.getCourseId())).isVisible() != true) {
                        it.remove();
                    } else if (courseItemDto.getVisible() == false) {
                        it.remove();
                    }/*
                     long timeNow = System.currentTimeMillis();
                     Date date = new Date(timeNow);
                     if (courseItemDto.getEndCourse().before(date)) {
                     courseItemDto.setTimesUp(true);
                     } else {
                     courseItemDto.setTimesUp(false);
                     }*/

                }
                itemDto = serviceCourseItem.checkTimeIsUpList(itemDto);
                if (itemDto != null || !itemDto.isEmpty()) {
                    Collections.sort(itemDto);
                }
                if (searchAttr == null || searchAttr.isEmpty()) {
                    model.addAttribute(ATTR_ALL_ITEMS, itemDto);
                } else {
                    model.addAttribute(ATTR_ALL_ITEMS, serviceCourseItem.searchInListByCourse(searchAttr, itemDto));
                }

                model.addAttribute(ATTR_SEARCH, searchAttr);
                model.addAttribute(ATTR_CUR_ID_USER, userId);

                return VIEW_ALL_COURSE;
            } else {
                return VIEW_NO_ACCOUNT;
            }
        } catch (Exception e) {
            return VIEW_NO_ACCOUNT;
        }
    }

    @ActionMapping(ACTION_SEARCH_ALL_COURSE)
    public void searchCourse(
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        String userId = request.getRemoteUser();
        response.setRenderParameter(ATTR_CUR_ID_USER, userId);
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
    }

    // zobrazi  jednotlive lekcie za sebou cez next tlacidlo  
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_START_COURSE)
    public String allLesson(
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(ATTR_ID_COURSE_ITEM) Long courseItem,
            @RequestParam(ATTR_VISITED_COURSE) Boolean visitedCourse,
            Model model) {
        //nastavi CourseItem hodnotu true ze bola otvorena :visit
        if (visitedCourse == false) {
            serviceCourseItem.changeVisitCourseItem(courseItem, Boolean.TRUE);
        }
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<LessonDto> listLessonDto = serviceCourse.getAllLesson(courseDto);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());
        if (listLessonDto == null) {

            return VIEW_NO_LESSON;
        }
        //ak najde jednotku tak prida atributy pre posuvanie lekcii
        for (int i = 0; i < listLessonDto.size(); i++) {
            if (listLessonDto.get(i).getSerialNumber() == 1) {
                model.addAttribute(ATTR_LESSON_PTO, listLessonDto.get(i));
                model.addAttribute(ATTR_LESSON_SERIAL_NUMBER, 1);
                model.addAttribute(ATTR_LESSON_SIZE, listLessonDto.size());
            }
        }
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());

        return VIEW_ALL_LESSON_NEXT;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_LESSON_NEXT)
    public String allLessonNext(
            @RequestParam(PARAM_ID) int number,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            Model model) {
        CourseDto courseDto = serviceCourse.getCourseById(idCourse);
        List<LessonDto> listLessonDto = serviceCourse.getAllLesson(courseDto);

        for (int i = 0; i < listLessonDto.size(); i++) {
            if (listLessonDto.get(i).getSerialNumber() == number) {
                model.addAttribute(ATTR_LESSON_PTO, listLessonDto.get(i));
                model.addAttribute(ATTR_LESSON_SERIAL_NUMBER, listLessonDto.get(i).getSerialNumber());
                model.addAttribute(ATTR_LESSON_SIZE, listLessonDto.size());
            }
        }
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(PARAM_ID_COURSE, idCourse);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());

        return VIEW_ALL_LESSON_NEXT;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_FINISH_COURSE)
    public String showFinishCourse(@RequestParam(ATTR_SEARCH) String searchAttr,
            Model model,
            @RequestParam(ATTR_CUR_ID_USER) String userId) {
        try {
            AccountUserDto dto = serviceAccountUser.getAccountUserByUserId(userId);
            if (dto.getActivate() == true) {
                List<CourseItemDto> itemDto = dto.getListCourses();
                for (Iterator<CourseItemDto> it = itemDto.iterator(); it.hasNext();) {
                    CourseItemDto courseItemDto = it.next();
                    CourseDto courseDto = serviceCourse.getCourseById(Long.valueOf(courseItemDto.getCourseId()));
                    courseItemDto.setNameCourse(courseDto.getName());
                    if (courseItemDto.isFinishedCourse() != true) {
                        it.remove();
                    }
                    if (courseItemDto.getVisible() == false) {
                        it.remove();
                    }
                }
                if (itemDto != null || !itemDto.isEmpty()) {
                    Collections.sort(itemDto);
                }
                for (Iterator<CourseItemDto> it = itemDto.iterator(); it.hasNext();) {
                    CourseItemDto courseItemDto = it.next();
                    courseItemDto.setVisibleDocuments(serviceCourse.getCourseById(Long.valueOf(courseItemDto.getCourseId())).isVisibleDocuments());
                    courseItemDto.setVisibleLessons(serviceCourse.getCourseById(Long.valueOf(courseItemDto.getCourseId())).isVisibleLessons());
                    courseItemDto.setVisibleQuestions(serviceCourse.getCourseById(Long.valueOf(courseItemDto.getCourseId())).isVisibleQuestions());
                }
                
                
                if (searchAttr == null || searchAttr.isEmpty()) {
                    model.addAttribute(ATTR_ALL_ITEMS, itemDto);
                } else {
                    model.addAttribute(ATTR_ALL_ITEMS, serviceCourseItem.searchInListByCourse(searchAttr, itemDto));
                }
                model.addAttribute(ATTR_SEARCH, searchAttr);
                model.addAttribute(ATTR_CUR_ID_USER, userId);

                return VIEW_FINISH_COURSE;
            } else {
                return VIEW_NO_ACCOUNT;
            }
        } catch (Exception e) {
            return VIEW_NO_ACCOUNT;
        }
    }

    @ActionMapping(ACTION_SEARCH_ALL_COURSE_FINISH)
    public void searchCourseFinish(
            ActionRequest request,
            ActionResponse response,
            Model model) {
        String searchRequest = ParamUtil.getString(request, "ASearchRequest");
        String userId = request.getRemoteUser();
        response.setRenderParameter(ATTR_CUR_ID_USER, userId);
        response.setRenderParameter(ATTR_SEARCH, searchRequest);
        response.setRenderParameter(PARAM_PAGE, PAGE_FINISH_COURSE);
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_INFO_Q)
    public String viewInfoResultTests(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(ATTR_ID_COURSE_ITEM) Long courseItemId,
            Model model) {
        CourseItemDto courseItem = serviceCourseItem.getCourseItemById(courseItemId);
        CourseDto courseDto = serviceCourse.getCourseById(id);
        model.addAttribute(ATTR_ID_COURSE_ITEM, courseItemId);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());
        if (courseItem.getUserBankQ() == null) {
            return VIEW_NO_TEST;
        }
        List<HelperUserBankDto> listBQ = new ArrayList<HelperUserBankDto>();
        List<UserBankQDto> listUserBankQ = courseItem.getUserBankQ();
        for (UserBankQDto userBankQDto : listUserBankQ) {
            BankQuestionsDto dto = serviceBankQuestions.getById(Long.valueOf(userBankQDto.getBankQuestions()));
            HelperUserBankDto helper = new HelperUserBankDto();
            helper.setName(dto.getName());
            helper.setDescription(dto.getDescription());
            helper.setPoints(userBankQDto.getPoints());
            helper.setAttempts(userBankQDto.getListAttempts().size());
            listBQ.add(helper);
        }
        model.addAttribute(ATTR_BANK_Q_PTO, listBQ);
        model.addAttribute(PARAM_ID, id.toString());

        return VIEW_INFO_TEST_RESULT;
    }

//    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_FINISH_COURSE)
//    public String showAllFinishCourse(
//            @RequestParam(ATTR_CUR_ID_USER) String userId,
//            Model model) {
//        try {
//            AccountUserDto dto = serviceAccountUser.getAccountUserByUserId(userId);
//            if (dto.getActivate() == true) {
//                return VIEW_ALL_FINISH_COURSE;
//            } else {
//                return VIEW_NO_ACCOUNT;
//            }
//        } catch (Exception e) {
//            return VIEW_NO_ACCOUNT;
//        }
//    }
    //test
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_TESTS)
    public String viewTests(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(ATTR_ID_COURSE_ITEM) Long courseItemId,
            Model model) {

        CourseItemDto courseItem = serviceCourseItem.getCourseItemById(courseItemId);
        CourseDto courseDto = serviceCourse.getCourseById(id);
        LOG.info("log id coureItem " + courseItemId);
        LOG.info("log id coure" + id);
        model.addAttribute(ATTR_ID_COURSE_ITEM, courseItemId);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());
        if (courseItem.getUserBankQ() == null) {
            return VIEW_NO_TEST;
        } /*else if (courseItem.getUserBankQ() == null) {
         return VIEW_NO_TEST;
         }*/

        List<BankQuestionsDto> listBQ = new ArrayList<BankQuestionsDto>();
        List<UserBankQDto> listUserBankQ = courseItem.getUserBankQ();
        for (UserBankQDto userBankQDto : listUserBankQ) {
            BankQuestionsDto dto = serviceBankQuestions.getById(Long.valueOf(userBankQDto.getBankQuestions()));

            if (userBankQDto.isDone()) {
                dto.setNameIcon("/images/testIconOk.png");
                dto.setCompleted(true);
            } else {
                dto.setNameIcon("/images/testIcon.png");
                dto.setCompleted(false);
            }
            dto.setIdUserBankQ(String.valueOf(userBankQDto.getId()));
            listBQ.add(dto);
        }
        Collections.sort(listBQ);
        model.addAttribute(ATTR_BANK_Q_PTO, listBQ);
        model.addAttribute(PARAM_ID, id.toString());

        return VIEW_TESTS;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_TEST_Q)
    public String testQuestion(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(PARAM_ID_BANK) Long idBank,
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(ATTR_ID_COURSE_ITEM) Long courseItemId,
            @RequestParam(ATTR_USER_BANKQ) Long idUserBankQ,
            Model model) {
        CourseDto courseDto = serviceCourse.getCourseById(id);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());
        model.addAttribute(ATTR_ID_COURSE_ITEM, courseItemId);
        if (courseDto.getBankQuestionsDto() == null) {
            return VIEW_NO_TEST;
        }
        BankQuestionsDto dto = serviceBankQuestions.getById(idBank);
        model.addAttribute(PARAM_ID, dto.getId());
        model.addAttribute(ATTR_BANK_NAME, dto.getName());
        model.addAttribute(PARAM_ID_COURSE, id);
        model.addAttribute(ATTR_BANK_Q_PTO, serviceBankQuestions.generateTestQuestions(dto));
        model.addAttribute(ATTR_USER_BANKQ, idUserBankQ);

        return VIEW_QUIZ_TEST;
    }
////////////// servicee

    @ActionMapping(ACTION_CHECK_TEST)
    public void checkTest(
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_CUR_ID_USER) Long userId,
            @RequestParam(ATTR_USER_BANKQ) Long idUserBankQ,
            @RequestParam(PARAM_LIST) List<String> list,
            @RequestParam(PARAM_LIST_2) List<String> list2,
            ActionRequest request,
            ActionResponse response) {
        List<MultipleChoiceDto> multiple = serviceHelper.changeTwoStringToMultiple(list, list2);
        Map<String, String[]> newMap = new HashMap<String, String[]>();
        //create list answers and count points for right questions
        for (Iterator<MultipleChoiceDto> it = multiple.iterator(); it.hasNext();) {
            MultipleChoiceDto m = it.next();
            for (Iterator<ItemMultipleChoiceDto> it1 = m.getItems().iterator(); it1.hasNext();) {
                ItemMultipleChoiceDto item = it1.next();
                newMap.put(item.getId().toString(), new String[]{item.isAnswer().toString(), String.valueOf(m.getPoints())});
            }
        }
        int points = 0;
        Map<String, String[]> mapa = request.getParameterMap();
        List<String> sendIds = new ArrayList<String>();
        for (Map.Entry<String, String[]> entry : mapa.entrySet()) {
            for (Map.Entry<String, String[]> entry1 : newMap.entrySet()) {
                if (entry.getKey().equals(entry1.getKey())) {

                    String[] pole = entry.getValue();
                    String[] pole1 = entry1.getValue();
                    sendIds.add(entry.getKey());
                    if (pole[0].equals(pole1[0])) {
                        ItemMultipleChoiceDto itemMul = serviceItemMultipleChoice.getById(Long.valueOf(entry.getKey()));
                        MultipleChoiceDto multipleDto = serviceMultipleChoice.getById(itemMul.getMultipleChoiceDto().getId());
                        List<ItemMultipleChoiceDto> listIt = multipleDto.getItems();
                        int poc = 0;
                        for (Map.Entry<String, String[]> entry2 : mapa.entrySet()) {
                            for (Iterator<ItemMultipleChoiceDto> it = listIt.iterator(); it.hasNext();) {
                                ItemMultipleChoiceDto itemMultipleChoiceDto = it.next();
                                if (entry2.getKey().equals(String.valueOf(itemMultipleChoiceDto.getId()))) {
                                    poc = poc + 1;

                                }
                            }
                        }

                        if (poc == 1) {
                            points = points + Integer.valueOf(pole1[1]);
                        }
                    }
                }
            }
        }
        LOG.info("Points : " + points);

        BankQuestionsDto dto = serviceBankQuestions.getById(id);
        int sumPoints = 0;
        List<String> question = serviceHelper.removeBrackets(list);
        for (Iterator<String> it = question.iterator(); it.hasNext();) {
            String string = it.next();
            MultipleChoiceDto dtoM = serviceMultipleChoice.getById(Long.valueOf(string));
            sumPoints = sumPoints + dtoM.getPoints();
        }

        UserBankQDto userBankQDto = serviceUserBankQ.getById(idUserBankQ);
        boolean ok = false;
        int result = (100 / sumPoints) * points;
        if (dto.getInPercentDone() <= result) {
            ok = true;
            userBankQDto.setDone(true);
        }
        ItemAttemptsDto itemAttempts = new ItemAttemptsDto(null, null, points);
        serviceUserBankQ.addItemAttempts(itemAttempts, userBankQDto);

        response.setRenderParameter(ATTR_POINTS, String.valueOf(points));
        response.setRenderParameter(ATTR_ANSWER, sendIds.toString());
        response.setRenderParameter(ATTR_CUR_ID_USER, userId.toString());
        response.setRenderParameter(PARAM_ID, id.toString());
        response.setRenderParameter(PARAM_LIST, serviceHelper.removeBrackets(list).toString());
        response.setRenderParameter(PARAM_LIST_2, serviceHelper.removeBrackets(list2).toString());
        response.setRenderParameter(ATTR_BANK_NAME, dto.getName());
        response.setRenderParameter(PARAM_PAGE, PAGE_RESULT_TEST);
        response.setRenderParameter(ATTR_USER_BANKQ, idUserBankQ.toString());
    }

    //view correct answers
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_RESULT_TEST)
    public String resultTestQuestion(
            @RequestParam(ATTR_POINTS) int points,
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(ATTR_ANSWER) List<String> sendIds,
            @RequestParam(PARAM_ID) Long id,
            @RequestParam(ATTR_BANK_NAME) String bankName,
            @RequestParam(PARAM_LIST) List<String> list,
            @RequestParam(PARAM_LIST_2) List<String> list2,
            @RequestParam(ATTR_USER_BANKQ) Long idUserBankQ,
            Model model) {
        List<MultipleChoiceDto> multiple = serviceHelper.changeTwoStringToMultiple(list, list2);
        List<String> listAnswers = serviceHelper.removeBrackets(sendIds);
        List<ItemMultipleChoiceDto> itemsList = new ArrayList<ItemMultipleChoiceDto>();
        for (Iterator<MultipleChoiceDto> it = multiple.iterator(); it.hasNext();) {
            MultipleChoiceDto multipleChoiceDto = it.next();
            for (Iterator<ItemMultipleChoiceDto> itt = multipleChoiceDto.getItems().iterator(); itt.hasNext();) {
                ItemMultipleChoiceDto item = itt.next();
                itemsList.add(item);
            }
        }
        BankQuestionsDto dto = serviceBankQuestions.getById(id);
        UserBankQDto userBankQ = serviceUserBankQ.getById(idUserBankQ);
        model.addAttribute(ATTR_BANK_PERCENT, dto.getInPercentDone());
        model.addAttribute(ATTR_BANK_Q_PTO, multiple);
        model.addAttribute(ATTR_ANSWER, listAnswers);
        model.addAttribute(ATTR_POINTS, points);
        model.addAttribute("userAnswer", new Gson().toJson(listAnswers));
        model.addAttribute("allAnswer", new Gson().toJson(itemsList));
        model.addAttribute(PARAM_ID, dto.getId());
        model.addAttribute(ATTR_BANK_NAME, bankName);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_USER_BANKQ, userBankQ);
        return VIEW_CHECK_Q;
    }

    //DMS
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_DMS)
    public String showDms(
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        CourseDto courseDto = serviceCourse.getCourseById(id);
        if (courseDto.getGroupDocuments() == null) {
            model.addAttribute(ATTR_CUR_ID_USER, userId);
            model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());

            return VIEW_NO_DMS;
        }
        List<GroupDocumentsDto> groupDto = courseDto.getGroupDocuments();
        for (Iterator<GroupDocumentsDto> it = groupDto.iterator(); it.hasNext();) {
            GroupDocumentsDto groupDocumentsDto = it.next();
            if (groupDocumentsDto.isVisible() == false) {
                it.remove();
            } else {
                serviceGroupDoc.addIconToItems(groupDocumentsDto.getItemDocumentDto());
            }
        }
        if (groupDto != null || !groupDto.isEmpty()) {
            Collections.sort(groupDto);
        }
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_GROUP_DOC_PTO, groupDto);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());

        return VIEW_DMS;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_INFO_COURSE)
    public String viewInfoCourse(
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(PARAM_ID) Long id,
            Model model) {
        CourseDto courseDto = serviceCourse.getCourseById(id);
        model.addAttribute(PARAM_COURSE_PTO, courseDto);
        model.addAttribute(ATTR_CUR_ID_USER, userId);
        model.addAttribute(ATTR_SHORT_CUT_COURSE, courseDto.getShortName());

        return VIEW_INFO_COURSE;
    }

    //email
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_SEND_EMAIL)
    public String createFormEmail(
            @RequestParam(ATTR_CUR_ID_USER) String userId,   
            Model model) {
        try {
            AccountUserDto dto = serviceAccountUser.getAccountUserByUserId(userId);
            if (dto.getActivate() == true) {
                User user = UserLocalServiceUtil.getUserById(Long.valueOf(userId));
                EmailPto pto = new EmailPto();
                pto.setFrom(user.getEmailAddress());
                
                List<UserGroup> userGroup = UserGroupLocalServiceUtil.getUserGroups(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
                List<User> listUser = new ArrayList<User>();
                List<EmailPto> listEmails = new ArrayList<EmailPto>();
                for (UserGroup userGroup1 : userGroup) { 
                    if (ROLE_LEKTOR.equals(userGroup1.getName())) { 
                               listUser = UserLocalServiceUtil.getUserGroupUsers(userGroup1.getPrimaryKey());
                               break;
                    }
                }
                 boolean isNull = false;
                if(listUser != null || !listUser.isEmpty()){ 
                    for (Iterator<User> it = listUser.iterator(); it.hasNext();) {
                        User user1 = it.next();
                        EmailPto emailPto = new EmailPto();
                        emailPto.setTo(user1.getFullName() + " <" +user1.getEmailAddress() + ">");
                        emailPto.setUserId(String.valueOf(user1.getPrimaryKey())); 
                        listEmails.add(emailPto);
                    }
                }else if(listUser == null){
                    LOG.info("null");
                 isNull = true;
                    
                     model.addAttribute(ATTR_IS_NULL, isNull);
                     model.addAttribute(ATTR_EMAIL_PTO, pto);
                     model.addAttribute(ATTR_CUR_ID_USER, userId);
                     
                   return VIEW_CREATE_FORM_EMAIL;  
                } 
                model.addAttribute(ATTR_IS_NULL, isNull);
                model.addAttribute(ATTR_LIST_EMAILS, listEmails);
                model.addAttribute(ATTR_EMAIL_PTO, pto);
                model.addAttribute(ATTR_CUR_ID_USER, userId);

                return VIEW_CREATE_FORM_EMAIL;
            } else {
                return VIEW_NO_ACCOUNT;
            }
        } catch (Exception e) {
            return VIEW_NO_ACCOUNT;
        }
    }

    @ActionMapping(ACTION_SEND_EMAIL)
    public void sendEmail(
            @Valid @ModelAttribute(ATTR_EMAIL_PTO) EmailPto pto,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) throws AddressException, UnsupportedEncodingException, PortalException, SystemException {
        String userId = request.getRemoteUser();
        User user = UserLocalServiceUtil.getUserById(Long.valueOf(userId));
        LOG.info("sending mail... ");
        String getValueFromEditor = ParamUtil.getString(request, "htmlCodeFromEditorPlacedHere");
         
        if (!result.hasErrors() && (!getValueFromEditor.isEmpty())) {
            LOG.info("Log from " + user.getEmailAddress());
            LOG.info("Log subject " + pto.getSubject()); 
            LOG.info("Log message " + getValueFromEditor);
            LOG.info("TO : " + pto.getTo());
            
//           senderEmail("lubto.lacika@gmail.com","gx112@azet.sk", pto.getSubject(), pto.getTo(), request);
            try {
                InternetAddress to = new InternetAddress(pto.getTo());
                InternetAddress from = new InternetAddress(user.getEmailAddress());
                MailMessage mailMessage = new MailMessage();
                mailMessage.setTo(to);
                mailMessage.setFrom(from);
                mailMessage.setSubject(pto.getSubject());
                mailMessage.setBody(getValueFromEditor);
                mailMessage.setHTMLFormat(true);
                MailServiceUtil.sendEmail(mailMessage); // Sending message
                SessionMessages.add(request, "KeySend");
                response.setRenderParameter(ATTR_CUR_ID_USER, userId);
                response.setRenderParameter(PARAM_PAGE, PAGE_SEND_EMAIL);
            } catch (Exception e) {
                SessionMessages.add(request, "ErrorKeySend");
                response.setRenderParameter(ATTR_CUR_ID_USER, userId);
                response.setRenderParameter(PARAM_PAGE, PAGE_SEND_EMAIL);
                e.printStackTrace();
            }
        } else {
            SessionErrors.add(request, "ErrorKeySend");
            response.setRenderParameter(ATTR_CUR_ID_USER, userId);
            response.setRenderParameter(PARAM_PAGE, PAGE_SEND_EMAIL);
        }
    }

    //catgory
    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CATEGORY)
    public String viewCategory(
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            Model model) {
        try {
            AccountUserDto dto = serviceAccountUser.getAccountUserByUserId(userId);
            if (dto.getActivate() == true) {
                LOG.info("LOG CATEGORY");
                List<CategoryDto> listC = new ArrayList<CategoryDto>();
                listC = serviceCategory.getFreeCategory();
                if (listC == null || listC.size() == 0) {
                    model.addAttribute(ATTR_CUR_ID_USER, userId);
                    return VIEW_NO_CATEGORY;
                }
                model.addAttribute(ATTR_CATEGORY_PTO, serviceCategory.getFreeCategory());
                model.addAttribute(ATTR_CUR_ID_USER, userId);

                return VIEW_CATEGORY;
            } else {
                return VIEW_NO_ACCOUNT;
            }
        } catch (Exception e) {
            return VIEW_NO_ACCOUNT;
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_FREE_COURSES)
    public String viewFreeCourses(
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(PARAM_ID) Long idCategory,
            Model model) {
        CategoryDto dto = serviceCategory.getCategoryById(idCategory);
        List<CourseDto> listDto = new ArrayList<CourseDto>();
        listDto = serviceCourse.getAllCourseByCategory(dto);
        if (listDto == null || listDto.size() == 0) {
            model.addAttribute(ATTR_CUR_ID_USER, userId);

            return VIEW_NO_FREE_COURSE;
        }
        List<CourseItemDto> listCItems = serviceAccountUser.getAllCourseItems(serviceAccountUser.getAccountUserByUserId(userId));
        for (Iterator<CourseDto> it = listDto.iterator(); it.hasNext();) {
            CourseDto courseDto = it.next();
            for (CourseItemDto courseItemDto : listCItems) {

                if (courseItemDto.getCourseId().equals(String.valueOf(courseDto.getId()))) {
                    LOG.info("add true " + courseItemDto.getCourseId() + " - " + courseDto.getName());
                    courseDto.setAdded(true);
                }
            }
        }
        model.addAttribute(ATTR_COURSE_PTO, listDto);
        model.addAttribute(ATTR_CUR_ID_USER, userId);

        return VIEW_FREE_COURSE;
    }

    @ActionMapping(ACTION_ADD_USERS_TO_COURSE)
    public void addUsersToCourse(
            @RequestParam(ATTR_CUR_ID_USER) String userId,
            @RequestParam(PARAM_ID_COURSE) Long idCourse,
            ActionRequest request,
            ActionResponse response,
            Model model) throws PortalException, SystemException {
        CourseDto dto = serviceCourse.getCourseById(idCourse);
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
        cal.add(Calendar.DATE, 14); // add 14 days   
        Date endDate = cal.getTime();
        AccountUserDto accountDto = serviceAccountUser.getAccountUserByUserId(userId);
        CourseItemDto courseI = new CourseItemDto(null, userId, String.valueOf(idCourse), accountDto, false, false, date, endDate, true, " ", false, " ", null,dto.isVisibleDocuments(), dto.isVisibleQuestions(), dto.isVisibleLessons());
        serviceAccountUser.addCourseItemToAccountUser(courseI, accountDto.getId(), listUserBankQ);

        SessionMessages.add(request, "SuccessKey");
        response.setRenderParameter(PARAM_ID, idCourse.toString());
        response.setRenderParameter(ATTR_CUR_ID_USER, userId);
        response.setRenderParameter(ATTR_SEARCH, "");
        response.setRenderParameter(PARAM_PAGE, PAGE_ALL_COURSE);
    }

    //DMS
    @ActionMapping(ACTION_DOCUMENT)
    public void document(
            ActionRequest request,
            ActionResponse response) throws SystemException, PortalException {

        //String userId = request.getRemoteUser();
        //ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        //long scopedGroupId = themeDisplay.getScopeGroupId();
        ///  LOG.info("scope id " + scopedGroupId);
//        
//        Long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;
//        LOG.info("LOG parent folder " + parentFolderId); // vypisalo 0
        List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getDLFolders(0, DLFolderLocalServiceUtil.getDLFoldersCount());
        for (DLFolder folder : dlFolders) {
            LOG.info("Folder Id >> " + folder.getFolderId());
            LOG.info("Folder Name >>" + folder.getName());
            ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
            List<DLFileEntry> fileEntryService = null;
            try {
                fileEntryService = DLFileEntryLocalServiceUtil.getFileEntries(themeDisplay.getScopeGroupId(), folder.getFolderId(), -1, -1, null); //12345 is folderId  
                LOG.info("file size " + fileEntryService.size());
            } catch (SystemException e) {
                LOG.info("chyba-  vynimka");
                e.printStackTrace();
            }
            for (DLFileEntry fileEntryObj : fileEntryService) {
                LOG.info("Log - title " + fileEntryObj.getTitle());
            }
        }
    }

//    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ALL_FINISH_COURSE)
//    public String viewFreeCourses(
//            Model model) {
//
//        return VIEW_UPLOAD_DMS;
//    }
//
//    @ActionMapping(ACTION_UPLOAD_DMS)
//    public void uploadDms(
//            ActionRequest request,
//            ActionResponse response) throws PortletException, SystemException, IOException, PortalException {
//        LOG.info("ulpoad doc");
//
//        List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getDLFolders(0, DLFolderLocalServiceUtil.getDLFoldersCount());
//        DLFolder folder = null;
//        for (DLFolder folder1 : dlFolders) {
//            LOG.info("Folder Id >> " + folder1.getFolderId());
//            LOG.info("Folder Name >>" + folder1.getName());
//            if (folder1.getName().equals("uploadLms")) {
//                LOG.info("yes equals");
//                folder = folder1;
//                break;
//            }
//        }
//        //LOG.info("RealPath" + realPath + " UploadFolder :" + folder);
//        try {
//            LOG.info("Siamo nel try");
//            UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(request);
//            System.out.println("Size: " + uploadRequest.getSize("fileName"));
//
//            if (uploadRequest.getSize("fileName") == 0) {
//                LOG.info("EROR pri nacitavani ");//SessionErrors.add(actionRequest, "error");
//            }
//
//            String sourceFileName = uploadRequest.getFileName("fileName");
//            File file = uploadRequest.getFile("fileName");
//           // LOG.info("Nome file:" + uploadRequest.getFileName("fileName"));
//            LOG.info("Nome file:" + file.getName());
//            LOG.info("Nome path:" + file.getPath());
//            File newFile = null;
//            newFile = new File(sourceFileName);
//           // newFile = new File(folder.getTreePath() + "\\"+sourceFileName); 
//            LOG.info("New file name: " + newFile.getName());
//            LOG.info("New file path: " + newFile.getPath());
//
//            InputStream in = new BufferedInputStream(uploadRequest.getFileAsStream("fileName"));
//            LOG.info("info");
//            FileInputStream fis = new FileInputStream(file);
//            LOG.info("info1");
//            FileOutputStream fos = new FileOutputStream(newFile);
//            
//            byte[] bytes_ = FileUtil.getBytes(in);
//            int i = fis.read(bytes_);
//            LOG.info("info2");
//            while (i != -1) {
//                fos.write(bytes_, 0, i);
//                i = fis.read(bytes_);
//            }
//            fis.close();
//            fos.close();
//            Float size = (float) newFile.length();
//            LOG.info("file size bytes:" + size);
//            LOG.info("file size Mb:" + size / 1048576);
//            LOG.info("File created: " + newFile.getName());
//            LOG.info("Path: " + newFile.getPath());
//            ServiceContext serviceContext = ServiceContextFactory.getInstance(request);
//            ThemeDisplay themeDisplay = null;
//            themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
//            long repositoryId = themeDisplay.getScopeGroupId();
//            LOG.info("repozitary id: " + repositoryId);
//            DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.addFileEntry(10201, repositoryId, repositoryId, folder.getFolderId(), file.getName() + ".png", MimeTypesUtil.getContentType(file), "titles", "description", "sss",
//                    0, null, file, null, file.length(), serviceContext);
//
//            DLFileEntryLocalServiceUtil.updateFileEntry(10201, dlFileEntry.getFileEntryId(), file.getName() + ".png", MimeTypesUtil.getContentType(file), "titles", "descriptionn", "comment", true, dlFileEntry.getFileEntryTypeId(), null, file, null, file.length(), serviceContext);
//
//        } catch (NullPointerException e) {
//            System.out.println("File Not Found");
//            e.printStackTrace();
//            //SessionMessages.add(actionRequest, "error");
//        }
//
//    }
}
