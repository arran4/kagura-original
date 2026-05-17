package com.base2.kagura.core.report.connectors;

import com.base2.kagura.core.report.configmodel.FreeMarkerSQLReportConfig;
import freemarker.template.TemplateException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FreemarkerSQLDataReportConnectorTest {

    private FreemarkerSQLDataReportConnector connector;

    @Before
    public void setUp() {
        FreeMarkerSQLReportConfig config = new FreeMarkerSQLReportConfig() {
            @Override
            public ReportConnector getReportConnector() {
                return null;
            }
        };
        connector = new FreemarkerSQLDataReportConnector(config) {
            @Override
            protected void getStartConnection() throws SQLException {
                // Mock implementation
            }
        };
    }

    @Test
    public void testSSTIPrevention() {
        String payload = "<#assign ex=\"freemarker.template.utility.Execute\"?new()> ${ ex(\"echo VULNERABLE\") }";
        try {
            connector.freemakerParams(new HashMap<>(), false, payload);
            Assert.fail("Expected TemplateException to be thrown due to SSTI prevention");
        } catch (TemplateException e) {
            Assert.assertTrue(
                    "Exception should indicate security restriction",
                    e.getMessage().contains("Instantiating freemarker.template.utility.Execute is not allowed"));
        } catch (Exception e) {
            Assert.fail("Expected TemplateException, but got " + e.getClass().getName());
        }
    }

    @Test
    public void testWhitelistingAllowedClass() throws Exception {
        FreeMarkerSQLReportConfig config = new FreeMarkerSQLReportConfig() {
            @Override
            public ReportConnector getReportConnector() {
                return null;
            }
        };
        config.setAllowedClasses(Arrays.asList("freemarker.template.utility.Execute"));
        FreemarkerSQLDataReportConnector whitelistedConnector = new FreemarkerSQLDataReportConnector(config) {
            @Override
            protected void getStartConnection() throws SQLException {}
        };

        String payload = "<#assign ex=\"freemarker.template.utility.Execute\"?new()> ${ ex(\"echo VULNERABLE\") }";
        // Should not throw the security exception because it is whitelisted.
        // It will execute the template (which might result in some other error or output, but not the instantiation
        // security error).
        try {
            whitelistedConnector.freemakerParams(new HashMap<>(), false, payload);
        } catch (TemplateException e) {
            Assert.assertFalse(
                    "Exception should not be a security restriction",
                    e.getMessage().contains("Instantiating freemarker.template.utility.Execute is not allowed"));
        }
    }
}
