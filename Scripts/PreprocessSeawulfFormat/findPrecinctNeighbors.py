import geopandas
import json

# 200 feet
NEIGHBOR_TOL = 60.96
NUM_DISTRICTS = 13


def find_district(districts, precinct):
    inter_areas = {}

    for i in range(NUM_DISTRICTS):
        district_id = districts.get('District')[i]
        district_geom = districts.get('geometry')[i]

        inter = district_geom.intersection(precinct)
        inter_areas[district_id] = inter.area

    max_area = 0
    district = 0

    for id, area in inter_areas.items():
        if area > max_area:
            max_area = area
            district = id

    return int(district)


if __name__ == '__main__':
    # Read precincts
    precinct_file = '../PreprocessNC/precincts_output.json'

    with open(precinct_file) as f:
        precincts = geopandas.read_file(f)

    precincts.to_crs('EPSG:32119', inplace=True)

    # Read districts
    districts_file = '../../416-web-app/src/data/NC/EnactedDistrictingPlan2016WithData.json'

    with open(districts_file) as f:
        districts = geopandas.read_file(f)

    districts.to_crs('EPSG:32119', inplace=True)

    neighbors = {}
    count = precincts.count()['state']

    # Iterate through each pair of precincts to find precincts that share an edge
    for p1 in range(count):
        p1_id = int(precincts.get('id')[p1])
        p1_geom = precincts.get('geometry')[p1]
        p1_id_str = str(p1_id)

        print(p1_id)

        for p2 in range(count):
            p2_geom = precincts.get('geometry')[p2]
            p2_id = int(precincts.get('id')[p2])

            if p1_id == p2_id:
                continue

            inter = p1_geom.intersection(p2_geom)

            if inter.length >= NEIGHBOR_TOL:
                if p1_id_str not in neighbors:
                    neighbors[p1_id_str] = {'adjacent_nodes': []}

                neighbors[p1_id_str]['adjacent_nodes'].append(p2_id)

        neighbors[p1_id_str]['population'] = int(precincts.get('population')[p1])
        neighbors[p1_id_str]['district'] = find_district(districts, p1_geom)

    # Dump to JSON file
    with open('precinctNeighbors.json', 'w') as f:
        json.dump(neighbors, f)
