import json
from random import randint

# Python script for adding RGB properties to GeoJson file features.
# Call example: "python3 AddRandomRGBToGeoJson.py ./TestGeoJson.json output.json"
# Takes the features in TestGeoJson.json and outputs it into output.json


inputFile = "../input/precincts_output.json"
outputFile = "../output/al_precincts_output.json"


def getRandomRGBVal():
    return randint(0, 180)


# Open the file
with open(inputFile) as f:
    data = json.load(f)

count = 0
# Add the property
for feature in data['features']:
    feature['properties']['rgb-R'] = getRandomRGBVal()
    feature['properties']['rgb-G'] = getRandomRGBVal()
    feature['properties']['rgb-B'] = getRandomRGBVal()
    count += 1

# Write the file
with open(outputFile, 'w+') as json_file:
    json.dump(data, json_file)
print(f"Successfully added RGB to {count} features.")
