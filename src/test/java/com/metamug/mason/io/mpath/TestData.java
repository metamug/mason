/** ***********************************************************************
 *Freeware License Agreement
 *
 *This license agreement only applies to the free version of this software.
 *
 *Terms and Conditions
 *
 *BY DOWNLOADING, INSTALLING, USING, TRANSMITTING, DISTRIBUTING OR COPYING THIS SOFTWARE ("THE SOFTWARE"), YOU AGREE TO THE TERMS OF THIS AGREEMENT (INCLUDING THE SOFTWARE LICENSE AND DISCLAIMER OF WARRANTY) WITH METAMUG THE OWNER OF ALL RIGHTS IN RESPECT OF THE SOFTWARE.
 *
 *PLEASE READ THIS DOCUMENT CAREFULLY BEFORE USING THE SOFTWARE.
 *
 *IF YOU DO NOT AGREE TO ANY OF THE TERMS OF THIS LICENSE THEN DO NOT DOWNLOAD, INSTALL, USE, TRANSMIT, DISTRIBUTE OR COPY THE SOFTWARE.
 *
 *THIS DOCUMENT CONSTITUTES A LICENSE TO USE THE SOFTWARE ON THE TERMS AND CONDITIONS APPEARING BELOW.
 *
 *The Software is licensed to you without charge for use only upon the terms of this license, and METAMUG TECHNOLOGIES LLP (hereafter METAMUG) reserves all rights not expressly granted to you. METAMUG retains ownership of all copies of the Software.
 *
 *1. License
 *
 *You may use the Software without charge.
 *
 *You may freely distribute exact copies of the Software to anyone.
 *
 *The inclusion of the Software in any Shareware, Freeware or similar media compilation or distribution method whereby it is made available at cost (ie. sold) is strictly prohibited.
 *
 *The selling of the Software is strictly prohibited.
 *2. Restrictions
 *
 *METAMUG reserves the right to revoke the above distribution right at any time, for any or no reason.
 *
 *YOU MAY NOT MODIFY, ADAPT, TRANSLATE, RENT, LEASE, LOAN, SELL, ONSELL, REQUEST DONATIONS OR CREATE DERIVATIVE WORKS BASED UPON THE SOFTWARE OR ANY PART THEREOF.
 *
 *The Software contains intellectual property and to protect them you may not decompile, reverse engineer, disassemble or otherwise reduce the Software to a humanly perceivable form. You agree not to divulge, directly or indirectly, until such intellectual property cease to be confidential, for any reason not your own fault.
 *
 *3. Termination
 *
 *This license is effective until terminated. The License will terminate automatically without notice from METAMUG if you fail to comply with any provision of this License. Upon termination you must destroy the Software and all copies thereof. You may terminate this License at any time by destroying the Software and all copies thereof. Upon termination of this license for any reason you shall continue to be bound by the provisions of Section 2 above. Termination will be without prejudice to any rights METAMUG may have as a result of this agreement.
 *
 *4. Disclaimer of Warranty, Limitation of Remedies
 *
 *TO THE FULL EXTENT PERMITTED BY LAW, METAMUG HEREBY EXCLUDES ALL CONDITIONS AND WARRANTIES, WHETHER IMPOSED BY STATUTE OR BY OPERATION OF LAW OR OTHERWISE, NOT EXPRESSLY SET OUT HEREIN. THE SOFTWARE, AND ALL ACCOMPANYING FILES, DATA AND MATERIALS ARE DISTRIBUTED "AS IS" AND WITH NO WARRANTIES OF ANY KIND, WHETHER EXPRESS OR IMPLIED. METAMUG DOES NOT WARRANT, GUARANTEE OR MAKE ANY REPRESENTATIONS REGARDING THE USE, OR THE RESULTS OF THE USE, OF THE SOFTWARE WITH RESPECT TO ITS CORRECTNESS, ACCURACY, RELIABILITY, CURRENTNESS OR OTHERWISE. THE ENTIRE RISK OF USING THE SOFTWARE IS ASSUMED BY YOU. METAMUG MAKES NO EXPRESS OR IMPLIED WARRANTIES OR CONDITIONS INCLUDING, WITHOUT LIMITATION, THE WARRANTIES OF MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE WITH RESPECT TO THE SOFTWARE. NO ORAL OR WRITTEN INFORMATION OR ADVICE GIVEN BY METAMUG, IT'S DISTRIBUTORS, AGENTS OR EMPLOYEES SHALL CREATE A WARRANTY, AND YOU MAY NOT RELY ON ANY SUCH INFORMATION OR ADVICE.
 *
 *IMPORTANT NOTE: Nothing in this Agreement is intended or shall be construed as excluding or modifying any statutory rights, warranties or conditions which by virtue of any national or state Fair Trading, Trade Practices or other such consumer legislation may not be modified or excluded. If permitted by such legislation, however, METAMUG's liability for any breach of any such warranty or condition shall be and is hereby limited to the supply of the Software licensed hereunder again as METAMUG at its sole discretion may determine to be necessary to correct the said breach.
 *
 *IN NO EVENT SHALL METAMUG BE LIABLE FOR ANY SPECIAL, INCIDENTAL, INDIRECT OR CONSEQUENTIAL DAMAGES (INCLUDING, WITHOUT LIMITATION, DAMAGES FOR LOSS OF BUSINESS PROFITS, BUSINESS INTERRUPTION, AND THE LOSS OF BUSINESS INFORMATION OR COMPUTER PROGRAMS), EVEN IF METAMUG OR ANY METAMUG REPRESENTATIVE HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES. IN ADDITION, IN NO EVENT DOES METAMUG AUTHORIZE YOU TO USE THE SOFTWARE IN SITUATIONS WHERE FAILURE OF THE SOFTWARE TO PERFORM CAN REASONABLY BE EXPECTED TO RESULT IN A PHYSICAL INJURY, OR IN LOSS OF LIFE. ANY SUCH USE BY YOU IS ENTIRELY AT YOUR OWN RISK, AND YOU AGREE TO HOLD METAMUG HARMLESS FROM ANY CLAIMS OR LOSSES RELATING TO SUCH UNAUTHORIZED USE.
 *
 *5. General
 *
 *All rights of any kind in the Software which are not expressly granted in this Agreement are entirely and exclusively reserved to and by METAMUG.
 *
 *This Agreement shall be governed by the laws of the State of Maharashtra, India. Exclusive jurisdiction and venue for all matters relating to this Agreement shall be in courts and fora located in the State of Maharashtra, India, and you consent to such jurisdiction and venue. This agreement contains the entire Agreement between the parties hereto with respect to the subject matter hereof, and supersedes all prior agreements and/or understandings (oral or written). Failure or delay by METAMUG in enforcing any right or provision hereof shall not be deemed a waiver of such provision or right with respect to the instant or any subsequent breach. If any provision of this Agreement shall be held by a court of competent jurisdiction to be contrary to law, that provision will be enforced to the maximum extent permissible, and the remaining provisions of this Agreement will remain in force and effect.
 */
