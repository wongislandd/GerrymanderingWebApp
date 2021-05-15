import json


def get_geojson(filename):
    with open(filename) as f:
        gj = json.load(f)
        return gj


if __name__ == '__main__':
    num_districts = 6
    features = []
    state = 'louisiana'
    dir = f'../PreprocessNC/{state}'

    for i in range(1, num_districts + 1):
        filename = f'{dir}/{i}.geojson'
        features.append(get_geojson(filename))

    geojson = dict()
    geojson['type'] = 'FeatureCollection'
    geojson['features'] = features

    with open('louisianaEnacted2016.json', 'w') as f:
        json.dump(geojson, f)
