var map = L.map('map', {
    attributionControl: false,
    crs: L.CRS.Simple
});
var bounds = [[-110,0], [1050,1750]];
var image = L.imageOverlay('img/plan.svg', bounds).addTo(map);
map.fitBounds(bounds);
var yx = L.latLng;
var xy = function(x, y) {
    if (L.Util.isArray(x)) {    // When doing xy([x, y]);
        return yx(x[1], x[0]);
    }
    return yx(y, x);  // When doing xy(x, y);
};
var geojson = L.geoJson(locations, {style: style}).addTo(map);

var stompClient = null;
var markers = [];
var firstCall = true;

function connect() {
    var socket = new SockJS('/socket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/topic/data', function(data){
            if (firstCall){
                createMarkers(JSON.parse(data.body));
                firstCall = false;
            }
            updateMarkers(JSON.parse(data.body));
        });
    });
}

function createMarkers(data) {
    for (var i = 0; i < data.length; i++){
        var customIcon = L.icon({
            iconUrl: 'img/' + data[i].pinColor
        });
        markers.push(L.Marker.movingMarker([xy(data[i].x, data[i].y)], [5000], {icon: customIcon}).addTo(map).bindPopup(data[i].name).on('move', markerMoved));
    }
}
function updateMarkers(data) {
    for (var i = 0; i < data.length; i++){
        markers[i].moveTo(xy(data[i].x, data[i].y), 5000);
    }
}
function markerMoved() {
    var layers = geojson.getLayers();
    for (var i = 0; i < layers.length; i++ ){
        var boun = layers[i].getBounds();
        var contains = false;
        for (var j = 0; j < markers.length; j++){
            var ll = markers[j].getLatLng();
            if (boun.contains(ll)){
                contains = true;
            }
        }
        if (contains){
            highlightFeature(layers[i]);
        } else {
            resetHighlight(layers[i]);
        }
    }

}
function highlightFeature(feature) {
    feature.setStyle({
        weight: 3,
        color: '#5498F8',
        dashArray: '',
        fillOpacity: 0.3
    });

    feature.bringToFront();
}
function resetHighlight(feature) {
    geojson.resetStyle(feature);
}
function style(feature) {
    return {
        fillColor: '#BCCDE4',
        weight: 0,
        fillOpacity: 0.0
    };
}

