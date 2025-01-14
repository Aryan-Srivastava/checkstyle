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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MethodNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/methodname";
    }

    @Test
    public void testGetRequiredTokens() {
        final MethodNameCheck checkObj = new MethodNameCheck();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "144:10: " + getCheckMessage(MSG_INVALID_PATTERN, "ALL_UPPERCASE_METHOD", pattern),
        };
        verify(checkConfig, getPath("InputMethodNameSimple.java"), expected);
    }

    @Test
    public void testMethodEqClass() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "24:16: " + getCheckMessage(MSG_KEY, "InputMethodNameEqualClassName"),
            "24:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "29:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethodNameEqualClassName",
                    pattern),
            "35:20: " + getCheckMessage(MSG_KEY, "Inner"),
            "35:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "40:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "49:24: " + getCheckMessage(MSG_KEY, "InputMethodNameEqualClassName"),
            "49:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName", pattern),
            "59:9: " + getCheckMessage(MSG_KEY, "SweetInterface"),
            "59:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "65:17: " + getCheckMessage(MSG_KEY, "Outer"),
            "65:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameEqualClassName.java"), expected);
    }

    @Test
    public void testMethodEqClassAllow() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        // allow method names and class names to equal
        checkConfig.addProperty("allowClassName", "true");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "24:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName2", pattern),
            "29:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PRIVATEInputMethodNameEqualClassName",
                    pattern),
            "35:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "40:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName2", pattern),
            "49:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName2", pattern),
            "59:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "65:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameEqualClassName2.java"), expected);
    }

    @Test
    public void testAccessTuning() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        // allow method names and class names to equal
        checkConfig.addProperty("allowClassName", "true");

        // allow method names and class names to equal
        checkConfig.addProperty("applyToPrivate", "false");

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "24:16: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName3", pattern),
            "35:20: " + getCheckMessage(MSG_INVALID_PATTERN, "Inner", pattern),
            "40:20: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName3", pattern),
            "49:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "InputMethodNameEqualClassName3", pattern),
            "59:9: " + getCheckMessage(MSG_INVALID_PATTERN, "SweetInterface", pattern),
            "65:17: " + getCheckMessage(MSG_INVALID_PATTERN, "Outer", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameEqualClassName3.java"), expected);
    }

    @Test
    public void testForNpe() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMethodNameExtra.java"), expected);
    }

    @Test
    public void testOverriddenMethods() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);

        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "29:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PUBLICfoo", pattern),
            "32:20: " + getCheckMessage(MSG_INVALID_PATTERN, "PROTECTEDfoo", pattern),
        };

        verify(checkConfig, getPath("InputMethodNameOverriddenMethods.java"), expected);
    }

    @Test
    public void testInterfacesExcludePublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);
        checkConfig.addProperty("applyToPublic", "false");
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_INVALID_PATTERN, "PrivateMethod", pattern),
            "20:25: " + getCheckMessage(MSG_INVALID_PATTERN, "PrivateMethod2", pattern),
        };

        verify(checkConfig, getNonCompilablePath("InputMethodNamePublicMethodsInInterfaces.java"),
            expected);
    }

    @Test
    public void testInterfacesExcludePrivate() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MethodNameCheck.class);
        checkConfig.addProperty("applyToPrivate", "false");
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "22:18: " + getCheckMessage(MSG_INVALID_PATTERN, "DefaultMethod", pattern),
            "25:25: " + getCheckMessage(MSG_INVALID_PATTERN, "DefaultMethod2", pattern),
            "28:10: " + getCheckMessage(MSG_INVALID_PATTERN, "PublicMethod", pattern),
            "30:17: " + getCheckMessage(MSG_INVALID_PATTERN, "PublicMethod2", pattern),
        };

        verify(checkConfig, getNonCompilablePath("InputMethodNamePrivateMethodsInInterfaces.java"),
            expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final MethodNameCheck methodNameCheckObj = new MethodNameCheck();
        final int[] actual = methodNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.METHOD_DEF,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

}
