import pandas

inputFile = "../output/PrecinctVoterDataDirty.csv"
outputFile = "../output/PrecinctVoterData.csv"


propertyToSortBy = 'prec_id'


df = pandas.read_csv(inputFile)

# Open the file
for entry in df["PREC_NAME"]:
    if entry:
        print(entry)