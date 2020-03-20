Vue.component("pocetnaStr", { 
	data: function () {
		    return {
				oglasi : [],  //vraca niz oglasa iz pretrage
				kategorija: {naziv : "", opis:""},
                kategorije : [],  //niz kategorija
                pret: {naziv : "" , maxcena:"" , mincena : "" , minocena : "",  maxocena : "" , grad : "", minDatum: "", maxDatum: ""},
                prikaz: false,
                opis : false,
                alertt: false,
                top: true
		    }
    },
    template: 
        ` <div class="row">
        
        <div class="col">  
        <div class="down">

        <button type="button" class="btn btn-warning" style="width: 330px; position: relative; top: 5px; left: 20px; color: white" v-on:click="otvoriFormu()" ><i class="fa fa-search"></i>  Pretraga</button>  
        </div>   
       
        <form  v-if="prikaz" class="message-form" style="position: relative; top: 25px; left: 20px; width: 330px">
        <div class="form-group">
            <label for="naziv">Naziv:</label>
            <input type="text" v-model ="pret.naziv" class="form-control" id="naziv" placeholder="Naziv">
        </div>
        <div class="form-group">
            <label for="cena">Opceg cene:</label>
            <input type="number" v-model ="pret.mincena" class="form-control" id="mincena" placeholder="Min cena" >
            <input type="number" v-model ="pret.maxcena" class="form-control" id="maxcena" placeholder="Max cena" >          
        </div>
        <div class="form-group">
            <label for="ocena">Opseg pozitvnih ocena:</label>
            <input type="number" v-model ="pret.minocena" class="form-control" id="minocena" placeholder="Min ocena" >
            <input type="number" v-model ="pret.maxocena" class="form-control" id="maxocena" placeholder="Max ocena" >          
        </div>
        <div class="form-group">
            <label for="grad">Grad:</label>
            <input type="text" v-model ="pret.grad" class="form-control" id="grad" placeholder="Grad">
        </div>
        <div class="form-group" >
            <label for="datum">Datum isticanja:</label>
            <br>
            Od: <input type="date" v-model="pret.minDatum" class="form-control" id="minDatum" >
            Do: <input type="date" v-model="pret.maxDatum" class="form-control" id="maxDatum" >
            
        </div>
        <button type="button" class="btn btn-warning"  style="color: white" v-on:click="pretrazi()">Pretrazite</button>
             
    </form>

    <div class="kat ">
        <ul class="list-group" style= "position: relative; top: 15px; left: 20px; width: 330px">
        <li class="list-group-item bg-info"  style="color: white; text-align: center" >Prikazi</li>
        <button type="button" v-on:click="izlistajSve()" class="list-group-item list-group-item-action border-warning">Svi oglasi</button>
        <button type="button" v-on:click="top9()" class="list-group-item list-group-item-action border-warning">Popularni oglasi</button>
        <button v-for="kategorija in kategorije" type="button" v-on:click="izlistajPoK(kategorija)" class="list-group-item list-group-item-action border-warning">{{kategorija.naziv}}</button>
        </ul>
    </div>
    
    </div>
 
    <div class="col">
    <div class="kat">
    
    <div v-if="alertt" style="width: 500px; position: relative; left: 150px" class="alert alert-dismissible  border-info" id="myalert" role="alert">
    <h4 class="alert-heading">Opis oglasa</h4>
     <hr class="bg-info">
     <div>
     <img v-bind:src="oglas.slika" style="max-width: 100%; max-height: 200px;" alt="...">
     </div>
     <strong>Naziv: </strong> {{oglas.naziv}} <br>
    <strong>Cena: </strong> {{oglas.cena}} dinara<br>
    <strong>Opis: </strong> {{oglas.opis}} <br>
    <strong>Datum isticanja: </strong> {{oglas.datumIsticanja}} <br>
    <strong>Grad: </strong> {{oglas.grad}} <br>
    <button type="button" v-on:click="restratuj()" class="close" data-dismiss="alert" aria-label="Close">
      <span aria-hidden="true">&times;</span>
    </button>
    </div>    
 
          

            <div class="card-columns " style="display: inline-block; right: 2000px">
                <div v-for="oglas in oglasi" class="card border-info" style="width: 18rem; right: 30px">
                    <img v-bind:src="oglas.slika" style="max-width: 100%; max-height: 170px;" class="card-img-top" alt="...">
                    <div class="card-body">
                        <h5 class="card-title">{{oglas.naziv}}</h5>
                        <p style="color:black" class="card-text"> <b>Cena </b>: {{oglas.cena}}</p>
                        <button class="btn btn-warning" type="button" v-on:click="like(oglas)"><i class="fa fa-thumbs-up"></i>  {{oglas.brLajkova}}</button>
                        <button class="btn btn-warning" type="button" v-on:click="dislike(oglas)"><i class="fa fa-thumbs-down"></i>  {{oglas.brDislajkova}}</button>
                        <button class="btn btn-warning" type="button"  v-on:click="dodajOmiljen(oglas)" ><i class="fa fa-heart"></i></button>              
                        <button  class="btn btn-warning" v-on:click="prikazi(oglas)"  ><i class="fa fa-info-circle"></i></button>               
                    </div>
                </div>
            </div>



    
    </div>    
  
       
      </div> 
    </div>`,
    methods: {
        otvoriFormu(){
            this.prikaz=!this.prikaz;
            this.pretraga = {naziv : "" , maxCena:"" , minCena : "" , minOcena : "",  maxOcena : "" , grad : "", minDatum: "", maxDatum: ""}              
        },
        top9(){
        	this.top = true;
    		self= this;
            axios.get('rest/izlistajtop9')
        .then(function (response) {
    		console.log(response);
    		self.oglasi=response.data;
        })
        .catch(function (error) {
            console.log(error);
           // alert('neuspesna registracija');
        
        });
    	},
    	prikazi(oglas){
            this.oglas = oglas;
            this.alertt = true;
        },
        restratuj(){
            this.alertt=false;
          },
          dodajOmiljen(oglas){
        	    
              axios.post('rest/dodajOmiljen/' + oglas.id)
              .then(function(response){
                  console.log(response);
                  alert("dodali ste oglas u omiljene");
              })    
              .catch(function(error){
                  alert('niste prijavljeni ili ste vec dodali ovja oglas u omiljene');
                      console.log(error);
              })
                  
              },
        pretrazi(){
        
            var self=this;
            console.log(this.pretraga.minDatum);
            console.log(this.pretraga.maxDatum);
            
            axios.post('rest/pretrazi', this.pret)
            .then(function(response)
            {
                console.log(response);
                self.oglasi=response.data;
            })
            .catch(function(error){
                console.log(error);
            });
        },
        like(oglas){
            var self=this;
            axios.post('rest/lajkuj/' + oglas.id)
            .then(function(response){
                console.log("lajkovi");   
                console.log(response);
                self.top9();
                
                
               
               // self.oglas.brLajkova=response.data;
                alert("Lajkovali ste oglas!");
            })    
            .catch(function(error){
                alert('Niste prijavljeni ili ste vec lajkovali oglas!');
                    console.log(error);
            })
                
        },
        dislike(oglas){
            var self=this;
            axios.post('rest/dislajkuj/' + oglas.id)
            .then(function(response){
                console.log("dislajkovi");
                console.log(response);
                self.top9();
               //self.oglas.brDisajkova=response.data;
                alert("Dislajkovali ste oglas!");
            })    
            .catch(function(error){
                alert('Niste prijavljeni ili ste vec dislajkovai oglas!');
                    console.log(error);
            })
        },
        ucitajKategorije(){
        	console.log("MIlica");
    		var self = this;
    		axios.get('rest/izlistajSveKategorije')
    		.then (function(response){
    			console.log(response);
    			self.kategorije = response.data;
    			self.top9();

    		})
    		.catch (function(error){
    			console.log(error);
    		});
        },
        dodajOmiljen(oglas){
            
            axios.post('rest/dodajOmiljen/' + oglas.id)
            .then(function(response){
                console.log(response);
                alert("Dodali ste oglas u omiljene!");
            })    
            .catch(function(error){
                alert('Niste prijavljeni ili ste vec dodali ovaj oglas u omiljene');
                    console.log(error);
            })
                
            },
        izlistajSve() {
            this.top = false;	
    		var self = this;
    		axios.get ('rest/izlistajSve/')
    		.then (function(response){
    			self.oglasi=response.data;
    		})
    		.catch(function (error){
    			console.log(error);
    		});
    	},
    	
    
        izlistajPoK(kategorija) {
    	    this.top = false;	
    		var self = this;
    		axios.get ('rest/izlistajPoKat/' + kategorija.naziv)
    		.then (function(response){
    			self.oglasi=response.data;
    		})
    		.catch(function (error){
    			console.log(error);
    		});
    	    }
        },
   
    mounted() {		//ponovi sta je mounted
    	this.top9();
		this.ucitajKategorije();
    },


    
});