package com.metamug.mason.io.mpath;
/**
 *
 * @author anishhirlekar
 */
public class TestData {
    public static final String TEST_JSON = "{\n"
            + "   \"Port\":\n"
            + "   {\n"
            + "       \"alias\": \"defaultHttp\",\n"
            + "       \"Enabled\": \"true\",\n"
            + "       \"Number\": \"10092\",\n"
            + "       \"Protocol\": \"http\",\n"
            + "       \"KeepAliveTimeout\": \"20000\",\n"
            + "       \"ThreadPool\":\n"
            + "       {\n"
            + "           \"enabled\": \"false\",\n"
            + "           \"Max\": \"150\",\n"
            + "           \"ThreadPriority\": \"5\"\n"
            + "       },\n"
            + "       \"ExtendedProperties\":\n"
            + "       {\n"
            + "           \"Property\":\n"
            + "           [                         \n"
            + "               {\n"
            + "                   \"name\": \"connectionTimeout\",\n"
            + "                   \"D\": 20000\n"
            + "               },\n"
            + "               {\n"
            + "                   \"name\": \"connectionTimeout_Again!\",\n"
            + "                   \"D\": \"60000\"\n"
            + "               }\n"
            + "           ]\n"
            + "       }\n"
            + "   }\n"
            + "}";

