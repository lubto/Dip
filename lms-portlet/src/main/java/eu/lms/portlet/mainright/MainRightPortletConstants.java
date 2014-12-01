package eu.lms.portlet.mainright;

/**
 *
 * @author Lubomir Lacika This class contains shared constants for controllers
 * and JSP pages
 */
public class MainRightPortletConstants {

    //settings
    public static final int STRING_CUT_SIZE = 39;
    public static final int DAY_FOR_STUDING = 14;  
    public static final String PARAM_ID_USER = "userId";
    public static final String ROLE_ADMINISTRATORL_LMS = "LMS-administrator";
    public static final String ROLE_LEKTOR = "Lektor";
    
    public static final String ACTION = "action"; 
    public static final String ATTR_SEARCH = "searchAtribute";
    public static final String SEARCH_ATTR = "atributeSearch";
    public static final String ACTION_SEARCH_CATEGORY = "searchCategory";
    public static final String ACTION_SEARCH_ACCOUNTS = "searchAccounts";
    public static final String ACTION_SEARCH_COURSE = "searchCourse";
    public static final String ACTION_SEARCH_DMS_BANK = "searchDMSBank";
    public static final String ACTION_SEARCH_QUIZ_BANK = "searchQuizBank";
    public static final String ACTION_SEARCH_QUESTION = "searchQuestion";
    public static final String ACTION_SEARCH_COURSE_ITEM = "searchCourseItem";
    public static final String ACTION_SEARCH_USER_IN_COURSE = "searchUserInCourse";
    public static final String ACTION_SEARCH_QUESTIONS_IN_BANK = "searchQuestionInBank";
    public static final String ACTION_SEARCH_COURSE_ITEM_ACCOUNT = "searchCourseItemAccounts";

    public static final String SEND_ALL_CATEGORY = "allCategory";
    public static final String SEND_CREATE_CATEGORY = "createCategory";
    public static final String SEND_ALL_COURSE = "allCourse";
    public static final String SEND_CREATE_COURSE = "createCourse";
    public static final String SEND_SETUP_GROUP = "setupGroup";
    public static final String SEND_ALL_USERS = "allUsers";
    public static final String SEND_CREATE_QUIZ_BANK = "createQuizBank";
    public static final String SEND_CREATE_Q = "createQuestions";
    public static final String SEND_ALL_Q = "allQuestions";
    public static final String SEND_ADD_BANK_TO_COURSE = "addBankToCourse";
    public static final String SEND_ADD_Q_TO_COURSE = "addQuestionsToCourse";
    public static final String SEND_ALL_Q_BANK = "allQuizBank";

    public static final String PARAM_VIEW = "view";
    public static final String MAIN_VIEW = "mainadminright/view";

    public static final String PARAM_PAGE = "page";
    public static final String PAGE_DETAIL_CATEGORY_IN_COURSE = "pageDetailCategoryInCourse";

    public static final String PAGE_CATEGORY_CREATE = "categpruCreate";

    public static final String DATE_TIME_PATTERN = "dd.MM.yyyy";

    public static final String PARAM_ID = "entityId";
    public static final String PARAM_ID_COURSE = "idCourse";
    public static final String ATTR_ALL_ITEMS = "allItems";
    public static final String PARAM_DELETE_CONFIRMED = "confirmedDelete";

    public static final String ACTION_DETAIL_CATEGORY = "actionDetailCategory";

//category
    public static final String VIEW_ALL_CATEGORY = "mainadminright/category/view";
    public static final String VIEW_CREATE_FORM_CATEGORY = "mainadminright/category/view_create";
    public static final String VIEW_DETAIL_CATEGORY = "mainadminright/category/view_detail";
    public static final String VIEW_DELETE_QUESTION_CATEGORY = "mainadminright/category/view_delete_question";
    public static final String VIEW_EDIT_FORM_CATEGORY = "mainadminright/category/view_edit";
    public static final String VIEW_ALL_CATEGORY_NO_TAB = "mainadminrights/category/view_all";
    //public static final String VIEW_ALL_COURSE_BY_CATEGORY = "mainadminrights/category/view_all_course";

