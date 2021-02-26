import * as ActionTypes from './ActionTypes'

export const togglePrecinctSwitch = (bool) => {
    return {
        type: ActionTypes.TOGGLE_PRECINCT_SWITCH, 
        DisplayPrecincts : bool
    }
}

export const toggleDistrictSwitch = (bool) => {
    return {
        type: ActionTypes.TOGGLE_DISTRICT_SWITCH, 
        DisplayDistricts : bool
    }
}

export const setTentativeDistricting = (districting) => {
    return {
        type: ActionTypes.SET_TENTATIVE_DISTRICTING,
        TentativeDistricting : {
            name : districting.name,
            geoJson : districting.geoJson
        }
    }
}

export const setCurrentDistricting = (districting) => {
    return {
        type: ActionTypes.SET_CURRENT_DISTRICTING,
        CurrentDistricting : districting
    }
}

export const moveMouse = (event) => {
    if (event.nativeEvent) {
        return {
            type : ActionTypes.MOVE_MOUSE,
            MouseX : event.nativeEvent.offsetX,
            MouseY : event.nativeEvent.offsetY,
        }
    }
}

export const setMouseEntered = (bool) => {
    return {
        type : ActionTypes.SET_MOUSE_ENTERED,
        MouseEntered : bool,
    }
}

export const setFeaturedDistrict = (district) => {
    return {
        type : ActionTypes.SET_FEATURED_DISTRICT,
        FeaturedDistrict : district
    }
}

export const setFeaturedPrecinct = (precinct) => {
    return {
        type : ActionTypes.SET_FEATURED_PRECINCT,
        FeaturedPrecinct : precinct
    }
}
