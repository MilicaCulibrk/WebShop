Vue.component ("register", {
    data: function() {
        return {
            korisnik  : { ime : '', prezime:'', email: '', korisnickoIme:'', lozinka:'', telefon:'', grad: '' },
        }
    },
    template: `<section class="container">
    <form id="my-form">
      <h1 class="regText">Registracija</h1>
      <div class="msg"></div>
      <div>
        <label for="name">Ime:</label>
        <input type="text" v-model = "korisnik.ime"  id="ime" placeholder="Ime" required>
      </div><div>
        <label for="name">Prezime:</label>
        <input type="text" v-model = "korisnik.prezime"  placeholder="Prezime" required>
      </div><div>
      <label for="exampleInputEmail1">Email </label>
      <input type="email" v-model = "korisnik.email" class="form-control" id="exampleInputEmail1" placeholder="Email@.com" aria-describedby="emailHelp">
   </div>
      <div>
        <label for="name">Korisnicko ime:</label>
        <input type="text" v-model = "korisnik.korisnickoIme"  id="kime"  placeholder="Korisnicko ime" required>
      </div>
      <div>
        <label for="email">Lozinka:</label>
        <input type="password" v-model = "korisnik.lozinka"  id="lozinka" placeholder="********" required>
      </div>
      <div>
        <label for="name">Broj telefona:</label>
        <input type="number" v-model = "korisnik.telefon"  id="telefon" placeholder="0xxxxxxxxx" required>
      </div><div>
        <label for="name">Grad:</label>
        <input type="text" v-model = "korisnik.grad"  id="grad" placeholder="Grad" required>
      </div>
      <input class="myBtn btn-info" type="button" v-on:click="reg" value="Registrujte se">
    </form>
  </section> `,
    methods: {
    reg(){
        var temp = this;
        if (this.korisnik.ime=="" || this.korisnik.prezime=="" || this.korisnik.korisnickoIme=="" 
        || this.korisnik.lozinka=="" || this.korisnik.grad=="" || this.korisnik.email=="" || this.korisnik.telefon=="" ){
          alert('Morate uneti sva polja!');
          return;
        }
        axios.post('rest/registracija', this.korisnik)
        .then(function (response) {
        console.log(response);  //vracam ga sa bc
        alert('Uspesno ste se registrovali!');
        temp.korisnik  = { ime : '', prezime:'', email: '', korisnickoIme:'', lozinka:'', telefon: '', grad: '' }
    
    })
    .catch(function (error) {
        console.log(error);
        alert('Neuspesna registracija!');
    
    });
    }
} 

});