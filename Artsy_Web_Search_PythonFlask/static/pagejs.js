Array.prototype.forEach.call(document.querySelectorAll('.clearable-input>[data-clear-input]'), function(el) {
  el.addEventListener('click', function(e) {
  e.target.previousElementSibling.value = '';
   });
});


var container = document.getElementById("back");
var container2 = document.getElementById("back2");
var load = document.getElementById("load");
var load2 = document.getElementById("load2");
const arr = [];
load.style.display = "none";
load2.style.display ="none";
function cleartext(){
  document.getElementById('textarea1').value = "";
}

var search_name =  document.getElementById('form')[0];
window.onload = function() {
  document.getElementById('form').onsubmit = function() {
    //console.log(document.getElementById('form')[2].value);
    load.style.display = "inline";
    document.getElementsByClassName("kuang")[0].style.visibility = "hidden";
    setTimeout(function(){load.style.display ="none"; },500)
    reset_style();
    var ourRequest = new XMLHttpRequest();
    ourRequest.open('GET', 'https://homework-6-787823.wl.r.appspot.com/search?id='+document.getElementById('form')[2].value);
    ourRequest.onload = function() {
    if (ourRequest.status >= 200 && ourRequest.status < 400) {
      var data1 = JSON.parse(ourRequest.responseText);
      renderHTML_search(data1);
    } else {
      console.log("We connected to the server, but it returned an error.");
    }
      
  };
  
  ourRequest.onerror = function() {
    console.log("Connection error");
  };
  
    ourRequest.send();
    return false;
  }
}

function reset_style(){
  var i;
    for(i; i<10;i++){
      document.getElementById(i.toString()).style.visibility = "hidden";
    }
    document.getElementById('text').innerHTML = "";
    document.getElementById('text2').innerHTML = "";
    document.getElementById('text3').innerHTML = "";
    document.getElementById('warmning').style.visibility = "hidden";
    document.getElementById('warmning').innerHTML ="";
}
function renderHTML_search(data) {
  var count =0;
  var link1 = "https://api.artsy.net/api/artists/"; 
  if(data.length ==0){
    document.getElementById('warmning').innerHTML = "NO RESULT FOUND";
    document.getElementById('warmning').style.visibility ="visible";
    var i =0;
    for(i; i<10;i++){
      document.getElementById(i.toString()).style.visibility = "hidden";
    }
  }else{
        document.getElementsByClassName("kuang")[0].style.visibility = "visible";  //TODO
        for(count; count<data.length; count++){
          if(data[count]['title'] != null){
            document.getElementById("p"+count.toString()).innerHTML = data[count]['title'];
            if(data[count]['image'] != "/assets/shared/missing_image.png"){
              document.getElementById("img"+count.toString()).src = data[count]['image'];
            }else{
            document.getElementById("img"+count.toString()).src = "\\static\\img\\artsy_logo.svg";
            }
            if(data[count]['id'] != null){
              arr[count] = data[count]['id'];
             }
            document.getElementById(count.toString()).style.visibility = "visible";
          }
        }
        for(count; count<10; count++){
          document.getElementById(count.toString()).style.visibility = "hidden";
        }
      }
    }

function getEndpoint(id){
  var art_id = arr[id];
  console.log(art_id);
  load2.style.display = "inline";
  var ourRequest = new XMLHttpRequest();
  ourRequest.open('GET', 'https://homework-6-787823.wl.r.appspot.com/endpoint?id='+art_id);
  setTimeout(function(){load2.style.display ="none"; },500)
  ourRequest.onload = function() {
  if (ourRequest.status >= 200 && ourRequest.status < 400) {
    var data1 = JSON.parse(ourRequest.responseText);
    renderHTML_endpoint(data1);
  } else {
    console.log("We connected to the server, but it returned an error.");
  }
      
};
  
  ourRequest.onerror = function() {
    console.log("Connection error");
  };
  
   ourRequest.send();
    return false;
  }

 //endpoint 
  function renderHTML_endpoint(data) {
    console.log(data)
    document.getElementById('text').innerHTML = data.name + "  ("+ data.birthday +"-" +data.deathday+")";
    document.getElementById('text2').innerHTML =  data.nationality;
    document.getElementById('text3').innerHTML = data.biography;
    //container.insertAdjacentHTML('beforeend', data.name + "  ("+ data.birthday +"-" +data.deathday+")\n" + data.nationality +"\n"+ data.biography );
  }