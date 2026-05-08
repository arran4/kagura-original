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
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import org.junit.Test;

public class FreemarkerLimitTest {

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
