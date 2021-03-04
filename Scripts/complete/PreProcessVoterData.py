import pandas
import numpy as np

df = pandas.read_csv("../input/NC_VOTER_DATA_FILTERED.csv")

print("File read")

class Precinct:
    def __init__(self, prec_id, enr_desc, county_id):
        self.prec_id = prec_id
        self.enr_desc = enr_desc
        self.county_id = county_id
        self.races = {"A": 0, "B": 0, "I": 0, "M": 0, "O": 0, "P": 0, "U": 0, "W": 0, " " : 0}
        self.affiliations = {"DEM" : 0, "REP" : 0, "OTHER" : 0}

Precincts = {}
# Create the precincts, which are unique based on their ID and ENR_DESC. Stored in Map as Tuple
for key, entry in df.iterrows():
    if (entry["precinct_abbrv"], entry["precinct_desc"], entry["county_id"]) not in entry.keys():
        Precincts[(entry["precinct_abbrv"], entry["precinct_desc"], entry["county_id"])] = Precinct(entry["precinct_abbrv"], entry["precinct_desc"], entry["county_id"])


# Load in the dataa
for key, entry in df.iterrows():
    # Access the precinct object by name
    current_precinct = Precincts[(entry["precinct_abbrv"], entry["precinct_desc"], entry["county_id"])]
    current_precinct.races[entry["race_code"]] += 1
    if entry["party_cd"] in current_precinct.affiliations.keys():
        current_precinct.affiliations[entry["party_cd"]] += 1
    else:
        current_precinct.affiliations["OTHER"] += 1

prec_ids = []
names = []
county_ids = []
A = []
B = []
I = []
M = []
O = []
P = []
U = []
W = []
DEM = []
REP = []
OTHER = []

print("Reading done")

# Summarize the data into csv ready form
for key in Precincts.keys():
    precinct = Precincts[key]
    prec_ids.append(precinct.prec_id)
    county_ids.append(precinct.county_id)
    names.append(precinct.enr_desc)
    A.append(precinct.races["A"])
    B.append(precinct.races["B"])
    I.append(precinct.races["I"])
    M.append(precinct.races["M"])
    O.append(precinct.races["O"])
    P.append(precinct.races["P"])
    U.append(precinct.races["U"])
    W.append(precinct.races["W"])
    DEM.append(precinct.affiliations["DEM"])
    REP.append(precinct.affiliations["REP"])
    OTHER.append(precinct.affiliations["OTHER"])

data = pandas.DataFrame({'PREC_ID' : prec_ids, 'PREC_NAME' : names, 'COUNTY_ID' : county_ids, 'DEM' : DEM, 'REP' : REP, 'OTHER' : OTHER,
        'W' : W, 'B' : B, 'I' : I, 'M' : M, 'O' : O, 'P' : P, 'U' : U,
        'A' : A})

data.to_csv("./output/NC-Processed-Large.csv")

print("Writing done")