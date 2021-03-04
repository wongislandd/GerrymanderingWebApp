import pandas as pd
import json
from difflib import SequenceMatcher

def similar(a,b):
    return SequenceMatcher(None, a, b).ratio()

PrecinctPropertiesCSV = "./output/NC-Processed.csv"

PrecinctGeoData = "./output/PrecIDSortedPrecinctGeoData.json"

OutputFile = "./output/PrecinctGeoDataOutput.json"

propertyToCheck = "prec_id"


# Uses binary search to search through the features arr
def search_for_prec_id(arr, x):
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


# Solves about 500 of the problems, but can't target exactly which ones. Should still solve this through uniform formatting.
def isOnlyOneWithID(arr, index):
    if index > 0:
        if arr[index - 1]["properties"]["prec_id"] == arr[index]["properties"]["prec_id"]:
            return False
    if index < len(arr)-1:
        if arr[index + 1]["properties"]["prec_id"] == arr[index]["properties"]["prec_id"]:
            return False
    return True

def cleanEnrDesc(str = ""):
    str = str.replace("#","")
    str = str.replace(" ","")
    str = str.upper()
    return str

def countsAsSimilar(a,b):
    # If the similarity metric counts it or A is a substring of B
    if similar(a, b) > .3 or a in b or b in a:
        return True
    else:
        return False


def custom_binary_search(arr, prec_id, enr_desc):
    rangeToCheck = 20
    idIndex = search_for_prec_id(arr,prec_id)
    if idIndex == -1:
        return -1
    # Dig for a full match, if it's the only one with the ID, skip the following check
    if isOnlyOneWithID(arr, idIndex):
        return idIndex
    print("-----------------------------------------------------------------------------")
    print(f"Searching for a match for {enr_desc}. . .")
    enr_desc = cleanEnrDesc(enr_desc)
    for i in range(idIndex-rangeToCheck, idIndex+rangeToCheck):
        if i < 0 or i > len(arr) - 1:
            continue
        featureProperties = arr[i]["properties"]
        if featureProperties["prec_id"] == prec_id:
            featureEnrDesc = cleanEnrDesc(featureProperties["enr_desc"])
            if featureEnrDesc == enr_desc or countsAsSimilar(featureEnrDesc, enr_desc):
                print(f"{featureEnrDesc} matches with {enr_desc}")
                return i
            else:
                print(f"{featureEnrDesc} does not match with {enr_desc} (Similarity: {similar(featureEnrDesc, enr_desc)})")
    print(f"Couldnt find {entry['PREC_ID']} ({entry['PREC_NAME']})")
    return -1


df = pd.read_csv(PrecinctPropertiesCSV)
success_count = 0
total_count = 0
with open(PrecinctGeoData) as f:
    data = json.load(f)
    features = data["features"]
    for key, entry in df.iterrows():
        search_index = custom_binary_search(features, entry["PREC_ID"], entry["PREC_NAME"])
        total_count += 1
        # If there is a match, write the properties.
        if search_index > -1:
            targetFeature = features[search_index]
            success_count += 1
            for col in entry.keys():
                targetFeature["properties"][col] = entry[col]

    # with open(OutputFile, 'w+') as json_file:
    #     json.dump(data, json_file)
print(f"Successfully wrote properties to {success_count} out of {total_count} precincts.")

