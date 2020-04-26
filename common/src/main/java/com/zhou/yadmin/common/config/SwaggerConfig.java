/**
 * <p>
 * 文件名称:    SwaggerConfig
 * </p>
 */
package com.zhou.yadmin.common.config;

import static com.google.common.collect.Lists.newArrayList;
import static springfox.documentation.schema.AlternateTypeRules.newRule;

import java.util.List;

import com.fasterxml.classmate.TypeResolver;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.data.domain.Pageable;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.AlternateTypeRule;
import springfox.documentation.schema.AlternateTypeRuleConvention;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>
 * api页面 /swagger-ui.html
 * 如controller在不同的包中，@ComponentScan(basePackages = {"me.aurora.app.rest","..."})
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 22:02
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.zhou.yadmin.*.web.controller"})
public class SwaggerConfig
{
    @Bean
    public Docket createRestApi()
    {
        ParameterBuilder ticketPar = new ParameterBuilder();
        ticketPar.name("Authorization").description("token").modelRef(new ModelRef("string")).parameterType("header").defaultValue("Bearer ")
          .required(true).build();
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select().paths(Predicates.not(PathSelectors.regex("/error.*"))).build()
          .globalOperationParameters(Lists.newArrayList(ticketPar.build()));
    }

    @Bean
    public AlternateTypeRuleConvention pageableConvention(final TypeResolver resolver)
    {
        return new AlternateTypeRuleConvention()
        {
            @Override
            public int getOrder()
            {
                return Ordered.HIGHEST_PRECEDENCE;
            }

            @Override
            public List<AlternateTypeRule> rules()
            {
                return newArrayList(newRule(resolver.resolve(Pageable.class), resolver.resolve(Page.class)));
            }
        };
    }

    private ApiInfo apiInfo()
    {
        return new ApiInfoBuilder().title("yAdmin 接口文档").description("powered by yuZhou").version("1.4").build();
    }

    @ApiModel
    static class Page
    {
        @ApiModelProperty("页码 (0..N)")
        private Integer page;

        @ApiModelProperty("每页显示的数目")
        private Integer size;

        @ApiModelProperty("以下列格式排序标准：property[,asc | desc]。 默认排序顺序为升序。 支持多种排序条件：如：id,asc")
        private List<String> sort;

        public Integer getPage()
        {
            return page;
        }

        public void setPage(Integer page)
        {
            this.page = page;
        }

        public Integer getSize()
        {
            return size;
        }

        public void setSize(Integer size)
        {
            this.size = size;
        }

        public List<String> getSort()
        {
            return sort;
        }

        public void setSort(List<String> sort)
        {
            this.sort = sort;
        }
    }
}
