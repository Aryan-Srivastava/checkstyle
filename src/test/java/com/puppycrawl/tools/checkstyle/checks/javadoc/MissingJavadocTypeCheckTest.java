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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck.MSG_JAVADOC_MISSING;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Scope;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocTypeCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype";
    }

    @Test
    public void testGetRequiredTokens() {
        final MissingJavadocTypeCheck missingJavadocTypeCheck = new MissingJavadocTypeCheck();
        assertArrayEquals(CommonUtil.EMPTY_INT_ARRAY, missingJavadocTypeCheck.getRequiredTokens(),
                "MissingJavadocTypeCheck#getRequiredTokens should return empty array by default");
    }

    @Test
    public void testGetAcceptableTokens() {
        final MissingJavadocTypeCheck missingJavadocTypeCheck = new MissingJavadocTypeCheck();

        final int[] actual = missingJavadocTypeCheck.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertArrayEquals(expected, actual, "Default acceptable tokens are invalid");
    }

    @Test
    public void testTags() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "308:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "333:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocTypeTags.java"), expected);
    }

    @Test
    public void testInner() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        final String[] expected = {
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocTypeInner.java"), expected);
    }

    @Test
    public void testStrict() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "20:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "40:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocTypePublicOnly1.java"), expected);
    }

    @Test
    public void testProtected() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocTypePublicOnly2.java"), expected);
    }

    @Test
    public void testPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputMissingJavadocTypeScopeInnerInterfaces1.java"),
               expected);
    }

    @Test
    public void testProtest() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "35:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "44:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "71:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputMissingJavadocTypeScopeInnerInterfaces2.java"),
               expected);
    }

    @Test
    public void testPkg() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty(
            "scope",
            Scope.PACKAGE.getName());
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "24:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "26:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocTypeScopeInnerClasses1.java"), expected);
    }

    @Test
    public void testEclipse() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty(
            "scope",
            Scope.PUBLIC.getName());
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig, getPath("InputMissingJavadocTypeScopeInnerClasses2.java"), expected);
    }

    @Test
    public void testScopes() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "73:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "85:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "97:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "109:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "121:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputMissingJavadocTypeNoJavadoc1.java"),
               expected);
    }

    @Test
    public void testLimitViolationsBySpecifyingTokens() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        checkConfig.addProperty("tokens", "INTERFACE_DEF");
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputMissingJavadocTypeNoJavadocOnInterface.java"),
               expected);
    }

    @Test
    public void testScopes2() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", Scope.PROTECTED.getName());
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "25:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputMissingJavadocTypeNoJavadoc2.java"),
               expected);
    }

    @Test
    public void testExcludeScope() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", Scope.PRIVATE.getName());
        checkConfig.addProperty("excludeScope", Scope.PROTECTED.getName());
        final String[] expected = {
            "37:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "49:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "62:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "73:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "85:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "97:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "109:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "121:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
               getPath("InputMissingJavadocTypeNoJavadoc3.java"),
               expected);
    }

    @Test
    public void testDontAllowUnusedParameterTag() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(MissingJavadocTypeCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
                getPath("InputMissingJavadocTypeUnusedParamInJavadocForClass.java"),
                expected);
    }

    @Test
    public void testSkipAnnotationsDefault() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
            getPath("InputMissingJavadocTypeSkipAnnotations1.java"),
            expected);
    }

    @Test
    public void testSkipAnnotationsWithFullyQualifiedName() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        checkConfig.addProperty(
            "skipAnnotations",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctype.ThisIsOk2");

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
                getPath("InputMissingJavadocTypeSkipAnnotations2.java"),
                expected);
    }

    @Test
    public void testSkipAnnotationsAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("skipAnnotations", "Generated3, ThisIsOk3");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verify(checkConfig,
            getPath("InputMissingJavadocTypeSkipAnnotations3.java"),
            expected);
    }

    @Test
    public void testSkipAnnotationsNotAllowed() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        checkConfig.addProperty("skipAnnotations", "Override");

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "17:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "21:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
            getPath("InputMissingJavadocTypeSkipAnnotations4.java"),
            expected);
    }

    @Test
    public void testMissingJavadocTypeCheckRecords() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", "PRIVATE");
        checkConfig.addProperty("skipAnnotations", "NonNull1");

        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "23:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "31:9: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "32:13: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "41:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
            getNonCompilablePath("InputMissingJavadocTypeRecords.java"),
            expected);
    }

    @Test
    public void testInterfaceMemberScopeIsPublic() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MissingJavadocTypeCheck.class);
        checkConfig.addProperty("scope", Scope.PUBLIC.getName());

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "15:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
            "19:5: " + getCheckMessage(MSG_JAVADOC_MISSING),
        };
        verify(checkConfig,
            getPath("InputMissingJavadocTypeInterfaceMemberScopeIsPublic.java"),
            expected);
    }

}
