Vue.component("pProfil", {
    data() {
        return {
            oglasi: [],
            oglas : {id : "" , naziv : "", cena : 0, opis:"", datumIsticanja:"", grad:"", brDislajkova:0, brLajkova:0, kategorija:""},
            oglasiProdavac : [],
            recenzije: [],
            setOR: true,
            kategorije: [],
            url: null,
            status: "" ,
            prodavac: {korisnickoIme:"", lozinka : "", ime : "", prezime : "", telefon : "", grad : " ",  email : "", datum : "", uloga : "", prijave : 0, likes : 0, dislikes: 0}, 
            prijave : false,
            dugme: "Dodaj",
            naslov: "Dodavanje oglasa"
        }
    },
    template:`
    <div class="down">
        <div v-if="prijave" class="alert alert-danger" role="alert">
        <b> Ovaj nalog je markiran zbog sumnjivih aktivnosti, ne mozete dodavati nove oglase! </b>
        </div>
        
        <div class="row">

        <div class="col-3">
              
            <h3  style="position: relative; left: 20px">Moje ocene: 
            <span class="badge badge-warning" > <i class="fa fa-thumbs-up"></i> {{prodavac.likes}}</span>
            <span class="badge badge-warning" > <i class="fa fa-thumbs-down"></i> {{prodavac.dislikes}}</span>
            </h3>
            <form class="message-form"  style="position: relative; top: 5px; left: 20px">
            <h3>{{naslov}}</h3>
            <div class="form-group">
                <label for="naziv">Naziv:</label>
                <input type="text" v-model="oglas.naziv"  class="form-control" id="naziv"
                    aria-describedby="emailHelp" placeholder="Naziv" required>
            </div>
            <div class="form-group">
                <label for="cena">Cena:</label>
                <input type="number" v-model="oglas.cena"  required class="form-control" id="cena"
                    placeholder="Cena">
            </div>
           
            <div class="form-group">
              <label for="opis">Opis:</label>
              <textarea class="form-control" required v-model="oglas.opis" id="exampleFormControlTextarea1" rows="3" placeholder="Opis"></textarea>
          </div>
            <div class="form-group">
                <label for="datum">Datum isticanja:</label>
                <input type="date" required v-model="oglas.datumIsticanja" required class="form-control" id="datum"
                    placeholder="Unesite datum isticanja">
            </div>
            <div class="form-group">
                <label for="grad">Grad:</label>
                <input type="text" v-model="oglas.grad" required class="form-control" id="grad"
                    placeholder="Unesite grad">
            </div>
            <div class="form-group">
                <label for="kategorija">Kategorija</label>
                <select class="form-control" v-model="oglas.kategorija">
                    <option v-for="kategorija in kategorije" v-bind:value="kategorija.naziv">
                        {{kategorija.naziv}}</option>
                    </select>
            </div>
            <div> 
                <div class="preview">
                    <img :src="url" />
                </div> 
                <input type="file" id="pic" @change="onFileChange" /> 

            </div>
            <button type="button" class="btn btn-warning" style="position: relative;  top: 25px; color: white"
                v-on:click="dodaj">{{dugme}}</button>
        </form>
       </div>
       
       <div class="col-8" style="left: 50px">
       <button type="button" class="btn btn-info" style="width: 200px" v-on:click="setOglasi()">Moji Oglasi</button>
       <button type="button" class="btn btn-info"  style="width: 200px" v-on:click="setRecenzije()">Moje recenzije</button>
       <div style="position: relative; top: 10px;">
       <div class="kat" v-if= "setOR">
       <form class="form-inline">
             
              
           <div class="form-group mx-sm-3 mb-2 ">
                 <select v-model="status" style="width: 600px" class="form-control border-warning">
                     <option>AKTIVAN</option>
                     <option>DOSTAVLJEN</option>
                     <option>UREALIZACIJI</option>  
                 </select> 
             </div>
             <button style="width: 200px" v-on:click="filtriraj" type="button" class="btn btn-warning mb-2">Filtriraj</button>
       </form>
       
       <div class="card-columns" style="display: inline-block;">
       <div v-for="oglas in oglasiProdavac" class="card border-info">
           <img v-bind:src="oglas.slika"  style="max-width: 100%; max-height: 200px;" class="card-img-top" alt="...">
           <div class="card-body">
             <h5 class="card-title">{{oglas.naziv}}</h5>
             <p style="color:black" class="card-text"> <b>Cena </b>: {{oglas.cena}}</p>
             <button class="btn btn-warning" type="button" v-on:click="izbrisi(oglas)"><i class="fa fa-trash"></i></button>
             <button class="btn btn-warning" type="button" v-on:click="izmeni(oglas)"><i class="fa fa-edit"></i></button>                
           </div>
         </div>
   </div>
       
       </div> 
       <div v-if="!setOR">
       <div  v-for="r in recenzije" class="card mb-3 border-info" style="max-width: 540px;">
           <div class="row no-gutters">
             <div class="col-md-4">
               <img v-bind:src="r.slika" class="card-img" alt="...">
             </div>
             <div class="col-md-8">
               <div class="card-body">
                 <h5 class="card-title">{{r.naslov}}</h5>
                 <p class="card-text">{{r.sadrzaj}}</p>
               </div>
             </div>
           </div>
         </div>
   </div>
        </div>
        </div>
    </div>
    </div>
    `,
    methods: {
    	  getProdavac(){
              var self = this;  
              axios.get('rest/getUlogovanog' )
                .then(function(response){
                    self.prodavac=response.data;
                    if (response.data.prijave>3){
                        self.prijave=true;
                    }
                    console.log(self.prodavac);
                    
                })
                .catch(function(error){
                  console.log(error);
                })
            },
            setOglasi(){
                this.setOR = true;
              },
              setRecenzije(){
                  this.setOR=false;
                },
                filtriraj() {
                    var self = this;
                    
                    if (this.status=="AKTIVAN"){
                        this.ucitajObjavlejne();
                    } else {

                    axios.post('rest/filtriraj/' + this.status)
                    .then(function(response){
                      console.log("filtr: ");
                      console.log(response.data);    
                      self.oglasiProdavac = response.data;
                      
                    })
                    .catch(function(error){
                        console.log(error);
                    })
                  }
                },
            dodaj(){
                this.dugme = "dodaj";
                this.naslov = "Dodavanje oglasa";
                var self = this;  
            
                    if(this.oglas.id == ""){
                        
                        if (this.prodavac.prijave > 3){
                            alert('Vas nalog je markiran zbog sumnjivih aktivnosti!');
                            return;
                        }

                        if (this.oglas.naziv=="" || this.oglas.opis=="" || this.oglas.cena==0 || this.oglas.datumIsticanja=="" || this.oglas.kategorija=="" || this.oglas.grad==""){
                            alert("Morate uneti sva polja");
                            return;
                        }

                        if( pic.files.length === 0){
                           alert("Morate uneti sliku!");
                             return;
                         } 

                          
                         
                     }
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
                        self.ucitajObjavlejne();
                    })
                    .catch(function(error){
                        alert('neuspesno dodavanje oglasa');
                        console.log(error);
                    })
                
              },
            onFileChange(e) {
                const file = e.target.files[0];
                this.url = URL.createObjectURL(file);
                console.log("slikaa");
                console.log(this.url);
              },
              izmeni(oglas){
                  this.url=oglas.slika;
                  this.oglas=oglas;
                  this.dugme = "izmeni";
                  this.naslov = "Izmena oglasa";
              },
              izbrisi(oglas){
                  var self = this;

                  axios.delete('rest/obrisi/' + oglas.id)
                  .then(function(response){
                      self.oglasi = response.data;
                      self.ucitajObjavlejne();
                  })
                  .catch (function(error){
                      console.log(error);
                    
                  });
                },
              ucitajObjavlejne(){
                  var self = this;
                  console.log('ucitavam objavljene oglasae oglase')

                  axios.get('rest/izlistajObjavljene')
                  .then(function(response){
                      console.log(response);
                      self.oglasiProdavac = response.data;
                  })
                  .catch(function(error){
                      console.log(error);
                  })
                },
                ucitajRecenzije(){
                    var self = this;
                    console.log('ucitavam recenzije')

                    axios.get('rest/izlistajRecenzije')
                    .then(function(response){
                        console.log(response);
                        self.recenzije = response.data;
                    })
                    .catch(function(error){
                        console.log(error);
                    })
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
          }
        },   
        
    mounted() {
        this.getProdavac();
        this.ucitajKategorije();
        this.ucitajObjavlejne();
        this.ucitajRecenzije();
    },
    
})    