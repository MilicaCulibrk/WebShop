Vue.component("aProfil", {
    data() {
        return {
            oglasi: [],
            oglas : {id : "" , naziv : "", cena : 0, opis:"", datumIsticanja:"", grad:"", brDislajkova:0, brLajkova:0, kategorija:""},
            kategorija: {naziv : "", opis:""},
            kategorije: [],
            korisnik: {korisnickoIme:"", lozinka : "", ime : "", prezime : "", telefon : "", grad : " ",  email : "", datum : "", uloga : "" },
            url: null,
            prikaz: 0,
            korisnici: [],
            sumnjivi : [],
            naslovK: "Dodavanje kategorije",
            dugmeK: "Dodaj"
        }
    },
    template: `<div class="down">
    <div class="row">
      <div  class="col-3">
          <form v-if="prikaz===0" class="message-form"  style="position: relative; top: 5px; left: 20px" >
            <h3>Izmena oglasa</h3>
              <div class="form-group">
                <label for="naziv">Naziv:</label>
                <input type="text" v-model="oglas.naziv"  class="form-control" id="naziv" aria-describedby="emailHelp" placeholder="Naziv">
              </div>
              <div class="form-group">
                <label for="cena">Cena:</label>
                <input type="number" v-model="oglas.cena" class="form-control" id="cena" placeholder="Cena">
              </div>
              <div class="form-group">
              <label for="opis">Opis</label>
              <textarea class="form-control" v-model="oglas.opis" id="opis" rows="3"></textarea>
            </div>
            <div class="form-group">
                <label for="datum">Datum isticanja</label>
                <input type="date" v-model="oglas.datumIsticanja" class="form-control" id="datum" placeholder="Datum:">
              </div>
              <div class="form-group">
                <label for="grad">Grad</label>
                <input type="text" v-model="oglas.grad" class="form-control" id="grad" placeholder="Grad:">
              </div>
              <div class="form-group">
                  <label for="kategorija">Kategorija</label>
                  <select class="form-control" v-model="oglas.kategorija"> 
                      <option v-for="kategorija in kategorije"  v-bind:value="kategorija.naziv">{{kategorija.naziv}}</option>
                  </select>
                </div>
            <div> 
                <div class="preview">
                    <img :src="url" />
                </div> 
                <input type="file" id="pic" @change="onFileChange" /> 

            </div>
            <button type="button" class="btn btn-warning"  v-on:click="dodaj" style="position: relative;  top: 25px; color: white">Izmeni</button>
            </form >
           
            <form v-else-if="prikaz===1" class="message-form"  style="position: relative; top: 5px; left: 20px" >
            <h3>{{naslovK}}</h3>
              <div class="form-group">
                <label for="naziv">Naziv:</label>
                <input type="text" class="form-control" required v-model="kategorija.naziv" id="nazivK"  placeholder="Naziv kategorije">
              </div>
              <div class="form-group">
                <label for="opis">Opis</label>
                <textarea class="form-control" v-model="kategorija.opis" id="opis" placeholder="Opis" rows="3"></textarea>
         
                </div>
              <button type="button" class="btn btn-warning"  style="color: white" v-on:click="dodajKat()">{{dugmeK}}</button>
          </form >
          
          <form v-else-if="prikaz===2" class="message-form"  style="position: relative; top: 5px; left: 20px">
          <h3>Promena prava korisnika</h3>
                <div class="form-group">
                  <label for="kIme">Korisnicko ime:</label>
                  <input type="text" class="form-control" required v-model="korisnik.korisnickoIme" id="korime"  placeholder="Korisnicko ime">
                </div>
                <div>
                <label for="prava">Prava:</label> 
                <select class="form-control" v-model="korisnik.uloga" required>
                    <option value="ADMINISTRATOR">ADMINISTRATOR</option>
                    <option value="KUPAC">KUPAC</option>
                    <option value="PRODAVAC">PRODAVAC</option>
                  </select>
                </div>
                  
                <button type="button" style="position: relative;  top: 15px; color: white"  class="btn btn-warning" v-on:click="promeniPrava()">Promeni</button>
        </form>
        </div>
        
        <div class="col-8" style="left: 50px">
	        <button type="button" class="btn btn-info" style="width: 210px" v-on:click="setOglasi()">Upravljanje oglasima</button>
	        <button type="button" class="btn btn-info"  style="width: 220px" v-on:click="setKategorije()">Upravljanje kategorijama</button>
	        <button type="button" class="btn btn-info" style="width: 210px" v-on:click="setPrava()">Upravljanje pravima</button>
	        <button type="button" class="btn btn-info" style="width: 210px" v-on:click="setPrijave()">Upravljanje prijavama</button>
       
	        <div class="kat">  
	        <div v-if="prikaz===0" class="card-columns" style="display: inline-block" >
	            <div v-for="oglas in oglasi" class="card border-info" style="width: 18rem;">
	                <img v-bind:src="oglas.slika" style="max-width: 100%; max-height: 200px;"  class="card-img-top" alt="...">
	                <div class="card-body">
	                  <h5 class="card-title">{{oglas.naziv}}</h5>
	                  <p class="card-text">{{oglas.opis}}</p>
	                  <button class="btn btn-warning" type="button" v-on:click="izbrisi(oglas)"><i class="fa fa-trash"></i></button>
	                  <button class="btn btn-warning" type="button" v-on:click="izmeni(oglas)"><i class="fa fa-edit"></i></button>                
	               </div>
	              </div>
	           </div>   
	          <div v-if="prikaz===1">
	              <table style="width: 860px;">
	                  <tr>
	                    <th class="bg-info  text-white">Naziv</th>
	                    <th class="bg-info  text-white">Opis</th>
	                    <th class="bg-info  text-white">Izmeni</th>
	                    <th class="bg-info  text-white">Izbrisi</th>
	                  </tr>
	                  <tr v-for="kategorija in kategorije">
	                    <td>{{kategorija.naziv}}</td>
	                    <td>{{kategorija.opis}}</td>
	                    <td style="text-align: center">   <button class="btn btn-warning" type="button" v-on:click="izmeniK(kategorija)"><i class="fa fa-edit"></i></button>              
	                    </td>
	                    <td style="text-align: center">   <button class="btn btn-warning" type="button" v-on:click="izbrisiK(kategorija)"><i class="fa fa-trash"></i></button>
	                    </td>
	                  </tr>
	              </table>
	          </div>
	          
	          <div v-if="prikaz===2">
	            <table style="width: 860px;">
	                <tr>
	                  <th class="bg-info  text-white">Ime i prezime</th>
	                  <th class="bg-info  text-white">Email</th>
	                  <th class="bg-info  text-white">Korisnicko ime</th>
	                  <th class="bg-info  text-white">Uloga</th>
	                  <th class="bg-info  text-white">Izmeni</th>
	                  
	                </tr>
	                <tr v-for="kor in korisnici">
	                  <td>{{kor.ime}} {{kor.prezime}}</td>
	                  <td>{{kor.email}}</td>
	                  <td>{{kor.korisnickoIme}}</td>
	                  <td>{{kor.uloga}}</td>
	                  
	                  <td style="text-align: center">   <button class="btn btn-warning" type="button" v-on:click="izmeniP(kor)"><i class="fa fa-edit"></i></button>              
	                  </td>
	                </tr>
	            </table>
	        </div>
	        
	        <div v-if="prikaz !== 0 && prikaz !== 1 && prikaz !== 2">
            <table style="width: 860px;">
                <tr>
                  <th class="bg-info  text-white">Ime i prezime prodavca</th>
                  <th class="bg-info  text-white">Email</th>
                  <th class="bg-info  text-white">Korisnicko ime</th>
                  <th class="bg-info  text-white">Broj prijava</th>
                  <th class="bg-info  text-white">Restartuj</th>
                  
                </tr>
                <tr v-for="kor in sumnjivi">
                  <td>{{kor.ime}} {{kor.prezime}}</td>
                  <td>{{kor.email}}</td>
                  <td>{{kor.korisnickoIme}}</td>
                  <td>{{kor.prijave}}</td>
                  
                  <td style="text-align: center">   <button class="btn btn-warning" type="button" v-on:click="refresh(kor)"><i class="fa fa-refresh"></i></button>              
                  </td>
                </tr>
            </table>
        </div>
	              
	              
	              
	          </div>    
	        
	      </div>
	        
	        
	    </div> 
        
        </div>
    </div>
    `,
    methods: {
    	  onFileChange(e) {
              const file = e.target.files[0];
              this.url = URL.createObjectURL(file);
              console.log("slikaa");
              console.log(this.url);
            },
            refresh(kor){
                var self = this;
                  
                        axios.post('rest/refresuj/'+ kor.id)
                        .then(function(response){
                            self.ucitajSumnjive();
                        })
                        .catch(function(erros){
                            console.log(error);
                            alert('neuspesna promena')
                    })
                  
                
            },
           
            ucitajOglase (){
            	  
          		var self = this;
          		axios.get ('rest/izlistajSve/')
          		.then (function(response){
          			self.oglasi=response.data;
          		})
          		.catch(function (error){
          			console.log(error);
          		});
            },
            izbrisiK(k){
              
                var self = this;
                axios.delete('rest/obrisiK/' + k.id)
                .then(function(response){
                	alert('Obrisali ste kategoriju!');
                    self.ucitajKategorije();
                })
                .catch(function(error){
                    console.log(error);     
                })
              },
            izmeniK(kategorija){
            	this.naslovK = "Izmena kategorije";
            	this.dugmeK = "Izmeni";
                this.kategorija=kategorija;
            },
            izbrisi(oglas){
                var self = this;

                axios.delete('rest/obrisi/' + oglas.id)
                .then(function(response){
                    self.oglasi=response.data;
                    self.ucitajOglase();
                })
                .catch (function(error){
                    console.log(error);
                  
                });
              },
              izmeni(oglas){
            	 
                  this.url=oglas.slika;
                  this.oglas=oglas;
              },
            dodajKat(){
            	this.naslovK = "Dodavanje kategorije";
              	this.dugmeK = "Dodaj";
                var self = this;

                if (this.kategorija.naziv=="" || this.kategorija.opis==""){
                  alert('Morate popuniti sva polja!');
                  return;
                }

                axios.post('rest/dodajK', this.kategorija)
                .then(function(response){
                    self.kategorija={naziv : "", opis:""}
                    self.ucitajKategorije();    
                })
                .catch(function(error){
                    console.log(error);
                    alert('neuspesno dodavanje');
                })

            },
            ucitajKorisnike(){
                var self=this;
                axios.get('rest/izlistajSveKorisnike')
                .then(function(response){
                    console.log('ucitavam korisnike');
                    console.log(response);
                    self.korisnici=response.data;
                })
                .catch(function(error){
                    console.log(error);
                })
            },
            izmeniP(kor){
                this.korisnik=kor;
            },
            setPrava(){
                this.prikaz=2;
                this.ucitajKorisnike();
                
            },
            setKategorije(){
               this.prikaz=1;
               this.ucitajKategorije();
            },
            setOglasi(){
                this.prikaz=0;
                this.ucitajOglase();
            },
            setPrijave(){
              this.prikaz=3;
             // this.ucitajOglase();
             this.ucitajSumnjive();
            
            },
            ucitajKategorije(){
                self=this;
                axios.get('rest/izlistajSveKategorije')
                .then (function(response){
                    console.log(response);
                    self.kategorije=response.data;
        
                })
                .catch (function(error){
                    console.log(error);
                });
            },
            promeniPrava(){
            	
                var self = this;
                if (this.korisnik.id==""){
                    alert('Morate oznaciti korisnika kome zelite da promenite prava!');
                }else 
                {
                    axios.post('rest/izmeniPrava', this.korisnik)
                    .then(function(response){
                        self.ucitajKorisnike();
                    })
                    .catch(function(erros){
                        console.log(error);
                        alert('neuspesna promena')
                })
                    this.korisnik = {korisnickoIme:"", lozinka : "", ime : "", prezime : "", telefon : "", grad : " ",  email : "", datum : "", uloga : "" };
          
            }
                },
            ucitajSumnjive (){
                self=this;
                axios.get('rest/izlistajPrijave')
                .then(function(response){
                    self.sumnjivi=response.data;
                })
                .catch(function(error){
                    console.log(error);
                })
              },
            dodaj(){
            
            	   var self=this;
                   if (this.oglas.id==""){
                     alert('Morate oznaciti oglas koji zelite da promenite!');
                   } else {
                     let data = new FormData();
                     var self = this;
                     let objFile = $("#pic");
                     let file = objFile[0].files[0];
                     let fileName = file == undefined ? '' : file.name;
                     data.append('Oglas', JSON.stringify( this.oglas));
                     data.append('fajl', file);
                     data.append('slika', fileName);
                       axios.post('rest/dodajO', data )
                       .then(function(response){
                         self.oglas = {naziv : "", cena : 0, opis:"", datumIsticanja:"", grad:"", brDislajkova:0, brLajkova:0, kategorija:""}
                         self.url=null;
                         self.ucitajOglase();
                       })
                       .catch(function(error){
                         alert('Neuspesno dodavanje oglasa!');
                         console.log(error);
                       })
                 }
            }
           
    	
    },
    mounted(){
    	   this.ucitajOglase();
          this.ucitajKategorije();
          this.ucitajKorisnike();
          this.ucitajSumnjive();
    	
    }
    
    
    
});