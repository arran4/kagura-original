package com.base2.kagura.core.report.freemarker;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

public class FreemarkerWhereClauseTest {

    @Test
    public void testMissingParentWhereTag() throws Exception {
        List<String> errors = new ArrayList<String>();
        FreemarkerWhereClause clause = new FreemarkerWhereClause(errors);

        Configuration cfg = new Configuration();
        Template t = new Template("name", new StringReader(""), cfg);
        Environment env = t.createProcessingEnvironment(new HashMap(), new StringWriter());

        Map params = new HashMap();
        TemplateModel[] loopVars = new TemplateModel[0];

        try {
            clause.execute(env, params, loopVars, null);
            Assert.fail("Expected TemplateModelException to be thrown");
        } catch (TemplateModelException e) {
            Assert.assertThat(e.getMessage(), Matchers.is("Can not find parent where clause."));
            Assert.assertThat(errors.size(), Matchers.is(1));
            Assert.assertThat(errors.get(0), Matchers.is("Can not find parent where clause."));
        }
    }
}
