import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormGroup, FormControl, FormArray, NgForm } from '@angular/forms'
import { query } from 'express';
import { ConfigService } from './config/config.service';
import { data1 } from './config/data1';
import { createCustomElement } from '@angular/elements';
import { Inject }  from '@angular/core';
import { DOCUMENT } from '@angular/common'; 
import { resetFakeAsyncZone } from '@angular/core/testing';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})


export class AppComponent {
  title = 'homework8';
  //url = "http://127.0.0.1:8000/artists/";
  constructor(private configS:ConfigService){}

  public info_path:any={   
  }

  
  
  i=0;

  //img0:any = document.getElementById("img0");

  /**
   * initialize variable for storing data from backends
   */
  htmlStr0: string= '';
  htmlStr1: string= '';
  htmlStr2: string= '';
  htmlStr3: string= '';
  htmlStr4: string= '';
  htmlStr5: string= '';
  htmlStr6: string= '';
  htmlStr7: string= '';
  htmlStr8: string= '';
  htmlStr9: string= '';
  artinfoHTML1: string ='';
  artinfoHTML2: string ='';
  artinfoHTML3: string ='';
  artworkHTML:string ='';
  artworktext0:string='';
  artworktext1:string='';
  artworktext2:string='';
  artworktext3:string='';
  artworktext4:string='';
  artworktext5:string='';
  artworktext6:string='';
  artworktext7:string='';
  artworktext8:string='';
  artworktext9:string='';

  modaltitle:string='';
  modalimg:string='';

  /**
   * initialize variable for style.display 
   */
  display_none: string= "display: none;";
  display_load: string= "display: none;";
  display_load2: string= "display: none;";
  display_load3: string= "display: none";

  //boxes
  display_noresult: string= "display: none;";
  display_sm_box0: string = "display:none;";
  display_sm_box1: string = "display:none;";
  display_sm_box2: string = "display:none;";
  display_sm_box3: string = "display:none;";
  display_sm_box4: string = "display:none;";
  display_sm_box5: string = "display:none;";
  display_sm_box6: string = "display:none;";
  display_sm_box7: string = "display:none;";
  display_sm_box8: string = "display:none;";
  display_sm_box9: string = "display:none;";
  display_nav:string = "display:none";
  display_artwork0:string = "width: 18rem; display:none;";
  display_artwork1:string = "width: 18rem; display:none;";
  display_artwork2:string = "width: 18rem; display:none;";
  display_artwork3:string = "width: 18rem; display:none;";
  display_artwork4:string = "width: 18rem; display:none;";
  display_artwork5:string = "width: 18rem; display:none;";
  display_artwork6:string = "width: 18rem; display:none;";
  display_artwork7:string = "width: 18rem; display:none;";
  display_artwork8:string = "width: 18rem; display:none;";
  display_artwork9:string = "width: 18rem; display:none;";
  display_noresult2:string ="display:none;";
  display_noresult2_class:string ="alert alert-danger col-lg-12 col-5";
  display_noresult3_class:string ="alert alert-danger";
  display_noresult3:string ="display:none;";
  artHTML1_style: string = "display:none;";
  artHTML2_style: string = "display:none;";
  artHTML3_style: string = "display:none;";
  container_sty:string = "display:none; " ;

  modaltext0:string ="";
  modaltext1:string ="";
  modaltext2:string ="";
  modaltext3:string ="";
  modaltext4:string ="";
  modaltext5:string ="";
  modaltext6:string ="";
  modaltext7:string ="";
  modaltext8:string ="";
  modaltext9:string ="";
  modalimg0:string ="";
  modalimg1:string ="";
  modalimg2:string ="";
  modalimg3:string ="";
  modalimg4:string ="";
  modalimg5:string ="";
  modalimg6:string ="";
  modalimg7:string ="";
  modalimg8:string ="";
  modalimg9:string ="";

  modalbox0:string ="width: 10rem; height: 75px; display:none;";
  modalbox1:string ="width: 10rem; display:none;";
  modalbox2:string ="width: 10rem; display:none;";
  modalbox3:string ="width: 10rem; display:none;";
  modalbox4:string ="width: 10rem; display:none;";
  modalbox5:string ="width: 10rem; display:none;";
  modalbox6:string ="width: 10rem; display:none;";
  modalbox7:string ="width: 10rem; display:none;";
  modalbox8:string ="width: 10rem; display:none;";
  modalbox9:string ="width: 10rem; display:none;";
  
