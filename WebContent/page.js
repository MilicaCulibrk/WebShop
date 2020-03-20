function addRacuniTr(racuni)
{
	let tr=$('<tr></tr>');
	let tdBroj=$('<td>'+racuni.brojRacuna +'</td>');
	let tdTip=$('<td>'+racuni.tip +'</td>');
	let tdRaspolozivo=$('<td>'+racuni.raspolozivoStanje +'</td>');
	let tdRezervisano=$('<td>'+racuni.rezervisanoStanje +'</td>');

	let on;
	switch(racuni.online)
	{
		case false: on="Ne"; break;
		case true: on="Da"; break;
	}
	let tdOnline=$('<td>'+on +'</td>');
	let tdAktivan=$('<td>'+racuni.aktivan +'</td>');
	let zbir = racuni.raspolozivoStanje+racuni.rezervisanoStanje;
	let tdUkupno=$('<td>'+zbir+'</td>');

	let tipic;
	switch(racuni.aktivan)
	{
		case 0: tipic="Aktiviraj"; break;
		case 1: tipic="Deaktiviraj"; break;
	}

	let tdObrisi = $('<td>' + '<a href="#">Obrisi</a>' + '</td>');
	tdObrisi.click(clickBrisi(racuni.brojRacuna));

	let tdAkDe = $('<td>' + '<a href="#">'+tipic+'</a>' + '</td>');
	tdAkDe.click(clickAD(racuni.brojRacuna));
	
	tr.append(tdBroj).append(tdTip).append(tdRaspolozivo).append(tdRezervisano).append(tdUkupno).append(tdOnline).append(tdAktivan).append(tdObrisi).append(tdAkDe);
	$('#tabelaracuna tbody').append(tr);
	
	if(racuni.aktivan===1)
	{
		dodajSelect(racuni);
	}
	
}

function clickAD(brojRacuna)
{
	return function() {
		
		let url = 'rest/promeniAktivan/';
		url += brojRacuna;

		$.get({
		
			url: url,
			contentType: 'application/json',
			success: function(racuni)
			{	
				window.location='./page.html';
				$('#racunitabela tbody').html('');
				console.log(racuni);
				
				for(let racun of racuni)
				{
					addRacuniTr(racun);
				}
			}
			
			
		});
		
	}	
}

function clickBrisi(brojRacuna)
{
	return function() {
		
		let url = 'rest/brisi/';
		url += brojRacuna;

		$.get({
		
			url: url,
			contentType: 'application/json',
			success: function(racuni)
			{	
				window.location='./page.html';
				$('#racunitabela tbody').html('');
				console.log(racuni);
				
				for(let racun of racuni)
				{
					addRacuniTr(racun);
				}
			}
			
			
		});
		
	}	
}



function dodajSelect(racuni)
{
	let tdBroj=$('<option>'+racuni.brojRacuna+'</option>');
	$('#select').append(tdBroj);
}


function getRacune()
{
	$.get({
		
		url:'rest/racuni',
		contentType: 'application/json',
		success: function(racuni)
		{	
			$('#racunitabela tbody').html('');
			console.log(racuni);
			
			for(let racun of racuni)
			{
				addRacuniTr(racun);
			}
		}
		
		
	});
}

$(document).ready(()=>{
	
	getRacune();
	
	$('#dodaj').submit((event)=>{
		event.preventDefault();
		
		let brojRacuna=$('#brojRacuna').val();
		let tip=$('#tip').val();
		console.log('brojRacuna', brojRacuna);
		console.log('tip', tip);
		let raspolozivoStanje=$('#raspolozivo').val();
		let online=$('#online').prop("checked");
		//let aktivan=$('#aktivan').val();
		let rezervisanoStanje=$('#rezervisano').val();
		
		console.log('online', online);

		
		$.post({
			url: 'rest/dodajRacun',
			data: JSON.stringify({brojRacuna, tip, raspolozivoStanje, rezervisanoStanje, online}),
			contentType: 'application/json',
			success: function() {
				alert('USPELO');
				window.location='./page.html';
				getRacune();
					
				
			},
			error: function() {
				alert('Pogresni pdoaci!');
			}
		});		
		
		
	});
	
	$('a[href="#odjava"]').click(function(){	 
	
	  $.get({
			url: 'rest/logout',
			success: function() {
				alert('izlogovali ste se!')
				window.location="./login.html";
				}
			
		});
	
	 
	
	}) 
	
	/////

	  $('#uplata').submit((event)=>{
		event.preventDefault();
		
		let brojRacuna=$('#select').val();
		let iznos=$('#iznos').val();
		console.log('brojRacuna', brojRacuna);
		console.log('iznos', iznos);
		
		let url = 'rest/uplata/';
		url += brojRacuna+','+iznos;

		$.get({
		
			url: url,
			contentType: 'application/json',
			success: function(racuni)
			{	
				window.location='./page.html';
				$('#racunitabela tbody').html('');
				console.log(racuni);
				
				for(let racun of racuni)
				{
					addRacuniTr(racun);
				}
			}
			
			
		});
		
		
	});

})

