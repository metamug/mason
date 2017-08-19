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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.sql.DataSource;
import org.json.JSONObject;

/**
 *
 * @author Kaisteel
 */
public class GroupTagHandler extends BodyTagSupport implements TryCatchFinally {

    private String value;
    @Resource(name = "jdbc/mtgMySQL")
    private DataSource ds;

    /**
     * Creates new instance of tag handler
     */
    public GroupTagHandler() {
        super();
    }

    /**
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE. This method is automatically generated. Do not modify this method. Instead, modify the
     * methods that this method calls.
     * @throws javax.servlet.jsp.JspException
     */
    @Override
    public int doEndTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        MtgRequest mtg = (MtgRequest) pageContext.getRequest().getAttribute("mtgReq");
        String header = request.getHeader("Authorization");
        try {
            if (header != null) {
                if (header.contains("Basic ")) {
                    String token = header.replaceFirst("Basic ", "");
                    String userCred = new String(Base64.getDecoder().decode(token.getBytes()));
                    String[] split = userCred.split(":");
                    if (split.length > 1 && !split[0].isEmpty() && !split[1].isEmpty()) {
                        JSONObject status = validateUser(split[0], split[1]);
                        switch (status.getInt("status")) {
                            case -1:
                                throw new JspException("Forbidden Access to resource.", new RoleAccessDeniedException(""));
                            case 0:
                                throw new JspException("Access Denied to resource due to unauthorization.", new RoleAuthorizationException(""));
                            case 1:
                                mtg.setUid(String.valueOf(status.getInt("user_id")));
                                pageContext.getRequest().setAttribute("mtgReq", mtg);
                                break;
                        }
                    } else {
                        throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
                    }
                } else {
                    throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
                }
            } else {
                throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
            }
        } catch (IllegalArgumentException ex) {
            throw new JspException("Access Denied due to unauthorization.", new RoleAuthorizationException(""));
        }
        return EVAL_PAGE;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
    }

    private JSONObject validateUser(String userName, String password) {
        JSONObject status = new JSONObject();
        status.put("status", 0);
        try (Connection con = ds.getConnection();) {
            PreparedStatement statement = con.prepareStatement("SELECT user_id FROM user WHERE user_name=? AND password=?");
            statement.setString(1, userName);
            statement.setString(2, password);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PreparedStatement stmnt = con.prepareStatement("SELECT count(id) as total_count FROM user_role WHERE role_name=? AND user_id=?");
                    stmnt.setString(1, value);
                    stmnt.setInt(2, resultSet.getInt("user_id"));
                    try (ResultSet result = stmnt.executeQuery()) {
                        while (result.next()) {
                            if (result.getInt("total_count") > 0) {
                                status.put("status", 1);
                                status.put("user_id", resultSet.getInt("user_id"));
                            } else {
                                status.put("status", -1);
                            }
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GroupTagHandler.class.getName()).log(Level.SEVERE, ex.getMessage());
        }
        return status;
    }

    private static class RoleAuthorizationException extends Exception {

        public RoleAuthorizationException(String message) {
            super(message);
        }
    }

    private static class RoleAccessDeniedException extends Exception {

        public RoleAccessDeniedException(String message) {
            super(message);
        }
    }

}
