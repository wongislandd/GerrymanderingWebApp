export const STATE_OPTIONS = {
    UNSELECTED : "UNSELECTED",
    NORTH_CAROLINA : "NC",
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

export const NC = {
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
