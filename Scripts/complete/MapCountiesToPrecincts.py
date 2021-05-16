import json

inputFile = "../input/nc_precincts_output.json"


countyPrecinctsDict = {}

with open(inputFile) as f:
    data = json.load(f)
    features = data["features"]
    totalPop = 0
    for feature in features:
        properties = feature["properties"]
        totalPop += properties["population"]
    print(totalPop/13)