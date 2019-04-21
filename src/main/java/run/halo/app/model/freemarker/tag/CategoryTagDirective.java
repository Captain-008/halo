package run.halo.app.model.freemarker.tag;

import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;
import run.halo.app.model.support.HaloConst;
import run.halo.app.service.CategoryService;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker custom tag of category.
 *
 * @author : RYAN0UP
 * @date : 2019/3/22
 */
@Component
public class CategoryTagDirective implements TemplateDirectiveModel {

    private final CategoryService categoryService;

    public CategoryTagDirective(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        final DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

        if (params.containsKey(HaloConst.METHOD_KEY)) {
            String method = params.get(HaloConst.METHOD_KEY).toString();
            switch (method) {
                case "list":
                    env.setVariable("categories", builder.build().wrap(categoryService.listAll()));
                    break;
                case "count":
                    env.setVariable("count", builder.build().wrap(categoryService.count()));
                    break;
                default:
                    break;
            }
        }
        body.render(env.getOut());
    }
}
