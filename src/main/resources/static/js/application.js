var defaultCountryCode='USA';
function setLatLng(position) {
    var lat = position.coords.latitude;
    var lng = position.coords.longitude;
    $('#lat').value = lat;
    $('#lng').value = lng;
}
function errorFunction(){
    alert("Geocoder failed");
}
function fetchCitiesInState(stateCode,countryCode){
    $.ajax({
        url: "/api/fetchCities?state="+stateCode+"&country="+countryCode,
        type: 'GET',
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log('data submitted successfully');
             for (var index = 0; index <= data.length; index++) {
                $('#stateNameList').append('<option value="' + data[index].stateName + '">' + data[index].stateName + '</option>');
            }
        }
    });
}
function fetchStatesInCountry(country){
    $.ajax({
        url: "/api/fetchStates/{"+country+"}",
        type: 'GET',
        cache: false,
        processData: false,
        contentType: false,
        success: function (data) {
            console.log('data submitted successfully');
             for (var index = 0; index <= data.length; index++) {
                $('#stateNameList').append('<option value="' + data[index].stateName + '">' + data[index].stateName + '</option>');
            }
        }
    });
}
$(document).ready(function () {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(setLatLng, errorFunction);
    } 
    $.ajax({
         url: "/api/fetchCountries/",
         type: 'GET',
         cache: false,
         processData: false,
         contentType: false,
         success: function (data) {
             console.log('data submitted successfully');
              for (var index = 0; index <= data.length; index++) {
                    if(defaultCountryCode === data[index].iso3){
                        $('#countryNameList').append('<option value="' + data[index].iso3 + ' selected">' + data[index].name + '</option>'); 
                    }else{
                        $('#countryNameList').append('<option value="' + data[index].iso3 + '">' + data[index].name + '</option>');
                    }
             }
         }
     });
 });
 $("#submitButton").click(function(ev) {
     var myform = document.getElementById("contentForm");
     var fd = new FormData(myform);
     $.ajax({
         url: "/api/saveContent/",
         data: fd,
         cache: false,
         processData: false,
         contentType: false,
         type: 'POST',
         success: function (dataofconfirm) {
             console.log('data submitted successfully');
         }
     });
  })