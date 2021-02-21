import json
from os import path
from functools import cmp_to_key
import sys


# Python script for sorting GeoJson based on a property.
# Useful since we'll need quick access in order to add properties to
# certain precincts when grouping them and that'll be way faster
# when it's pre-sorted.

# For command line args
# if len(sys.argv) < 3:
#     print("Not enough args.")
#     exit(0)
#
# inputFile = sys.argv[1]
# outputFile = sys.argv[2]

inputFile = "./datasets/NCGeoData.json"
outputFile = "./datasets/SortedNCGeoData.json"


propertyToSortBy = 'enr_desc'

if not path.isfile(inputFile):
    print("Invalid input (File doesn't exist)")
    exit(0)


# Open the file
with open(inputFile) as f:
    data = json.load(f)


# Sort the precincts
def precinct_comparator(a,b):
    if a['properties'][propertyToSortBy] < b['properties'][propertyToSortBy]:
        return -1
    elif a['properties'][propertyToSortBy] > b['properties'][propertyToSortBy]:
        return 1
    else:
        return 0

sorted_data = sorted(data['features'],key=cmp_to_key(precinct_comparator))

# Write the data
with open(outputFile, 'w+') as json_file:
    json.dump(data, json_file)