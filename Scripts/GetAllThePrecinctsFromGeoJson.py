import json

PrecinctGeoData = "./output/SortedPrecinctGeoData.json"

precinctIDs = set()

with open(PrecinctGeoData) as f:
    features = json.load(f)['features']
    for feature in features:
        precinctIDs.add(feature['properties']['prec_id'])


with open('./output/unique-precinct-ids.txt', 'w+') as file:
    file.writelines("%s\n" % id for id in precinctIDs)

