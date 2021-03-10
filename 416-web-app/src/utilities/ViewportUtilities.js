export const STATE_OPTIONS = {
    UNSELECTED : "UNSELECTED",
    NORTH_CAROLINA : "NORTH_CAROLINA",
    TEXAS : "TEXAS",
    LOUISIANA :"LOUISIANA"
}

/* Global view */
export const UNSELECTED = {
    Maximized : {
        latitude : 40.29091544906472, 
        longitude: -97.44332861851478,
        width: "75vw",
        height: window.innerHeight,
        zoom: 3.75
    },
    Minimized : {
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
        zoom: 6.5,
    },
    Minimized : {
        latitude : 35.490477690914446, 
        longitude: -79.41601173255576,
        width: "40vw",
        height: window.innerHeight,
        zoom: 5.5,
    }
}

export const LOUISIANA = {
    Maximized : {
        latitude : 37.079256997566794, 
        longitude: -121.64309376041251,
        width: "75vw",
        height: window.innerHeight,
        zoom: 5 
    },
    Minimized : {
        latitude : 37.079256997566794, 
        longitude: -121.64309376041251,
        width: "40vw",
        height: window.innerHeight,
        zoom: 4.5
    }
}
 
export const TEXAS = {
    Maximized : {
        latitude : 31.44340575349766, 
        longitude: -98.97300883275322,
        width: "75vw",
        height: window.innerHeight,
        zoom: 5.5
    },
    Minimized : {
        latitude : 31.44340575349766, 
        longitude: -98.97300883275322,
        width: "40vw",
        height: window.innerHeight,
        zoom: 4.5
    }
}