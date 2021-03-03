import pandas
import numpy as np

df = pandas.read_csv("input/NCPRECINCTDATA.csv")

print("File read")


# Code mappings listed here
# https://s3.amazonaws.com/dl.ncsbe.gov/data/layout_ncvoter.txt

# Get only the unique elements in a list
def unique(list):
    x = np.array(list)
    return np.unique(x)

class Precinct:
    def __init__(self, name):
        self.name = name
        self.races = {"A": 0, "B": 0, "I": 0, "M": 0, "O": 0, "P": 0, "U": 0, "W": 0, " " : 0}
        self.ethnicities = {"HL": 0, "NL": 0, "UN": 0}
        self.affiliations = {"DEM" : 0, "REP" : 0, "OTHER" : 0}

Precincts = {}
# Create the precincts
for precinctName in unique(df["precinct_desc"]):
    Precincts[precinctName] = Precinct(precinctName)

# Load in the dataa
for key, entry in df.iterrows():
    # Access the precinct object by name
    current_precinct = Precincts[entry["precinct_desc"]]
    current_precinct.races[entry["race_code"]] += 1
    current_precinct.ethnicities[entry["ethnic_code"]] += 1
    if entry["party_cd"] in current_precinct.affiliations.keys():
        current_precinct.affiliations[entry["party_cd"]] += 1
    else:
        current_precinct.affiliations["OTHER"] += 1


names = []
A = []
B = []
I = []
M = []
O = []
P = []
U = []
W = []
HL = []
NL = []
UN = []
DEM = []
REP = []
OTHER = []

print("Reading done")

# Summarize the data into csv ready form
for key in Precincts.keys():
    precinct = Precincts[key]
    names.append(precinct.name)
    A.append(precinct.races["A"])
    B.append(precinct.races["B"])
    I.append(precinct.races["I"])
    M.append(precinct.races["M"])
    O.append(precinct.races["O"])
    P.append(precinct.races["P"])
    U.append(precinct.races["U"])
    W.append(precinct.races["W"])
    HL.append(precinct.ethnicities["HL"])
    NL.append(precinct.ethnicities["NL"])
    UN.append(precinct.ethnicities["UN"])
    DEM.append(precinct.affiliations["DEM"])
    REP.append(precinct.affiliations["REP"])
    OTHER.append(precinct.affiliations["OTHER"])

data = pandas.DataFrame({'Name' : names, 'DEM' : DEM, 'REP' : REP, 'OTHER' : OTHER,
        'W' : W, 'B' : B, 'I' : I, 'M' : M, 'O' : O, 'P' : P, 'U' : U,
        'A' : A, 'HL' : HL, 'NL' : NL, 'UN' : UN})

data.to_csv("./output/NC-Processed.csv")