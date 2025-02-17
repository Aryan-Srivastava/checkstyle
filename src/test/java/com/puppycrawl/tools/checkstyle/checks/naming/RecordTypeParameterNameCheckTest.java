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
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class RecordTypeParameterNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/recordtypeparametername";
    }

    @Test
    public void testGetClassRequiredTokens() {
        final RecordTypeParameterNameCheck checkObj =
                new RecordTypeParameterNameCheck();
        final int[] expected = {TokenTypes.TYPE_PARAMETER};
        assertArrayEquals(expected, checkObj.getRequiredTokens(),
                "Default required tokens are invalid");
    }

    @Test
    public void testRecordDefault()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordTypeParameterNameCheck.class);

        final String pattern = "^[A-Z]$";

        final String[] expected = {
            "16:44: " + getCheckMessage(MSG_INVALID_PATTERN, "t", pattern),
            "23:15: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
            "38:25: " + getCheckMessage(MSG_INVALID_PATTERN, "foo", pattern),
        };
        verify(checkConfig,
                getNonCompilablePath("InputRecordTypeParameterName.java"), expected);
    }

    @Test
    public void testClassFooName()
            throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(RecordTypeParameterNameCheck.class);
        checkConfig.addProperty("format", "^foo$");

        final String pattern = "^foo$";

        final String[] expected = {
            "16:47: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
            "23:15: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
            "44:19: " + getCheckMessage(MSG_INVALID_PATTERN, "T", pattern),
        };
        verify(checkConfig,
                getNonCompilablePath("InputRecordTypeParameterNameFoo.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final RecordTypeParameterNameCheck typeParameterNameCheckObj =
                new RecordTypeParameterNameCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.TYPE_PARAMETER,
        };
        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }
}
