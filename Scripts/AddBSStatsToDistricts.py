import json
from random import randint

# Python script for adding RGB properties to GeoJson file features.
# Call example: "python3 AddRandomRGBToGeoJson.py ./TestGeoJson.json output.json"
# Takes the features in TestGeoJson.json and outputs it into output.json


inputFile = "./input/EnactedDistrictingPlan2011.json"
outputFile = "./output/EnactedDistrictingPlan2011WithData.json"



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
    black_pct = randint(15,25)
    blacks = round(remaining_pop * (.01 * black_pct))
    other = remaining_pop-blacks
    return ({
        'W' : whites,
        'B' : blacks,
        'O' : other,
    })



# Open the file
with open(inputFile) as f:
    data = json.load(f)

count = 0
# Add the property
for feature in data['features']:
    total_pop = randint(400000, 1000000)
    PartyDict = getParties(total_pop)
    RaceDict = getRaces(total_pop)
    feature['properties']['PARTY_DEM_COUNT'] = PartyDict['D']
    feature['properties']['PARTY_REP_COUNT'] = PartyDict['R']
    feature['properties']['PARTY_OTHER_COUNT'] = PartyDict['O']
    feature['properties']['RACE_WHITE_COUNT'] = RaceDict['W']
    feature['properties']['RACE_BLACK_COUNT'] = RaceDict['B']
    feature['properties']['RACE_OTHER_COUNT'] = RaceDict['O']
    count += 1

# Write the file
with open(outputFile, 'w+') as json_file:
    json.dump(data, json_file)
print(f"Successfully added info to {count} features.")
