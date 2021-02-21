import json
from random import randint
from os import path
import sys

# Python script for adding RGB properties to GeoJson file features.
# Call example: "python3 AddRandomRGBToGeoJson.py ./TestGeoJson.json output.json"
# Takes the features in TestGeoJson.json and outputs it into output.json


if len(sys.argv) < 3:
    print("Not enough args.")
    exit(0)

inputFile = sys.argv[1]
outputFile = sys.argv[2]

if not path.isfile(inputFile):
    print("Invalid input (File doesn't exist")
    exit(0)


def getRandomRGBVal():
    return randint(0, 255)


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