    public static final String ACTION_CREATE_CATEGORY = "createActionCategory";
    public static final String ACTION_SAVE_CATEGORY = "saveActionCategory";
    public static final String ACTION_DELETE_CATEGORY = "deleteActionCategory";
    public static final String ACTION_DE_ACTIVATE_CATEGORY = "actionDeActivateCategory";

    public static final String ATTR_CATEGORY_PTO = "categoryPto";

    public static final String PAGE_CREATE_FORM_CATEGORY = "addFormCategory";
    public static final String PAGE_DETAIL_CATEGORY = "detailCategory";
    public static final String PAGE_EDIT_FORM_CATEGORY = "editFormCategory";
    public static final String PAGE_DELETE_QUESTION_CATEGORY = "deleteQuest";
    public static final String PAGE_ALL_CATEGORY = "allCategory";
    public static final String PAGE_ALL_COURSE_BY_CATEGORY = "allCourseByCategory";
    //course
    public static final String VIEW_ALL_COURSE = "mainadminright/course/view";
    public static final String VIEW_CREATE_FORM_COURSE = "mainadminright/course/view_create";
    public static final String VIEW_DETAIL_COURSE = "mainadminright/course/view_detail";
    public static final String VIEW_DELETE_QUESTION_COURSE = "mainadminright/course/view_delete_question";
    public static final String VIEW_EDIT_FORM_COURSE = "mainadminright/course/view_edit";
    public static final String VIEW_BANK_OF_QUESTIONS = "mainadminright/course/questionsBank/view"; 
    public static final String VIEW_DETAIL_CATEGORY_IN_COURSE = "mainadminright/course/view_detail_category";
    public static final String VIEW_EDIT_FORM_COURSE_SETTINGS = "mainadminright/course/view_settings";
    public static final String ACTION_CREATE_COURSE = "createActionCourse";
    public static final String ACTION_SAVE_COURSE = "saveActionCourse";
    public static final String ACTION_DELETE_COURSE = "deleteActionCourse";
    public static final String ACTION_DE_ACTIVATE_COURSE = "actionActiDeactiCourse";
    public static final String ACTION_UP_LESSON = "actionUpLesson";
    public static final String ACTION_DOWN_LESSON = "actionDownLesson";
    public static final String ACTION_SETTINGS_COURSE = "actionSettingsCourse";
   // public static final String ATTR_SETTINGS_COURSE_PTO = "settingsCoursePto";
    public static final String PAGE_SETTINGS_COURSE = "pageSettingsCourse";

    public static final String ATTR_COURSE_PTO = "coursePto";
    public static final String ATTR_IS_NULL = "isNull";

    public static final String PAGE_CREATE_FORM_COURSE = "addFormCourse";
    public static final String PAGE_DETAIL_COURSE = "detailCourse";
    public static final String PAGE_EDIT_FORM_COURSE = "editFormCourse";
    public static final String PAGE_DELETE_QUESTION_COURSE = "deleteQuestionCourse";
    public static final String PAGE_ALL_COURSE = "allCourse";
    public static final String PAGE_Q_BANK = "pageQBank";

