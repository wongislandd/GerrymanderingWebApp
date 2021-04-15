import json

inputFile = "../input/RawPrecinctGeoData.json"
outputFile ="../input/PrecinctGeoDataCleaned.json"


# SOMETHING TO DO WITH #35, IT'S IN THE DATA FOR PRECINCTS,
# BUT ISN'T MAPPING PROPERLY.

with open(inputFile) as f:
    data = json.load(f)
    features = data["features"]
    # Remove the one with county_id of 0
    for feature in features:
        countyID = feature["properties"]["county_id"]
        if countyID == 0:
            features.remove(feature)
    with open(outputFile, 'w+') as json_file:
        print("Writing output file.")
        json.dump(data, json_file)