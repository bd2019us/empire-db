package org.apache.empire.struts2.jsp.tags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.empire.struts2.action.ListPagingInfo;
import org.apache.empire.struts2.html.HtmlTagDictionary;
import org.apache.empire.struts2.html.HtmlWriter;
import org.apache.empire.struts2.html.HtmlWriter.HtmlTag;
import org.apache.struts2.components.Component;

import com.opensymphony.xwork2.util.ValueStack;


@SuppressWarnings("serial")
public class ListPageInfoTag extends EmpireTagSupport // AbstractRemoteCallUITag
{
    protected ListPagingInfo pagingInfo; 
    protected String label;
    protected String of;
    
    /*
     * Clears all params since tag is reused
     */
    @Override
    protected void resetParams()
    {   // LinkTag
        pagingInfo = null; 
        label = null;
        of = null;
        // reset base params
        super.resetParams();
    }

    @Override
    public Component getBean(ValueStack stack, HttpServletRequest req, HttpServletResponse res)
    {
        return null;
    }
    
    @Override
    public int doStartTag() throws JspException
    {
        HtmlTagDictionary dic = HtmlTagDictionary.getInstance();
        HtmlWriter w = new HtmlWriter(pageContext.getOut());
        
        HtmlTag div = w.startTag(dic.PageInfoTag());
        addStandardAttributes(div, dic.PageInfoClass());
        div.beginBody();
        // Add Label
        w.print(getString(str(label, dic.PageInfoLabel())));
        w.print(dic.PageInfoLabelPadding());
        // Add first item
        HtmlTag first = w.startTag(dic.PageInfoItemTag());
        first.endTag(String.valueOf(pagingInfo.getFirstItemIndex()+1));
        // Add Separator
        w.print(dic.PageInfoLabelTo());
        // Add last item
        HtmlTag last = w.startTag(dic.PageInfoItemTag());
        last.endTag(String.valueOf(pagingInfo.getLastItemIndex()+1));
        // Add of label
        w.print(dic.PageInfoLabelPadding());
        w.print(getString(str(of, dic.PageInfoLabelOf())));
        w.print(dic.PageInfoLabelPadding());
        // Add item count
        HtmlTag count = w.startTag(dic.PageInfoItemTag());
        count.endTag(String.valueOf(pagingInfo.getItemCount()));
        // end
        div.endTag();
        // Don't call base class
        return SKIP_BODY; // EVAL_BODY_BUFFERED; // EVAL_BODY_INCLUDE;
    }
    
    @Override
    public int doEndTag()
        throws JspException
    {   // done
        resetParams();
        return EVAL_PAGE;
    }
    
    // ------- Property setters -------

    public void setPagingInfo(ListPagingInfo pagingInfo)
    {
        this.pagingInfo = pagingInfo;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public void setOf(String of)
    {
        this.of = of;
    }

}