    //CourseItems
    public static final String PAGE_VIEW_USERS_IN_COURSE = "viewUsersInCourse";
    public static final String VIEW_USERS_IN_COURSE = "mainadminright/course/users/view";
    public static final String ACTION_DE_ACTIVATE_COURSE_ITEM = "deActivateCourseItem";
    public static final String PAGE_ALL_USER_COURSES = "allUsersCourses";
    public static final String PAGE_EDIT_FORM_COURSE_ITEM = "editFormCourseItem";
    public static final String ACTION_SAVE_COURSE_ITEM = "actionSaveCourseItem";
    public static final String VIEW_EDIT_COURSE_ITEM = "mainadminright/course/users/view_edit";
    public static final String ATTR_COURSE_ITEM_PTO = "coruseItemPto";
    public static final String ACTION_REMOVE_COURSE_ITEM = "actionRemoveCourseItem";
    public static final String VIEW_ITEM_COURSE_BY_USER = "mainadminright/accountUser/view_course_item_by_user";
    public static final String VIEW_COURSE_ITEMS = "mainadminright/accountUser/view_course_items";
    public static final String ATTR_SHORTCUT_NAME = "shortcutName";
    public static final String ACTION_DE_ACTIVATE_ITEM_COURSE = "actionDeActiItemCourse";
    public static final String PARAM_ID_ACCOUNT = "idAccount";
    public static final String PAGE_EDIT_FORM_COURSE_ITEM_I = "pageEdFormCourseItemI";
    public static final String VIEW_EDIT_COURSE_ITEM_I = "mainadminright/accountUser/view_edit_course_item";
    public static final String ACTION_SAVE_COURSE_ITEM_I = "actionSaveCourseItemI";
    public static final String ACTION_REMOVE_COURSE_ITEM_I = "actionRemoveCourseItemI";
    public static final String ATTR_COURSE_NAME = "courseName";
//Lesson
    public static final String VIEW_ALL_LESSON = "mainadminright/lesson/view";
    public static final String VIEW_CREATE_FORM_LESSON = "mainadminright/lesson/view_create";
    public static final String VIEW_DETAIL_LESSON = "mainadminright/lesson/view_detail";
    public static final String VIEW_DELETE_QUESTION_LESSON = "mainadminright/lesson/view_delete_question";
    public static final String VIEW_EDIT_FORM_LESSON = "mainadminright/lesson/view_edit";
    public static final String VIEW_ALL_LESSON_NEXT = "mainadminright/lesson/view_all_lesson_next";

    public static final String ACTION_CREATE_LESSON = "createActionLesson";
    public static final String ACTION_SAVE_LESSON = "saveActionLesson";
    public static final String ACTION_DELETE_LESSON = "deleteActionLesson";
    public static final String ACTION_DE_ACTIVATE_LESSON = "actionDeActivateLesson";

    public static final String ATTR_LESSON_PTO = "lessonPto";
    public static final String ATTR_LESSON_SERIAL_NUMBER = "lessonSerialNumber";
    public static final String ATTR_LESSON_SIZE = "lessonSize";

    public static final String PAGE_CREATE_FORM_LESSON = "addFormLesson";
    public static final String PAGE_DETAIL_LESSONS = "detailLessons";
    public static final String PAGE_DETAIL_LESSON = "detailLesson";
    public static final String PAGE_EDIT_FORM_LESSON = "editFormLesson";
    public static final String PAGE_DELETE_QUESTION_LESSON = "deleteQuestionLesson";
    public static final String PAGE_ALL_LESSON = "pageAllLesson";

    public static final String VIEW_ALL_LESSON_NO_TAB = "course/lesson/view_all";
    public static final String ATTR_FOR_EDITOR = "editor";
    public static final String PAGE_ALL_LESSON_NEXT = "allNextLesson";

    //settings
    public static final String VIEW_ADD_COURSE_TO_USER = "mainadminright/course/users/view_add_course_to_user";
    public static final String PAGE_ADD_COURSE_TO_USER = "addCourseToUser";
    public static final String ATTR_USERS_LIST = "userList";
    public static final String ATTR_USERS_LIST_SELECTED = "userListSelected";
    public static final String ATTR_USER_PTO = "userPto";
    public static final String ACTION_POKUS = "pokus";
    public static final String ACTION_CREATE_ACCOUNTITEM_USER = "createAccounItemUser";
    public static final String PAEGE_SHOW_USERGROUP = "pageShowUsherGroup";

    public static final String PAGE_SETUP_GROUP = "setupGroup";
    public static final String VIEW_SETUP_GROUP = "mainadminright/accountUser/view_setup_group";
    public static final String ACTION_SHOW_USERGROUP = "actionShowUserGroup";

