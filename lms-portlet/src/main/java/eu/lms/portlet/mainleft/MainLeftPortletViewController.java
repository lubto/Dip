package eu.lms.portlet.mainleft;

import static eu.lms.portlet.mainleft.MainLeftPortletConstants.*;
import javax.portlet.ActionResponse;
import javax.xml.namespace.QName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 *
 * @author Lubomir Lacika
 * This class is base controller for VIEW mode.
 */
@Controller
@RequestMapping("VIEW")
public class MainLeftPortletViewController {

    @RenderMapping
    public String doView(Model model) {

        return MAIN_VIEW;
    }

    @ActionMapping(ACTION_SEND)
    public void detailMyCourses(
            @RequestParam(PARAM_ID) String id,
            @RequestParam(ATTR) String word,
            ActionResponse response) {

        QName qadmin = new QName("http://liferay.com", "admininfo");
        response.setEvent(qadmin, id);
        if (word.equals("k")) {
            response.setRenderParameter(PARAM_PAGE, PAGE_CATEGORY);
        } else if (word.equals("c")) {
            response.setRenderParameter(PARAM_PAGE, PAGE_COURSE);
        } else if (word.equals("q")) {
            response.setRenderParameter(PARAM_PAGE, PAGE_QUESTIONS);
        } else if (word.equals("a")) {
            response.setRenderParameter(PARAM_PAGE, PAGE_ACCOUNT);
        }
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_CATEGORY)
    public String detailCategory(Model model) {

        return VIEW_CATEGORY;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_COURSE)
    public String detailCourse(Model model) {

        return VIEW_COURSE;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_QUESTIONS)
    public String detailQuestions(Model model) {

        return VIEW_QUESTIONS;
    }

    @RenderMapping(params = PARAM_PAGE + "=" + PAGE_ACCOUNT)
    public String detailAccount(Model model) {

        return VIEW_ACCOUNTS;
    }
}
