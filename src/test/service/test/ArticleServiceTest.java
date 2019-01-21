package service.test;

import com.ssm.demo.entity.Article;
import com.ssm.demo.service.ArticleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 13 on 2018/9/7.
 */
@RunWith(SpringJUnit4ClassRunner.class) //指定测试用例的运行器 这里是指定了Junit4
@ContextConfiguration("classpath:applicationContext.xml")
public class ArticleServiceTest {
    @Autowired
    private ArticleService articleService;

    @Test
    public void getArticleById() throws Exception {
        Article article = articleService.queryObject(1256);
        System.out.println(article.toString());
    }

}
