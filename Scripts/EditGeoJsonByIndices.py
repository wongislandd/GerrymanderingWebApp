import json

# Take in a text file with each line listing the index of the feature to change and the new color to change it to.
# The format should be "[index][delimiter][newcolor]" ex. "2 #fffff"
# This script will read through the lines and make the adjustments to the geoJson as needed.
# The agreed upon index assumes that all the precincts are in alphabetical order.

editsFile = "./datasets/index_edits.txt"
jsonFile = "./datasets/TestGeoJson.json"
delimiter = " "

try:
    count = 0
    # Open the json file
    with open(jsonFile) as f:
        data = json.load(f)
        # Open the edits file
        edits = open(editsFile)
        for line in edits:
            # Identify the precinct and new color
            precinctIndex, newColor = line.replace("\n", "").split(delimiter)
            # Add it to the data set
            data['features'][int(precinctIndex)]['properties']['rgb'] = newColor
            print(f"Changed {precinctIndex}'s RGB to {newColor}")
            count += 1
        # Update the file
        with open(jsonFile, 'w+') as json_file:
            json.dump(data, json_file)
        print(f"Successfully adjusted {count} precincts.")
except:
    print("Couldn't write all new colors, no changes saved.")
