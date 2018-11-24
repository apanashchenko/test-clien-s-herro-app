package com.s.hero.api;

import com.s.hero.model.Hero;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Created by alpa on 11/24/18
 */
public interface HeroSevice {


    @GET("/heroes")
    Call<List<Hero>> getHeroes();

    @GET("/hero/{id}")
    Call<Hero> getHeorById(@Path ("id") Long id);

    @POST("/hero")
    Call<Hero> createHero(@Body Hero hero);

    @POST("/heroresponse/{id}")
    Call<Hero> createHeroResponse(@Path ("id") Long id, @Body Hero hero);

}
