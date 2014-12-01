package eu.lms.portlet.userleft;

import static eu.lms.portlet.userleft.MainCardLeftPortletConstants.*; 
import javax.portlet.ActionResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;
import javax.xml.namespace.QName;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author Lubomir Lacika
 * This class is base controller for VIEW mode.
 */
@Controller
@RequestMapping("VIEW")
public class MainCardLeftPortletViewController {

    protected final Logger LOG = Logger.getLogger(MainCardLeftPortletViewController.class);

    @RenderMapping
    public String doView(Model model) { 
        return VIEW_MAIN;
    }

    @ActionMapping(ACTION_SEND)
    public void detailMyCourses(
            @RequestParam(PARAM_ID) String id, 
            ActionResponse response) {  
        
        QName qname = new QName("http://liferay.com", "basketinfo"); 
        response.setEvent(qname, id);  
    } 
}
