package com.s.hero;

import com.s.hero.api.HeroSevice;
import com.s.hero.model.Hero;
import com.s.hero.model.RequestMessage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Created by alpa on 11/24/18
 */

@Epic("Hero")
@Feature("Hero request")
@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
public class HeroRequestFlowTest extends AbstractTestNGSpringContextTests {

    private HeroSevice heroSevice = RestClient.createService(HeroSevice.class, "http://localhost:8080");
    private Hero hero;


    @BeforeClass(alwaysRun = true)
    public void setUp() {
        hero = new Hero();
        hero.setName("bob b");
        hero.setTitle("b-man");
        hero.setDescription("hero");
        hero.setStatus("Requested");
        hero.setAuthorComment("want be a hero");
    }


    @Story("Hero request")
    @Test(priority = 1, description = "Request hero test")
    public void requestHeroTest() throws IOException {
        Response<Hero> response = heroSevice.createHero(hero).execute();
        assertThat("Bad response code!", response.code(), is(200));

        hero = response.body();
        assertThat("Bad hero id!", hero.getId(), notNullValue());

        RequestMessage requestMessage = MessageHolder.getRequestMessage();

        assertThat("Bad request message status!", requestMessage.getStatus(), is(equalTo("Requested")));
        assertThat("Bad request message description!", requestMessage.getDescription(), is(equalTo("Person on review")));
        assertThat("Bad request message hero id!", requestMessage.getHeroId(), is(hero.getId()));
    }

    @Story("Hero approve")
    @Test(priority = 2, description = "Approve hero test")
    public void approveHeroTest() throws IOException {
        hero.setStatus("Approved");
        hero.setArproverComment("Approve this person");

        Response<Hero> response = heroSevice.createHeroResponse(hero.getId(), hero).execute();
        assertThat("Bad response code!", response.code(), is(200));

        Hero hero = response.body();
        assertThat("Bad hero id!", hero.getId(), notNullValue());
        assertThat("Hero id was change after request!", this.hero.getId(), is(hero.getId()));

        RequestMessage requestMessage = MessageHolder.getRequestMessage();

        assertThat("Bad request message status!", requestMessage.getStatus(), is(equalTo("Approved")));
        assertThat("Bad request message description!", requestMessage.getDescription(), is(equalTo("Person approved")));
        assertThat("Bad request message hero id!", requestMessage.getHeroId(), is(hero.getId()));

        assertThat("Bad approver comment!", hero.getArproverComment(), is(equalTo("Approve this person")));
    }

    @Story("Hero Canceled")
    @Test(priority = 3, description = "Canceled hero test")
    public void cancelHeroTest() throws IOException {
        hero.setStatus("Canceled");
        hero.setArproverComment("Cancel this person");

        Response<Hero> response = heroSevice.createHeroResponse(hero.getId(), hero).execute();
        assertThat("Bad response code!", response.code(), is(200));

        Hero hero = response.body();
        assertThat("Bad hero id!", hero.getId(), notNullValue());
        assertThat("Hero id was change after request!", this.hero.getId(), is(hero.getId()));

        RequestMessage requestMessage = MessageHolder.getRequestMessage();

        assertThat("Bad request message status!", requestMessage.getStatus(), is(equalTo("Canceled")));
        assertThat("Bad request message description!", requestMessage.getDescription(), is(equalTo("Person canceled")));
        assertThat("Bad request message hero id!", requestMessage.getHeroId(), is(hero.getId()));

        assertThat("Bad approver comment!", hero.getArproverComment(), is(equalTo("Cancel this person")));

    }

    @Story("Hero info")
    @Test(priority = 4, description = "Show hero info")
    public void heroInfoTest() throws IOException {
        Response<Hero> response = heroSevice.getHeorById(hero.getId()).execute();
        assertThat("Bad response code!", response.code(), is(200));

        hero = response.body();
        assertThat("Bad hero id!", hero.getId(), notNullValue());

        List<RequestMessage> requestMessageList = hero.getMessages();

        assertThat("Bad request messages list size", requestMessageList, hasSize(3));
    }


}
