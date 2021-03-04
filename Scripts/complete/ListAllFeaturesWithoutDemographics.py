import json


PrecinctGeoData = "./output/PrecinctGeoDataOutput.json"

count = 0
total_count = 0

missed = set()

with open(PrecinctGeoData) as f:
    data = json.load(f)
    features = data["features"]
    for feature in features:
        if "DEM" not in feature["properties"].keys():
            missed.add((feature["properties"]["prec_id"], feature["properties"]["enr_desc"], feature["properties"]["county_id"]))
            count += 1
        total_count += 1

print(f"There are {count}/{total_count} features with no demographic data")

with open('../output/precincts-missing-data.txt', 'w+') as file:
    file.writelines(str(precinct[2]) + ", " + precinct[0] + ", " + precinct[1]+"\n" for precinct in missed)

