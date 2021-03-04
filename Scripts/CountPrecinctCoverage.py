import pandas as pd
import json

PrecinctPropertiesCSV = "./output/NC-Processed-Prec-ID.csv"

PrecinctGeoData = "./output/SortedPrecinctGeoData.json"

propertyToCheck = "prec_id"


# Uses binary search to search through the features arr
def custom_binary_search(arr, x):
    low = 0
    high = len(arr) - 1
    mid = 0
    while low <= high:
        mid = (high + low) // 2
        # If x is greater, ignore left half
        if arr[mid]["properties"][propertyToCheck] < x:
            low = mid + 1
        # If x is smaller, ignore right half
        elif arr[mid]["properties"][propertyToCheck] > x:
            high = mid - 1
        # means x is present at mid
        else:
            return mid
    # If we reach here, then the element was not present
    return -1

found = set()
not_found = set()
df = pd.read_csv(PrecinctPropertiesCSV)

with open(PrecinctGeoData) as f:
    features = json.load(f)['features']
    for entry in df['PREC_ID']:
        search_index = custom_binary_search(features, entry)
        if search_index >= 0:
            found.add(entry)
        else:
            not_found.add(entry)
    print(f"Found {len(found)} Precincts")
    print(f"Couldn't find {len(not_found)} Precincts")


# Take all the unique precincts in the GeoJson - All Precincts found matches for to see whats left
PrecinctsInTheGeoJsonFile = "./output/unique-precinct-ids.txt"

with open(PrecinctsInTheGeoJsonFile) as f:
    precinctsInFile = set()
    for line in f:
        precinctsInFile.add(line.strip())
    f.close()

difference = precinctsInFile.difference(found)

print(f"Missing {len(difference)} precincts.")






