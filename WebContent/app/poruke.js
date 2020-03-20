Vue.component ("poruka" , {
    data() {
        return {
            setPoruke: true,
            primljene : [],
            poslate: [],
            username: "",
            poruka: { id:"", naslov:"", sadrzaj: "", nazivOglasa:"", datum:"", posiljalac:""}
            
        }
    },
    template: ` 
    <div class="down">
    <div class="row">
    <div class="col-5">
	<form class="message-form" style="position: relative; left: 20px">
	  <div class="form-group row">
	    <label for="staticEmail" class="col-sm-2 col-form-label">Od:  </label>
	   <div class="col-sm-10">
	      <input type="text" readonly class="form-control-plaintext" id="staticEmail" value="Mene"> 
	   </div>
	  </div>
	  <div class="form-group row">
	    <label for="inputPassword" class="col-sm-2 col-form-label">Za: </label>
	    <div class="col-sm-10">
	      <input type="text" v-model="username" class="form-control" id="inputPassword" placeholder="Korisnicko ime primaoca">
	    </div>
	  </div>
	  <div class="form-group row">
			<label for="inputPassword" class="col-sm-2 col-form-label">Naslov: </label>
			<div class="col-sm-10">
			  <input type="text" v-model="poruka.naslov" class="form-control" id="inputPassword" placeholder="Naslov poruke">
			</div>
		  </div>
	  <div class="form-group row">
			<label for="inputPassword" class="col-sm-2 col-form-label">Naziv oglasa: </label>
			<div class="col-sm-10">
			  <input type="text" v-model="poruka.nazivOglasa" class="form-control" id="inputPassword" placeholder="Naziv oglasa o kome pisete">
			</div>
		  </div>
	  <div class="form-group">
    	<label for="exampleFormControlTextarea1">Tekst:</label>
   		<textarea class="form-control" v-model="poruka.sadrzaj" id="exampleFormControlTextarea1" placeholder="Tekst poruke"  rows="3"></textarea>
  	  </div>
         <button type="button" class="btn btn-warning" style="color: white" v-on:click="posalji()">Posalji</button>
  
	</form>           
    </div>
    
    <div class="col-6" style="position: relative; left: 20px">
	<button type="button" class="btn btn-info" style="width: 100px" v-on:click="setPrimljene()">Primljene</button>
<button type="button" class="btn btn-info"  style="width: 100px; left 100px" v-on:click="setPoslate()">Poslate</button>

	<div v-if= "setPoruke">
    <div v-for="poruka in primljene" class="card border-info" style="width: 46rem; top: 20px">
       <div class="card">
          <div class="card-header">
            Naslov: <b>{{poruka.naslov}}</b>
            
		
	                   <br> 
            Od: {{poruka.posiljalac}}

		</div>
		  <div class="card-body">
		    <blockquote class="blockquote mb-0" >
			  <p style="color: black">
				{{poruka.sadrzaj}}	
			  </p>
              <footer class="blockquote-footer">Datum i vreme: <cite title="Source Title">{{poruka.datum}}</cite>
              <button class="btn btn-warning" style="position: relative; left:330px" type="button" v-on:click="izbrisi(poruka)"><i class="fa fa-trash"></i></button>
              </footer>
              
         
		    </blockquote>
		  </div>
		</div>     
	</div>
</div>
<div v-else>
	<div v-for="poruka in poslate" class="card border-info" style="width: 46rem;  top: 20px">
			<div class="card">
			   <div class="card-header">
               Naslov: <b>{{poruka.naslov}}</b>
            
		
               <br> 
            Od: {{poruka.posiljalac}}

			 </div>
			   <div class="card-body">
				 <blockquote class="blockquote mb-0" >
				   <p style="color: black">
					 {{poruka.sadrzaj}}	
				   </p>
                   <footer class="blockquote-footer">Datum i vreme: <cite title="Source Title">{{poruka.datum}}</cite>
                   <button class="btn btn-warning" style="position: relative; left:280px" type="button" v-on:click="izbrisi(poruka)"><i class="fa fa-trash"></i></button>
                   <button class="btn btn-warning" style="position: relative; left:280px" v-on:click="izmeni(poruka)"><i class="fa fa-edit"></i></button>
               
                   </footer>
				 </blockquote>
			   </div>
			 </div>     
		 </div>
		</div>
</div>
</div>
</div>
    
    
   
	`,
	
    methods: {
    	  ucitajPoslate(){
              var self = this;

              axios.get('rest/izlistajPoslate')
              .then (function(response){
                  self.poslate=response.data;
              })
              .catch (function(error){
                  console.log(error);    
              });
            
            },
            izmeni(o){
                this.username="";
                this.poruka=o;

              },  
            izbrisi(poruka){
                var self = this;
                
                axios.delete('rest/obrisiP/' + poruka.id)
                .then(function(response){
                    alert('Obrisali ste poruku!');
                    self.ucitajPrimljene();
                    self.ucitajPoslate();
                })
                .catch (function (error){
                    alert('Niste obrisali poruku!');
                });
            },
            setPoslate(){
                this.setPoruke=false;
                console.log(this.setPoruke);
              },
            setPrimljene(){
                this.setPoruke=true;
                console.log(this.setPoruke);
              },
            posalji(){
                var self = this;

                if(this.username=="" || this.poruka.naslov=="" || this.poruka.sadrzaj==""){
                  alert('Morate uneti sva polja!');
                  return;
                }
                
                axios.post('rest/dodajP/' + this.username, this.poruka)
                .then (function(response){
                	alert("Poslali ste poruku!");
                	  self.username="";
                      self.ucitajPoslate();
                	 
                })
                .catch (function(error){
                	
                    console.log(error);    
                });  
              
              
              },
            ucitajPrimljene(){
                var self = this;

                axios.get('rest/izlistajPrimljene')
                .then (function(response){
                    self.primljene=response.data;
                })
                .catch (function(error){
                    console.log(error);    
                });  
              
              }
          
    	
    	
    },
    
    mounted(){
    	  this.ucitajPoslate();
          this.ucitajPrimljene();
    },
});
    