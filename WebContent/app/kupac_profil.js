Vue.component("kProfil", {
    data() {
        return {
            oglasi: [],
            poruceniO : [],
            dostavljeniO : [],
            oglas : {id : "" ,naziv : "", cena : 0, opis:"", datumIsticanja:"", grad:"", brDislajkova:0, brLajkova:0, slika:""},
            prikazi: 1,
            recenzija : {id:"", naslov : "", sadrzaj: "", tacnost : false , postovanjeDog : false, oglas:""},
            roglas : {naziv : "", cena : 0, opis:"", datumIsticanja:"", grad:"", brDislajkova:0, brLajkova:0},
            forma : false,
            recenzije : [],
            setPrikaz: 0 ,
            url: null,
            naslov: "Dodavanje recenzije",
            dugme: "Dodaj"
            
        }
    },
    
    template: ` <div class="down">
    <div class="row">
    <div class="col-3">
        <form v-if="forma" class="message-form"  style="position: relative; top: 5px; left: 20px"">
            <h3>{{naslov}}</h3>
              <div class="form-group">
                <label for="naslov">Naslov: </label>
                <input type="text" class="form-control" v-model="recenzija.naslov"   id="nazivK"  placeholder="Naslov">
              </div>
              <div class="form-group">
                <label for="Sadrzaj">Sadrzaj:</label>
                <textarea class="form-control" v-model="recenzija.sadrzaj" placeholder="Opis" id="sadrzaj" rows="3"></textarea>
            </div>
               <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" v-model="recenzija.tacnost" id="exampleCheck1">
                <label class="form-check-label" for="exampleCheck1">Opis iz sadrzaja je ispostovan</label>
              </div>
              <div class="form-group form-check">
                  <input type="checkbox" class="form-check-input" v-model="recenzija.postovanjeDog" id="exampleCheck2">
                  <label class="form-check-label" for="exampleCheck1">Dogovor je ispostovan</label>
              </div>  
              <div> 
              <div class="preview">
                  <img :src="url" />
              </div> 
              <input type="file" id="pic" @change="onFileChange" /> 

             </div>
             
              <button style="position: relative; top:10px" type="button" class="btn btn-warning" v-on:click="dodaj()">{{dugme}}</button>
            </form>
        </div>
            
            <div class="col-8">
	            <button type="button" class="btn btn-info" style="position: relative; width: 200px" v-on:click="setOmiljene()">Omiljeni oglasi</button>
	            <button type="button" class="btn btn-info" style="position: relative; width: 200px" v-on:click="setPorucene()">Poruceni proizvodi</button>
	            <button type="button" class="btn btn-info" style="position: relative; width: 200px" v-on:click="setDostavljene()">Dostavljeni proizvodi</button>
	            <button type="button" class="btn btn-info" style="position: relative; width: 200px" v-on:click="setRecenzije()">Moje recenzije</button>
            
	           
	            <div class="kat">
	            <div v-if="setPrikaz===0">
	                <div class="card-columns" style="display: inline-block">
	                    <div v-for="oglas in oglasi" class="card border-info" style="width: 18rem;">
	                        <img v-bind:src="oglas.slika" style="max-width: 100%; max-height: 170px;"  class="card-img-top" alt="...">
	                        <div class="card-body">
	                          <h5 class="card-title">{{oglas.naziv}}</h5>
	                          <p class="card-text"> <b> Cena : </b> {{oglas.cena}}</p>
	                          <button type="button" class="btn btn-warning" v-on:click="poruci(oglas)">Poruci</button>
	                        </div>
	                      </div>
	                </div>
	            </div>
	            <div v-else-if= "setPrikaz===1">
	                <div class="card-columns" style="display: inline-block">
	                    <div v-for="o in poruceniO" class="card border-info" style="width: 18rem;">
	                        <img v-bind:src="o.slika" style="max-width: 100%; max-height: 170px;" class="card-img-top" alt="...">
	                        <div class="card-body">
	                          <h5 class="card-title">{{o.naziv}}</h5>
	                          <p class="card-text">Cena : </b> {{o.cena}}</p>
	                          <button type="button" class="btn btn-warning" v-on:click="dostavi(o)">Dostavljeno</button>
	                        </div>
	                      </div>
	                </div>
	            </div>
	            <div v-else-if= "setPrikaz===2">
	                <div class="card-columns" style="display: inline-block">
	                <div v-for="d in dostavljeniO" class="card border-info" style="width: 18rem;">
	                  <img v-bind:src="d.slika" style="max-width: 100%; max-height: 200px;" class="card-img-top" alt="...">
	                    <div class="card-body">
	                      <h5 class="card-title">{{d.naziv}}</h5>
	                      <p class="card-text">Cena : </b> {{d.cena}}</p>  
	                      <button type="button" class="btn btn-warning" v-on:click="posaljiR(d)">Napisi recenziju</button>  
	                      <button type="button" class="btn btn-danger" style="color: black" v-on:click="prijavi(d)">Prijavi</button>  
	                  </div>
	                  </div>
	                </div>
	        
	            </div>
	            <div v-else>
	                <div  v-for="r in recenzije" class="card mb-3 border-info" style="max-width: 540px; left: 150px">
	                    <div class="row no-gutters">
	                      <div class="col-md-4">
	                        <img v-bind:src="r.slika" style="max-width: 100%; max-height: 100%;" class="card-img" alt="...">
	                      </div>
	                      <div class="col-md-8">
	                        <div class="card-body">
	                          <h5 class="card-title">{{r.naslov}}</h5>
	                          <p class="card-text">{{r.sadrzaj}}</p>
	                          <button class="btn btn-warning" type="button" v-on:click="izbrisi(r)"><i class="fa fa-trash"></i></button>
	                          <button class="btn btn-warning" v-on:click="izmeni(r)"><i class="fa fa-edit"></i></button>  
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
    	 ucitajDostavljene(){
             var self = this;
             console.log('ucitavam dostavljene')
         
             axios.get('rest/izlistajDostavljene')
             .then(function(respons){
                 self.dostavljeniO = respons.data;  //idevi dostavljenih oglasa
             })
             .catch(function(error){
                 console.log(error);
             })
           },
           izmeni(r){
        	   this.naslov = "Izmena recenzije";
        	   this.dugme = "Izmeni";
               this.recenzija=r;
               this.oglas.id=r.oglas;
               this.url=r.slika;
           },
           izbrisi(r){
               var self = this;

               axios.delete('rest/obrisiRec/' + r.id)
               .then(function(response){
                   alert('Da li ste sigurni da zelite da obrisete recenziju? ');
                   self.ucitajRecenzije();
               })
               .catch (function(error){
                   console.log(error);
                 
               });
           },
           poruci(o){
               self=this,

               axios.post('rest/poruci/'+ o.id)
               .then(function(respons){
                   alert('Porucili ste proizvod!');
                   self.ucitajOmiljene();
                   self.ucitajPorucene();
               })
               .catch(function(error){
                   alert('Proizvod nije porucen!');
                   console.log(data);   
               })
             },
           
           setOmiljene(){
               this.setPrikaz=0;
               this.forma=false;
               this.ucitajOmiljene();
             },
             setPorucene(){
                 this.setPrikaz=1;
                 this.forma=false;
                 this.ucitajPorucene();
      
               },
               setDostavljene(){
                 this.setPrikaz=2;
                 this.ucitajDostavljene();
               },
               setRecenzije(){
                 this.forma=true;
                 this.setPrikaz=3;
                 this.ucitajRecenzije();
               },
               posaljiR(d){
                   this.forma=true;
                   this.oglas=d;
               },
               prijavi(d){
                   var self = this;

                   axios.post('rest/prijavi/' + d.id)
                   .then(function(response){
                     console.log(response);
                       alert('Prijavili ste oglas!');
                   })
                   .catch (function(error){
                       console.log(error);
                     
                   });
                 },

            
         
           dodaj(){
               this.naslov = "Dodavanje recenzije";
               this.dugme = "Dodaj";
               self = this;
               
               if (this.recenzija.naslov == "" || this.recenzija.sadrzaj==""){
                 alert('Morate popuniti sva polja!');
                 return;
               }

    
               let data = new FormData();
               let objFile = $("#pic");
               let file = objFile[0].files[0];
               let fileName = file == undefined ? '' : file.name;
               data.append('Recenzija', JSON.stringify( this.recenzija));
               data.append('fajl', file);
               data.append('slika', fileName);

               axios.post('rest/dodajR/' + self.oglas.id, data)
               .then(function(reponse){
                   self.recenzija = {naslov : "", sadrzaj: "", tacnost : false , postovanjeDog : false};
                   self.ucitajRecenzije();
                   self.url=null;
                  
               })
               .catch (function(error){
                   alert('neuspesno slanje recenzije');
               });
           }, 
           onFileChange(e) {
               const file = e.target.files[0];
               this.url = URL.createObjectURL(file);
               console.log("slikaa");
               console.log(this.url);
             },
           ucitajPorucene(){
               var self = this;
               console.log('ucitavam prucene')
           
               axios.get('rest/izlistajPorucene')
               .then(function(respons){
                   self.poruceniO=respons.data;
               })
               .catch(function(error){
                   console.log(error);
               })
             },
             ucitajRecenzije(){
                 var self = this;
                 console.log('ucitavam recenzije')
             
                 axios.get('rest/izlistajSveRecenzije')
                 .then(function(respons){
                     self.recenzije=respons.data;
                 })
                 .catch(function(error){
                     console.log(error);
                 })
               },
               dostavi(o){
                   self=this,

                   axios.post('rest/dostavljeno/'+ o.id)
                   .then(function(respons){
                       self.ucitajPorucene();
                       self.ucitajDostavljene();
                   })
                   .catch(function(error){
                       alert('neuspesno ste oznacili da je proizvod dostavljen');
                       console.log(data);   
                   })
                 },
           ucitajOmiljene(){
               var self = this;
               console.log('ucitavam omiljene oglase')
           
               axios.get('rest/izlistajOmiljene')
               .then(function(respons){
                   self.oglasi=respons.data;
               })
               .catch(function(error){
                   console.log(error);
               })
             }
    	
    
    
    },
    
    mounted() {
    	   this.ucitajDostavljene();
           this.ucitajOmiljene();
           this.ucitajPorucene();
           this.ucitajRecenzije();
    	
    },
}) 