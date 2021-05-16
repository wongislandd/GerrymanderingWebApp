import json

inputFile = "../input/precincts_output.json"

with open(inputFile) as f:
    data = json.load(f)
    features = data["features"]
    total = 0

    for feature in features:
        properties = feature["properties"]
        total += properties["population"]

    print(total / 7)
