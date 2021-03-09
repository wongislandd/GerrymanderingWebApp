fs = require('fs')
var turf = require('@turf/turf');

// Find the total length of a set of lines
function sumLength(lines) {
  var total = 0

  for (line of lines) {
    total += turf.length(line)
  }

  return total
}

// Read precinct data
fs.readFile('../../416-web-app/src/data/NC/PrecinctGeoDataOutput.json', 'utf8', function (err,data) {
  if (err) {
    return console.log(err);
  }
  
  // Parse JSON
  const precincts = JSON.parse(data)
  var coords = {}

  for (let precinct of precincts.features) {
      const c_id = precinct.properties.COUNTY_ID
      const p_name = precinct.properties.PREC_NAME
      const p_id = precinct.properties.PREC_ID

      const p_strings = [c_id, p_name, p_id]
      const p_obj = p_strings.join(' ')
      coords[p_obj] = precinct.geometry
  }

  const all_precincts = Object.keys(coords)
  var neighbors_graph = {}

  var count = 0

  // Iterate through each pair of precincts to find precincts that share an edge
  for (let precinct1 of all_precincts) {
    for (let precinct2 of all_precincts) {
      if (precinct1 === precinct2) {
        continue
      }
      
      // 200 feet is 0.06096 kilometers
      const TOL = 0.06096
      const overlap = turf.lineOverlap(coords[precinct1], coords[precinct2], {tolerance: TOL})

      if (overlap.features.length > 0) {
        const overlap_length = sumLength(overlap.features)

        if (overlap_length >= TOL) {

          if (!(precinct1 in neighbors_graph)){
            neighbors_graph[precinct1] = []
          }

          neighbors_graph[precinct1].push(precinct2)
        }
      }
    }

    count += 1
    console.log(count)
  }

  console.log(neighbors_graph)

  const graph_data = JSON.stringify(neighbors_graph)
  fs.writeFile('precinctNeighbors.json', graph_data, (err) => {
    if (err) {
      throw err;
    }

    console.log('JSON saved')
  })
});