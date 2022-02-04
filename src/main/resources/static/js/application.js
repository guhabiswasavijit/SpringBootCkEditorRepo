var defaultCountryCode='US';

$(document).ready(function () {
  var editor = CKEDITOR.replace('content', {
      filebrowserBrowseUrl: 'http://cms.local/scripts/ckfinder/ckfinder.html',
	  filebrowserUploadUrl: 'http://cms.local/scripts/ckfinder/core/connector/php/connector.php?command=QuickUpload&type=Files',
	  filebrowserWindowWidth: '1000',
      filebrowserWindowHeight: '700'
    });
    editor.on('change', function( event ) {
         // getData() returns CKEditor's HTML content.
         console.log( 'Total bytes: ' + event.editor.getData().length );
         var contentForm = document.getElementById("contentForm");
         contentForm.content.value = event.editor.getData();
    });
    console.log('Got ckeditor');
    console.log(editor);
    CKFinder.setupCKEditor( editor );
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude;
            var lng = position.coords.longitude;
            console.log(lat);
            console.log(lng);
            $('#lat').val(lat);
            $('#lng').val(lng);
        });
    }
    $.get({
         url: "/dawn/api/fetchCountries?region=Americas",
         success: function (response, status) {
              console.log('data submitted successfully');
              console.log(response.length);
              $.each(response,function(index,data){
                     if(defaultCountryCode === data.iso2){
                        $('#countryNameList').append('<option value="' + data.iso2 + '" selected>' + data.name + '</option>');
                     }else{
                        $('#countryNameList').append('<option value="' + data.iso2 + '">' + data.name + '</option>');
                     }
              });
         }
     });
 });

 $("#submitButton").click(function(button) {
     var contentForm = document.getElementById("contentForm");
     console.log(contentForm.content.value);
     var datum = {};
     datum["content"] = contentForm.content.value;
     datum["name"] = contentForm.name.value;
     datum["title"] = contentForm.title.value;
     datum["state"] = $("#stateNameList option:selected").text();
     datum["country"] = $("#countryNameList option:selected").text();
     datum["city"] = contentForm.city.value;
     datum["lat"] = contentForm.lat.value;
     datum["lng"] = contentForm.lng.value;

     var json = JSON.stringify(datum);
     console.log(json);
     $.post({
         url: "/dawn/api/saveContent/",
         data: json,
         processData: false,
         contentType: 'application/json',
         success: function (data) {
             console.log('data submitted successfully'+data.title);
         }
     });
})
$("#countryNameList").change(function(event) {
   console.log(event.target.value);
   var country = $("#countryNameList option:selected").val();
       $.get({
           url: "/dawn/api/fetchStates?country="+country,
           success: function (response,status) {
               console.log('got states:'+response.length);
               console.log('got states status:'+status);
                $.each(response,function(index,data){
                    $('#stateNameList').append('<option value="' + data.stateCode + '">' + data.stateName + '</option>');
                });
           }
       });
})
$("#stateNameList").change(function(event) {
     console.log(event.target.value);
     var countryCode = $("#countryNameList option:selected").val();
     var stateCode = $("#stateNameList option:selected").val();
     $.get({
         url: "/dawn/api/fetchCities?state="+stateCode+"&country="+countryCode,
         success: function (response) {
              console.log('got cities:'+response.length);
              $.each(response,function(index,data){
                  $('#cityNameList').append('<option value="' + data.name + '">' + data.name + '</option>');
              });
         }
     });
})