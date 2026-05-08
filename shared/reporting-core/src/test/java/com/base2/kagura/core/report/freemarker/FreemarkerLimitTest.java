package com.base2.kagura.core.report.freemarker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.base2.kagura.core.report.configmodel.FreeMarkerSQLReportConfig;
import com.base2.kagura.core.report.connectors.FreemarkerSQLDataReportConnector;
import com.base2.kagura.core.report.connectors.ReportConnector;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class FreemarkerLimitTest {

    private FreemarkerLimit limitDirective;
    private Boolean[] limitExists;
    private List<String> errors;

    @Before
    public void setUp() {
        limitExists = new Boolean[]{false};
        errors = new ArrayList<>();
        // We can pass null for the connector since it's not used when an exception is thrown early.
        limitDirective = new FreemarkerLimit(limitExists, errors, null);
    }

    @Test
    public void testNonScalarSqlParameterThrowsException() throws IOException, TemplateException {
        Map<String, TemplateModel> params = new HashMap<>();
        // Passing a parameter that is NOT a TemplateScalarModel
        // A generic object that implements TemplateModel but not TemplateScalarModel
        params.put("sql", new TemplateModel() {});

        try {
            limitDirective.execute(null, params, new TemplateModel[]{}, null);
            fail("Should have thrown a TemplateModelException");
        } catch (TemplateModelException e) {
            assertThat(e.getMessage(), is("This directive only accepts string values for 'sql'."));
        }

        assertThat(errors.size(), is(1));
        assertThat(errors.get(0), is("This directive only accepts string values for 'sql'."));
    }

    public static class MockFreeMarkerSQLReportConfig extends FreeMarkerSQLReportConfig {
        @Override
        public ReportConnector getReportConnector() {
            return null;
        }
    }

    public static class MockFreemarkerSQLDataReportConnector extends FreemarkerSQLDataReportConnector {
        public MockFreemarkerSQLDataReportConnector(FreeMarkerSQLReportConfig reportConfig) {
            super(reportConfig);
        }

        @Override
        protected void getStartConnection() throws NamingException, SQLException {}
    }

    @Test
    public void testInvalidParameterError() throws IOException, TemplateException {
        Boolean[] limitExists = new Boolean[] {false};
        List<String> errors = new ArrayList<>();
        FreeMarkerSQLReportConfig config = new MockFreeMarkerSQLReportConfig();
        FreemarkerSQLDataReportConnector connector = new MockFreemarkerSQLDataReportConnector(config);
        FreemarkerLimit freemarkerLimit = new FreemarkerLimit(limitExists, errors, connector);

        Environment env = null;
        Map<String, Object> params = new HashMap<>();
        params.put("invalid_param", "some_value");
        TemplateModel[] loopVars = new TemplateModel[0];
        TemplateDirectiveBody body = null;

        try {
            freemarkerLimit.execute(env, params, loopVars, body);
            fail("Expected TemplateModelException to be thrown");
        } catch (TemplateModelException e) {
            String expectedMessage =
                    "This directive only takes 'sql', which you specify the type of engine, ie mysql, postgres, etc.";
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(1, errors.size());
            assertEquals(expectedMessage, errors.get(0));
        }
    }
}
