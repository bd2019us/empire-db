package org.apache.empire.struts2.jsp.controls;

import java.util.Locale;

import org.apache.empire.commons.StringUtils;
import org.apache.empire.data.Column;
import org.apache.empire.struts2.html.HtmlWriter;
import org.apache.empire.struts2.html.HtmlWriter.HtmlTag;
import org.apache.empire.struts2.web.WebErrors;


public class PasswordInputControl extends InputControl
{
    public static final String PASSWORD_NOT_CHANGED = "********";

    
    private int getMinPasswordLength()
    {
        return 6;
    }
    
    // ------- parsing -------

    @Override
    protected Object parseValue(String value, Locale locale, Column column)
    {
        // Has password changed
        if (value.equals(PASSWORD_NOT_CHANGED))
            return NO_VALUE;
        // Default
        return value;
    }

    @Override
    protected Object validate(Object value, Locale locale, Column column, String s)
    {
        String pwd = StringUtils.toString(value);
        int minLength = getMinPasswordLength();
        if (pwd!=null && pwd.length()<minLength)
        {
            return error(WebErrors.InputTextTooShort, minLength, s);
        }
        // Default
        return value;
    }
    
    // ------- formatting -------

    @Override
    protected String formatValue(Object value, ValueInfo vi, boolean hasError)
    {
        String pwd = StringUtils.toString(value);
        if (hasError)
            return "";
        // Replace by Default-Mask
        return (StringUtils.isValid(pwd) ? PASSWORD_NOT_CHANGED : null);
    }

    @Override
    public void renderInput(HtmlWriter writer, ControlInfo ci)
    {
        HtmlTag input = writer.startTag("input");
        input.addAttribute("type", "password");
        input.addAttribute("id",    ci.getId());
        input.addAttribute("class", ci.getCssClass());
        input.addAttribute("style", ci.getCssStyle());
        if (ci.getDisabled()==false)
        {   // Name of the field
            input.addAttribute("name", ci.getName());
            // Get Max Length
            int maxLength = (int)ci.getColumn().getSize();
            if (maxLength>0)
            {
                input.addAttribute("maxlength", maxLength);
                input.addAttribute("size", String.valueOf(Math.min(maxLength, ci.getHSize())));
            }   
        }
        else
        {   // Disabled text control
            input.addAttribute("disabled");
            // Get Max Length
            int maxLength = (int)ci.getColumn().getSize();
            if (maxLength>0)
            {
                input.addAttribute("size", String.valueOf(Math.min(maxLength, ci.getHSize())));
            }   
        }
        // Value
        input.addAttribute("value",     formatValue(ci));
        // Event Attributes
        input.addAttribute("onclick",   ci.getOnclick());
        input.addAttribute("onchange",  ci.getOnchange());
        input.addAttribute("onfocus",   ci.getOnfocus());
        input.addAttribute("onblur",    ci.getOnblur());
        input.endTag();
    }
    
}