    public static final String TEST_JSON2 = "[\n"
            + "   {\n"
            + "       \"Port\":\n"
            + "       {\n"
            + "           \"alias\": 8080,\n"
            + "           \"ThreadPool\":\n"
            + "           {\n"
            + "               \"enabled\": false,\n"
            + "               \"Max\": 150,\n"
            + "               \"ThreadPriority\": 5\n"
            + "           }\n"
            + "       }\n"
            + "   },\n"
            + "   {\n"
            + "       \"Port\":\n"
            + "       {\n"
            + "           \"alias\": 3306,\n"
            + "           \"ThreadPool\":\n"
            + "           {\n"
            + "               \"enabled\": true,\n"
            + "               \"Max\": 20,\n"
            + "               \"ThreadPriority\": 1\n"
            + "           }\n"
            + "       }\n"
            + "   }\n"
            + "]";

    public static final String TEST_JSON3 = "{\n"
            + "    \"data\": {\n"
            + "        \"a\": {\n"
            + "            \"value\":\"17\",\n"
            + "            \"b\": {\n"
            + "                \"value\":18,\n"
            + "                \"c\": \"19\"\n"
            + "            }\n"
            + "        }\n"
            + "    }\n"
            + "}";
    public static final String TEST_JSON4 = "[{  \n"
            + "   \"addresses\":[  \n"
            + "      {  \n"
            + "         \"id\":\"178\",\n"
            + "         \"active\":true,\n"
            + "         \"city\":\"mumbai\",\n"
            + "         \"landmark\":\"elroy landmark\",\n"
            + "         \"lat\":\"19.1999999999999993\",\n"
            + "         \"line_1\":\"line 11\",\n"
            + "         \"line_2\":\"line 22\",\n"
            + "         \"lng\":\"72.7999999999999972\",\n"
            + "         \"pincode\":\"400001\",\n"
            + "         \"state_country_id\":1,\n"
            + "         \"created_on\":null,\n"
            + "         \"modified_on\":null,\n"
            + "         \"created_by_id\":null,\n"
            + "         \"modified_by_id\":null,\n"
            + "         \"area\":null,\n"
            + "         \"sub_area\":null,\n"
            + "         \"locality\":null\n"
            + "      }\n"
            + "   ],\n"
            + "   \"last_name\":\"Dsouza\",\n"
            + "   \"first_name\":\"Elroy\"\n"
            + "}]";

    public static final String TEST_XML = "<Resource version=\"1.1\" >\n"
            + "\n"
            + "	<Desc>Contains records for data type apple</Desc>\n"
            + "\n"
            + "	<Request method=\"GET\" item=\"true\" status=\"200\">\n"
            + "		<Param name=\"t\" blank=\"true\" min=\"1\" exists=\"table.column\" />\n"
            + "                \n"
            + "                <Sql when=\"@q eq 1\" type=\"query\">\n"
            + "                    select * from employee where employee_name = @v\n"
            + "                </Sql>\n"
            + "                <Sql when=\"@q eq 3\" type=\"update\">\n"
            + "                    <content>update employee set employee_name = @param2 where employee_id=@id\n"
            + "                </content></Sql>\n"
            + "	</Request>\n"
            + "	       \n"
            + "	<Request method=\"POST\" status=\"201\">\n"
            + "		<Desc>Request for creating an employee record.</Desc>\n"
            + "		<Sql type=\"update\">\n"
            + "		insert into employee \n"
            + "		(employee_name, employee_id) \n"
            + "		values(@name, @id)\n"
            + "		</Sql>\n"
            + "\n"
            + "	</Request>\n"
            + "\n"
            + "	<!--derived from request -->\n"
            + "	<Request method=\"PUT\" item=\"true\">\n"
            + "		<Execute className=\"com.mtg.Apple\" />\n"
            + "	</Request>\n"
            + "	\n"
            + "</Resource>\n"
            + "";

    public static final String TEST_XML2 = "<Resource version=\"1.1\" >\n"
            + "\n"
            + "	<Desc>Contains records for data type apple</Desc>\n"
            + "\n"
            + "	<Request method=\"GET\" item=\"true\" status=\"200\">\n"
            + "		<Param name=\"t\" blank=\"true\" min=\"1\" exists=\"table.column\" />\n"
            + "                \n"
            + "                <Sql when=\"@q eq 1\" type=\"query\">\n"
            + "                    select * from employee where employee_name = @v\n"
            + "                </Sql>\n"
            + "                <Sql when=\"@q eq 3\" type=\"update\">\n"
            + "                    <content>update employee set employee_name = @param2 where employee_id=@id\n"
            + "                </content>Illegal Content</Sql>\n"
            + "	</Request>\n"
            + "	       \n"
            + "	</Resource>";
}