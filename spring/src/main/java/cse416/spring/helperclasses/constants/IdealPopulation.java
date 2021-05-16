package cse416.spring.helperclasses.constants;

import cse416.spring.enums.StateName;

import java.util.HashMap;

public class IdealPopulation {
    private static final HashMap<StateName, Integer> populationMap = new HashMap<>();

    static {
        populationMap.put(StateName.NORTH_CAROLINA, 733499);
        populationMap.put(StateName.LOUISIANA, 755562);
        populationMap.put(StateName.ALABAMA, 676293);
    }

    public static int getIdealPopulation(StateName stateName) {
        return populationMap.get(stateName);
    }
}
