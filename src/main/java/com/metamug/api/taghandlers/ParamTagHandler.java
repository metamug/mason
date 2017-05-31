/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.metamug.api.taghandlers;

import com.metamug.api.common.MtgRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

/**
 *
 * @author Kaisteel
 */
public class ParamTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String name;
    private String type;
    private Boolean isRequired;
    private Double max;
    private Double min;
    private Integer maxLen;
    private Integer minLen;
    private String pattern;
    private String exists;
    private String defaultValue;
    private String value;

    /**
     * Creates new instance of tag handler
     */
    public ParamTagHandler() {
        super();
    }

    /**
     * This method is called after the JSP engine finished processing the tag.
     *
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE. This method is automatically generated. Do not modify this method. Instead, modify the
     * methods that this method calls.
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public int doEndTag() throws JspException {
        MtgRequest mtg = (MtgRequest) pageContext.getRequest().getAttribute("mtgReq");
        mtg.getParams().put(name, value);
        if (isRequired != null && isRequired) {
            if (value == null && defaultValue != null) {
                value = defaultValue;
                mtg.getParams().put(name, value);
            } else if (value == null && defaultValue == null) {
                throw new JspException(name + " parameter can't be null", new InputValidationException(""));
            }
        } else {
            if (value == null && defaultValue != null) {
                value = defaultValue;
                mtg.getParams().put(name, value);
            }
        }
        if (pattern != null) {
            try {
                if (!((String) value).matches(pattern)) {
                    throw new JspException("Input value doesn't match with specified pattern of " + name + " parameter", new InputValidationException(""));
                }
            } catch (PatternSyntaxException ex) {
                throw new JspException("Incorrect pattern syntax of " + name + " parameter", ex);
            } catch (NullPointerException ex) {
                throw new JspException(name + " parameter can't be null", new InputValidationException(""));
            }
        }
        if (type != null) {
            Pattern regexPattern;
            Matcher matcher;
            switch (type.toLowerCase()) {
                case "date":
                    if (pattern == null) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            sdf.setLenient(false);
                            sdf.parse(value);
                        } catch (ParseException ex) {
                            throw new JspException("Incorrect date pattern of " + name + " parameter", new InputValidationException(""));
                        } catch (NullPointerException ex) {
                            throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                        }
                    }
                    break;
                case "datetime":
                    if (pattern == null) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            sdf.setLenient(false);
                            System.out.println("value:" + value);
                            sdf.parse(value);
                        } catch (ParseException ex) {
                            throw new JspException("Incorrect datetime pattern of " + name + " parameter", ex);
                        } catch (NullPointerException ex) {
                            throw new JspException(name + " parameter can't be null", ex);
                        }
                    }
                    break;
                case "email":
                    String emailPattern
                            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
                    regexPattern = Pattern.compile(emailPattern);
                    try {
                        matcher = regexPattern.matcher(value);
                        if (!matcher.matches()) {
                            throw new JspException("Invalid email input", new InputValidationException(""));
                        }
                    } catch (NullPointerException ex) {
                        throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                    }
                    break;
                case "number":
                    try {
                        double val = Double.parseDouble(value);
                        if (max != null) {
                            double maxVal = max;
                            if (val > maxVal) {
                                throw new JspException("Max value allowed for " + name + " is " + maxVal, new InputValidationException(""));
                            }
                        }
                        if (min != null) {
                            double minVal = min;
                            if (val < minVal) {
                                throw new JspException("Min value allowed for " + name + " is " + minVal, new InputValidationException(""));
                            }
                        }
                    } catch (NumberFormatException ex) {
                        throw new JspException("Empty or invalid parameter \'" + name + "\' value", new InputValidationException(""));
                    } catch (NullPointerException ex) {
                        throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                    }
                    break;
                case "text":
                    if (maxLen != null) {
                        double maxLength = maxLen;
                        try {
                            if (value.length() > maxLength) {
                                throw new JspException("Input ((String)value) can be " + maxLength + " character long for " + name + " parameter", new InputValidationException(""));
                            }
                        } catch (NullPointerException ex) {
                            throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                        }
                    }
                    if (minLen != null) {
                        double minLength = minLen;
                        try {
                            if (value.length() < minLength) {
                                throw new JspException("Input value must be " + minLength + " character long for " + name + " parameter", new InputValidationException(""));
                            }
                        } catch (NullPointerException ex) {
                            throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                        }
                    }
                    break;
                case "time":
                    if (pattern == null) {
                        try {
                            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                            sdf.setLenient(false);
                            sdf.parse(value);
                        } catch (ParseException ex) {
                            throw new JspException("Incorrect time pattern of " + name + " parameter", new InputValidationException(""));
                        } catch (NullPointerException ex) {
                            throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                        }
                    }
                    break;
                case "url":
                    String urlPattern = "(?:(?:https?|ftp)://)(?:\\S+(?::\\S*)?@)?(?:(?!10(?:\\.\\d{1,3}){3})(?!127(?:\\.\\d{1,3}){3})(?!169\\.254(?:\\.\\d{1,3}){2})(?!192\\.168(?:\\.\\d{1,3}){2})(?!172\\.(?:1[6-9]|2\\d|3[0-1])(?:\\.\\d{1,3}){2})(?:[1-9]\\d?|1\\d\\d|2[01]\\d|22[0-3])(?:\\.(?:1?\\d{1,2}|2[0-4]\\d|25[0-5])){2}(?:\\.(?:[1-9]\\d?|1\\d\\d|2[0-4]\\d|25[0-4]))|(?:(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}0-9]+-?)*[a-z\\x{00a1}-\\x{ffff}0-9]+)*(?:\\.(?:[a-z\\x{00a1}-\\x{ffff}]{2,})))(?::\\d{2,5})?(?:/[^\\s]*)?";
                    regexPattern = Pattern.compile(urlPattern);
                    try {
                        matcher = regexPattern.matcher(value);
                        if (!matcher.matches()) {
                            throw new JspException("Invalid URL input", new InputValidationException(""));
                        }
                    } catch (NullPointerException ex) {
                        throw new JspException(name + " parameter can't be null", new InputValidationException(""));
                    }
                    break;
                default:
            }
        }

        pageContext.getRequest().setAttribute("mtgReq", mtg);
        release();
        return EVAL_PAGE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setIsRequired(boolean required) {
        this.isRequired = required;
    }

    public void setMax(String max) {
        this.max = Double.parseDouble(max);
    }

    public void setMin(String min) {
        this.min = Double.parseDouble(min);
    }

    public void setMaxLen(String maxLen) {
        this.maxLen = Integer.parseInt(maxLen);
    }

    public void setMinLen(String minLen) {
        this.minLen = Integer.parseInt(minLen);
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public void setExists(String exists) {
        this.exists = exists;
    }

    public void setValue(String value) {
        if (value.isEmpty()) {
            this.value = null;
        } else {
            this.value = value;
        }
    }

    public void setDefaultValue(String defaultValue) {
        if (defaultValue.isEmpty()) {
            this.defaultValue = null;
        } else {
            this.defaultValue = defaultValue;
        }
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        name = null;
        type = null;
        isRequired = null;
        max = null;
        min = null;
        maxLen = null;
        minLen = null;
        pattern = null;
        exists = null;
        defaultValue = null;
        value = null;
    }

    private static class InputValidationException extends Throwable {

        public InputValidationException(String message) {
            super(message);
        }
    }
}
