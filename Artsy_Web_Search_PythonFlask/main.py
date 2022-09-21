from builtins import print
from flask import Flask
from flask import request
from flask import jsonify
import json
import requests


app = Flask(__name__)

@app.route('/')
def main_page():  #reutrn index.html
    return app.send_static_file('mainpage.html')

url ="something" #we castating the artist to the url and make another variable for token 
search_target = ""
token = ""
#get name of artist from frontend ->"convert to ready data to send to artsy" -> retreive data from the artsy ---->send data to front end       

@app.route('/token')
def get_token():
    t = requests.post("https://api.artsy.net/api/tokens/xapp_token?client_id=4d761265215dcd03708b&client_secret=5f869eb13ff5f5714b502283dd4adc46") #return JSON requests 
    global token 
    res = json.loads(t.text)
    token = res['token']
    print(token)

# @app.route('/id'])
@app.route('/endpoint', methods=['GET'])
def get_endpoint():
    name = request.args.get('id')
    #print(name)
    get_token()
    header = {"X-XAPP-Token": token}
    response = requests.get("https://api.artsy.net/api/artists/"+ name, headers=header)
    data = json.loads(response.text)
    print(data)
    artist_name = data['name']
    artist_brithday = data['birthday']
    artist_deathday = data['deathday']
    nationality = data['nationality']
    biography = data['biography']
    dictionary = {'name': data['name'], 'birthday':data['birthday'], 'deathday':data['deathday'], 'nationality':data['nationality'], 'biography':data['biography']}
    print(dictionary)
    return jsonify(dictionary) #return JSON requests 

# @app.route('/search_name  '])
@app.route('/search')
def get_search_result():
    get_token()
    name = request.args.get('id')
    header = {"X-XAPP-Token":token}
    response = requests.get("https://api.artsy.net/api/search?q="+ name+"&size=10", headers=header)
    data = json.loads(response.text)
    size_result = len(data["_embedded"]['results'])
    list1 = list()
    for i in range(size_result):
        if data["_embedded"]['results'][i]['type'] == "artist":
            id = data["_embedded"]['results'][i]["_links"]["self"]["href"].split('/', 6)[5]
            dictionary = { 'title': data["_embedded"]['results'][i]['title'], 'image': data["_embedded"]['results'][i]["_links"]["thumbnail"]["href"], 'id': id}
            #list1 = {data["_embedded"]['results'][i]['title'], data["_embedded"]['results'][i]["_links"]["thumbnail"]["href"], id}
            list1.append(dictionary.copy())
    return jsonify(list1) #return raw JSON requests 

@app.route('/test')
def get_seart():
    name = request.args.get('id')
    return jsonify(name)

if __name__ == '__main__':
    app.run()