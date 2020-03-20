Vue.component("loginn", {
	data: function () {
		    return {
                korisnik : {korisnickoIme:'', lozinka:''}     
		    }
    },
    template:` <div> <section class="container">
    <form id="my-form">
      <h2 class="regText">Prijava</h2>
      <div>
        <label for="ime">Korisnicko ime:</label>
        <input type="text" v-model="korisnik.korisnickoIme"  placeholder="Korisnicko ime" required id="ime">
      </div>
      <div>
        <label for="lozinka">Lozinka:</label>
        <input type="password" v-model="korisnik.lozinka"  placeholder="********" required id="lozinka">
      </div>
      <input class="myBtn btn-info" type="button" v-on:click="login" value="Prijavite se">
      <p>Nemate nalog? <a href="http://localhost:8080/PocetniRESTL/index.html?#/register">Registrujte se</a></p> 
    </form>
    </section>
    </div>		  
`,
methods: {
    login(){
    	   axios.post('http://localhost:8080/login', {
               korisnicko: this.email,
               lozinka: this.lozinka,
           }).then(data => {this.token = data.tokenState;
           this.uspesnoLogovanje = true;
           setTimeout(() => this.uspesnoLogovanje = false, 3500);}) // Kad stigne odgovor od servera preuzmi objekat
           .catch(() => {this.neispravniPodaci = true;
           setTimeout(() => this.neispravniPodaci = false, 3500)
    
    });
    }
}
});