  cardbody0:string =" background-color: #205375";
  cardbody1:string =" background-color: #205375";
  cardbody2:string =" background-color: #205375";
  cardbody3:string =" background-color: #205375";
  cardbody4:string =" background-color: #205375";
  cardbody5:string =" background-color: #205375";
  cardbody6:string =" background-color: #205375";
  cardbody7:string =" background-color: #205375";
  cardbody8:string =" background-color: #205375";
  cardbody9:string =" background-color: #205375";

  
  //change class 
  artistInfo1:string = "tab-pane fade show active text-center wow" ;
  artistInfo2:string = "tab-pane fade show active text-center wow2";
  artistInfo3:string = "tab-pane fade show active text-left  wow2";
  artworkInfo:string = "tab-pane fade d-flex justify-content-center";
  navbar1:string = "nav-link active active_color" ;
  navbar2:string = "nav-link" ;

  //textbox check whether has inputs 
  checkField:string = "";

  //current artist pointer 
  currentArtistID: string ="";

  //flag for showing No Result Alert
  flag_showing_alter: boolean = false;
  reset_textbox: string ="jijoi";

 
  /**
   * initialize array for storing IDs from backend
   */
  artistsID: string[]= []; 
  artworksID: string[]=[];
  artworksName: string[] =[];
  artworkDate: string[]=[];
  artworkImg: string[]=[];
  reset_box(){
    this.display_sm_box0 = "display:none";
    this.display_sm_box1 = "display:none";
    this.display_sm_box2 = "display:none";
    this.display_sm_box3 = "display:none";
    this.display_sm_box4 = "display:none";
    this.display_sm_box5 = "display:none";
    this.display_sm_box6 = "display:none";
    this.display_sm_box7 = "display:none";
    this.display_sm_box8 = "display:none";
    this.display_sm_box9 = "display:none";
    this.display_artwork0 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork1 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork2 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork3 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork4 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork5 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork6 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork7 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork8 = "width: 18rem; display:none; border-color: transparent;";
    this.display_artwork9 = "width: 18rem; display:none; border-color: transparent;";
    this.container_sty = "display: none; ";
    this.display_noresult= "display: none;";

  }

