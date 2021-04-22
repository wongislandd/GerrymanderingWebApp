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

// Read districts data
function readDistricts(filename) {
  const data = fs.readFileSync(filename, 'utf8')
    
  var district_coords = {}
  const districts = JSON.parse(data)

  for (let district of districts.features) {
    const id = district.properties.District;
    district_coords[id] = district.geometry;
  }

  return district_coords;
}

function findDistrict(districts_geom, precinct) {
  const p_coords = precinct.geometry;
  var inter_areas = {};
  
  // For each district, find the intersection area with the precinct
  for (let district_id of Object.keys(districts_geom)) {
    const intersection = turf.intersect(districts_geom[district_id], p_coords);
    if (intersection === null)
      continue

    const inter_area = turf.area(intersection);
    inter_areas[district_id] = inter_area;
  }

  var max_area = 0;
  var containing_district = '';

  // Find the district with the largest intersection area
  for (let district_id of Object.keys(inter_areas)) {
    if (inter_areas[district_id] > max_area) {
      max_area = inter_areas[district_id];
      containing_district = district_id;
    }
  }

  return containing_district;
}

// Read precinct data
fs.readFile('../PrecinctGeoDataSmall.json', 'utf8', function (err,data) {
  if (err) {
    return console.log(err);
  }
  
  // Parse JSON
  const precincts = JSON.parse(data)
  var all_precincts = {}

  for (let precinct of precincts.features) {
      const p_id = precinct.properties.id
      all_precincts[p_id] = precinct
  }

  // Read districts data
  const districts = readDistricts("../../416-web-app/src/data/NC/EnactedDistrictingPlan2016WithData.json");

  var neighbors_graph = {}
  var count = 0

  // Iterate through each pair of precincts to find precincts that share an edge
  for (let precinct1 of Object.keys(all_precincts)) {
    const precinct1Int = parseInt(precinct1)

    for (let precinct2 of Object.keys(all_precincts)) {
      const precinct2Int = parseInt(precinct2)

      if (precinct1 === precinct2) {
        continue
      }
      
      // 200 feet is 0.06096 kilometers
      const TOL = 0.06096
      const overlap = turf.lineOverlap(all_precincts[precinct1].geometry, all_precincts[precinct2].geometry, {tolerance: TOL})

      if (overlap.features.length > 0) {
        const overlap_length = sumLength(overlap.features)

        if (overlap_length >= TOL) {

          if (!(precinct1 in neighbors_graph)){
            neighbors_graph[precinct1Int] = {"adjacent_nodes" : []}
          }

          neighbors_graph[precinct1Int].adjacent_nodes.push(precinct2Int)
        }
      }
    }

    count += 1
    console.log(count)

    const properties = all_precincts[precinct1].properties;
    neighbors_graph[precinct1Int]["population"] = properties.TOTAL_POPULATION;

    // Find the enacted district number the precinct is contained in
    neighbors_graph[precinct1Int]["district"] = parseInt(findDistrict(districts, all_precincts[precinct1]))
  }

const graph_data = JSON.stringify(neighbors_graph)

  // Write data into json file
  fs.writeFile('precinctNeighbors.json', graph_data, (err) => {
    if (err) {
      throw err;
    }

    console.log('JSON saved')
  })
})