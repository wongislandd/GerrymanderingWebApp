export default  class Districting {
    constructor(name, geojsonRef) {
        this.name = name
        /* This is going to be sent to the MapBoxComponent file
        and so the reference needs to be relative to there, I'll just 
        add most of the reference from here.
        The provided path should be from the data/ folder
        */
        this.geojsonRef = "../../data/" + geojsonRef
    }
}