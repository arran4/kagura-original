package com.base2.kagura.core.report.connectors;

import com.base2.kagura.core.report.configmodel.GroovyReportConfig;
import com.base2.kagura.core.report.configmodel.parts.ColumnDef;
import com.base2.kagura.core.report.parameterTypes.ParamConfig;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author aubels
 *         Date: 27/05/2014
 */
public class GroovyDataReportConnectorTest {

    @Test
    public void testNormalGroovyScript() {
        GroovyDataReportConnector connector = new GroovyDataReportConnector(new GroovyReportConfig() {
            {
                setColumns(new ArrayList<ColumnDef>());
                setExtraOptions(new HashMap<String, String>());
                setGroovy("def a = 1; def b = 2; a + b");
                setParamConfig(new ArrayList<ParamConfig>());
                setReportId("test-normal");
            }
        });
        connector.run(new HashMap<String, Object>());
        Assert.assertTrue(connector.getErrors().isEmpty());
    }

    @Test
    public void testSecurityExploitBlockedRuntime() {
        GroovyDataReportConnector connector = new GroovyDataReportConnector(new GroovyReportConfig() {
            {
                setColumns(new ArrayList<ColumnDef>());
                setExtraOptions(new HashMap<String, String>());
                setGroovy("Runtime.getRuntime().exec('id')");
                setParamConfig(new ArrayList<ParamConfig>());
                setReportId("test-exploit-runtime");
            }
        });
        connector.run(new HashMap<String, Object>());
        Assert.assertFalse(
                "Should contain errors regarding restricted execution",
                connector.getErrors().isEmpty());
    }

    @Test
    public void testSecurityExploitBlockedExecute() {
        GroovyDataReportConnector connector = new GroovyDataReportConnector(new GroovyReportConfig() {
            {
                setColumns(new ArrayList<ColumnDef>());
                setExtraOptions(new HashMap<String, String>());
                setGroovy("'id'.execute()");
                setParamConfig(new ArrayList<ParamConfig>());
                setReportId("test-exploit-execute");
            }
        });
        connector.run(new HashMap<String, Object>());
        Assert.assertFalse(
                "Should contain errors regarding restricted execution",
                connector.getErrors().isEmpty());
    }

    @Test
    @Ignore("Tends to hang for too long")
    public void awsImportTest() {
        GroovyDataReportConnector groovyDataReportConnector = new GroovyDataReportConnector(new GroovyReportConfig() {
            {
                setColumns(new ArrayList<ColumnDef>());
                setExtraOptions(new HashMap<String, String>());
                setGroovy(
                        "@GrabResolver(name = 'mvnrepository', root = 'http://repo1.maven.org/maven2')\n" + "@Grapes(\n"
                                + "        @Grab(group='com.amazonaws', module='aws-java-sdk', version='1.7.9')\n"
                                + ")\n"
                                + "import com.amazonaws.auth.AWSCredentials\n"
                                + "import com.amazonaws.auth.BasicAWSCredentials\n"
                                + "\n"
                                + "AWSCredentials awsCredentials = new BasicAWSCredentials(\"\",\"\");\n"
                                + "\n"
                                + "");
                setParamConfig(new ArrayList<ParamConfig>());
                setReportId("test");
            }
        });
        groovyDataReportConnector.run(new HashMap<String, Object>());
    }
}
