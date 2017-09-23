function initMap()
{
  var geocoder = new google.maps.Geocoder();

  geocoder.geocode( {"address" : localStorage.getItem("address") }, function(results, status) {
    if (status == 'OK') {
      var position = results[0].geometry.location;

      var map = new google.maps.Map(document.getElementById('map'), {
        zoom: 6,
        center: position
      });

      var marker = new google.maps.Marker({
        position: position,
        map: map
      });

    } else {
      alert('Geocode was not successful for the following reason: ' + status);
    }
  });
}
