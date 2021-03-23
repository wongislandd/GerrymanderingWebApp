import json
from random import randint

# Python script for adding RGB properties to GeoJson file features.
# Call example: "python3 AddRandomRGBToGeoJson.py ./TestGeoJson.json output.json"
# Takes the features in TestGeoJson.json and outputs it into output.json

import random



def randPercentage():
    return round(random.random(),2)

def addObjectiveFunctionData(data):
    data['objectivefunc'] = {
        'AVG_POPULATION_EQUALITY' : round(randPercentage(),2),
        'AVG_POLITICAL_FAIRNESS' : round(randPercentage(),2),
        'SPLIT_COUNTY_SCORE' : round(randPercentage(),2),
        'TOTAL_MAJORITY_MINORITY_DISTRICTS' : randint(0,3),
        'AVG_DEVIATION_FROM_AVG_DISTRICTING' : round(randPercentage(),2),
        'AVG_DEVIATION_FROM_ENACTED_DISTRICTING' : round(randPercentage(),2),
        'AVG_COMPACTNESS' : round(randPercentage(),2),
    }
    return data


def getRandomAmountOfSplitCounties():
    return randint(0,3)

def getMinorityPopulation(white_population, total_population):
    return total_population - white_population


def getParties(total_pop):
    democrat_pct = randint(30, 50)
    democrats = round(total_pop * (.01 * democrat_pct))
    remaining_pop = total_pop - democrats
    republicans_pct = randint(30, 50)
    republicans = round(remaining_pop * (.01 * republicans_pct))
    other = remaining_pop-republicans
    return ({
        'D' : democrats,
        'R' : republicans,
        'O' : other
    })



def getRaces(total_pop):
    white_pct = randint(40,70)
    whites = round(total_pop * (.01 * white_pct))
    remaining_pop = total_pop - whites

    black_pct = randint(10,15)
    blacks = round(remaining_pop * (.01 * black_pct))
    remaining_pop = remaining_pop - blacks

    american_indian_pct = randint(1, 3)
    american_indians = round(remaining_pop * (.01 * american_indian_pct))
    remaining_pop = remaining_pop - american_indians

    native_hawaiian_or_pacific_islander_pct = randint(3, 5)
    native_hawaiian_or_pacific_islanders = round(remaining_pop * (.01 * native_hawaiian_or_pacific_islander_pct))
    remaining_pop = remaining_pop - native_hawaiian_or_pacific_islanders

    undesignated_pct = randint(1, 2)
    undesignateds = round(remaining_pop * (.01 * undesignated_pct))
    remaining_pop = remaining_pop - undesignateds

    asian_pct = randint(2, 3)
    asians = round(remaining_pop * (.01 * asian_pct))
    remaining_pop = remaining_pop - asians
    return ({
        'W' : whites,
        'B' : blacks,
        'I' : american_indians,
        'P' : native_hawaiian_or_pacific_islanders,
        'A' : asians,
        'U' : undesignateds,
        'O' : remaining_pop,
    })


def getRandomTrueOrFalseAtRate(rate):
    if randPercentage() < rate:
        return True
    else:
        return False

def addBSData(inputFile, outputFile):
    # Open the file
    with open(inputFile) as f:
        data = json.load(f)
        data = addObjectiveFunctionData(data)

    count = 0
    # Add the property to each district
    for feature in data['features']:
        total_pop = randint(400000, 1000000)
        numberOfVoters = total_pop-randint(20000,100000)
        PartyDict = getParties(numberOfVoters)
        RaceDict = getRaces(total_pop)
        feature['properties']['VOTER_COUNT'] = numberOfVoters
        feature['properties']['PARTY_DEM_COUNT'] = PartyDict['D']
        feature['properties']['PARTY_REP_COUNT'] = PartyDict['R']
        feature['properties']['PARTY_OTHER_COUNT'] = PartyDict['O']
        feature['properties']['TOTAL_POPULATION'] = total_pop
        feature['properties']['RACE_WHITE_COUNT'] = RaceDict['W']
        feature['properties']['RACE_BLACK_COUNT'] = RaceDict['B']
        feature['properties']['RACE_ASIAN_COUNT'] = RaceDict['A']
        feature['properties']['RACE_NATIVE_COUNT'] = RaceDict['I']
        feature['properties']['RACE_PACIFIC_ISLANDER_COUNT'] = RaceDict['P']
        feature['properties']['RACE_UNDESIGNATED_COUNT'] = RaceDict['U']
        feature['properties']['RACE_OTHER_COUNT'] = RaceDict['O']

        # Individual district contribution towards the objective function
        feature['properties']['POPULATION_EQUALITY'] = randPercentage()
        feature['properties']['POLITICAL_FAIRNESS'] = randPercentage()
        feature['properties']['SPLIT_COUNTIES'] = getRandomAmountOfSplitCounties()
        feature['properties']['MAJORITY_MINORITY_DISTRICT'] = str(getRandomTrueOrFalseAtRate(.35))
        feature['properties']['DEVIATION_FROM_AVG_DISTRICTING'] = randPercentage()
        feature['properties']['DEVIATION_FROM_ENACTED_DISTRICTING'] = randPercentage()
        feature['properties']['COMPACTNESS'] = randPercentage()
        feature['properties']['MINORITY_POPULATION'] = getMinorityPopulation(total_pop - RaceDict['W'], total_pop)
        count += 1

    # Write the file
    with open(outputFile, 'w+') as json_file:
        json.dump(data, json_file)
    print(f"Successfully added info to {count} features.")



inputFile1 = "./input/EnactedDistrictingPlan2011.json"
outputFile1 = "../416-web-app/src/data/NC/EnactedDistrictingPlan2011WithData.json"
inputFile2 = "./input/EnactedDistrictingPlan2016.json"
outputFile2 = "../416-web-app/src/data/NC/EnactedDistrictingPlan2016WithData.json"
inputFile3 = "./input/EnactedDistrictingPlan2019.json"
outputFile3 = "../416-web-app/src/data/NC/EnactedDistrictingPlan2019WithData.json"


addBSData(inputFile1, outputFile1)
addBSData(inputFile2, outputFile2)
addBSData(inputFile3, outputFile3)

