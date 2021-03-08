export const STATE_OPTIONS = {
    UNSELECTED : "UNSELECTED",
    NORTH_CAROLINA : "NORTH_CAROLINA",
    TEXAS : "TEXAS",
    CALIFORNIA :"CALIFORNIA"
}

/* Global view */
export const UNSELECTED = {
    Maximized : {
        latitude : 40.29091544906472, 
        longitude: -97.44332861851478,
        width: "75vw",
        height: window.innerHeight,
        zoom: 3.75
    }
}

export const NORTH_CAROLINA = {
    Maximized : {
        latitude : 35.490477690914446, 
        longitude: -79.41601173255576,
        width: "75vw",
        height: window.innerHeight,
        zoom: 6.5
    },
    Minimized : {
        latitude : 35.490477690914446, 
        longitude: -79.41601173255576,
        width: "40vw",
        height: window.innerHeight,
        zoom: 5.5
    }
}

export const CALIFORNIA = {
    Maximized : {
        latitude : 35.27597043220337, 
        longitude: -118.1878699884443,
        width: "75vw",
        height: window.innerHeight,
        zoom: 4.5
    },
    Minimized : {
        latitude : 35.27597043220337, 
        longitude: -118.1878699884443,
        width: "40vw",
        height: window.innerHeight,
        zoom: 3.5
    }
}

export const TEXAS = {
    Maximized : {
        latitude : 32.02951701992466, 
        longitude: -97.95986810159535,
        width: "75vw",
        height: window.innerHeight,
        zoom: 5.5
    },
    Minimized : {
        latitude : 32.02951701992466, 
        longitude: -97.95986810159535,
        width: "40vw",
        height: window.innerHeight,
        zoom: 4.5
    }
}