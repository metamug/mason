/** ***********************************************************************
 * Freeware Licence Agreement
 *
 * This licence agreement only applies to the free version of this software.
 *
 * Terms and Conditions
 *
 * BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENCE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 * PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 * IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENCE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 * THIS DOCUMENT CONSTITUTES A LICENCE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 * The Software is licensed to you without charge for use only upon the terms of this licence, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 * 1. Licence
 *
 * You may use the Software without charge.
 *
 * You may freely distribute exact copies of the Software to anyone.
 *
 * The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 * The selling of the Software is strictly prohibited.
 * 2. Restrictions
 *
 * METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 * YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 * The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 *
 * 3. Termination
 *
 * This licence is effective until terminated. The Licence will terminate automatically without notice from METAMUG if you fail to comply with any provision of this Licence. Upon termination you must destroy the Software and all copies thereof. You may terminate this Licence at any time by destroying the Software and all copies thereof. Upon termination of this licence for any reason you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 * 4. Disclaimer of Warranty, Limitation of Remedies
 *
 * TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 * IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 * IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 *
 * 5. General
 *
 * All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 * This Agreement shall be governed by the laws of the State of Maharastra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharastra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
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
