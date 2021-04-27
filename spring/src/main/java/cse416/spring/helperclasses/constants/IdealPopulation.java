package cse416.spring.helperclasses.constants;

import cse416.spring.enums.StateName;

import java.util.HashMap;

public class IdealPopulation {
    private static final HashMap<StateName, Integer> populationMap = new HashMap<>();

    static {
        populationMap.put(StateName.NORTH_CAROLINA, 733499);
        populationMap.put(StateName.LOUISIANA, 0);
        populationMap.put(StateName.TEXAS, 0);
    }

    public static int getIdealPopulation(StateName stateName) {
        return populationMap.get(stateName);
    }
}