    public static final String PAGE_SHOW_TEXT = "showText";
    public static final String VIEW_SHOW_TEXT = "mainadminright/accountUser/view";
    public static final String ATTR_GROUP_LIST = "groupList";
    public static final String ATTR_SELECT_GROUP = "selectGroup";
    //
    public static final String PAGE_ALL_USERS = "allAccountUser";
    public static final String VIEW_ALL_ACCOUNT_USER = "mainadminright/accountUser/view_all_account_users";
    public static final String ATTR_ACCOUNT_USER_PTO = "accountUserPto";
    public static final String PAGE_ITEMS_USER = "showAllAccountUser";
    public static final String PAGE_DEACTIVATE_ACCOUNT = "deactivateAccount";
    public static final String PAGE_ACTIVATE_ACCOUNT = "activateAccount";
    public static final String PAGE_DELETE_QUESTION_ACCOUNTUSER = "deleteQuestinAccountUser";
    public static final String ACTION_DELETE_ACCOUNTUSER = "actionDeleteAccountUser";
    public static final String ACTION_DE_ACTIVATE_ACCOUN = "actionDeActivateAccount";
    public static final String PARAM = "param";

    public static final String ACTION_ADD_USERS_TO_COURSE = "actionAddUsersToCourse";
    public static final String ATTR_COURSE_ID = "courseId";
    //Quiz
    public static final String VIEW_EDIT_Q_BANK = "mainadminright/quiz/view_edit_q_bank";
    public static final String VIEW_CREATE_Q = "mainadminright/quiz/view_create_q";
    public static final String VIEW_ALL_Q_BANK = "mainadminright/quiz/view_all_q_bank";
    public static final String VIEW_ALL_Q = "mainadminright/quiz/view_all_q";
    public static final String VIEW_ADD_Q_TO_BANK = "mainadminright/quiz/view_add_q_to_bank";
    public static final String VIEW_ADD_BANK_TO_COURSE = "mainadminright/quiz/view_add_bank_to_course";
    public static final String VIEW_ADD_TO_BANK = "mainadminright/quiz/view_add_to_bank";
    public static final String VIEW_ALL_Q_IN_BANK = "mainadminright/quiz/view_all_q_in_bank";
    public static final String VIEW_REMOVE_QUESTION_FROM_BANK = "mainadminright/quiz/view_remove_q_from_bank";
    public static final String VIEW_EDIT_FORM_MULTIPLECHOICE = "mainadminright/quiz/view_edit_q";

    public static final String ACTION_SAVE_Q = "saveQ";

    public static final String VIEW_ADD_TO_COURSE = "mainadminright/quizBank/view_add_to_course";

    public static final String PAGE_CREATE_Q_BANK = "createQuizBank";
    public static final String PAGE_CREATE_Q = "createQuestions";
    public static final String PAGE_ALL_Q = "allQuestions";
    public static final String PAGE_ADD_BANK_TO_COURSE = "addBankToCourse";
    public static final String PAGE_ADD_Q_TO_BANK = "addQuestionsToCourse";
    public static final String PAGE_ALL_Q_BANK = "allQuizBank";
    public static final String PAGE_EDIT_FORM_MULTIPLECHOICE = "editFormMultiplechoice";

    public static final String ACTION_CREATE_Q_TF = "actionCreateQuestionTrueFalse";
    public static final String ATTR_TRUEFALSE_PTO = "turefalsePto";

    public static final String ACTION_CREATE_Q_MCH = "actionCreateMultiplechoice";
    public static final String ATTR_MULTIPLE_CHOICE_PTO = "multipleChoicePto";
    public static final String ATTR_ITEM_MCH_PTO = "itemMPto";
    public static final String ATTR_ALL_MULTIPLE = "itemsAllMultiple";

    public static final String ACTION_CREATE_Q_MATCHING = "actionCreateQMatching";
    public static final String ATTR_MATCHING_PTO = "matchingPto";
    public static final String ATTR_ITEM_MATCHING_PTO = "itemMatchingPto";
    public static final String ATTR_ALL_MATCHING = "itemsAllMatching";

