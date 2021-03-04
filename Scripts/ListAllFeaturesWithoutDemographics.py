import json


PrecinctGeoData = "./output/PrecinctGeoDataOutput.json"

count = 0

with open(PrecinctGeoData) as f:
    data = json.load(f)
    features = data["features"]
    for feature in features:
        if "DEM" not in feature["properties"].keys():
            print(feature["properties"]["prec_id"], feature["properties"]["enr_desc"])
            count += 1

print(f"There are {count} features with no demographic data")