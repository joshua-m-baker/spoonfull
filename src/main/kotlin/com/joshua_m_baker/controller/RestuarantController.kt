package com.joshua_m_baker.controller

import com.joshua_m_baker.domain.Dish
import com.joshua_m_baker.domain.Experience
import com.joshua_m_baker.domain.Review
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import java.time.LocalDate

@Controller("/experiences")
class ExperienceController {

    @Get(produces = [MediaType.TEXT_JSON])
     fun getExperiences(): List<Experience> {
         return listOf(
             Experience(
                 date = LocalDate.now(),
                 restaurantName = "Vinai",
                 restaurantId = "1",
                 reviews = listOf(
                    Review("Josh", "1", 5, listOf(Dish("Crabby Fried Rice")), notes = "Yay free food"),
                    Review("Sara", "2", 5, listOf(Dish("Lamb Hearts"))),
                 )
             ),
             Experience(
                 date = LocalDate.now(),
                 restaurantName = "LeeAnn Chinn",
                 restaurantId = "2",
                 reviews = listOf(
                     Review("Josh", "1", 3, emptyList()),
                 )
             )
         )
    }
}