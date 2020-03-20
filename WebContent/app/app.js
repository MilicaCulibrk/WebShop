const PocetnaStr = { template: '<pocetnaStr></pocetnaStr>' }
const Login = { template: '<loginn></loginn>'  }
const Registration = { template: '<register></register>'}
const Aprofil = {template: '<aProfil></aProfil>'}
const Pprofil = {template: '<pProfil></pProfil>'}
const Kprofil = {template: '<kProfil></kProfil>'}
const Poruke = { template: '<poruka></poruka>' }  


const router = new VueRouter({
	  mode: 'hash',
	  routes: [
	    { path: '/', component: PocetnaStr},
		{ path: '/register', component: Registration},
		{ path: '/loginn', component: Login},
		{ path: '/aProfil', component: Aprofil},
		{ path: '/pProfil', component: Pprofil},
		{ path: '/kProfil', component: Kprofil},
		{ path: '/poruke', component: Poruke},
	  ]
});

var app = new Vue({
	router,
	el: '#app1',
	data: {
		prijavljen: true
	},
});