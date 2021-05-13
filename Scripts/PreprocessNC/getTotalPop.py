import json
import re


def get_county_id(record):
    return record[29:32]


def get_record_num(record):
    return record[18:25]


def read_precincts():
    with open('precincts.json') as f:
        precincts = json.load(f)

    ids = set()

    for precinct in precincts['features']:
        ids.add(precinct['properties']['name'])

    return ids


def get_pop_data(record_nums):
    pop_file = 'pop_data/al1.pl'
    record_to_pop = {}

    with open(pop_file) as f:
        for line in f:
            fields = line.split(',')
            record_num = fields[4]

            if record_num in record_nums:
                pop = {
                    'population': int(fields[5]),
                    'white': int(fields[7]),
                    'black': int(fields[8]),
                    'native_american': int(fields[9]),
                    'asian': int(fields[10]),
                    'pacific_islander': int(fields[11]),
                    'other': int(fields[12]),
                    'hispanic': int(fields[77])
                }

                record_to_pop[record_num] = pop

    return record_to_pop


def get_records():
    headers_file = 'pop_data/al_headers.pl'
    prec_to_record = {}
    prec_to_pop = {}

    record_nums = set()

    with open(headers_file) as f:
        for line in f:
            if 'AL700' in line:
                prec_id = line[226:316].strip()
                county_id = get_county_id(line)
                record_num = get_record_num(line)

                prec_to_record[(county_id, prec_id)] = record_num
                record_nums.add(record_num)

    record_to_pop = get_pop_data(record_nums)

    for prec in prec_to_record:
        prec_to_pop[prec] = record_to_pop[prec_to_record[prec]]

    return prec_to_pop


def main():
    prec_to_pop = get_records()
    precinct_input_file = 'al_precincts_input.json'

    with open(precinct_input_file) as f:
        precincts = json.load(f)

    for precinct in precincts['features']:
        county_id = precinct['properties']['county']
        prec_id = precinct['properties']['name']
        print(prec_id)

        pop = prec_to_pop[(county_id, prec_id)]
        props = precinct['properties'] | pop
        precinct['properties'] = props

    precinct_output_file = 'al_precincts_output.json'

    with open(precinct_output_file, 'w') as f:
        json.dump(precincts, f)


if __name__ == '__main__':
    main()
