import json

editsFile = "./datasets/name_edits.txt"
jsonFile = "./datasets/TestGeoJson.json"
delimiter = " "

propertyToCheck = "enr_desc"


# Uses binary search
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


try:
    count = 0
    # Open the file
    with open(jsonFile) as f:
        data = json.load(f)
        edits = open(editsFile)
        for line in edits:
            # Identify the precinct and new color
            precinctName, newColor = line.replace("\n", "").split(delimiter)
            indexTarget = custom_binary_search(data['features'], precinctName)
            data['features'][indexTarget]['properties']['rgb'] = newColor
            print(f"Changed {precinctName}'s RGB to {newColor}")
            count += 1
        # Update the file
        with open(jsonFile, 'w+') as json_file:
            json.dump(data, json_file)
        print(f"Successfully adjusted {count} precincts.")
except:
    print("Couldn't write all new colors, no changes saved.")
