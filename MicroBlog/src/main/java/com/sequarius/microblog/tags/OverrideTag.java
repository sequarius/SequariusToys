package com.sequarius.microblog.tags;

/**
 * Created by Sequarius on 2015/6/1.
 */
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
/**
 * @author badqiu
 */
public class OverrideTag extends BodyTagSupport{
    private static final long serialVersionUID = -8379959647039117369L;

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int doStartTag() throws JspException {
        return isOverrided() ? SKIP_BODY : EVAL_BODY_BUFFERED;
    }

    @Override
    public int doEndTag() throws JspException {
        if(isOverrided()) {
            return EVAL_PAGE;
        }
        BodyContent b = getBodyContent();
//		System.out.println("Override.content:"+b.getString());
        String varName = Utils.getOverrideVariableName(name);

        pageContext.getRequest().setAttribute(varName, b.getString());
        return EVAL_PAGE;
    }

    private boolean isOverrided() {
        String varName = Utils.getOverrideVariableName(name);
        return pageContext.getRequest().getAttribute(varName) != null;
    }

}