  onSubmit(search:NgForm){
    console.log(search.value.inputbox);  
    this.display_load= "display: block;";
    this.reset_box();
    this.reset_box2();
    this.reset_color();
    this.display_nav = "display: none;";
    this.artHTML1_style = "display:none;";
    this.artHTML2_style = "display:none;";
    this.artHTML3_style = "display:none;";
    this.artworkHTML= "display: none;";
    this.display_noresult2 = "display:none;";
    this.container_sty ="display:block; width:78%; margin-left:10.5%";

    var result = this.configS.getconnect(search.value.inputbox).subscribe(data=>{
      
      this.display_noresult = "display: none;";
      console.log(data.length);
      let temp="";
      this.display_none = "display: block;";
      this.display_load = "display: none;";
      if(data.length <1){
        this.display_noresult= "display: block;";
        this.display_none=  "display: none;";
        
      }else{
        for(this.i = 0; this.i<data.length; this.i++){


          if(data[this.i]['id'] != null){
            this.artistsID[this.i] = data[this.i]['id'];
          }
          if(data[0]!=null &&data[0]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            if(data[0]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path0= data[0]['img'];
            }else{
              this.info_path.path0 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr0 = data[0]['name'];
            this.display_sm_box0 = "display:block;";
          }
          if(data[1]!=null &&data[1]['name']!=null){
            let temp:any= document.getElementById('path1')?.style.visibility;
            if(data[1]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path1= data[1]['img'];
            }else{
              this.info_path.path1 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr1 = data[1]['name'];
            this.display_sm_box1 = "display:block";
          }
          if(data[2]!=null &&data[2]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[2]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path2= data[2]['img'];
            }else{
              this.info_path.path2 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr2 = data[2]['name'];
            this.display_sm_box2 = "display:block";
          }
          if(data[3]!=null &&data[3]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[3]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path3= data[3]['img'];
            }else{
              this.info_path.path3 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr3 = data[3]['name'];
            this.display_sm_box3 = "display:block";
          }
          if(data[4]!=null &&data[4]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[4]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path4= data[4]['img'];
            }else{
              this.info_path.path4 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr4 = data[4]['name'];
            this.display_sm_box4 = "display:block";
          }
          if(data[5]!=null &&data[5]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[5]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path5= data[5]['img'];
            }else{
              this.info_path.path5 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr5 = data[5]['name'];
            this.display_sm_box5 = "display:block";
          }
          if(data[6]!=null &&data[6]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[6]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path6= data[6]['img'];
            }else{
              this.info_path.path6 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr6 = data[6]['name'];
            this.display_sm_box6 = "display:block";
          }
          if(data[7]!=null &&data[7]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[7]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path7= data[7]['img'];
            }else{
              this.info_path.path7 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr7 = data[7]['name'];
            this.display_sm_box7 = "display:block";
          }
          if(data[8]!=null &&data[8]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[8]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path8= data[8]['img'];
            }else{
              this.info_path.path8 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr8 = data[8]['name'];
            this.display_sm_box8 = "display:block";
          }
          if(data[9]!=null &&data[9]['name']!=null){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            //temp.innerHTML  = data[this.i]['name'];
            //temp.style.visibility = "visible"; 
            if(data[9]['img'] !="/assets/shared/missing_image.png"){
              this.info_path.path9= data[9]['img'];
            }else{
              this.info_path.path9 = "./assets/img/artsy_logo.svg";
            }
            this.htmlStr9 = data[9]['name'];
            this.display_sm_box9 = "display:block";
          }
  
  
        }
          for(this.i; this.i<10;this.i++){
            let temp:any= document.getElementById('name'+ this.i.toString)?.style.visibility;
            
          }
      }
     
      
    });

  }

  loadArtistInfo(id:string){
    this.display_load2 = "display:block";
    this.switchToArtInfo();

    this.currentArtistID = this.artistsID[parseInt(id)];
    this.reset_color();
    if(id =='0'){
      this.cardbody0 =" background-color: #112B3C";
    }else if(id == '1'){
      this.cardbody1 =" background-color: #112B3C";
    }else if(id == '2'){  
      this.cardbody2 =" background-color: #112B3C";
    }else if(id == '3'){
      this.cardbody3 =" background-color: #112B3C";
    }else if(id == '4'){
      this.cardbody4 =" background-color: #112B3C";
    }else if(id == '5'){
      this.cardbody5 =" background-color: #112B3C";
    }else if(id == '6'){
      this.cardbody6 =" background-color: #112B3C";
    }else if(id == '7'){
      this.cardbody7 =" background-color: #112B3C";
    }else if(id == '8'){
      this.cardbody8 =" background-color: #112B3C";
    }else if(id == '9'){
      this.cardbody9 =" background-color: #112B3C";
    }


    var result = this.configS.getartist(this.artistsID[parseInt(id)]).subscribe(data=>{
    this.display_load2 = "display:none";
    this.display_nav ="display:block";
    this.artHTML1_style = "display:block";
    this.artHTML2_style = "display:block";
    this.artHTML3_style = "display:block";
    this.artinfoHTML1 = data[0]['name'] + " ("+ data[0]['birthday'] +'-' + data[0]['deathday']+ ")";
    this.artinfoHTML2 = data[0]['nationality'];
    this.artinfoHTML3 = data[0]['biography'];

   });
   this.artworks();


  }

  //reset artwork 
  reset_box2(){
    this.display_artwork0 = "width: 18rem; display:none;";
    this.display_artwork1 = "width: 18rem; display:none;";
    this.display_artwork2 = "width: 18rem; display:none;";
    this.display_artwork3 = "width: 18rem; display:none;";
    this.display_artwork4 = "width: 18rem; display:none;";
    this.display_artwork5 = "width: 18rem; display:none;";
    this.display_artwork6 = "width: 18rem; display:none;";
    this.display_artwork7 = "width: 18rem; display:none;";
    this.display_artwork8 = "width: 18rem; display:none;";
    this.display_artwork9 = "width: 18rem; display:none;";
  }

  //reset modal boxes
  reset_box3(){
    this.modalbox0 = "display:none;";
    this.modalbox1 = "width: 10rem; display:none;";
    this.modalbox2 = "width: 10rem; display:none;";
    this.modalbox3 = "width: 10rem; display:none;";
    this.modalbox4 = "width: 10rem; display:none;";
    this.modalbox5 = "width: 10rem; display:none;";
    this.modalbox6 = "width: 10rem; display:none;";
    this.modalbox7 = "width: 10rem; display:none;";
    this.modalbox8 = "width: 10rem; display:none;";
    this.modalbox9 = "width: 10rem; display:none;";
  }

  reset_color(){
  this.cardbody0 =" background-color: #205375";
  this.cardbody1 =" background-color: #205375";
  this.cardbody2 =" background-color: #205375";
  this.cardbody3 =" background-color: #205375";
  this.cardbody4 =" background-color: #205375";
  this.cardbody5 =" background-color: #205375";
  this.cardbody6 =" background-color: #205375";
  this.cardbody7 =" background-color: #205375";
  this.cardbody8 =" background-color: #205375";
  this.cardbody9 =" background-color: #205375";
  }

  typeIn(){
    this.reset_textbox = "sdasdas";
  }

  reset_all(){
    this.reset_box();
    this.reset_box2();
    this.reset_box3();
    this.checkField ="";
    this.reset_textbox ="TRUE";
    this.display_noresult2 = "display:none;";
    this.display_none = "display: none;";
    this.display_load = "display: none;";
    this.display_load2 = "display: none;";
    this.artHTML1_style = "display:none;";
    this.artHTML2_style = "display:none;";
    this.artHTML3_style = "display:none;";
    this.artworkHTML= "display: none;";
    this.display_nav = "display: none;";
    this.display_noresult3 = "display:none;";
  }

  switchToArtInfo(){
    this.navbar1 = "nav-link active active_color";
    this.navbar2 = "nav-link";
    this.artistInfo1 = "tab-pane fade text-center wow show active" ;
    this.artistInfo2 = "tab-pane fade text-center wow2 show active";
    this.artistInfo3 = "tab-pane fade text-left  wow2 show active";
    this.artworkInfo = "tab-pane fade d-flex justify-content-center";
    this.flag_showing_alter = false;
    this.display_noresult2 = "display:none;";
  }

  artworks(){
    var result = this.configS.getartwork(this.currentArtistID).subscribe(data=>{
      console.log(data.length);
      this.reset_box2();
      this.display_noresult2 = "display: none;";
      if(data.length ==0 && this.flag_showing_alter == true){
        this.display_noresult2 = "display: block;";
      }else{
        //this.display_artwork ="display: block;";
      for(var j=0; j<data.length; j++){
        this.artworksID[j] = data[j]['id'];
        this.artworksName[j] = data[j]['name'];
        this.artworkDate[j] = data[j]['date'];
        this.artworkImg[j] = data[j]['img'];
        console.log(this.artworkImg[j]);
          if( data[0]['name'] !=null){
            this.info_path.artwork0 =  data[0]['img'];
            this.artworktext0 = data[0]['name'] +"<br>"+ data[0]['date'];
            this.display_artwork0 = "width: 18rem; display: block; ";
          }
          if( data[1]['name'] !=null){
            this.info_path.artwork1 =  data[1]['img'];
            this.artworktext1 = data[1]['name'] +"<br>"+ data[1]['date'];
            this.display_artwork1 = "width: 18rem; display: block;";
          }
          if( data[2]['name'] !=null){
            this.info_path.artwork2 =  data[2]['img'];
            this.artworktext2 = data[2]['name'] +"<br>"+ data[2]['date'];
            this.display_artwork2 = "width: 18rem; display: block;";
          }
          if( data[3]['name'] !=null){
            this.info_path.artwork3 =  data[3]['img'];
            this.artworktext3 = data[3]['name'] +"<br>"+ data[3]['date'];
            this.display_artwork3 = "width: 18rem; display: block;";
          }
          if( data[4]['name'] !=null){
            this.info_path.artwork4 =  data[4]['img'];
            this.artworktext4 = data[4]['name'] +"<br>"+ data[4]['date'];
            this.display_artwork4 = "width: 18rem; display: block;";
          }
          if( data[5]['name'] !=null){
            this.info_path.artwork5 =  data[5]['img'];
            this.artworktext5 = data[5]['name'] +"<br>"+ data[5]['date'];
            this.display_artwork5 = "width: 18rem; display: block;";
          }
          if( data[6]['name'] !=null){
            this.info_path.artwork6 =  data[6]['img'];
            this.artworktext6 = data[6]['name'] +"<br>"+ data[6]['date'];
            this.display_artwork6 = "width: 18rem; display: block;";
          }
          if( data[7]['name'] !=null){
            this.info_path.artwork7 =  data[7]['img'];
            this.artworktext7 = data[7]['name'] +"<br>"+ data[7]['date'];
            this.display_artwork7 = "width: 18rem; display: block;";
          }
          if( data[8]['name'] !=null){
            this.info_path.artwork8 =  data[8]['img'];
            this.artworktext8 = data[8]['name'] +"<br>"+ data[8]['date'];
            this.display_artwork8 = "width: 18rem; display: block;";
          }
          if( data[9]['name'] !=null){
            this.info_path.artwork9 =  data[9]['img'];
            this.artworktext9 = data[9]['name'] +"<br>"+ data[9]['date'];
            this.display_artwork9 = "width: 18rem; display: block;";
          }

      }
        
      }
   });

  }

  switchToArtwork(){
    this.navbar1 = "nav-link";
    this.navbar2 = "nav-link active active_color";
    this.artistInfo1 = "tab-pane fade text-center wow" ;
    this.artistInfo2 = "tab-pane fade text-center wow2";
    this.artistInfo3 = "tab-pane fade text-left  wow2";
    this.artworkInfo = "tab-pane fade show active d-flex justify-content-center"; 
    //notes: working on switch content but need to add actual conentents
    this.flag_showing_alter = true;
    this.artworks();

  }

  genes(id:string){
    this.display_load3 = "display: block; ";
    this.reset_box3();
    var result = this.configS.getGenes(this.artworksID[parseInt(id)]).subscribe(data=>{
     
      this.modaltitle = this.artworksName[parseInt(id)] + "<br>" + this.artworkDate[parseInt(id)];
      this.modalimg = this.artworkImg[parseInt(id)];
      this.display_noresult3 = "display:none;";
    
      this.display_load3 = "display: none";
      if(data.length<1){
        this.display_noresult3 = "display:block;";
      }
      
      for(var k=0; k < data.length;k++){
        console.log(data[k]['name']);
        if(data[0]['name'] != null){
          this.modaltext0 = data[0]['name'];
          this.modalimg0 = data[0]['img'];
          this.modalbox0 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[1]['name'] != null){
          this.modaltext1 = data[1]['name'];
          this.modalimg1 = data[1]['img'];
          this.modalbox1 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[2]['name'] != null){
          this.modaltext2 = data[2]['name'];
          this.modalimg2 = data[2]['img'];
          this.modalbox2 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[3]['name'] != null){
          this.modaltext3 = data[3]['name'];
          this.modalimg3 = data[3]['img'];
          this.modalbox3 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[4]['name'] != null){
          this.modaltext4 = data[4]['name'];
          this.modalimg4 = data[4]['img'];
          this.modalbox4 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[5]['name'] != null){
          this.modaltext5 = data[5]['name'];
          this.modalimg5 = data[5]['img'];
          this.modalbox5 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[6]['name'] != null){
          this.modaltext6 = data[6]['name'];
          this.modalimg6 = data[6]['img'];
          this.modalbox6 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[7]['name'] != null){
          this.modaltext7 = data[7]['name'];
          this.modalimg7 = data[7]['img'];
          this.modalbox7 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[8]['name'] != null){
          this.modaltext8 = data[8]['name'];
          this.modalimg8 = data[8]['img'];
          this.modalbox8 ="width: 10rem; height: 280px; display: block;";
        }
        if(data[9]['name'] != null){
          this.modaltext9 = data[9]['name'];
          this.modalimg9 = data[9]['img'];
          this.modalbox9 ="width: 10rem; height: 280px; display: block;";
        }
      }
    });
    
  }


}
