package eu.lms.portlet.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.beans.PropertyEditorSupport;

/**
 * @author Lubomir Lacika
 * This class is for time convertor format.
 */
public class JodaDateEditor extends PropertyEditorSupport {

    private DateTimeFormatter dtf;

    public JodaDateEditor(String dateTimePattern) {
        this.dtf = DateTimeFormat.forPattern(dateTimePattern);
    }

    @Override
    public String getAsText() {
        DateTime date = (DateTime) this.getValue();
        if (date != null) {
            return dtf.print(date);
        } else {
            return null;
        }

    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text != null && !text.trim().isEmpty()){
            setValue(dtf.parseDateTime(text));
        }
    }
}
