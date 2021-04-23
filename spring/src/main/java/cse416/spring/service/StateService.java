package cse416.spring.service;

import com.github.javafaker.Faker;
import cse416.spring.enums.StateName;
import cse416.spring.models.precinct.Incumbent;

import java.util.ArrayList;

public class StateService {
    public static ArrayList<Incumbent> getIncumbents(StateName state) {
        int numIncumbents = 5;
        Faker faker = new Faker();
        ArrayList<Incumbent> fakePeople = new ArrayList<Incumbent>();
        for (int i=0; i<numIncumbents;i++) {
            fakePeople.add(new Incumbent(faker.gameOfThrones().character(), faker.gameOfThrones().city()));
        }
    return fakePeople;
    }
}
