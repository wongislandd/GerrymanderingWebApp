import pandas
import math

inputFile = "../output/PrecinctVoterDataDirty.csv"
outputFile = "../output/PrecinctVoterData.csv"


propertyToSortBy = 'prec_id'


df = pandas.read_csv(inputFile).dropna()

df.to_csv(outputFile)