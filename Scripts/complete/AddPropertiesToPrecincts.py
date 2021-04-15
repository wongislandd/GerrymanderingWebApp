import pandas as pd
import json
from difflib import SequenceMatcher

# Works on a Precinct-ID-Sorted GeoData

def similar(a,b):
    return SequenceMatcher(None, a, b).ratio()

PrecinctPropertiesCSV = "../output/PrecinctVoterData.csv"

PrecinctGeoData = "../output/SortedPrecinctGeoData.json"

OutputFile = "../output/PrecinctGeoDataOutput.json"

propertyToCheck = "prec_id"

write = True


# Manually went through the ~140 missing ones and will create
# custom mappings to the ones I can figure out
# If it's in this dictionary and is part of the right county,
# use the key (prec_id)'s value, which corresponds to what the GeoData has it listed as

#CustomPrecinctEnrDescMappings[key][0] = County ID to replace for
#CustomPrecinctEnrDescMappings[key][1] = Value to replace with

CustomPrecinctEnrDescMappings = {
    "G2C-2" : (26, "CROSS CREEK G2C-2"),
    "01" : (32, "BROGDEN MIDDLE SCHOOL"),
}



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
    # if similar(a, b) > .3 or a in b or b in a:
    if a in b or b in a:
        return True
    else:
        return False


# GOING TO NEED TO LOOK FOR THE EXACT SAME PRECINCT_ID, PRECINCT_NAME, AND COUNTY_NAME

def custom_binary_search(arr, prec_id, enr_desc, county_id):
    rangeToCheck = 20
    idIndex = search_for_prec_id(arr,prec_id)
    if idIndex == -1:
        print(f"There is no feature in the GeoJson with prec_id: {prec_id} {enr_desc}")
        return -1
    # Dig for a full match, if it's the only one with the ID, skip the following check
    if isOnlyOneWithID(arr, idIndex):
        #print(f"{arr[idIndex]['properties']['enr_desc']} is the only one with this the ID {prec_id}")
        return idIndex
    # Change the enr_desc if it's a custom one
    if prec_id in CustomPrecinctEnrDescMappings.keys():
        if (CustomPrecinctEnrDescMappings[prec_id][0] == county_id):
            enr_desc = CustomPrecinctEnrDescMappings[prec_id][1]
   # print("-----------------------------------------------------------------------------")
    #print(f"Searching for a match for {county_id} {prec_id} {enr_desc}. . .")
    enr_desc = cleanEnrDesc(enr_desc)
    for i in range(idIndex-rangeToCheck, idIndex+rangeToCheck):
        if i < 0 or i > len(arr) - 1:
            continue
        featureProperties = arr[i]["properties"]
        # It must match both precinct ID and county ID
        if featureProperties["prec_id"] == prec_id and featureProperties["county_id"] == county_id:
            featureEnrDesc = cleanEnrDesc(featureProperties["enr_desc"])
            # Some (~100) entries in the data file list the prec_id as the enr_desc when there's actually a name for it that the
            # GeoJson file is expecting
            if featureEnrDesc == enr_desc or countsAsSimilar(featureEnrDesc, enr_desc) or featureProperties["prec_id"] == enr_desc:
                #print(f"{featureEnrDesc} matches with {enr_desc} (Similarity: {similar(featureEnrDesc, enr_desc)})")
                return i
            else:
                print(f"{featureEnrDesc} does not match with {enr_desc} (Similarity: {similar(featureEnrDesc, enr_desc)})")
    print(f"Couldnt find a match for {entry['PREC_ID']} ({entry['PREC_NAME']})")
    return -1


df = pd.read_csv(PrecinctPropertiesCSV)
success_count = 0
total_count = 0
with open(PrecinctGeoData) as f:
    data = json.load(f)
    features = data["features"]
    for key, entry in df.iterrows():
        if(entry["PREC_ID"] == "UNVERIFIED"):
            continue
        search_index = custom_binary_search(features, entry["PREC_ID"], entry["PREC_NAME"], entry["COUNTY_ID"])
        total_count += 1
        # If there is a match, write the properties.
        if search_index > -1:
            targetFeature = features[search_index]
            success_count += 1
            for col in entry.keys():
                targetFeature["properties"][col] = entry[col]

    if write:
        with open(OutputFile, 'w+') as json_file:
            print("Writing output file.")
            json.dump(data, json_file)
            print(f"Successfully wrote properties to {success_count} out of {total_count} precincts.")
    else:
        print(f"Successfully matched properties {success_count} out of {total_count} precincts.")

