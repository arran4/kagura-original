package com.base2.kagura.core.report.freemarker;

import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
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
}
