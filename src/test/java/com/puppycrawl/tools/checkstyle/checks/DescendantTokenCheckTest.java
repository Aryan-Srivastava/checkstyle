////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks;

import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MAX;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_MIN;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_SUM_MAX;
import static com.puppycrawl.tools.checkstyle.checks.DescendantTokenCheck.MSG_KEY_SUM_MIN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class DescendantTokenCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/descendanttoken";
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens.java"), expected);
    }

    @Test
    public void testMaximumNumber()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_NATIVE");
        checkConfig.addProperty("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addProperty("maximumNumber", "0");
        final String[] expected = {
            "32:12: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "LITERAL_NATIVE", "LITERAL_NATIVE"),
        };
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens2.java"), expected);
    }

    @Test
    public void testMessage()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_NATIVE");
        checkConfig.addProperty("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumMessage", "Using ''native'' is not allowed.");
        final String[] expected = {
            "32:12: Using 'native' is not allowed.",
        };
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens3.java"), expected);
    }

    @Test
    public void testMinimumNumber()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SWITCH");
        checkConfig.addProperty("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addProperty("minimumNumber", "2");
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_KEY_MIN, 1, 2, "LITERAL_SWITCH", "LITERAL_DEFAULT"),
        };
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens4.java"), expected);
    }

    @Test
    public void testMinimumDepth()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SWITCH");
        checkConfig.addProperty("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("minimumDepth", "3");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens5.java"), expected);
    }

    @Test
    public void testMaximumDepth()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SWITCH");
        checkConfig.addProperty("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "1");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens6.java"), expected);
    }

    @Test
    public void testEmptyStatements()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "EMPTY_STAT");
        checkConfig.addProperty("limitedTokens", "EMPTY_STAT");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "0");
        checkConfig.addProperty("maximumMessage", "Empty statement.");

        final String[] expected = {
            "22:7: Empty statement.",
            "27:7: Empty statement.",
            "32:19: Empty statement.",
            "36:10: Empty statement.",
            "39:16: Empty statement.",
            "43:10: Empty statement.",
            "53:10: Empty statement.",
            "59:13: Empty statement.",
            "61:13: Empty statement.",
            "64:19: Empty statement.",
            "68:10: Empty statement.",
            "71:9: Empty statement.",
            "76:10: Empty statement.",
            "82:10: Empty statement.",
            "86:10: Empty statement.",
            "90:10: Empty statement.",
        };

        verify(checkConfig, getPath("InputDescendantTokenEmptyStatement.java"), expected);
    }

    @Test
    public void testMissingSwitchDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SWITCH");
        checkConfig.addProperty("limitedTokens", "LITERAL_DEFAULT");
        checkConfig.addProperty("minimumNumber", "1");
        checkConfig.addProperty("maximumDepth", "2");
        checkConfig.addProperty("minimumMessage", "switch without \"default\" clause.");

        final String[] expected = {
            "32:9: switch without \"default\" clause.",
        };

        verify(checkConfig, getPath("InputDescendantTokenMissingSwitchDefault.java"), expected);
    }

    @Test
    public void testStringLiteralEquality() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "EQUAL,NOT_EQUAL");
        checkConfig.addProperty("limitedTokens", "STRING_LITERAL");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "1");
        checkConfig.addProperty("maximumMessage",
            "Literal Strings should be compared using equals(), not ''==''.");

        final String[] expected = {
            "22:18: Literal Strings should be compared using equals(), not '=='.",
            "27:20: Literal Strings should be compared using equals(), not '=='.",
            "32:22: Literal Strings should be compared using equals(), not '=='.",
        };
        verify(checkConfig, getPath("InputDescendantTokenStringLiteralEquality.java"), expected);
    }

    @Test
    public void testIllegalTokenDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_SWITCH, POST_INC, POST_DEC");
        checkConfig.addProperty("limitedTokens", "LITERAL_SWITCH, POST_INC, POST_DEC");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "0");
        checkConfig.addProperty("maximumMessage", "Using ''{2}'' is not allowed.");

        final String[] expected = {
            "23:9: Using 'LITERAL_SWITCH' is not allowed.",
            "26:18: Using 'POST_DEC' is not allowed.",
            "27:18: Using 'POST_INC' is not allowed.",
        };
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens7.java"), expected);
    }

    @Test
    public void testIllegalTokenNative() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "LITERAL_NATIVE");
        checkConfig.addProperty("limitedTokens", "LITERAL_NATIVE");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "0");
        checkConfig.addProperty("maximumMessage", "Using ''{2}'' is not allowed.");

        final String[] expected = {
            "32:12: Using 'LITERAL_NATIVE' is not allowed.",
        };
        verify(checkConfig, getPath("InputDescendantTokenIllegalTokens8.java"), expected);
    }

    @Test
    public void testReturnFromCatch() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);

        checkConfig.addProperty("tokens", "LITERAL_CATCH");
        checkConfig.addProperty("limitedTokens", "LITERAL_RETURN");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumMessage", "Return from catch is not allowed.");

        final String[] expected = {
            "22:11: Return from catch is not allowed.",
            "30:11: Return from catch is not allowed.",
        };

        verify(checkConfig, getPath("InputDescendantTokenReturnFromCatch.java"), expected);
    }

    @Test
    public void testReturnFromFinally() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);

        checkConfig.addProperty("tokens", "LITERAL_FINALLY");
        checkConfig.addProperty("limitedTokens", "LITERAL_RETURN");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumMessage", "Return from finally is not allowed.");

        final String[] expected = {
            "22:11: Return from finally is not allowed.",
            "30:11: Return from finally is not allowed.",
        };

        verify(checkConfig, getPath("InputDescendantTokenReturnFromFinally.java"), expected);
    }

    @Test
    public void testNoSum() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);

        checkConfig.addProperty("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addProperty("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addProperty("maximumNumber", "1");
        checkConfig.addProperty("maximumMessage", "What are you doing?");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputDescendantTokenReturnFromFinally2.java"), expected);
    }

    @Test
    public void testWithSumCustomMsg() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addProperty("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addProperty("maximumNumber", "1");
        checkConfig.addProperty("maximumDepth", "1");
        checkConfig.addProperty("maximumMessage", "this cannot be null.");
        checkConfig.addProperty("sumTokenCounts", "true");

        final String[] expected = {
            "37:32: this cannot be null.",
            "37:50: this cannot be null.",
            "38:33: this cannot be null.",
            "38:51: this cannot be null.",
        };

        verify(checkConfig, getPath("InputDescendantTokenReturnFromFinally3.java"), expected);
    }

    @Test
    public void testWithSumDefaultMsg() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addProperty("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addProperty("maximumNumber", "1");
        checkConfig.addProperty("maximumDepth", "1");
        checkConfig.addProperty("sumTokenCounts", "true");

        final String[] expected = {
            "37:32: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "EQUAL"),
            "37:50: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "EQUAL"),
            "38:33: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "NOT_EQUAL"),
            "38:51: " + getCheckMessage(MSG_KEY_SUM_MAX, 2, 1, "NOT_EQUAL"),
        };

        verify(checkConfig, getPath("InputDescendantTokenReturnFromFinally4.java"), expected);
    }

    @Test
    public void testWithSumLessThenMinDefMsg() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addProperty("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addProperty("minimumNumber", "3");
        checkConfig.addProperty("sumTokenCounts", "true");

        final String[] expected = {
            "31:44: " + getCheckMessage(MSG_KEY_SUM_MIN, 0, 3, "EQUAL"),
            "37:32: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "EQUAL"),
            "37:50: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "EQUAL"),
            "38:33: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "NOT_EQUAL"),
            "38:51: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "NOT_EQUAL"),
            "40:13: " + getCheckMessage(MSG_KEY_SUM_MIN, 2, 3, "EQUAL"),
            "40:36: " + getCheckMessage(MSG_KEY_SUM_MIN, 1, 3, "EQUAL"),
        };

        verify(checkConfig, getPath("InputDescendantTokenReturnFromFinally5.java"), expected);
    }

    @Test
    public void testWithSumLessThenMinCustomMsg() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "NOT_EQUAL,EQUAL");
        checkConfig.addProperty("limitedTokens", "LITERAL_THIS,LITERAL_NULL");
        checkConfig.addProperty("minimumNumber", "3");
        checkConfig.addProperty("sumTokenCounts", "true");
        checkConfig.addProperty("minimumMessage", "custom message");

        final String[] expected = {
            "31:44: custom message",
            "37:32: custom message",
            "37:50: custom message",
            "38:33: custom message",
            "38:51: custom message",
            "40:13: custom message",
            "40:36: custom message",
        };

        verify(checkConfig, getPath("InputDescendantTokenReturnFromFinally6.java"), expected);
    }

    @Test
    public void testMaxTokenType() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "OBJBLOCK");
        checkConfig.addProperty("limitedTokens", "LCURLY,RCURLY");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "2");
        final String[] expected = {
            "21:48: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "LCURLY"),
            "21:48: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "RCURLY"),
        };
        verify(checkConfig, getPath("InputDescendantTokenLastTokenType.java"), expected);
    }

    @Test
    public void testMaxTokenTypeReverseOrder() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(DescendantTokenCheck.class);
        checkConfig.addProperty("tokens", "OBJBLOCK");
        checkConfig.addProperty("limitedTokens", "RCURLY,LCURLY");
        checkConfig.addProperty("maximumNumber", "0");
        checkConfig.addProperty("maximumDepth", "2");
        final String[] expected = {
            "21:49: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "LCURLY"),
            "21:49: " + getCheckMessage(MSG_KEY_MAX, 1, 0, "OBJBLOCK", "RCURLY"),
        };
        verify(checkConfig, getPath("InputDescendantTokenLastTokenType2.java"), expected);
    }

}
