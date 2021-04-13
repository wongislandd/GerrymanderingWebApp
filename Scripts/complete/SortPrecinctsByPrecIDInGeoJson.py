import json

# Python script for sorting GeoJson based on a property.
# Useful since we'll need quick access in order to add properties to
# certain precincts when grouping them and that'll be way faster
# when it's pre-sorted.


inputFile = "./input/RawPrecinctGeoData.json"
outputFile = "./output/SortedPrecinctGeoData.json"



# Open the file
with open(inputFile) as f:
    data = json.load(f)


sorted_data = sorted(data['features'], key=lambda x : x['properties'][propertyToSortBy])

data['features'] = sorted_data

# Write the data
with open(outputFile, 'w+') as json_file:
    json.dump(data, json_file)

