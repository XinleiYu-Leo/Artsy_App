var http = require('http');
var url = require('url');
const express = require('express');
const axios = require('axios').default;
var cors = require('cors');
var token ='';
const app = express()
//const port = 8000;
const PORT = process.env.PORT || 8080;
app.use(cors());
app.use(express.static('dist/homework8'));


// Imports the Google Cloud client library
const {ErrorReporting} = require('@google-cloud/error-reporting');

// Instantiates a client
const errors = new ErrorReporting();



app.get('/error', (req, res, next) => {
  res.send('Something broke!');
  next(new Error('Custom error message'));
});

app.get('/exception', () => {
  JSON.parse('{"malformedJson": true');
});

// Note that express error handling middleware should be attached after all
// the other routes and use() calls. See the Express.js docs.
app.use(errors.express);

app.listen(PORT, () => {
  console.log(`App listening on port ${PORT}`);
  console.log('Press Ctrl+C to quit.');
});


//success
app.get('/token', (req, res) => {
    var search_text = req.url;
    const myarr = search_text.split("/");

    (async function(){
        var respond = await axios.post('https://api.artsy.net/api/tokens/xapp_token?client_id=937902276ff20df29c40&client_secret=25cc674e880f6dd6646315b524de2c32')
        token = respond.data['token'];
        res.send(respond.data['token'])
    })()
})


// success, note: need to run token first. TODO: 1. add token refresh. 2. decode json package
app.get('/search/:name', (req, res) => {
    req.params;
    const name = req.params.name;
    var myJson = [];
    (async function(){
        var respond = await axios.post('https://api.artsy.net/api/tokens/xapp_token?client_id=937902276ff20df29c40&client_secret=25cc674e880f6dd6646315b524de2c32')
        token = respond.data['token'];
        respond = await axios.get('https://api.artsy.net/api/search?q='+ name +'&size=10',{
        headers: {
        'X-Xapp-Token': token
        }
        });
        var i = 0; 
        var temp ="";
        for(i; i< respond.data['_embedded']['results'].length; i++){
            if(respond.data['_embedded']['results'][i]['type'] == 'artist'){
                temp = respond.data['_embedded']['results'][i]["_links"]["self"]["href"].split('/');
                let newitem = { name:respond.data['_embedded']['results'][i]['title'], type:respond.data['_embedded']['results'][i]['type'], id:temp[5], img:respond.data["_embedded"]['results'][i]["_links"]["thumbnail"]["href"]};
                myJson.push(newitem);
            }
        }
        res.send(myJson);
    })()
    
})

// success, note: need to run token first. same above    4d8b92b34eb68a1b2c0003f4  'name': data['name'], 'birthday':data['birthday'], 'deathday':data['deathday'], 'nationality':data['nationality'], 'biography':data['biography']
app.get('/artists/:id', (req, res) => {
    req.params;
    const id = req.params.id;
    var myJson = [];
    console.log("12345");
    (async function(){
        var respond = await axios.post('https://api.artsy.net/api/tokens/xapp_token?client_id=937902276ff20df29c40&client_secret=25cc674e880f6dd6646315b524de2c32')
        token = respond.data['token'];
        respond = await axios.get('https://api.artsy.net/api/artists/'+ id,{
  headers: {
    'X-Xapp-Token': token
  }
});
        let newitem = {name:respond.data['name'], birthday:respond.data['birthday'], deathday:respond.data['deathday'], nationality:respond.data['nationality'], biography:respond.data['biography']};
        myJson.push(newitem);
        res.send(myJson);
    })()
})

// success, note: need to run token first. same above   4d8b928b4eb68a1b2c0001f2
app.get('/artworks/:id', (req, res) => {
    req.params;
    const id = req.params.id;
    var myJson = [];    
    (async function(){
        var respond = await axios.post('https://api.artsy.net/api/tokens/xapp_token?client_id=937902276ff20df29c40&client_secret=25cc674e880f6dd6646315b524de2c32')
        token = respond.data['token'];
        respond = await axios.get('https://api.artsy.net/api/artworks?artist_id='+ id + "&size=10",{
  headers: {
    'X-Xapp-Token': token
  }
});
        var i = 0; 
        for(i; i< respond.data['_embedded']['artworks'].length; i++){
            let newitem = { id:respond.data['_embedded']['artworks'][i]['id'], name:respond.data['_embedded']['artworks'][i]['title'], date:respond.data['_embedded']['artworks'][i]['date'] ,img:respond.data["_embedded"]['artworks'][i]["_links"]["thumbnail"]["href"]};
            myJson.push(newitem);
    
}
        res.send(myJson);
    })()
})

app.get('/genes/:id', (req, res) => {
    req.params;
    const id = req.params.id;
    var myJson = [];    
    (async function(){
        var respond = await axios.post('https://api.artsy.net/api/tokens/xapp_token?client_id=937902276ff20df29c40&client_secret=25cc674e880f6dd6646315b524de2c32')
        token = respond.data['token'];
        respond = await axios.get('https://api.artsy.net/api/genes?artwork_id='+ id,{
  headers: {
    'X-Xapp-Token': token
  }
});
        var i = 0; 
        for(i; i< respond.data['_embedded']['genes'].length; i++){
            let newitem = {name:respond.data['_embedded']['genes'][i]['name'],img:respond.data["_embedded"]['genes'][i]["_links"]["thumbnail"]["href"]};
            myJson.push(newitem);
        }
        res.send(myJson);
    })()
})

  


/** 
app.listen(PORT, () => {
    console.log(`Server.js listening on port ${PORT}`);
});
//
var axiosript = document.createElement('script');  
jQueryScript.setAttribute('src','https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js');
document.head.appendChild(axiosript);
//
const search={
    url:"https://api.artsy.net/api/artists/",
    header:{
        'token':'token'
    }
}


//receive http requeste from the frontend and parse it to local variable
http.createServer(function (req, res) {
  res.writeHead(200, {'Content-Type': 'text/html'});
  var search_text = req.url;
  const myarr = search_text.split("/");
  res.write(myarr[1]);

  res.end();
}).listen(8080);

myJson[i]['name'] = respond.data['_embedded']['results'][i]['name']; 
                myJson[i]['type'] = respond.data['_embedded']['results'][i]['type']; 
                temp = respond.data['_embedded']['results'][i]["_links"]["self"]["href"].split('/');
                myJson[i]['links'] = temp[5]; 
                myJson[i]['img'] = respond.data["_embedded"]['results'][i]["_links"]["thumbnail"]["href"]; 

*/
