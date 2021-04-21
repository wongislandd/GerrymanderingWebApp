import json

inputFile = "../input/CountiesGeoDataDirty.json"
outputFile ="../input/CountiesGeoDataCleaned.json"


with open(inputFile) as f:
    data = json.load(f)
    features = data["features"]
    for feature in features:
        properties = feature["properties"]
        newProperties = {}
        newProperties["name"] = properties["UpperCount"]
        newProperties["id"] = properties["SAP_CNTY_N"]
        feature["properties"] = newProperties
    with open(outputFile, 'w+') as json_file:
        print("Writing output file.")
        json.dump(data, json_file)