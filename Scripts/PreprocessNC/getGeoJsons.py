import json
import os
import requests
import shapefile
import zipfile


def get_counties_list():
    counties = []
    for c in range(1001, 1135, 2):
        counties.append('0' + str(c))

    return counties


def get_useful_atrs(atrs):
    new_atrs = {
        'state': atrs['STATEFP10'],
        'county': atrs['COUNTYFP10'],
        'name': atrs['NAME10'][:90]
    }

    print(atrs['NAME10'][:90])
    return new_atrs


if __name__ == '__main__':
    counties = get_counties_list()
    prec_id = 1

    # The geojson string
    buffer = []

    for county in counties:
        print(f'Processing county {county}')

        # Get the zip file from the web
        url = f'https://www2.census.gov/geo/pvs/tiger2010st/01_Alabama/{county}/tl_2010_{county}_vtd10.zip'
        r = requests.get(url, allow_redirects=True)

        # Extract the zip file
        output_zip_path = f'temp/{county}.zip'
        open(output_zip_path, 'wb').write(r.content)

        with zipfile.ZipFile(output_zip_path, 'r') as zip_ref:
            zip_ref.extractall(f'temp/{county}')

        # Delete the zip file
        os.remove(output_zip_path)

        # Convert the shapefiles into a geojson
        shp = open(f'temp/{county}/tl_2010_{county}_vtd10.shp', 'rb')
        dbf = open(f'temp/{county}/tl_2010_{county}_vtd10.dbf', 'rb')

        reader = shapefile.Reader(shp=shp, dbf=dbf)
        fields = reader.fields[1:]
        field_names = [field[0] for field in fields]

        for sr in reader.shapeRecords():
            atr = dict(zip(field_names, sr.record))
            atr = get_useful_atrs(atr)
            atr['id'] = prec_id
            prec_id += 1
            geom = sr.shape.__geo_interface__

            buffer.append(dict(type='Feature', geometry=geom, properties=atr))

    geojson = open('al_precincts_input.json', 'w')
    geojson.write(json.dumps({
        'type': 'FeatureCollection',
        'features': buffer
    }))

    geojson.close()
