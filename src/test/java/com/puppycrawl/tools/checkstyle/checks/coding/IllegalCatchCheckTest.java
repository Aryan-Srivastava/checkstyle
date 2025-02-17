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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class IllegalCatchCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/illegalcatch";
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalCatchCheck.class);

        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "15:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "16:11: " + getCheckMessage(MSG_KEY, "Throwable"),
            "22:11: " + getCheckMessage(MSG_KEY, "java.lang.RuntimeException"),
            "23:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "24:11: " + getCheckMessage(MSG_KEY, "java.lang.Throwable"),
        };

        verify(checkConfig, getPath("InputIllegalCatch.java"), expected);
    }

    @Test
    public void testIllegalClassNames() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalCatchCheck.class);
        checkConfig.addProperty("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, java.lang.Throwable");

        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "15:11: " + getCheckMessage(MSG_KEY, "Throwable"),
            "22:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
            "23:11: " + getCheckMessage(MSG_KEY, "java.lang.Throwable"),
        };

        verify(checkConfig, getPath("InputIllegalCatch3.java"), expected);
    }

    @Test
    public void testIllegalClassNamesBad() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalCatchCheck.class);
        checkConfig.addProperty("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException");

        // check that incorrect names don't break the Check
        checkConfig.addProperty("illegalClassNames",
                "java.lang.IOException.");

        final String[] expected = {
            "14:11: " + getCheckMessage(MSG_KEY, "Exception"),
            "22:11: " + getCheckMessage(MSG_KEY, "java.lang.Exception"),
        };

        verify(checkConfig, getPath("InputIllegalCatch4.java"), expected);
    }

    @Test
    public void testMultipleTypes() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(IllegalCatchCheck.class);
        checkConfig.addProperty("illegalClassNames",
                                 "java.lang.Error, java.lang.Exception, NullPointerException,"
                                + "OneMoreException, RuntimeException, SQLException");

        final String[] expected = {
            "15:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "15:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "18:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "18:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "18:11: " + getCheckMessage(MSG_KEY, "OneMoreException"),
            "21:11: " + getCheckMessage(MSG_KEY, "OneMoreException"),
            "21:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
            "21:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "24:11: " + getCheckMessage(MSG_KEY, "OneMoreException"),
            "24:11: " + getCheckMessage(MSG_KEY, "SQLException"),
            "24:11: " + getCheckMessage(MSG_KEY, "RuntimeException"),
        };

        verify(checkConfig, getPath("InputIllegalCatch2.java"), expected);
    }

    @Test
    public void testTokensNotNull() {
        final IllegalCatchCheck check = new IllegalCatchCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

}