    //quiz bank
    public static final String ATTR_QUIZ_BANK_PTO = "quizBankPto";
    public static final String ACTION_CREATE_Q_BANK = "actionCreateQBank";
    public static final String PAGE_EDIT_Q_BANK = "editQuizBank";
    public static final String ACTION_SAVE_Q_BANK = "actionSaveQBank";
    public static final String PAGE_ADD_TO_BANK = "pageAddToBank";
    public static final String ATTR_LIST_BANKS_Q = "listBanksQ";
    public static final String ACTION_SAVE_ADD_TO_BANK = "actionSaveAddToBank";

    public static final String PARAM_TYPE_Q = "typeQ";
    public static final String PAGE_VIEW_ALL_Q_IN_BANK = "viewAllQuestionsInBank";

    public static final String PAGE_DELETE_Q_TRUE_FALSE = "deleteQuestionTrueFalse";

    public static final String ACTION_DELETE_Q_T_F = "actionDeleteQTF";

    public static final String ATTR_QUESTION_PTO = "questionPto";

    public static final String ACTION_REMOVE_Q_FROM_BANK = "actionRemoveQFromBank";
    public static final String PAGE_REMOVE_Q_FROM_BANK = "removeQFromBanks";

    public static final String PAGE_DELETE_Q_BANK = "deleteQBank";
    public static final String ACTION_DELETE_Q_BANK = "actionDeleteQBank";

    public static final String PAGE_ADD_TO_COURSE = "addToCourse";
    public static final String PAGE_ADD_MORE_QUESTIONS = "pageAddMoreQuestions";
    public static final String ACTION_SAVE_ADD_TO_COURSE = "actionSaveAddToCourse";
    public static final String ACTION_SAVE_ADD_Q_TO_TEST = "actionSaveAddQToTest";
    public static final String PAGE_REMOVE_Q_FROM_COURSE = "removeQFromCourse";
    public static final String VIEW_REMOVE_QUESTION_FROM_COURSE = "mainadminright/quizBank/view_remove_bank_from_course";
    public static final String ACTION_REMOVE_Q_FROM_COURSE = "actionRemoveQFromCourse";

    public static final String PAGE_DETAIL_QUESTION = "detailQuestions";
    public static final String VIEW_DETAIL_QUESTION = "mainadminright/quiz/view_detail_question";
    public static final String VIEW_ADD_MORE_QUESTIONST_TO_TEST = "mainadminright/quizBank/view_add_more_questions_to_test";
    // public static final String ACTION_TEST_MATCHING_Q = "actionTestMatchingQ";

