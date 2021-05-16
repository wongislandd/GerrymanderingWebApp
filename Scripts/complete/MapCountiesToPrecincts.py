import json

inputFile = "../input/precincts_output.json"
outputFile ="../output/CountiesPrecinctsMapping.json"


countyPrecinctsDict = {}

with open(inputFile) as f:
    data = json.load(f)
    features = data["features"]
    for feature in features:
        properties = feature["properties"]
        countyID = properties["county"]
        if countyID not in countyPrecinctsDict:
            countyPrecinctsDict[countyID] = {'name' : countyID, 'precincts' : [properties['id']]}
        else:
            countyPrecinctsDict[countyID]['precincts'].append(properties["id"])
    with open(outputFile, 'w+') as json_file:
        print("Writing output file.")
        json.dump(countyPrecinctsDict, json_file)