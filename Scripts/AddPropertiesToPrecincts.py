import pandas as pd
import json

PrecinctPropertiesCSV = "./output/NC-Processed-Prec-ID.csv"

PrecinctGeoData = "./output/SortedPrecinctGeoData.json"

OutputFile = "./output/PrecinctGeoData.json"

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


df = pd.read_csv(PrecinctPropertiesCSV)
success_count = 0
total_count = 0
with open(PrecinctGeoData) as f:
    data = json.load(f)
    features = data["features"]
    for key, entry in df.iterrows():
        search_index = custom_binary_search(features, entry["PREC_ID"])
        total_count += 1
        # If there is a match, write the properties.
        if search_index > -1:
            targetFeature = features[search_index]
            success_count += 1
            for col in entry.keys():
                targetFeature["properties"][col] = entry[col]
    with open(OutputFile, 'w+') as json_file:
        json.dump(data, json_file)
        print(f"Successfully wrote properties to {success_count} out of {total_count} precincts.")