    //DMS
    public static final String PAGE_ALL_GROUP_DOC = "allDocuments";
    public static final String VIEW_ALL_GROUP_DOC = "mainadminright/dms/groupDocuments/view_all";
    public static final String VIEW_CREATE_GROUP_DOC = "mainadminright/dms/groupDocuments/view_create";
    public static final String PAGE_CREATE_GROUP_DOC = "createGroupDoc";
    public static final String ATTR_GROUP_DOC_PTO = "groupDocPto";
    public static final String ACTION_CREATE_GROUP_DOC = "actionCreateGroupDoc";
    public static final String ACTION_UPLOAD_DMS = "actionUploadDms";
    public static final String ACTION_UP_GDOCUMENTS = "actionUpGDocuments";
    public static final String ACTION_DOWN_GDOCUMENTS = "actionDownGDocuments";
    //item Doc
    public static final String VIEW_UPLOAD_ITEM = "mainadminright/dms/itemDocument/view_upload";
    public static final String VIEW_Q_CREATE_ITEM = "mainadminright/dms/itemDocument/view_question";
    public static final String PAGE_ADD_ITEM_DOC = "addItemDoc";
    public static final String PAGE_Q_ADD_ITEM_DOC = "addQItemDoc";
    public static final String PAGE_UPLOAD_ITEM_DOC = "uploadItemDoc";
    public static final String VIEW_CREATE_ITEM_DOC = "mainadminright/dms/itemDocument/view_create";
    public static final String ATTR_ITEM_DOC_PTO = "itemDocPto";
    public static final String ACTION_ADD_ITEM_DOC = "acitonAddItemDoc";
    public static final String PARAM_ID_GROUP = "idGroup";
    public static final String VIEW_DETAIL_ITEMS_DOC = "mainadminright/dms/itemDocument/view_detail";
    public static final String PAGE_DETAIL_ITEM_DOC = "detailItemDoc";
    public static final String ATTR_SIZE_ITEMS = "sizeItems";
    public static final String PAGE_REMOVE_ITEM_DOC = "removeItemDoc";
    public static final String VIEW_REMOVE_ITEMS_DOC = "mainadminright/dms/itemDocument/view_remove";
    public static final String ACTION_DELETE_ITEM_DOC = "deleteItemDoc";
    public static final String ACTION_DELETE_ITEM_DOC_FROM_REPO = "deleteItemDocFromRepo";
    public static final String PARAM_ID_ITEM_DOC = "itemDoc";
    public static final String PAGE_DELETE_Q_ITEM_DOC = "deleteItemDoc";
    public static final String VIEW_DELETE_QUESTION_ITEM_DOC = "";/*mainadminright/dms/itemDocument/view_delete_question*/

    public static final String ACTION_DELETE_GROUP_DOC = "actionDeleteGroupDoc";
    public static final String PAGE_DETAIL_GROUP_DOC = "detailGroupDoc";
    public static final String VIEW_DETAIL_GROUP_DOC = "mainadminright/dms/groupDocuments/view_detail";
    public static final String PAGE_EDIT_GROUP_DOC = "editGroupDoc";
    public static final String VIEW_EDIT_FROM_GROUP_DOC = "mainadminright/dms/groupDocuments/view_edit";
    public static final String ACTION_EDIT_GROUP_DOC = "actionEditGroupDoc";
    public static final String ACTION_DE_ACTIVATE_GROUP_DOC = "actionDeActivateGroupDoc";
    public static final String PARAM_SHORTCUT_COURSE = "shortcutC";
    
    public static final String NAME_OF_REPOSITARE= "LMS-repo";
    //question bank in course
    public static final String PAGE_EDIT_Q_BANK_IN_COURSE ="pageEditQBankInCourse"; 
    public static final String VIEW_EDIT_Q_BANK_IN_COURSE = "mainadminright/course/questionsBank/view_edit_q_bank";
    public static final String ACTION_SAVE_Q_BANK_IN_BANK = "actionSaveQBankInBank";
    public static final String ACTION_REMOVE_Q_BANK_FROM_COURSE = "actionRemoveQBankFromCourse";
    public static final String PAGE_VIEW_Q_IN_BANK = "pageViewQInBank"; 
    public static final String VIEW_Q_BANK_IN_COURSE = "mainadminright/course/questionsBank/view_all_q_in_bank";
    public static final String PAGE_DETAIL_QUESTION_IN_COURSE = "pageDetailQInCourse";
    public static final String ACTION_REMOVE_Q_FROM_BANK_IN_COURSE = "actionRemoveQFBInCourse";
    public static final String VIEW_DETAIL_QUESTION_IN_COURSE = "mainadminright/course/questionsBank/view_detail_question";
    public static final String PARAM_ID_BANK = "paramIdBank";
    public static final String VIEW_EDIT_Q_IN_COURSE = "mainadminright/course/questionsBank/view_edit_q";
    public static final String PAGE_EDIT_Q_IN_COURSE = "pageEditQInCourse";
    public static final String ACTION_SAVE_Q_IN_COURSE = "actionSaveQInCourse";
    public static final String ACTION_UP_QBANK = "actionUpQbank";
    public static final String ACTION_DOWN_QBANK = "actionDoqnQbank";
}
